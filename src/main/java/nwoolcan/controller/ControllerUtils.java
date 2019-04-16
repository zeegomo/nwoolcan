package nwoolcan.controller;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.model.brewery.batch.QueryBatchBuilder;
import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.utils.Result;

import java.util.Optional;

/**
 * Utils class for controllers.
 */
public final class ControllerUtils {
    private static final String BATCH_NOT_FOUND = "Batch not found.";
    private static final String BEER_ARTICLE_NOT_FOUND = "Beer Article not found.";
    private static final String NO_STEP_FOUND_MESSAGE = "Cannot find step with type name: ";

    private final Brewery brewery;

    /**
     * Basic constructor.
     * @param brewery the brewery to use as model.
     */
    public ControllerUtils(final Brewery brewery) {
        this.brewery = brewery;
    }

    /**
     * Returns a {@link Batch} with the specified id.
     * @param batchId the batch id.
     * @return a {@link Result} bearing the found batch or an error if no batch is found.
     */
    public Result<Batch> getBatchById(final int batchId) {
        final QueryBatch querySingleBatch = new QueryBatchBuilder().setMinId(batchId)
                                                                   .setMaxId(batchId)
                                                                   .build().getValue();
        return Result.of(brewery.getBatches(querySingleBatch))
                     .map(batches -> batches.stream().findAny())
                     .require(Optional::isPresent,
                         new IllegalArgumentException(BATCH_NOT_FOUND))
                     .map(Optional::get);
    }

    /**
     * Returns a {@link BeerArticle} with the specified id.
     * @param beerArticleId the beer article id.
     * @return a {@link Result} bearing the found beer article or an error if no beer article is found.
     */
    public Result<BeerArticle> getBeerArticleById(final int beerArticleId) {
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

    /**
     * Returns a step identified by its batch id and step type name.
     * @param batchId the batch id.
     * @param stepTypeName the step type name.
     * @return a possible step found.
     */
    public Result<Step> getStepByBatchIdAndStepTypeName(final int batchId, final String stepTypeName) {
        return this.getBatchById(batchId)
                   .map(b -> b.getSteps()
                              .stream()
                              .filter(s -> s.getStepInfo().getType().getName().equals(stepTypeName))
                              .findAny())
                   .require(Optional::isPresent, new IllegalStateException(NO_STEP_FOUND_MESSAGE + stepTypeName))
                   .map(Optional::get);
    }
}
