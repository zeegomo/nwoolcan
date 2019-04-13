package nwoolcan.controller;

import nwoolcan.controller.warehouse.WarehouseController;
import nwoolcan.controller.warehouse.WarehouseControllerImpl;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.model.brewery.batch.QueryBatchBuilder;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

import java.util.Collection;
import java.util.Date;
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
    public Collection<MasterBatchViewModel> getBatches(final QueryBatch queryBatch) {
        return brewery.getBatches(queryBatch)
                      .stream()
                      .map(MasterBatchViewModel::new)
                      .collect(Collectors.toList());
    }

    @Override
    public Result<Empty> stockBatch(final int batchId, final int beerArticleId, final Date expirationDate) {
        final Result<Batch> batchResult = getBatchById(batchId);
        final Result<BeerArticle> beerArticleResult = getBeerArticleById(beerArticleId);
        return Result.ofEmpty()
                     .require(batchResult::isPresent, batchResult.getError())
                     .require(beerArticleResult::isPresent, beerArticleResult.getError())
                     .flatMap(() -> brewery.stockBatch(batchResult.getValue(),
                                                       beerArticleResult.getValue(),
                                                       expirationDate));
    }

    @Override
    public Result<Empty> stockBatch(final int batchId, final int beerArticleId) {
        final Result<Batch> batchResult = getBatchById(batchId);
        final Result<BeerArticle> beerArticleResult = getBeerArticleById(beerArticleId);
        return Result.ofEmpty()
                     .require(batchResult::isPresent, batchResult.getError())
                     .require(beerArticleResult::isPresent, beerArticleResult.getError())
                     .flatMap(() -> brewery.stockBatch(batchResult.getValue(),
                                                       beerArticleResult.getValue()));
    }

    @Override
    public void setBreweryName(final String breweryName) {
        brewery.setBreweryName(breweryName);
    }

    @Override
    public void setOwnerName(final String ownerName) {
        brewery.setOwnerName(ownerName);
    }

    @Override
    public WarehouseController getWarehouseController() {
        return warehouseController;
    }

    private Result<Batch> getBatchById(final int batchId) {
        final QueryBatch querySingleBatch = new QueryBatchBuilder().setMinId(batchId)
                                                                   .setMaxId(batchId)
                                                                   .build().getValue();
        return Result.of(brewery.getBatches(querySingleBatch))
                     .require(batches -> batches.size() == 1,
                              new IllegalArgumentException(BATCH_NOT_FOUND))
                     .map(batches -> batches.iterator().next());
    }

    private Result<BeerArticle> getBeerArticleById(final int beerArticleId) {
        final QueryArticle querySingleBeerArticle = new QueryArticleBuilder().setMinID(beerArticleId)
                                                                             .setMaxID(beerArticleId)
                                                                             .build();
        return Result.of(brewery.getWarehouse().getArticles(querySingleBeerArticle))
                     .require(articles -> articles.size() == 1,
                              new IllegalArgumentException(BEER_ARTICLE_NOT_FOUND))
                     .map(articles -> articles.get(0))
                     .require(article -> article.getArticleType() == ArticleType.FINISHED_BEER)
                     .map(article -> (BeerArticle) article);
    }
}
