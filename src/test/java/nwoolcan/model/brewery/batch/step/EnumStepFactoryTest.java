package nwoolcan.model.brewery.batch.step;

import nwoolcan.utils.Result;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for factory util.
 */
public class EnumStepFactoryTest {

    private final StepFactory factory = new EnumStepFactory();

    /**
     * Test simple step creation.
     */
    @Test
    public void testSimpleCreation() {
        Result<Step> res = factory.create(StepTypeEnum.AGING);
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(StepTypeEnum.AGING, res.getValue().getStepInfo().getType());

        Date d = new Date();
        res = factory.create(StepTypeEnum.MASHING, d);
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(StepTypeEnum.MASHING, res.getValue().getStepInfo().getType());
        Assert.assertEquals(d, res.getValue().getStepInfo().getStartDate());
    }

    /**
     * Test wrong step creation.
     */
    @Test
    public void testWrongCreation() {
        Result<Step> res = factory.create(new StepType() {
            @Override
            public String getName() {
                return "test";
            }

            @Override
            public boolean isEndType() {
                return false;
            }
        });
        Assert.assertTrue(res.isError());
        Assert.assertSame(res.getError().getClass(), IllegalArgumentException.class);
    }
}
