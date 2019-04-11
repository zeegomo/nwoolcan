package nwoolcan.controller;

import nwoolcan.controller.brewery.BreweryController;
import nwoolcan.controller.brewery.BreweryControllerImpl;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.production.batch.BatchBuilder;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.production.batch.QueryBatchBuilder;
import nwoolcan.model.brewery.production.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurementFactory;
import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterFactory;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.CreateBatchDTO;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller basic implementation.
 */
public final class ControllerImpl implements Controller {

    private final Brewery brewery;
    private final BreweryController breweryController;

    /**
     * Construct a controller and inject the model ({@link Brewery}) to the subcontrollers.
     * @param model the model to inject,
     */
    public ControllerImpl(final Brewery model) {
        this.brewery = model;
        this.breweryController = new BreweryControllerImpl(model);
    }

    @Override
    public BreweryController getBreweryController() {
        return this.breweryController;
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
                          .reduce(Result.of(Collections.<Pair<WaterMeasurement.Element, Parameter>>emptyList()),
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


}
