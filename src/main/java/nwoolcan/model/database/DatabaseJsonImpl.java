package nwoolcan.model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;


/**
 * Saves and loads data as JSON.
 */
public final class DatabaseJsonImpl implements Database {
    private final Gson gson;
    private final File filePath;

    /**
     * Initialize JSON serialization.
     * @param filePath The path of the file to save/load data to/from.
     */
    public DatabaseJsonImpl(final File filePath) {
        this.gson = new GsonBuilder()
            .registerTypeAdapterFactory(new TypeWrapperAdapterFactory())
            .setPrettyPrinting()
            .create();

        this.filePath = filePath;
    }

    public DatabaseJsonImpl(final String filePath) {
        this(new File(filePath));
    }

    /**
     * Serializes to JSON a generic object.
     * @param toSerialize The object to be serialized.
     * @param <T> The type of the object to be serialized.
     * @return The JSON string representing the given object.
     */
    public <T> Result<String> serialize(final T toSerialize) {
        final StringWriter output = new StringWriter();
        return this.serialize(toSerialize, output)
            .map(e -> output.toString());
    }

    /**
     * Serializes to JSON a generic object to a {@link Writer}.
     * @param toSerialize The object to be serialized.
     * @param output Where to put the output.
     * @param <T> The type of the object to be serialized.
     * @return A {@link Result} with an error if something went wrong.
     */
    public <T> Result<Empty> serialize(final T toSerialize, final Writer output) {
        return Results.ofChecked(() -> this.gson.toJson(toSerialize, output));
    }

    /**
     * De-serializes a generic object: it's type must be given as a parameter.
     * @param serialized The JSON string to deserialize.
     * @param type The TokenType of the resulting object.
     * @param <T> The actual compile-time type of the resulting object.
     * @return The de-serialized object.
     */
    public <T> Result<T> deserialize(final String serialized, final TypeToken<T> type) {
        return this.deserialize(new StringReader(serialized), type);
    }

    /**
     * De-serializes a generic object: it's type must be given as a parameter.
     * @param serialized A reader to read the JSON representation from.
     * @param type The TokenType of the resulting object.
     * @param <T> The actual compile-time type of the resulting object.
     * @return The de-serialized object.
     */
    public <T> Result<T> deserialize(final Reader serialized, final TypeToken<T> type) {
        return Results.ofChecked(() -> this.gson.fromJson(serialized, type.getType()));
    }

    @Override
    public Result<Empty> save(final Brewery toSave) {
        return Results.ofChecked(() -> new BufferedWriter(new FileWriter(this.filePath)))
            .flatMap(buf -> this.serialize(toSave, buf));
    }

    @Override
    public Result<Brewery> load() {
        return Results.ofChecked(() -> new BufferedReader(new FileReader(this.filePath)))
            .flatMap(buf -> this.deserialize(buf, new TypeToken<Brewery>() { }));
    }
}
