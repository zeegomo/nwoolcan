package nwoolcan.model.database;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import nwoolcan.utils.Result;

import java.io.IOException;
import java.lang.reflect.Type;

public class InterfaceAdapter<T> extends TypeAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    final String CLASSNAME_FIELD = "CLASSNAME";
    final String DATA_FIELD = "DATA";
    final Gson baseSerializer;

    public InterfaceAdapter(final Gson baseSerializer) {
        this.baseSerializer = baseSerializer;
    }

    @Override
    public T deserialize(final JsonElement jsonElement, final Type type, JsonDeserializationContext deserializationContext) throws JsonParseException {
        final JsonObject obj = jsonElement.getAsJsonObject();
        final JsonPrimitive classNamePrimitive = (JsonPrimitive) obj.get(CLASSNAME_FIELD);
        final String className = classNamePrimitive.getAsString();
        final Class desClass = getClassFromName(className);
        return deserializationContext.deserialize(jsonElement, desClass);
    }

    @Override
    public JsonElement serialize(T src, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonObject obj = new JsonObject();
        obj.addProperty(CLASSNAME_FIELD, src.getClass().getName());
        obj.add(DATA_FIELD, context.serialize(src));
        return obj;
    }


    private Class<?> getClassFromName(final String name) throws JsonParseException {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public void write(final JsonWriter out, final T value) throws IOException {
        out.beginObject();
        out.name(CLASSNAME_FIELD).value(value.getClass().getName());
        out.name(DATA_FIELD);
    }

    @Override
    public T read(final JsonReader in) throws IOException {
        return null;
    }
}