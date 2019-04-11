package nwoolcan.model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.warehouse.stock.Record;


/**
 * Saves and loads data as JSON.
 */
public final class DatabaseJsonImpl implements Database {
    private final Gson gson;

    /**
     * Initialize JSON serialization.
     */
    public DatabaseJsonImpl() {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new InterfaceAdapterFactory());
        builder.setPrettyPrinting();

        this.gson = builder.create();
    }

    private String saveAll(final Object toSave) {
        return this.gson.toJson(toSave);
    }

    @Override
    public String save(final Record toSave) {
        return this.saveAll(toSave);
    }

    @Override
    public Record loadRecord(final String serialized) {
        return gson.fromJson(serialized, Record.class);
    }

    @Override
    public String save(final BatchEvaluation toSave) {
        return this.saveAll(toSave);
    }

    @Override
    public BatchEvaluation loadEvaluation(final String serialized) {
        return gson.fromJson(serialized, BatchEvaluation.class);
    }

}
