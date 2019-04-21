package nwoolcan.view.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import nwoolcan.controller.file.FileController;

import java.io.File;
import java.util.Optional;

/**
 * Useful stuff when saving and loading.
 */
public final class PersistencyUtils {
    private final FileChooser.ExtensionFilter defaultFilter;
    private final Window owner;
    private final FileController fileController;

    /**
     * Initializes the owner of the dialogs to open.
     * @param owner the owner window for the dialogs.
     * @param fileController predicate to check is a filename is valid.
     */
    public PersistencyUtils(final Window owner, final FileController fileController) {
        this.owner = owner;
        this.fileController = fileController;
        this.defaultFilter = new FileChooser.ExtensionFilter("nWoolcan save file (*." + fileController.getValidExtension() + ")",
            "*." + fileController.getValidExtension());
    }

    private void showFilenameError() {
        final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setTitle("Error");
        errorAlert.setContentText("Invalid filename! The extension must be " + this.defaultFilter.getExtensions().get(0));
        errorAlert.initOwner(this.owner);
        errorAlert.showAndWait();
    }

    private Optional<File> showFileChooser(final boolean openMode) {
        Optional<File> target;
        boolean ok;
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(this.defaultFilter);
        do {
            if (openMode) {
                target = Optional.ofNullable(fileChooser.showOpenDialog(this.owner));
            } else {
                target = Optional.ofNullable(fileChooser.showSaveDialog(this.owner));
            }
            ok = !target.isPresent() || fileController.checkFilename(target.get());
            if (!ok) {
                this.showFilenameError();
            }
        } while (!ok);
        return target;
    }

    /**
     * Show a file picker in save mode.
     * @return an optional containing the selected file.
     */
    public Optional<File> showSaveFile() {
        return this.showFileChooser(false);
    }

    /**
     * Show a file picker in open mode.
     * @return an optional containing the selected file.
     */
    public Optional<File> showOpenFile() {
        return this.showFileChooser(true);
    }

    /**
     * Show an error.
     */
    public void showErrorAlert() {
        final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setContentText("An error occurred!");
        errorAlert.setHeaderText("Error");
        errorAlert.setTitle("Error");
        errorAlert.initOwner(this.owner);
        errorAlert.showAndWait();
    }

    /**
     * Show an alert for successful save.
     */
    public void showSaveSuccessAlert() {
        final Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setContentText("Saved successfully!");
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Success!");
        successAlert.initOwner(this.owner);
        successAlert.showAndWait();
    }

    /**
     * Ask the user if it wants to save.
     * @return a boolean indicating if the user confirmed.
     */
    public boolean askSaveConfirmation() {
        final Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save?", ButtonType.YES, ButtonType.NO);
        saveAlert.initOwner(this.owner);
        saveAlert.setTitle("Save?");
        saveAlert.setHeaderText("Save?");
        return saveAlert.showAndWait().map(buttonType -> buttonType.equals(ButtonType.YES)).orElse(false);
    }
}
