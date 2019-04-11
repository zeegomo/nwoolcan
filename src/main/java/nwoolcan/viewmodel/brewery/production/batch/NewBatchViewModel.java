package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;

import java.util.Collection;
import java.util.Collections;

/**
 * View model representing the data needed to show the new batch modal.
 */
public class NewBatchViewModel {

    private final Collection<IngredientArticleViewModel> ingredients;
    private final Collection<WaterMeasurement.Element> waterMeasurementElements;

    /**
     * Basic constructor.
     * @param ingredients all possible ingredients that can be chosen.
     * @param waterMeasurementElements all possible elements that can be chosen.
     */
    public NewBatchViewModel(final Collection<IngredientArticleViewModel> ingredients,
                             final Collection<WaterMeasurement.Element> waterMeasurementElements) {
        this.ingredients = Collections.unmodifiableCollection(ingredients);
        this.waterMeasurementElements = Collections.unmodifiableCollection(waterMeasurementElements);
    }

    /**
     * Returns a collection of all possible ingredients to use for batch creation.
     * @return all possible ingredients that can be chosen.
     */
    public Collection<IngredientArticleViewModel> getIngredients() {
        return Collections.unmodifiableCollection(ingredients);
    }

    /**
     * Returns a collection of all possible elements to use for batch creation.
     * @return all possible elements that can be chosen.
     */
    public Collection<WaterMeasurement.Element> getWaterMeasurementElements() {
        return Collections.unmodifiableCollection(waterMeasurementElements);
    }
}
