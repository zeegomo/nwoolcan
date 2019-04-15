package nwoolcan.controller;

import nwoolcan.controller.warehouse.WarehouseController;
import nwoolcan.controller.warehouse.WarehouseControllerImpl;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.BatchBuilder;
import nwoolcan.model.brewery.batch.BatchMethod;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.model.brewery.batch.QueryBatchBuilder;
import nwoolcan.model.brewery.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.batch.misc.WaterMeasurementFactory;
import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.batch.step.parameter.ParameterFactory;
import nwoolcan.model.brewery.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.model.utils.Quantities;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.CreateBatchDTO;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.NewBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Controller} implementation.
 */
public final class BreweryController implements Controller {

    private final WarehouseController warehouseController;
    private final Brewery brewery;
    private static final String BATCH_NOT_FOUND = "Batch not found.";
    private static final String BEER_ARTICLE_NOT_FOUND = "Beer Article not found.";

    /**
     * Constructor which creates the {@link WarehouseController}.
     * @param brewery the brewery to be controlled.
     */
    public BreweryController(final Brewery brewery) {
        this.brewery = brewery;
        this.warehouseController = new WarehouseControllerImpl(brewery.getWarehouse());
    }

    @Override
    public WarehouseController getWarehouseController() {
        return warehouseController;
    }

    @Override
    public ProductionViewModel getProductionViewModel() {
        return new ProductionViewModel(this.brewery.getBatches(new QueryBatchBuilder().build().getValue()));
    }

    @Override
    public List<MasterBatchViewModel> getBatches(final QueryBatch query) {
        return Collections.unmodifiableList(this.brewery.getBatches(query).stream()
                                                        .map(MasterBatchViewModel::new)
                                                        .collect(Collectors.toList()));
    }

    @Override
    public NewBatchViewModel getNewBatchViewModel() {
        return new NewBatchViewModel(
            this.brewery.getWarehouse()
                        .getArticles(new QueryArticleBuilder().setIncludeArticleType(ArticleType.INGREDIENT).build())
                        .stream()
                        .map(a -> a.toIngredientArticle().getValue())
                        .map(IngredientArticleViewModel::new)
                        .collect(Collectors.toList()),
            Arrays.asList(WaterMeasurement.Element.values()),
            Arrays.asList(BatchMethod.values()),
            Quantities.getValidUnitsOfMeasure()
        );
    }

    @Override
    public Result<Empty> createNewBatch(final CreateBatchDTO batchDTO) {
        //create batch builder
        final BatchBuilder bBuilder = this.brewery.getBatchBuilder();

        //take all ingredients from warehouse that have ids equals to the ones in batchDTO
        this.brewery.getWarehouse().getArticles(
            new QueryArticleBuilder().setIncludeArticleType(ArticleType.INGREDIENT).build())
                    .stream()
                    .filter(a -> batchDTO.getIngredients()
                                         .stream()
                                         .map(Pair::getLeft)
                                         .anyMatch(i -> a.getId() == i))
                    .map(a -> Pair.of(a.toIngredientArticle().getValue(),
                        batchDTO.getIngredients()
                                .stream()
                                .filter(p -> p.getLeft() == a.getId())
                                .map(Pair::getRight).findAny().get()))
                    //add all ingredients to the batch builder
                    .forEach(p -> bBuilder.addIngredient(p.getLeft(), p.getRight()));

        Result<Empty> res = Result.ofEmpty();

        //if there is at least one measurement, build it and set it
        if (batchDTO.getWaterMeasurement().size() > 0) {
            res = batchDTO.getWaterMeasurement()
                          .stream()
                          //map to correct representations
                          .map(t -> Pair.of(
                              t.getLeft(),
                              ParameterFactory.create(
                                  ParameterTypeEnum.WATER_MEASUREMENT,
                                  t.getMiddle(),
                                  t.getRight())))
                          //propagate error of parameter construction out of pair
                          .map(reg -> Result.of(reg)
                                            .flatMap(r -> r.getRight()
                                                           .map(rr -> Pair.of(r.getLeft(), rr))))
                          //propagate errors in reduction from collection
                          .reduce(Result.of(new ArrayList<Pair<WaterMeasurement.Element, Parameter>>()),
                              (acc, r) -> acc.flatMap(col -> r.map(rr -> {
                                  col.add(rr);
                                  return col;
                              })),
                              (r1, r2) -> r1.flatMap(col1 -> r2.peek(col2 -> col2.addAll(col1))))
                          .flatMap(WaterMeasurementFactory::create)
                          .peek(bBuilder::setWaterMeasurement)
                          .toEmpty();
        }

        //finally build the batch and add it to the brewery
        return res.flatMap(e -> bBuilder.build(
            new BeerDescriptionImpl(batchDTO.getName(), batchDTO.getStyle(), batchDTO.getCategory().orElse(null)),
            batchDTO.getMethod(),
            batchDTO.getIntialSize(),
            batchDTO.getInitialStep()))
                  .peek(this.brewery::addBatch)
                  .toEmpty();
    }

    @Override
    public Result<Empty> stockBatch(final int batchId, final int beerArticleId, final Date expirationDate) {
        final Controllers utils = new Controllers(this.brewery);
        final Result<Batch> batchResult = utils.getBatchById(batchId);
        final Result<BeerArticle> beerArticleResult = utils.getBeerArticleById(beerArticleId);

        if (batchResult.isPresent() && beerArticleResult.isPresent()) {
            return Result.of(this.brewery.stockBatch(
                batchResult.getValue(),
                beerArticleResult.getValue(),
                expirationDate
            )).toEmpty();
        }

        return batchResult.flatMap(b -> beerArticleResult).toEmpty();
    }

    @Override
    public Result<Empty> stockBatch(final int batchId, final int beerArticleId) {
        final Controllers utils = new Controllers(this.brewery);
        final Result<Batch> batchResult = utils.getBatchById(batchId);
        final Result<BeerArticle> beerArticleResult = utils.getBeerArticleById(beerArticleId);

        if (batchResult.isPresent() && beerArticleResult.isPresent()) {
            return Result.of(this.brewery.stockBatch(
                batchResult.getValue(),
                beerArticleResult.getValue()
            )).toEmpty();
        }

        return batchResult.flatMap(b -> beerArticleResult).toEmpty();
    }

    @Override
    public void setBreweryName(final String breweryName) {
        brewery.setBreweryName(breweryName);
    }

    @Override
    public void setOwnerName(final String ownerName) {
        brewery.setOwnerName(ownerName);
    }
}
