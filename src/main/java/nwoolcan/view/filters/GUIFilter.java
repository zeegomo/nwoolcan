package nwoolcan.view.filters;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

/**
 * A filter to be used in the GUI.
 * @param <T> the type of filter value.
 */
public abstract class GUIFilter<T> extends AnchorPane {

    private Label label = new Label();
    /**
     * Returns the filter selected value. Empty optional if no value selected.
     * @return the filter selected value. Empty optional if no value selected.
     */
    abstract Optional<T> getValue();

    /**
     * Return the label of the node.
     * @return the label of the node.
     */
    public Label getLabel() {
        return this.label;
    }

    /**
     * Removes the value from the filter.
     */
    abstract void resetValue();
}
