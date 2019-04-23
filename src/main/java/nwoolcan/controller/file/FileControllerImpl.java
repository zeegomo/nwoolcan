package nwoolcan.controller.file;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.database.Database;
import nwoolcan.model.database.DatabaseJsonFromJarImpl;
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

    @Override
    public Result<Empty> saveBreweryTo(final File filename, final Brewery toSave) {
        if (!this.checkFilename(filename)) {
            return Result.error(new IllegalArgumentException("Invalid filename"));
        }
        final Database db = new DatabaseJsonImpl(filename);
        return db.save(toSave);
    }

    @Override
    public Result<Brewery> loadBreweryFrom(final File filename) {
        if (!this.checkFilename(filename)) {
            return Result.error(new IllegalArgumentException("Invalid filename"));
        }
        final Database db = new DatabaseJsonImpl(filename);
        return db.load();
    }

    @Override
    public Result<Brewery> loadBreweryFromJar(final InputStream stream) {
        return new DatabaseJsonFromJarImpl(stream).load();
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
