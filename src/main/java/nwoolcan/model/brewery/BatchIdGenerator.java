package nwoolcan.model.brewery;

final class BatchIdGenerator implements IdGenerator {

    private int nextId;

    /**
     * Creates an id generator for batches.
     * @param startId start id.
     */
    BatchIdGenerator(final int startId) {
        this.nextId = startId;
    }
    /**
     * Returns the next available id.
     * @return the next available id.
     */
    @Override
    public synchronized int getNextId() {
        return this.nextId++;
    }
}
