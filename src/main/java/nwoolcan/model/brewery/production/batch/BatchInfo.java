package nwoolcan.model.brewery.production.batch;

import javafx.util.Pair;
import nwoolcan.model.brewery.production.batch.misc.BeerDescription;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;

import java.util.Collection;
import java.util.Optional;

/**
 * BatchInfo (Immutable).
 */
public interface BatchInfo {
    /**
     * Return a {@link BeerDescription}.
     * @return the {@link BeerDescription}.
     */
    BeerDescription getBeerDescription();
    /**
     * Return the method used for this batch.
     * @return a {@link BatchMethod}.
     */
    BatchMethod getMethod();
    /**
     * Return the size create this batch.
     * @return a {@link Quantity}.
     */
    Quantity getBatchSize();
    /**
     * Return the original gravity for this batch, if available.
     * @return a {@link Quantity}.
     */
    Optional<Parameter> getOg();
    /**
     * Return the final gravity for this batch, if available.
     * @return a {@link Quantity}.
     */
    Optional<Parameter> getFg();
    /**
     * Return the color measurements create this batch, if available.
     * @return a {@link Quantity}.
     */
    Optional<Parameter> getEbc();
    /**
     * Return the alcohol by volume create this batch, if available.
     * @return a {@link Quantity}.
     */
    Optional<Parameter> getAbv();
    /**
     * Return the bitterness create this batch, if available.
     * @return a {@link Quantity}.
     */
    Optional<Parameter> getIbu();
    /**
     * Return the water measurements create this batch, if available.
     * @return a {@link Quantity}.
     */
    Optional<WaterMeasurement> getWaterMeasurements();
    /**
     * Return the ingredients used in this batch.
     * @return a {@link Collection}.
     */
    Collection<Pair<IngredientArticle, Quantity>> listIngredients();
}
