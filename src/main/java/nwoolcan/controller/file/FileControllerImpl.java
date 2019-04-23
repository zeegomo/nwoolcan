package nwoolcan.controller.file;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.database.Database;
import nwoolcan.model.database.DatabaseJsonImpl;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Controller to handle files.
 */
public final class FileControllerImpl implements FileController {
    private static final String VALID_EXTENSION = "nws";

    /**
     * Saves the current state of the Brewery to a file with the given name.
     * @param filename the name of the file to be saved.
     * @param toSave the object to save.
     * @return a {@link Result} describing the operation's outcome.
     */
    public Result<Empty> saveTo(final File filename, final Brewery toSave) {
        if (!this.checkFilename(filename)) {
            return Result.error(new IllegalArgumentException("Invalid filename"));
        }
        final Database db = new DatabaseJsonImpl(filename);
        return db.save(toSave);
    }
    /**
     * Loads the state of the Brewery from the file with the given name.
     * @param filename the name of the file to load.
     * @return a {@link Result} describing the operation's outcome.
     */
    public Result<Brewery> loadFrom(final File filename) {
        if (!this.checkFilename(filename)) {
            return Result.error(new IllegalArgumentException("Invalid filename"));
        }
        final Database db = new DatabaseJsonImpl(filename);
        return db.load();
    }

    /**
     * Loads the state of the Brewery from the file with the given name.
     * @param stream the name of the file to load.
     * @return a {@link Result} describing the operation's outcome.
     */
    public Result<Brewery> loadFromJAR(final InputStream stream) {
        return DatabaseJsonImpl.loadFromJAR(stream);
    }

    @Override
    public boolean checkFilename(final File filename) {
        final List<String> extensions = Arrays.asList(filename.getName().split("\\."));
        return extensions.size() >= 2 && extensions.get(extensions.size() - 1).equals(VALID_EXTENSION);
    }

    @Override
    public String getValidExtension() {
        return VALID_EXTENSION;
    }
}
