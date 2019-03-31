package nwoolcan.model.brewery.production.batch;

final class BatchIdGenerator {

    private static class LazyHolder {
        private static final BatchIdGenerator SINGLETON = new BatchIdGenerator(0);
    }

    private int nextId;

    private BatchIdGenerator(final int startId) {
        this.nextId = startId;
    }
    /**
     * Returns the singleton instance.
     * @return the singleton instance.
     */
    static BatchIdGenerator getInstance() {
        return LazyHolder.SINGLETON;
    }
    /**
     * Returns the next available id.
     * @return the next available id.
     */
    synchronized int getNextId() {
        return this.nextId++;
    }
}
