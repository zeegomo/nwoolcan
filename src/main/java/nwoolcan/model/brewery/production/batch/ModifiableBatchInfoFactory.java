package nwoolcan.model.brewery.production.batch;

import javafx.util.Pair;
import nwoolcan.model.brewery.production.batch.misc.BeerDescription;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;

import java.util.Collection;

/**
 * Constructs a {@link ModifiableBatchInfo}.
 */
//Package private
final class ModifiableBatchInfoFactory {

    private ModifiableBatchInfoFactory() { }

    static ModifiableBatchInfo of(final Collection<Pair<IngredientArticle, Quantity>> ingredients,
                           final BeerDescription beerDescription,
                           final BatchMethod method,
                           final Quantity size,
                           final WaterMeasurement measurement) {
        return new ModifiableBatchInfoImpl(ingredients, beerDescription, method, size, measurement);
    }

    static ModifiableBatchInfo of(final Collection<Pair<IngredientArticle, Quantity>> ingredients,
                           final BeerDescription beerDescription,
                           final BatchMethod method,
                           final Quantity size) {
        return new ModifiableBatchInfoImpl(ingredients, beerDescription, method, size, null);
    }

}
