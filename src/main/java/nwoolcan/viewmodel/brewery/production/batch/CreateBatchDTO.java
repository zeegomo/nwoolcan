package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.BatchMethod;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CreateBatchDTO {

    private final String name;
    private final String style;
    @Nullable
    private final String category;
    private final BatchMethod method;
    private final Quantity intialSize;
    private final StepType initialStep;

    private final List<Pair<Integer, Double>> ingredients;
    private final List<Triple<WaterMeasurement.Element, Double, Date>> waterMeasurement;

    public CreateBatchDTO(final String name,
                          final String style,
                          @Nullable final String category,
                          final BatchMethod method,
                          final Quantity intialSize,
                          final StepType initialStep,
                          final List<Pair<Integer, Double>> ingredients,
                          final List<Triple<WaterMeasurement.Element, Double, Date>> waterMeasurement) {
        this.name = name;
        this.style = style;
        this.category = category;
        this.method = method;
        this.intialSize = intialSize;
        this.initialStep = initialStep;
        this.ingredients = Collections.unmodifiableList(ingredients);
        this.waterMeasurement = Collections.unmodifiableList(waterMeasurement);
    }

    public String getName() {
        return this.name;
    }

    public String getStyle() {
        return this.style;
    }

    public Optional<String> getCategory() {
        return Optional.ofNullable(this.category);
    }

    public BatchMethod getMethod() {
        return this.method;
    }

    public Quantity getIntialSize() {
        return this.intialSize;
    }

    public StepType getInitialStep() {
        return this.initialStep;
    }

    public List<Pair<Integer, Double>> getIngredients() {
        return Collections.unmodifiableList(this.ingredients);
    }

    public List<Triple<WaterMeasurement.Element, Double, Date>> getWaterMeasurement() {
        return Collections.unmodifiableList(waterMeasurement);
    }

}
