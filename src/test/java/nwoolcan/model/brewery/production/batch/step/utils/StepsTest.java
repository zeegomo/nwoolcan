package nwoolcan.model.brewery.production.batch.step.utils;

import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.utils.Result;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for Steps util.
 */
public class StepsTest {

    /**
     * Test simple step creation.
     */
    @Test
    public void testSimpleCreation() {
        Result<Step> res = Steps.create(StepTypeEnum.Aging);
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(StepTypeEnum.Aging, res.getValue().getStepInfo().getType());

        Date d = new Date();
        res = Steps.create(StepTypeEnum.Mashing, d);
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(StepTypeEnum.Mashing, res.getValue().getStepInfo().getType());
        Assert.assertEquals(d, res.getValue().getStepInfo().getStartDate());
    }

    /**
     * Test wrong step creation.
     */
    @Test
    public void testWrongCreation() {
        Result<Step> res = Steps.create(null);
        Assert.assertTrue(res.isError());
        Assert.assertSame(res.getError().getClass(), NullPointerException.class);

        res = Steps.create(StepTypeEnum.Mashing, null);
        Assert.assertTrue(res.isError());
        Assert.assertSame(res.getError().getClass(), NullPointerException.class);

        res = Steps.create(() -> "Wrong");
        Assert.assertTrue(res.isError());
        Assert.assertSame(res.getError().getClass(), IllegalArgumentException.class);
    }
}
