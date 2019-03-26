package nwoolcan.model.brewery.production.batch;

final class BatchIdGenerator {
    private static BatchIdGenerator singleton = new BatchIdGenerator(0);

    private int nextId;

    /**
     * Returns the singleton instance.
     * @return the singleton instance.
     */
    static BatchIdGenerator getInstance() {
        return singleton;
    }

    private BatchIdGenerator(final int startId) {
        this.nextId = startId;
    }

    /**
     * Returns the next available id.
     * @return the next available id.
     */
    int getNextId() {
        return this.nextId++;
    }
}
