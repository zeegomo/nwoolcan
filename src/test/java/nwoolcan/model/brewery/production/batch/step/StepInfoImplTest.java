package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for {@link StepInfoImpl}.
 */
public class StepInfoImplTest {

    private static final int TWENTY = 20;
    private static final StepType ST = StepTypeEnum.MASHING;
    private static final Date START = new Date();
    private static final Date END = new Date(START.getTime() + 100);
    private static final String NOTE = "Test note.";
    private static final Quantity Q = Quantity.of(TWENTY, UnitOfMeasure.LITER).getValue();

    private StepInfo si;

    /**
     * Sets up fields.
     */
    @Before
    public void setUp() {
        final ModifiableStepInfo modsi = new ModifiableStepInfoImpl(ST, START);
        this.si = new StepInfoImpl(modsi);
    }

    private void populate() {
        final ModifiableStepInfo modsi = new ModifiableStepInfoImpl(ST, START);
        modsi.setEndDate(END);
        modsi.setNote(NOTE);
        modsi.setEndStepSize(Q);
        this.si = new StepInfoImpl(modsi);
    }

    /**
     * Simple construction and verification of getters.
     */
    @Test
    public void simpleConstructorTest() {
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
}
