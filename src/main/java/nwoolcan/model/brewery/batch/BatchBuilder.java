package nwoolcan.model.brewery.batch;

import nwoolcan.model.brewery.IdGenerator;
import nwoolcan.model.brewery.batch.misc.BeerDescription;
import nwoolcan.model.brewery.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.batch.step.BasicStepFactory;
import nwoolcan.model.brewery.batch.step.StepType;
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

    private final IdGenerator generator;

    private Collection<Pair<IngredientArticle, Double>> ingredients;

    @Nullable
    private WaterMeasurement waterMeasurement;

    /**
     * Constructor with mandatory parameters for a Batch.
     * @param generator the id generator to use for generating the batch.
     */
    public BatchBuilder(final IdGenerator generator) {
        this.ingredients = new ArrayList<>();
        this.generator = generator;
    }
    /**
     * Adds an ingredient to the batch.
     * The ingredients must be distinct, so you cannot add the same ingredients twice.
     * @param article the article representing the ingredient.
     * @param quantity the ingredient's quantity.
     * @return this.
     */
    public BatchBuilder addIngredient(final IngredientArticle article, final double quantity) {
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
     * @param beerDescription the batch's beer description.
     * @param batchMethod the batch method.
     * @param initialSize the batch's initial size.
     * @param initialStep the batch's initial step.
     * @return a {@link Result} containing the built {@link Batch}.
     */
    public Result<Batch> build(final BeerDescription beerDescription,
                               final BatchMethod batchMethod,
                               final Quantity initialSize,
                               final StepType initialStep) {
        return Result.ofEmpty()
                     .require(() -> this.ingredients.stream().map(Pair::getKey).distinct().count() == this.ingredients.size(),
                         new IllegalStateException(MUST_BE_DISTINCT_INGREDIENTS_MESSAGE))
                     .flatMap(e -> Results.ofChecked(() -> new BatchImpl(
                         beerDescription,
                         batchMethod,
                         initialSize,
                         this.ingredients,
                         initialStep,
                         this.waterMeasurement,
                         this.generator,
                         new BasicStepFactory()
                     )));
    }
}
