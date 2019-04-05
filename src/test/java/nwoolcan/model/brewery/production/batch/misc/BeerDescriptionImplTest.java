package nwoolcan.model.brewery.production.batch.misc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test BeerDescriptionImpl.
 */
public class BeerDescriptionImplTest {

    /**
     * Test setter.
     */
    @Test
    public void testSetter() {
        final String newName = "frampanio";
        final String newStyle = "european lager";
        BeerDescription desc = new BeerDescriptionImpl("rossina", "alfredina");
        assertFalse(desc.getStyleCategory().isPresent());
        desc.setName(newName);
        assertEquals(desc.getName(), newName);
        desc.setStyleCategory(newStyle);
        assertTrue(desc.getStyleCategory().isPresent());
        assertTrue(desc.getStyleCategory().get().equals(newStyle));
    }
}
