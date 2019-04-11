package nwoolcan.model.database;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.warehouse.stock.Record;

/**
 * Saves/loads data to/from a persistent storage.
 */
public interface Database {
    /**
     * Saves a Record.
     * @param toSave Object to be saved
     * @return The serialized object
     */
    String save(Record toSave);

    /**
     * Loads a record.
     * @param serialized The serialized Record object
     * @return A deserialized Record
     */
    Record loadRecord(String serialized);

    /**
     * Saves a BatchEvaluation.
     * @param toSave Object to be saved
     * @return The serialized object
     */
    String save(BatchEvaluation toSave);

    /**
     * Loads a BatchEvaluation.
     * @param serialized The serialized Record object
     * @return A deserialized Record
     */
    BatchEvaluation loadEvaluation(String serialized);


}
