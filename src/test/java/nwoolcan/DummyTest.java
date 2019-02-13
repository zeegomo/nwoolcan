package nwoolcan;

import org.junit.Assert;
import org.junit.Test;

/**
 *  Dummy test class.
 */
public class DummyTest {

    /**
     *  Dummy test method that pass.
     */
    @Test
    public void dummyTestPassing() {
        Assert.assertTrue(true);
    }

    /**
     *  Dummy test method that doesn't pass.
     */
    @Test(expected = java.lang.AssertionError.class)
    public void dummyTestNotPassing() {
        Assert.assertTrue(false);
    }
}
