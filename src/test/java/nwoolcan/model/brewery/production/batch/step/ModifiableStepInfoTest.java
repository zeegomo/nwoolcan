package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.brewery.production.batch.step.info.ModifiableStepInfo;
import nwoolcan.model.brewery.production.batch.step.info.ModifiableStepInfoImpl;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for {@link ModifiableStepInfoImpl}.
 */
public class ModifiableStepInfoTest {

    private static final StepType ST1 = StepTypeEnum.Mashing;
    private static final Date NOW = new Date();
    private static final Date PAST = new Date(0);
    private static final Date FUTURE = new Date(NOW.getTime() + 1000);
    private static final String NOTE1 = "Test note.";
    private static final Quantity Q1 = Quantity.of(10, UnitOfMeasure.Liter);
    private static final Quantity Q2 = Quantity.of(20, UnitOfMeasure.Liter);

    private ModifiableStepInfo si;

    /**
     * Sets up fields.
     */
    @Before
    public void setUp() {
        this.si = new ModifiableStepInfoImpl(ST1, NOW);
    }

    private void populate() {
        this.si.setNote(NOTE1);
        this.si.setEndDate(FUTURE);
        this.si.setEndStepSize(Q1);
    }

    /**
     * Simple construction and verification of getters.
     */
    @Test
    public void simpleConstructionTest() {
        Assert.assertEquals(ST1, this.si.getType());
        Assert.assertEquals(NOW, this.si.getStartDate());

        Assert.assertFalse(this.si.getNote().isPresent());
        Assert.assertFalse(this.si.getEndDate().isPresent());
        Assert.assertFalse(this.si.getEndStepSize().isPresent());
    }

    /**
     * Complete construction and verification of getters.
     */
    @Test
    public void completeConstructionTest() {
        populate();

        Assert.assertTrue(this.si.getNote().isPresent());
        Assert.assertTrue(this.si.getEndDate().isPresent());
        Assert.assertTrue(this.si.getEndStepSize().isPresent());

        Assert.assertEquals(NOTE1, this.si.getNote().orElse(""));
        Assert.assertEquals(FUTURE, this.si.getEndDate().orElse(PAST));
        Assert.assertEquals(Q1, this.si.getEndStepSize().orElse(Q2));
    }

    /**
     * Checking correct encapsulation protection for modifiable types such as Dates.
     */
    @Test
    public void encapsulationTest() {
        populate();

        //checking the only types that are modifiable
        this.si.getStartDate().setTime(0);
        Assert.assertEquals(NOW, this.si.getStartDate());

        this.si.getEndDate().orElse(PAST).setTime(0);
        Assert.assertEquals(FUTURE, this.si.getEndDate().orElse(PAST));

        this.si.setEndDate(FUTURE);
        FUTURE.setTime(0);
        Assert.assertNotEquals(FUTURE, this.si.getEndDate().orElse(FUTURE));
    }

    /**
     * Checking insertion of end date that is before the start date.
     */
    @Test
    public void invalidEndDateTest() {
        final Result<Empty> res = this.si.setEndDate(PAST);
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalArgumentException.class, res.getError().getClass());
    }
}
