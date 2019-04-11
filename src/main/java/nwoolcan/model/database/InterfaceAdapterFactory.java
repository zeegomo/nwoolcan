package nwoolcan.model.database;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class InterfaceAdapterFactory implements TypeAdapterFactory {

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

            @Override
            public T read(final JsonReader in) throws IOException {
                in.beginObject();
                final String className = in.nextName();
                final TypeToken<?> objType = TypeToken.get(this.getClassFromName(className));
                final T obj = (T) gson.getDelegateAdapter(thisFactory, objType).read(in);
                in.endObject();
                return obj;
             }
        };
    }
}
