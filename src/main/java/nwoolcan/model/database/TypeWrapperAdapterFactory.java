package nwoolcan.model.database;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Factory to handle polymorphic type.
 */
public final class TypeWrapperAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        final TypeAdapterFactory thisFactory = this;
        return new TypeAdapter<T>() {

            private Class<?> getClassFromName(final String name) throws JsonParseException {
                try {
                    return Class.forName(name);
                } catch (ClassNotFoundException e) {
                    throw new JsonParseException(e.getMessage());
                }
            }

            /**
             * Wraps the actual object inside an object with a single property: the runtime class name.
             * @param out Output writer.
             * @param value The value to serialize.
             * @throws IOException When something goes wrong while writing to out.
             */
            @Override
            public void write(final JsonWriter out, final T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.beginObject();
                out.name(value.getClass().getName());

                gson.getDelegateAdapter(thisFactory, type).write(out, value);
                out.endObject();
            }

            /**
             * De-serializes a Json using the runtime type specified by {@link #read(JsonReader)}. If the actual compile-time type is parametrized, applies the same type parameters to the runtime type.
             * @param in Input reader.
             * @return The deserialized object.
             * @throws IOException When something goes wrong reading to in.
             */
            @Override
            @SuppressWarnings("unchecked")
            public T read(final JsonReader in) throws IOException {
                in.beginObject();
                final String className = in.nextName();
                final TypeToken<?> objType;
                if (type.getType() instanceof ParameterizedType) {
                    objType = TypeToken.getParameterized(this.getClassFromName(className), ((ParameterizedType) type.getType()).getActualTypeArguments());
                } else {
                    objType = TypeToken.get(this.getClassFromName(className));
                }
                final T obj = (T) gson.getDelegateAdapter(thisFactory, objType).read(in);
                in.endObject();
                return obj;
             }
        };
    }
}
