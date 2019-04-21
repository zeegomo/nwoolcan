package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.batch.BatchMethod;
import nwoolcan.model.brewery.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.utils.Quantity;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class representing a DTO to use to create a new batch.
 */
public class CreateBatchDTO {

    private final String name;
    private final String style;
    @Nullable
    private final String category;
    private final BatchMethod method;
    private final Quantity initialSize;
    private final StepType initialStep;

    private final List<Pair<Integer, Double>> ingredients;
    private final List<Triple<WaterMeasurement.Element, Double, Date>> waterMeasurement;

    /**
     * Basic constructor.
     * @param name beer name.
     * @param style style name.
     * @param category category.
     * @param method batch method.
     * @param initialSize initial size quantity.
     * @param initialStep initial step type.
     * @param ingredients list of pair of ingredient article id and double quantity.
     * @param waterMeasurement list of triple of element, double quantity and registration date.
     */
    public CreateBatchDTO(final String name,
                          final String style,
                          @Nullable final String category,
                          final BatchMethod method,
                          final Quantity initialSize,
                          final StepType initialStep,
                          final List<Pair<Integer, Double>> ingredients,
                          final List<Triple<WaterMeasurement.Element, Double, Date>> waterMeasurement) {
        this.name = name;
        this.style = style;
        this.category = category;
        this.method = method;
        this.initialSize = initialSize;
        this.initialStep = initialStep;
        this.ingredients = Collections.unmodifiableList(ingredients);
        this.waterMeasurement = Collections.unmodifiableList(waterMeasurement);
    }

    /**
     * Returns the beer name.
     * @return the beer name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the style.
     * @return the style.
     */
    public String getStyle() {
        return this.style;
    }

    /**
     * Returns the optional category.
     * @return the optional category.
     */
    public Optional<String> getCategory() {
        return Optional.ofNullable(this.category);
    }

    /**
     * Returns the batch method.
     * @return the batch method.
     */
    public BatchMethod getMethod() {
        return this.method;
    }

    /**
     * Returns the initial quantity size.
     * @return the initial quantity size.
     */
    public Quantity getInitialSize() {
        return this.initialSize;
    }

    /**
     * Returns the initial step type.
     * @return the initial step type.
     */
    public StepType getInitialStep() {
        return this.initialStep;
    }

    /**
     * Returns the list of ingredients, represented as pair of ingredient article id and double quantity.
     * @return the list of ingredients.
     */
    public List<Pair<Integer, Double>> getIngredients() {
        return Collections.unmodifiableList(this.ingredients);
    }

    /**
     * Returns the list of water measurement registrations, represented as triples of
     * element, double quantity and registration date.
     * @return the list of water measurement registrations.
     */
    public List<Triple<WaterMeasurement.Element, Double, Date>> getWaterMeasurement() {
        return Collections.unmodifiableList(waterMeasurement);
    }

}
