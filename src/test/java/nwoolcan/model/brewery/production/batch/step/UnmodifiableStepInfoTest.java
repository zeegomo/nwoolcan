package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for {@link UnmodifiableStepInfo}.
 */
public class UnmodifiableStepInfoTest {

    private static final StepType ST = StepType.Mashing;
    private static final Date START = new Date();
    private static final Date END = new Date(START.getTime() + 100);
    private static final String NOTE = "Test note.";
    private static final Quantity Q = Quantity.of(20, UnitOfMeasure.Liter);

    private StepInfo si;

    private void init() {
        final StepInfo modsi = new StepInfoImpl(ST, START);
        this.si = new UnmodifiableStepInfo(modsi);
    }

    private void populate() {
        final StepInfo modsi = new StepInfoImpl(ST, START);
        modsi.setEndDate(END);
        modsi.setNote(NOTE);
        modsi.setEndStepSize(Q);
        this.si = new UnmodifiableStepInfo(modsi);
    }

    /**
     * Simple construction and verification of getters.
     */
    @Test
    public void simpleConstructorTest() {
        init();
        Assert.assertEquals(ST, this.si.getType());
        Assert.assertEquals(START, this.si.getStartDate());
    }

    /**
     * Complete construction and verification of getters.
     */
    @Test
    public void completeConstructorTest() {
        populate();
        Assert.assertEquals(END, this.si.getEndDate().orElse(START));
        Assert.assertEquals(NOTE, this.si.getNote().orElse(""));
        Assert.assertEquals(Q, this.si.getEndStepSize().orElse(null));
    }

    /**
     * Checking unmodifiability of the class.
     */
    @Test
    public void unmodifiableTest() {
        populate();
        Result<Empty> res;
        res = this.si.setEndDate(END);
        Assert.assertTrue(res.isError());
        res = this.si.setNote(NOTE);
        Assert.assertTrue(res.isError());
        res = this.si.setEndStepSize(Q);
        Assert.assertTrue(res.isError());
    }
}
