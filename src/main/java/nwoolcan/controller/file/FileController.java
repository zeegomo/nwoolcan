package nwoolcan.controller.file;

import java.io.File;

/**
 * Controller to handle files.
 */
public interface FileController {
    /**
     * Checks if the given filename is valid.
     * @param filename the filename to check.
     * @return if the given filename is valid.
     */
    boolean checkFilename(File filename);
    /**
     * Returns the valid file extension.
     * @return the valid file extension.
     */
    String getValidExtension();
}
