package nwoolcan.model.brewery.batch.step.utils;

import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.batch.step.Steps;
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
        Result<Step> res = Steps.create(StepTypeEnum.AGING);
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(StepTypeEnum.AGING, res.getValue().getStepInfo().getType());

        Date d = new Date();
        res = Steps.create(StepTypeEnum.MASHING, d);
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(StepTypeEnum.MASHING, res.getValue().getStepInfo().getType());
        Assert.assertEquals(d, res.getValue().getStepInfo().getStartDate());
    }

    /**
     * Test wrong step creation.
     */
    @Test
    public void testWrongCreation() {
        Result<Step> res = Steps.create(new StepType() {
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
