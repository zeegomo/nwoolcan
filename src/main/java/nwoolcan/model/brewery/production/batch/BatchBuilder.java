package nwoolcan.model.brewery.production.batch;

import javafx.util.Pair;
import nwoolcan.model.brewery.production.batch.misc.BeerDescription;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class BatchBuilder {

    private static final String MUST_BE_DISTINCT_INGREDIENTS_MESSAGE = "All ingredients added must be distinct.";
    private static final String INCONSISTENT_INGREDIENT_WITH_QUANTITY_MESSAGE = "All ingredients' unit of measure must be same as their quantity unit of measure.";

    private final BeerDescription beerDescription;
    private final BatchMethod batchMethod;
    private final Quantity initialSize;
    private final StepType initialStep;

    private Collection<Pair<IngredientArticle, Quantity>> ingredients;
    private boolean isSameUnitOfMeasure;

    @Nullable
    private WaterMeasurement waterMeasurement;

    public BatchBuilder(final BeerDescription beerDescription,
                        final BatchMethod batchMethod,
                        final Quantity initialSize,
                        final StepType initialStep) {
        this.beerDescription = beerDescription;
        this.batchMethod = batchMethod;
        this.initialSize = initialSize;
        this.initialStep = initialStep;
        this.ingredients = new ArrayList<>();
        this.isSameUnitOfMeasure = true;
    }

    public BatchBuilder addIngredient(final IngredientArticle article, final Quantity quantity) {
        if (article.getUnitOfMeasure().equals(quantity.getUnitOfMeasure())) {
            this.ingredients.add(new Pair<>(article, quantity));
        } else {
            this.isSameUnitOfMeasure = false;
        }
        return this;
    }

    public BatchBuilder setWaterMeasurement(final WaterMeasurement waterMeasurement) {
        this.waterMeasurement = waterMeasurement;
        return this;
    }

    public Result<Batch> build() {
        return Result.ofEmpty()
                     .require(() -> this.ingredients.stream().map(Pair::getKey).distinct().count() == this.ingredients.size(),
                         new IllegalStateException(MUST_BE_DISTINCT_INGREDIENTS_MESSAGE))
                     .require(() -> this.isSameUnitOfMeasure,
                         new IllegalArgumentException(INCONSISTENT_INGREDIENT_WITH_QUANTITY_MESSAGE))
                     .flatMap(e -> {
                         return Results.ofChecked(() -> new BatchImpl(
                             this.beerDescription,
                             this.batchMethod,
                             this.initialSize,
                             this.ingredients,
                             this.initialStep,
                             this.waterMeasurement
                         ));
                     });
    }
}
