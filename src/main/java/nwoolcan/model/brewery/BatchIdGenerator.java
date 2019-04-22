package nwoolcan.model.brewery;

final class BatchIdGenerator implements IdGenerator {

    private static final int START_ID = 1;

    private int nextId;

    /**
     * Creates an id generator for batches.
     */
    BatchIdGenerator() {
        this.nextId = START_ID;
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
