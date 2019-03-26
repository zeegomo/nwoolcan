package nwoolcan.model.brewery.production.batch.step.utils;

import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.production.batch.step.Steps;
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
