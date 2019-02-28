package nwoolcan.utils.test;

import org.junit.Assert;

/**
 * Test utils class for generating error messages.
 */
public final class TestUtils {

    private static final String NULL = "null";

    private TestUtils() { }

    /**
     * Returns an error message for objects that should be equals.
     * @param o1 first object.
     * @param o2 second object.
     * @return an error message for objects that should be equals.
     */
    public static String equalsErrorMessage(final Object o1, final Object o2) {
        return (o1 == null ? NULL : o1.toString())
            + " expected equals to "
            + (o2 == null ? NULL : o2.toString())
            + " but they are not equals.";
    }

    /**
     * Returns an error message for objects that should not be equals.
     * @param o1 first object.
     * @param o2 second object.
     * @return an error message for objects that should not be equals.
     */
    public static String notEqualsErrorMessage(final Object o1, final Object o2) {
        return (o1 == null ? NULL : o1.toString())
            + " expected not equals to "
            + (o2 == null ? NULL : o2.toString())
            + " but they are equals.";
    }

    /**
     * Asserts that objects are equals with a generated error message.
     * @param o1 expected value.
     * @param o2 actual value.
     */
    public static void assertEqualsWithMessage(final Object o1, final Object o2) {
        Assert.assertEquals(equalsErrorMessage(o1, o2), o1, o2);
    }

    /**
     * Asserts that objects are not equals with a generated error message.
     * @param o1 expected value.
     * @param o2 actual value.
     */
    public static void assertNotEqualsWithMessage(final Object o1, final Object o2) {
        Assert.assertNotEquals(notEqualsErrorMessage(o1, o2), o1, o2);
    }
}
