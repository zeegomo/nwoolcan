package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.batch.BatchInfo;
import nwoolcan.model.brewery.batch.misc.WaterMeasurement;
import nwoolcan.viewmodel.brewery.production.step.ParameterViewModel;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * batch info view model.
 */
public class BatchInfoViewModel {

    private final String beerStyle;
    private final String beerName;
    @Nullable
    private final String beerStyleCategory;
    private final String batchMethod;
    private final QuantityViewModel batchSize;
    @Nullable
    private final ParameterViewModel og;
    @Nullable
    private final ParameterViewModel fg;
    @Nullable
    private final ParameterViewModel ebc;
    @Nullable
    private final ParameterViewModel abv;
    @Nullable
    private final ParameterViewModel ibu;
    @Nullable
    private final List<ParameterViewModel> waterMeasurements;

    private final List<Pair<IngredientArticleViewModel, Double>> ingredients;

    public BatchInfoViewModel(final BatchInfo data) {
        this.beerStyle = data.getBeerDescription().getStyle();
        this.beerName = data.getBeerDescription().getName();
        this.beerStyleCategory = data.getBeerDescription().getStyleCategory().orElse(null);
        this.batchMethod = data.getMethod().getName();
        this.batchSize = new QuantityViewModel(data.getBatchSize());
        this.ingredients = data.listIngredients()
                               .stream()
                               .map(pair -> Pair.of(new IngredientArticleViewModel(pair.getLeft()), pair.getRight()))
                               .collect(Collectors.toList());
        this.waterMeasurements = data.getWaterMeasurements().map(mes -> Arrays.stream(WaterMeasurement.Element.values())
                                                                              .map(mes::getMeasurement)
                                                                              .filter(Optional::isPresent)
                                                                              .map(Optional::get)
                                                                              .map(ParameterViewModel::new)
                                                                              .collect(Collectors.toList()))
                                     .orElse(null);
        this.abv = data.getAbv().map(ParameterViewModel::new).orElse(null);
        this.ebc = data.getEbc().map(ParameterViewModel::new).orElse(null);
        this.ibu = data.getIbu().map(ParameterViewModel::new).orElse(null);
        this.fg = data.getFg().map(ParameterViewModel::new).orElse(null);
        this.og = data.getOg().map(ParameterViewModel::new).orElse(null);
    }

    /**
     * Returns beer name.
     * @return beer name.
     */
    public String getBeerName() {
        return this.beerName;
    }
    /**
     * Returns beer style category, if available.
     * @return beer style category, if available.
     */
    public Optional<String> getBeerStyleCategory() {
        return Optional.ofNullable(beerStyleCategory);
    }
    /**
     * Return the string representation of the batch method.
     * @return the string representation of the batch method.
     */
    public String getBatchMethod() {
        return this.batchMethod;
    }
    /**
     * Return the initial size of the batch.
     * @return the initial size of the batch.
     */
    public QuantityViewModel getBatchSize() {
        return this.batchSize;
    }
    /**
     * Returns the ingredients of this batch.
     * @return the ingredients of this batch.
     */
    public List<Pair<IngredientArticleViewModel, Double>> getIngredients() {
        return this.ingredients;
    }
    /**
     * Returns the style of the beer.
     * @return the style of the beer.
     */
    public String getBeerStyle() {
        return this.beerStyle;
    }
}
