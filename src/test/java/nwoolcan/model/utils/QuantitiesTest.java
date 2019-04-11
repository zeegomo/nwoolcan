package nwoolcan.model.utils;

import nwoolcan.utils.Result;
import nwoolcan.utils.test.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * Test class for {@link Quantities} utils class.
 */
public class QuantitiesTest {

    private Quantity q1;
    private Quantity q2;
    private Quantity q3;
    private Quantity q4;

    private static final int V1 = 10;
    private static final int V2 = 3;
    private static final int V3 = 200;
    private static final int V4 = 200;

    private static final UnitOfMeasure UM1 = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure UM2 = UnitOfMeasure.MILLILITER;

    /**
     * Sets up fields.
     */
    @Before
    public void initQuantities() {
        this.q1 = Quantity.of(V1, UM1).getValue();
        this.q2 = Quantity.of(V2, UM1).getValue();
        this.q3 = Quantity.of(V3, UM1).getValue();
        this.q4 = Quantity.of(V4, UM2).getValue();
    }

    /**
     * Method that tests adding operations between quantities.
     */
    @Test
    public void testAddQuantities() {
        initQuantities();
        final Result<Quantity> sum1res = Quantities.add(q1, q2);
        final Result<Quantity> sum2res = Quantities.add(q1, q3);
        final Result<Quantity> sum3res = Quantities.add(q2, q3);

        Assert.assertTrue(sum1res.isPresent());
        Assert.assertTrue(sum2res.isPresent());
        Assert.assertTrue(sum3res.isPresent());

        TestUtils.assertEqualsWithMessage(Quantity.of(V1 + V2, UM1).getValue(), sum1res.getValue());
        TestUtils.assertEqualsWithMessage(Quantity.of(V1 + V3, UM1).getValue(), sum2res.getValue());
        TestUtils.assertEqualsWithMessage(Quantity.of(V2 + V3, UM1).getValue(), sum3res.getValue());
    }

    /**
     * Method that tests removing operations between quantities.
     */
    @Test
    public void testRemoveQuantities() {
        initQuantities();
        final Result<Quantity> rem1res = Quantities.remove(q1, q2);
        final Result<Quantity> rem2res = Quantities.remove(q1, q3);
        final Result<Quantity> rem3res = Quantities.remove(q2, q3);

        Assert.assertTrue(rem1res.isPresent());
        Assert.assertTrue(rem2res.isError());
        Assert.assertSame(IllegalArgumentException.class, rem2res.getError().getClass());
        Assert.assertTrue(rem3res.isError());
        Assert.assertSame(IllegalArgumentException.class, rem3res.getError().getClass());

        TestUtils.assertEqualsWithMessage(Quantity.of(V1 - V2, UM1).getValue(), rem1res.getValue());
    }

    /**
     * Method that tests different operations between quantities that have not the same unit of measure.
     */
    @Test
    public void testOperationsWithDifferentUM() {
        initQuantities();
        final Result<Quantity> sumRes = Quantities.add(q1, q4);
        final Result<Quantity> remRes = Quantities.remove(q1, q4);
        Assert.assertTrue(sumRes.isError());
        Assert.assertSame(ArithmeticException.class, sumRes.getError().getClass());
        Assert.assertTrue(remRes.isError());
        Assert.assertSame(IllegalArgumentException.class, remRes.getError().getClass());
    }
}
