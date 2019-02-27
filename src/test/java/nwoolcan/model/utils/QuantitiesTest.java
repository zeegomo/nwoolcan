package nwoolcan.model.utils;

import nwoolcan.utils.Result;
import nwoolcan.utils.test.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * Test Quantities utils class.
 */
public class QuantitiesTest {

    private Quantity q1;
    private Quantity q2;
    private Quantity q3;
    private Quantity q4;

    private static final Number V1 = 10;
    private static final Number V2 = 3.14;
    private static final Number V3 = 200;
    private static final Number V4 = 200;

    private static final UnitOfMeasure UM1 = UnitOfMeasure.Kilogram;
    private static final UnitOfMeasure UM2 = UnitOfMeasure.Liter;

    /**
     * Sets up fields.
     */
    @Before
    public void initQuantities() {
        this.q1 = Quantity.of(V1, UM1);
        this.q2 = Quantity.of(V2, UM1);
        this.q3 = Quantity.of(V3, UM1);
        this.q4 = Quantity.of(V4, UM2);
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

        TestUtils.assertEqualsWithMessage(Quantity.of(Numbers.add(V1, V2), UM1), sum1res.getValue());
        TestUtils.assertEqualsWithMessage(Quantity.of(Numbers.add(V1, V3), UM1), sum2res.getValue());
        TestUtils.assertEqualsWithMessage(Quantity.of(Numbers.add(V2, V3), UM1), sum3res.getValue());
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
        Assert.assertTrue(rem3res.isError());

        TestUtils.assertEqualsWithMessage(Quantity.of(Numbers.subtract(V1, V2), UM1), rem1res.getValue());
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
        Assert.assertTrue(remRes.isError());
    }
}
