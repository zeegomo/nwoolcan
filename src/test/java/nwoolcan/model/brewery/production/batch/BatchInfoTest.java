package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.misc.BeerDescription;
import nwoolcan.model.brewery.production.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;


/**
 * BatchInfo test.
 */
public class BatchInfoTest {

    private static final int TWO_THOUSAND = 2000;

    /**
     * Test construction.
     */
    @Test
    public void testConstructor() {
        final Collection<Pair<IngredientArticle, Double>> ingredients = new ArrayList<>();
        final BeerDescription desc = new BeerDescriptionImpl("test", "lager");
        ModifiableBatchInfo info = ModifiableBatchInfoFactory.create(ingredients, desc, BatchMethod.EXTRACT, Quantity.of(TWO_THOUSAND, UnitOfMeasure.MILLILITER).getValue());
        assertFalse(info.getAbv().isPresent());
        assertFalse(info.getWaterMeasurements().isPresent());
        assertEquals(info.getBeerDescription(), desc);
        assertEquals(info.getBatchSize(), Quantity.of(TWO_THOUSAND, UnitOfMeasure.MILLILITER).getValue());
        assertNotEquals(info.getBatchSize(), Quantity.of(2, UnitOfMeasure.UNIT).getValue());
    }
    /**
     * Test update.
     */
    @Test
    public void testUpdate() {
        final int og = 1050;
        final int fg = 1020;
        final Collection<Pair<IngredientArticle, Double>> ingredients = new ArrayList<>();
        final BeerDescription desc = new BeerDescriptionImpl("test", "lager");
        ModifiableBatchInfo info = ModifiableBatchInfoFactory.create(ingredients, desc, BatchMethod.EXTRACT, Quantity.of(TWO_THOUSAND, UnitOfMeasure.MILLILITER).getValue());

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
