package nwoolcan.model.database;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;

public class InterfaceAdapterFactory implements TypeAdapterFactory {

    final String CLASSNAME_FIELD = "className";
    final String DATA_FIELD = "data";

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
                out.name(CLASSNAME_FIELD).value(value.getClass().getName());

                out.name(DATA_FIELD);
                gson.getDelegateAdapter(thisFactory, type).write(out, value);
                out.endObject();
            }

            @Override
            public T read(final JsonReader in) throws IOException {
                TypeToken jsonType = null;
                JsonElement jsonObj = null;
                in.beginObject();
                while (in.hasNext()) {
                    final String name = in.nextName();
                    if (name.equals(CLASSNAME_FIELD)) {
                        jsonType = TypeToken.get(this.getClassFromName(in.nextString()));
                    } else if (name.equals(DATA_FIELD)) {

                    } else {
                        in.skipValue();
                    }
                }
                in.endObject();
                final TypeAdapter<?> resultAdapter = gson.getDelegateAdapter(thisFactory, jsonType);
                final JsonReader objReader = new Gson().newJsonReader(new StringReader(new Gson().toJson(jsonObj)));
                return (T)resultAdapter.read(objReader);
             }
        };
    }
}
