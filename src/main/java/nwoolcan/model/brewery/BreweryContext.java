package nwoolcan.model.brewery;

/**
 * Singleton for {@link Brewery}.
 */
public final class BreweryContext {

    private static final Brewery INSTANCE = new BreweryImpl();

    /**
     * Return the only instance of the {@link Brewery}.
     * @return the only instance of the {@link Brewery}.
     */
    public static Brewery getInstance() {
        return INSTANCE;
    }

    private BreweryContext() { }
}
