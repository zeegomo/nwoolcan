package nwoolcan.model.brewery.production.batch;

import javafx.util.Pair;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * BatchInfo test.
 */
public class BatchInfoTest {
    /**
     * Test construction.
     */
    @Test
    public void testConstructor() {
        final Collection<Pair<IngredientArticle, Quantity>> ingredients = new ArrayList<>();
        final BeerDescription desc = new BeerDescriptionImpl("test", "lager");
        ModifiableBatchInfo info = new ModifiableBatchInfoImpl(ingredients, desc, BatchMethod.EXTRACT, Quantity.of(2, UnitOfMeasure.Liter));
        assertFalse(info.getAbv().isPresent());
        assertFalse(info.getWaterMeasurements().isPresent());
        assertEquals(info.getBeerDescription(), desc);
        assertEquals(info.getBatchSize(), Quantity.of(2, UnitOfMeasure.Liter));
        assertNotEquals(info.getBatchSize(), Quantity.of(2, UnitOfMeasure.Unit));
    }
    /**
     * Test update.
     */
    @Test
    public void testUpdate() {
        final int og = 1050;
        final int fg = 1020;
        final Collection<Pair<IngredientArticle, Quantity>> ingredients = new ArrayList<>();
        final BeerDescription desc = new BeerDescriptionImpl("test", "lager");
        ModifiableBatchInfo info = new ModifiableBatchInfoImpl(ingredients, desc, BatchMethod.EXTRACT, Quantity.of(2, UnitOfMeasure.Liter));

        info.update(new ParameterImpl(ParameterTypeEnum.GRAVITY, og));
        assertEquals(info.getOg().get().getRegistrationValue(), og);
        assertFalse(info.getFg().isPresent());

        info.update(new ParameterImpl(ParameterTypeEnum.GRAVITY, fg));
        assertEquals(info.getFg().get().getRegistrationValue(), fg);
        assertEquals(info.getOg().get().getRegistrationValue(), og);
        assertEquals(info.getOg().get().getType(), ParameterTypeEnum.GRAVITY);
        assertEquals(info.getFg().get().getType(), ParameterTypeEnum.GRAVITY);

        info.update(new ParameterImpl(ParameterTypeEnum.ABV, 2));
        assertEquals(info.getAbv().get().getType(), ParameterTypeEnum.ABV);
        assertEquals(info.getAbv().get().getRegistrationValue(), 2);

    }
}