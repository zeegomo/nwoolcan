package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.batch.BatchMethod;
import nwoolcan.model.brewery.batch.misc.WaterMeasurement;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;

import java.util.Collections;
import java.util.List;

/**
 * View model representing the data needed to show the new batch modal.
 */
public final class NewBatchViewModel {

    private final List<IngredientArticleViewModel> ingredients;
    private final List<WaterMeasurement.Element> waterMeasurementElements;
    private final List<BatchMethod> batchMethods;
    private final List<UnitOfMeasure> unitsOfMeasure;

    /**
     * Basic constructor.
     * @param ingredients all possible ingredients that can be chosen.
     * @param waterMeasurementElements all possible elements that can be chosen.
     * @param batchMethods all possible batch methods that can be chosen.
     * @param unitsOfMeasure all possible unit of measure to build a quantity.
     */
    public NewBatchViewModel(final List<IngredientArticleViewModel> ingredients,
                             final List<WaterMeasurement.Element> waterMeasurementElements,
                             final List<BatchMethod> batchMethods,
                             final List<UnitOfMeasure> unitsOfMeasure) {
        this.ingredients = Collections.unmodifiableList(ingredients);
        this.waterMeasurementElements = Collections.unmodifiableList(waterMeasurementElements);
        this.batchMethods = Collections.unmodifiableList(batchMethods);
        this.unitsOfMeasure = Collections.unmodifiableList(unitsOfMeasure);
    }

    /**
     * Returns a list of all possible ingredients to use for batch creation.
     * @return all possible ingredients that can be chosen.
     */
    public List<IngredientArticleViewModel> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    /**
     * Returns a list of all possible elements to use for batch creation.
     * @return all possible elements that can be chosen.
     */
    public List<WaterMeasurement.Element> getWaterMeasurementElements() {
        return Collections.unmodifiableList(waterMeasurementElements);
    }

    /**
     * Returns a list of all possible batch methods to use for batch creation.
     * @return all possible batch methods that can be chosen.
     */
    public List<BatchMethod> getBatchMethods() {
        return Collections.unmodifiableList(batchMethods);
    }

    /**
     * Returns a list of all possible units of measure to use for quantity creation.
     * @return all possible units of measure that can be chosen.
     */
    public List<UnitOfMeasure> getUnitsOfMeasure() {
        return Collections.unmodifiableList(unitsOfMeasure);
    }
}
