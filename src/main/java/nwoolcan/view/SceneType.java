package nwoolcan.view;

/**
 * Types of views of the application.
 * For each type must exist a resource with the same name, lowercased and with ".fxml" at the end
 */
public enum SceneType {
    /**
     * The dashboard scene.
     */
    DASHBOARD;

    /**
     * Name of the FXML files associated with this type of scene.
     * @return The resource name, relative to this package.
     */
    public String getResourceName() {
        return this.name().toLowerCase() + ".fxml";
    }
}
