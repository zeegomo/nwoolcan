package nwoolcan.model.database;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

/**
 * Saves/loads data to/from a persistent storage.
 */
public interface Database {
    /**
     * Saves a {@link Brewery}.
     * @param toSave Object to be saved
     * @return A {@link Result} with an error if something went wrong.
     */
    Result<Empty> save(Brewery toSave);

    /**
     * Loads a {@link Brewery}.
     * @return A {@link Result} containing the deserialized {@link Brewery} or an error if something went wrong.
     */
    Result<Brewery> load();
}
