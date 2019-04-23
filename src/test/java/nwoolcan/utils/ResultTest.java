package nwoolcan.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

/**
 * Test Result class.
 */
public class ResultTest {
    //Using this because of Ecplise error. Gradle (and IntelliJ) works just fine with the following lamda version:
    //() -> () -> {
    // throw new IllegalStateException();
    //}
    private static class FallibleAutoCloseable implements AutoCloseable {

        @Override
        public void close() throws Exception {
            throw new IllegalStateException();
        }
    }
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
        assertFalse(presentEmpty.isError());

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
        empty.getError();
    }
    /**
     * Tests orElse.
     */
    @Test(expected = NoSuchElementException.class)
    public void testEmptyOrElse() {
        Result<Integer> error = Result.error(new Exception());
        assertTrue(error.orElse(2).equals(2));
        error.getValue();
    }
    /**
     * Tests require.
     */
    @Test
    public void testRequire() {
        assertTrue(Result.of(4).require(i -> i > 2).isPresent());
        assertTrue(Result.of(4).require(i -> i < 2).isError());
        assertTrue(Result.ofEmpty().require(() -> false).isError());
        assertTrue(Result.ofEmpty().require(() -> true).isPresent());
    }
    /**
     * Test map.
     */
    @Test
    public void testMap() {
        Result<String> error = Result.error(new Exception("e"));
        Result<String> duke = Result.of("Duke");

        // Map an empty value
        Result<Boolean> b = error.map(String::isEmpty);
        assertFalse(b.isPresent());
        // Map into null
        b = error.map(n -> true);
        assertFalse(b.isPresent());
        b = duke.map(s -> true);
        assertTrue(b.isPresent());

        try {
            Result<Boolean> res = error.map(s -> Optional.empty().get()).map(s -> false);
            assertFalse(res.isPresent());
            assertTrue(res.isError());
        } catch (NullPointerException npe) {
            throw new AssertionError("Mapper function should not be invoked", npe);
        }

        // Map to value
        Result<Integer> l = duke.map(String::length);
        assertTrue(l.getValue() == 4);
    }
    /**
     * Test flatMap.
     */
    @Test
    public void testFlatMap() {
        Result<String> error = Result.error(new Exception());
        Result<String> duke = Result.of("Duke");

        // Empty won't invoke mapper function
        try {
            Result<Boolean> b = error.flatMap(s -> null);
            assertFalse(b.isPresent());
        } catch (NullPointerException npe) {
            throw new AssertionError("Mapper function should not be invoked", npe);
        }

        // Map an empty value
        Result<Integer> l = error.flatMap(s -> Result.of(s.length()));
        assertFalse(l.isPresent());

        // Map to value
        Result<Integer> fixture = Result.of(Integer.MAX_VALUE);
        l = duke.flatMap(s -> Result.of(s.length()));
        assertTrue(l.isPresent());
        assertEquals(4, l.getValue().intValue());

        // Verify same instance
        l = duke.flatMap(s -> fixture);
        assertSame(l, fixture);
        assertEquals(Result.of(4), l.flatMap(() -> Result.of(4)));
    }
    /**
     * Tests peek.
     */
    @Test
    public void testPeek() {
        Collection<Integer> coll = new ArrayList<>();
        Result.of(2).peek(coll::add);
        assertEquals(1, coll.size());
        Result.error(new Exception()).peek(i -> coll.add(2));
        assertEquals(1, coll.size());
    }
    /**
     * Tests ofChecked.
     */
    @Test
    public void testOfChecked() {
        Result<Integer> r1 = Results.ofChecked(() -> 1);
        assertTrue(r1.getValue() == 1);
        Result<Integer> r2 = Results.ofChecked(() -> {
            throw new IllegalAccessException("Illegal test");
        });
        assertTrue(r2.isError());
        r2.getError().printStackTrace();
        System.out.println(r2.getError().toString());
    }
    /**
     * Tests ofCloseable.
     */
    @Test
    public void testOfCloseable() {
        Result<Integer> r1 = Results.ofCloseable(() -> this.getClass().getResourceAsStream(""), e -> 3);
        assertTrue(r1.getValue() == 3);
        Result<Integer> r2 = Results.ofCloseable(FallibleAutoCloseable::new, e -> 3);
        assertTrue(r2.isError());
    }
    /**
     * Tests stream.
     */
    @Test
    public void testStream() {
        Result<Integer> r1 = Result.of(2);
        assertEquals(1, r1.stream().distinct().count());
        assertTrue(r1.stream().allMatch(i -> i.getValue() == 2));
        assertEquals(Result.of(2), r1.stream().findAny().get());
    }
    /**
     * Tests toEmpty.
     */
    @Test
    public void testToEmpty() {
        Result<Integer> r1 = Result.of(2);
        assertTrue(r1.toEmpty().isPresent());
        r1.peekError(NullPointerException.class, NullPointerException::printStackTrace);
    }
    /**
     * Tests toEmpty.
     */
    @Test
    public void testPeekError() {
        Result<Integer> r2 = Results.ofChecked(() -> {
            throw new IllegalAccessException("Illegal test");
        });
        Collection<Integer> c = new ArrayList<>();
        r2.peekError(e -> c.add(2));
        assertEquals(1, c.size());
        r2.peekError(IllegalAccessException.class, e -> c.add(1));
        assertEquals(2, c.size());
        r2.peekError(NullPointerException.class, e -> c.add(1));
        r2.peekError(IllegalArgumentException.class, e -> c.add(1));
        assertEquals(2, c.size());
    }
    /**
     * Tests test Map error.
     */
    @Test
    public void testMapError() {
        Result<Integer> r2 = Results.ofChecked(() -> {
            throw new IllegalAccessException("Illegal test");
        });
        Result<Integer> r3 = r2.mapError(err -> new IllegalArgumentException("ciao"));
        assertTrue(r3.isError());
        assertEquals(r3.getError().getMessage(), "ciao");
        Result<Integer> r = Result.of(2);
        Collection<Integer> c = new ArrayList<>();
        r.mapError(err -> {
            c.add(2);
            return new IllegalArgumentException();
        });
        assertEquals(c.size(), 0);
        r3.mapError(IllegalArgumentException.class, err ->  {
            c.add(2);
            return new IllegalArgumentException();
        });
        assertEquals(c.size(), 1);
        r3.mapError(NullPointerException.class, err ->  {
            c.add(2);
            return new IllegalArgumentException();
        });
        assertEquals(c.size(), 1);
    }

    /**
     * Test lazy require.
     */
    @Test
    public void testRequireLazy() {
        Result<Integer> r2 = Result.of(2);
        r2.require(r2::isPresent, r2::getError);
        Result<Integer> r1 = Results.ofChecked(() -> {
            throw new IllegalAccessException("Illegal test");
        });
        assertTrue(r2.require(r1::isPresent, r1::getError).isError());
        assertEquals("Illegal test", r1.require(r2::isPresent, r2::getError).getError().getMessage());

    }

    /**
     * Test reduce utils in Results.
     */
    @Test
    public void testReduce() {
        Collection<Result<Integer>> coll = Arrays.asList(
            Result.of(1),
            Result.of(2),
            Result.of(3),
            Result.of(4),
            Result.of(1),
            Result.of(1)
        );

        Result<Collection<Integer>> res = Results.reduce(coll);

        assertTrue(res.isPresent());
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 1, 1}, res.getValue().toArray());

        coll = Arrays.asList(
            Result.of(1),
            Result.of(2),
            Result.error(new Exception()),
            Result.error(new Exception()),
            Result.of(1),
            Result.of(1)
        );

        res = Results.reduce(coll);

        assertTrue(res.isError());
    }
}

