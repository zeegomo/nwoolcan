package nwoolcan.model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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

    /**
     * Serializes to JSON a generic object.
     * @param toSerialize The object to be serialized.
     * @param <T> The type of the object to be serialized.
     * @return The JSON string representing the given object.
     */
    public <T> String serialize(final T toSerialize) {
        return this.saveAll(toSerialize);
    }

    /**
     * De-serializes a generic object: it's type must be given as a parameter.
     * @param serialized The JSON string to deserialize.
     * @param type The TokenType of the resulting object.
     * @param <T> The actual compile-time type of the resulting object.
     * @return The de-serialized object.
     */
    public <T> T deserialize(final String serialized, final TypeToken<T> type) {
        return this.gson.fromJson(serialized, type.getType());
    }

}
