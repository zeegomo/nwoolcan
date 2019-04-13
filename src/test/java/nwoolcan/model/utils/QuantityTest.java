package nwoolcan.model.utils;

import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Test;

import nwoolcan.utils.test.TestUtils;

/**
 * Test class for {@link Quantity} class.
 */
public class QuantityTest {

    private static final double VALUE1 = 20.1;
    private static final double VALUE2 = 30.4;
    private static final double NEG_VALUE = -2;

    private static final UnitOfMeasure GOOD_UM1 = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure GOOD_UM2 = UnitOfMeasure.LITER;
    private static final UnitOfMeasure BAD_UM = UnitOfMeasure.CELSIUS_DEGREE;

    /**
     * Method that tests a simple quantity creation.
     */
    @Test
    public void testQuantitySimpleCreation() {
        final Quantity q = Quantity.of(VALUE1, GOOD_UM1).getValue();
        Assert.assertEquals(VALUE1, q.getValue(), 0);
        Assert.assertEquals(GOOD_UM1, q.getUnitOfMeasure());
    }
    /**
     * Method that tests quantity creation with negative value.
     */
    @Test
    public void testQuantityWithNegativeValue() {
        Assert.assertTrue(Quantity.of(NEG_VALUE, GOOD_UM1).isError());
    }

    /**
     * Method that tests empty quantity creation.
     */
    @Test
    public void testEmptyQuantity() {
        Assert.assertTrue(Quantity.of(0, GOOD_UM1).isPresent());
    }

    /**
     * Method that tests quantity creation with not quantity unit of measure.
     */
    @Test
    public void testQuantityWithNoQuantityUM() {
        Assert.assertTrue(Quantity.of(VALUE1, BAD_UM).isError());
    }

    /**
     * Method that tests equals and hashcode methods of Quantity.
     */
    @Test
    public void testQuantityEqualsAndHashcode() {
        final Quantity q1 = Quantity.of(VALUE1, GOOD_UM1).getValue();
        final Quantity q2 = Quantity.of(VALUE1, GOOD_UM1).getValue();

        final Quantity q3 = Quantity.of(VALUE1, GOOD_UM2).getValue();
        final Quantity q4 = Quantity.of(VALUE2, GOOD_UM2).getValue();

        TestUtils.assertEqualsWithMessage(q1, q2);
        Assert.assertEquals(q1.hashCode(), q2.hashCode());

        TestUtils.assertNotEqualsWithMessage(q1, q3);
        TestUtils.assertNotEqualsWithMessage(q3, q4);
    }

    /**
     * Method that tests comparison between quantities.
     */
    @Test
    public void testCompareQuantities() {
        final Quantity q1 = Quantity.of(VALUE1, GOOD_UM1).getValue();
        final Quantity q2 = Quantity.of(VALUE2, GOOD_UM1).getValue();
        final Quantity q3 = Quantity.of(VALUE1, GOOD_UM2).getValue();
        final Quantity q4 = Quantity.of(VALUE2, GOOD_UM2).getValue();

        Assert.assertTrue(q1.lessThan(q2));
        Assert.assertTrue(q2.moreThan(q1));
        Assert.assertTrue(q2.compareTo(q1) > 0);

        Assert.assertTrue(q1.checkedCompareTo(q3).isError());
        Assert.assertTrue(q2.checkedLessThan(q3).isError());
        Assert.assertTrue(q2.checkedMoreThan(q4).isError());

        Assert.assertTrue(q3.checkedLessThan(q4).isPresent());
        q3.checkedLessThan(q4).peek(Assert::assertTrue);
    }

    /**
     * Methods that tests quantity precision.
     */
    @Test
    public void testPrecision() {
        final double a = 0.3;
        final double b = 0.2;
        final double c = 0.1;
        final double d = 0.100001;

        final Quantity qa = Quantity.of(a, UnitOfMeasure.GRAM).getValue();
        final Quantity qb = Quantity.of(b, UnitOfMeasure.GRAM).getValue();
        final Quantity qc = Quantity.of(c, UnitOfMeasure.GRAM).getValue();
        final Quantity qd = Quantity.of(d, UnitOfMeasure.GRAM).getValue();

        Assert.assertEquals(a, qa.getValue(), 0);
        Assert.assertEquals(b, qb.getValue(), 0);
        Assert.assertEquals(c, qc.getValue(), 0);

        final Quantity computedC = Quantities.remove(qa, qb).getValue();

        //0.3 - 0.2 = 0.1
        Assert.assertEquals(c, computedC.getValue(), 0);

        //0.1 - 0.1 = 0
        Assert.assertEquals(0, Quantities.remove(qc, qc).getValue().getValue(), 0);

        //0.3 - 0.2 - 0.1 = 0
        Assert.assertEquals(0, Quantities.remove(computedC, qc).getValue().getValue(), 0);

        //0.1 - 0.100001 < 0
        Result<Quantity> res = Quantities.remove(qc, qd);
        Assert.assertTrue(res.isError());

        //0.100001 - 0.1 > 0
        res = Quantities.remove(qd, qc);
        Assert.assertTrue(res.getValue().moreThan(Quantity.of(0, UnitOfMeasure.UNIT).getValue()));
    }
}
