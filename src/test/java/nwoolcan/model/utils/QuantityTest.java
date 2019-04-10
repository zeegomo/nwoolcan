package nwoolcan.model.utils;

import org.junit.Assert;
import org.junit.Test;

import nwoolcan.utils.test.TestUtils;

/**
 * Test class for {@link Quantity} class.
 */
public class QuantityTest {

    private static final int VALUE1 = 20;
    private static final int VALUE2 = 30;
    private static final int NEG_VALUE = -2;

    private static final UnitOfMeasure GOOD_UM1 = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure GOOD_UM2 = UnitOfMeasure.MILLILITER;
    private static final UnitOfMeasure BAD_UM = UnitOfMeasure.CELSIUS_DEGREE;

    /**
     * Method that tests a simple quantity creation.
     */
    @Test
    public void testQuantitySimpleCreation() {
        final Quantity q = Quantity.of(VALUE1, GOOD_UM1);
        Assert.assertEquals(VALUE1, q.getValue());
        Assert.assertEquals(GOOD_UM1, q.getUnitOfMeasure());
    }
    /**
     * Method that tests quantity creation with negative value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testQuantityWithNegativeValue() {
        final Quantity q = Quantity.of(NEG_VALUE, GOOD_UM1);
    }

    /**
     * Method that tests empty quantity creation.
     */
    @Test
    public void testEmptyQuantity() {
        final Quantity q = Quantity.of(0, GOOD_UM1);
    }

    /**
     * Method that tests quantity creation with not quantity unit of measure.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testQuantityWithNoQuantityUM() {
        final Quantity q = Quantity.of(VALUE1, BAD_UM);
    }

    /**
     * Method that tests equals and hashcode methods of Quantity.
     */
    @Test
    public void testQuantityEqualsAndHashcode() {
        final UnitOfMeasure um = UnitOfMeasure.GRAM;
        final Quantity q1 = Quantity.of(VALUE1, GOOD_UM1);
        final Quantity q2 = Quantity.of(VALUE1, GOOD_UM1);

        final Quantity q3 = Quantity.of(VALUE1, GOOD_UM2);
        final Quantity q4 = Quantity.of(VALUE2, GOOD_UM2);

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
        final Quantity q1 = Quantity.of(VALUE1, GOOD_UM1);
        final Quantity q2 = Quantity.of(VALUE2, GOOD_UM1);

        final Quantity q3 = Quantity.of(VALUE1, GOOD_UM2);
        final Quantity q4 = Quantity.of(VALUE2, GOOD_UM2);

        Assert.assertTrue(q1.lessThan(q2));
        Assert.assertTrue(q2.moreThan(q1));
        Assert.assertTrue(q2.compareTo(q1) > 0);

        Assert.assertTrue(q1.checkedCompareTo(q3).isError());
        Assert.assertTrue(q2.checkedLessThan(q3).isError());
        Assert.assertTrue(q2.checkedMoreThan(q4).isError());

        Assert.assertTrue(q3.checkedLessThan(q4).isPresent());
        q3.checkedLessThan(q4).peek(Assert::assertTrue);
    }
}
