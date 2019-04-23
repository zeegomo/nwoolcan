package nwoolcan.model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;
/**
 * Saves and loads data as JSON from jar.
 */
public final class DatabaseJsonFromJarImpl implements Database {
    private static final String CANNOT_SAVE_IN_JAR = "Cannot save in Jar";
    private final Gson gson;
    private final InputStream stream;
    /**
     * Initialize JSON serialization.
     * @param stream The stream to save/load data to/from.
     */
    public DatabaseJsonFromJarImpl(final InputStream stream) {
        this.gson = new GsonBuilder()
            .registerTypeAdapterFactory(new TypeWrapperAdapterFactory())
            .registerTypeAdapterFactory(new MapTypeAdapterFactory(new ConstructorConstructor(new HashMap<>()), true))
            .setPrettyPrinting()
            .create();
        this.stream = stream;
    }

    @Override
    public Result<Empty> save(final Brewery toSave) {
        return Result.error(new UnsupportedOperationException(CANNOT_SAVE_IN_JAR));
    }

    @Override
    public Result<Brewery> load() {
        return Results.ofChecked(() -> new BufferedReader(new InputStreamReader(this.stream, UTF_8)))
                      .flatMap(buf -> Results.ofCloseable(() -> buf, reader -> this.deserialize(reader, new TypeToken<Brewery>() { })))
                      .flatMap(Function.identity());
    }

    private <T> Result<T> deserialize(final Reader serialized, final TypeToken<T> type) {
        return Results.ofChecked(() -> this.gson.fromJson(serialized, type.getType()));
    }
}
