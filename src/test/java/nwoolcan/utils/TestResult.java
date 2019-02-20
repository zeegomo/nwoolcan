package nwoolcan.utils;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertSame;

/**
 * Test Result class.
 */
public class TestResult {
    /**
     * Test Result of Empty.
     */
    @Test
    public void testEmpty() {

        Result<Empty> exception = Result.error(new Exception());
        Result<Empty> presentEmpty = Result.ofEmpty();
        Result<Boolean> present = Result.of(true);

        // presence
        assertTrue(exception.isError());
        assertFalse(exception.isPresent());

        assertTrue(presentEmpty.isPresent());
        assertFalse((presentEmpty.isError()));

        assertTrue(present.isPresent());
        assertFalse(present.isError());

        //identity
        assertEquals(exception, exception);
        assertEquals(presentEmpty, presentEmpty);
        assertEquals(present, present);
        assertNotEquals(present, presentEmpty);
        assertNotEquals(present, exception);
    }
    /**
     * Tests getting an error from a Result holding a value.
     */
    @Test(expected = NoSuchElementException.class)
    public void testEmptyGet() {
        Result<Empty> empty = Result.ofEmpty();
        Exception e = empty.getError();
    }
    /**
     * Tests orElse.
     */
    @Test(expected = NoSuchElementException.class)
    public void testEmptyOrElse() {
        Result<Integer> error = Result.error(new Exception());
        assertTrue(error.orElse(2).equals(2));
        Integer i = error.getValue();
    }
    /**
     * Tests require.
     */
    @Test
    public void testRequire() {
        assertTrue(Result.of(4).require(i -> i > 2).isPresent());
        assertTrue(Result.of(4).require(i -> i < 2).isError());
    }
    @Test
    public void testRequireNonNull() {
        assertTrue(Results.requireNonNull(null).isError());
        assertTrue(Results.requireNonNull(new Empty() { }).isPresent());
    }
    /**
     * Test map.
     */
    @Test
    public void testMap() {
        Result<String> error = Result.error(new Exception("e"));
        Result<String> duke = Result.of("Duke");

        // Null mapper function
        try {
            Result<Boolean> b = error.map(null);
            fail("Should throw NPE on null mapping function");
        } catch (NullPointerException npe) {
            // expected
        }

        // Map an empty value
        Result<Boolean> b = error.map(String::isEmpty);
        assertFalse(b.isPresent());
        // Map into null
        b = error.map(n -> null);
        assertFalse(b.isPresent());
        b = duke.map(s -> true);
        assertTrue(b.isPresent());

        try {
            Result<Boolean> res = error.map(s -> Optional.empty().get()).map(s -> null);
            assertFalse(res.isPresent());
            assertTrue((res.isError()));
        } catch (NullPointerException npe) {
            fail("Mapper function should not be invoked");
        }

        // Map to value
        Result<Integer> l = duke.map(String::length);
        assertTrue(l.getValue() == 4);
    }
    /**
     *  Test flatMap.
     */
    @Test
    public void testFlatMap() {
        Result<String> error = Result.error(new Exception());
        Result<String> duke = Result.of("Duke");


        // Null mapper function
        try {
            Result<Boolean> b = error.map(null);
            fail("Should throw NPE on null mapping function");
        } catch (NullPointerException npe) {
            // expected
        }

        // Empty won't invoke mapper function
        try {
            Result<Boolean> b = error.flatMap(s -> null);
            assertFalse(b.isPresent());
        } catch (NullPointerException npe) {
            fail("Mapper function should not be invoked");
        }

        // Map an empty value
        Result<Integer> l = error.flatMap(s -> Result.of(s.length()));
        assertFalse(l.isPresent());

        // Map to value
        Result<Integer> fixture = Result.of(Integer.MAX_VALUE);
        l = duke.flatMap(s -> Result.of(s.length()));
        assertTrue(l.isPresent());
        assertEquals(l.getValue().intValue(), 4);

        // Verify same instance
        l = duke.flatMap(s -> fixture);
        assertSame(l, fixture);
    }


}

