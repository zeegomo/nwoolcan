package nwoolcan.model.brewery.production.batch;

/**
 * Batch production methods.
 */
public enum BatchMethod {
    /**
     * All grain.
     */
    ALL_GRAIN("All grain"),
    /**
     * Partial mash.
     */
    PARTIAL_MASH("Partial Mash"),
    /**
     * Extract.
     */
    EXTRACT("Exctract");

    private final String name;

    BatchMethod(final String name) {
        this.name = name;
    }
    /**
     * Return a {@link String} representation of the name of the method.
     * @return a {@link String}.
     */
    public String getName() {
        return this.name;
    }
}
