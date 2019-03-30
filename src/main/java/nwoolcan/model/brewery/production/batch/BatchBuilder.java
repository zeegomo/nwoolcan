package nwoolcan.model.brewery.production.batch;


import nwoolcan.model.brewery.production.batch.misc.BeerDescription;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for building a {@link Batch}.
 */
public class BatchBuilder {

    private static final String MUST_BE_DISTINCT_INGREDIENTS_MESSAGE = "All ingredients added must be distinct.";

    private final BeerDescription beerDescription;
    private final BatchMethod batchMethod;
    private final Quantity initialSize;
    private final StepType initialStep;

    private Collection<Pair<IngredientArticle, Integer>> ingredients;

    @Nullable
    private WaterMeasurement waterMeasurement;

    /**
     * Constructor with mandatory parameters for a Batch.
     * @param beerDescription batch's beer description.
     * @param batchMethod batch method.
     * @param initialSize batch's initial size.
     * @param initialStep batch's initial step type.
     */
    public BatchBuilder(final BeerDescription beerDescription,
                        final BatchMethod batchMethod,
                        final Quantity initialSize,
                        final StepType initialStep) {
        this.beerDescription = beerDescription;
        this.batchMethod = batchMethod;
        this.initialSize = initialSize;
        this.initialStep = initialStep;
        this.ingredients = new ArrayList<>();
    }
    /**
     * Adds an ingredient to the batch.
     * The ingredients must be distinct, so you cannot add the same ingredients twice.
     * @param article the article representing the ingredient.
     * @param quantity the ingredient's quantity.
     * @return this.
     */
    public BatchBuilder addIngredient(final IngredientArticle article, final Integer quantity) {
        this.ingredients.add(Pair.of(article, quantity));
        return this;
    }
    /**
     * Set the water measurement of the batch.
     * @param waterMeasurement batch's water measurement.
     * @return this.
     */
    public BatchBuilder setWaterMeasurement(final WaterMeasurement waterMeasurement) {
        this.waterMeasurement = waterMeasurement;
        return this;
    }
    /**
     * Builds the batch and return a result containing that batch.
     * Contains an error of type:
     * <ul>
     *     <li>{@link IllegalStateException} if the ingredients are not distinct.</li>
     *     <li>{@link IllegalArgumentException} if the initial step type of the batch cannot be created.</li>
     * </ul>
     * @return a {@link Result} containing the built {@link Batch}.
     */
    public Result<Batch> build() {
        return Result.ofEmpty()
                     .require(() -> this.ingredients.stream().map(Pair::getKey).distinct().count() == this.ingredients.size(),
                         new IllegalStateException(MUST_BE_DISTINCT_INGREDIENTS_MESSAGE))
                     .flatMap(e -> Results.ofChecked(() -> new BatchImpl(
                         this.beerDescription,
                         this.batchMethod,
                         this.initialSize,
                         this.ingredients,
                         this.initialStep,
                         this.waterMeasurement
                     )));
    }
}
