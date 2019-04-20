package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * A component that container and handles multiple filters.
 */
public final class FiltersBar extends HBox {
    private static final int BUTTONS_FILTERS_HSPACING = 10;
    private static final int BUTTONS_VSPACING = 2;
    private static final int FILTERS_HSPACING = 5;

    private final List<GUIFilter<?>> filters;
    private final Button applyButton;

    private void resetFilters() {
        this.filters.forEach(GUIFilter::resetValue);
        this.applyButton.fire();
    }

    /**
     * Create a new Filter bar containing the given filters.
     * @param filters the filters contained in this bar.
     */
    public FiltersBar(@NamedArg("filters") final List<GUIFilter<?>> filters) {
        this.filters = filters;

        this.setSpacing(BUTTONS_FILTERS_HSPACING);

        final VBox buttonsBox = new VBox(BUTTONS_VSPACING);
        this.getChildren().add(buttonsBox);
        this.applyButton = new Button("Apply filters");
        final Button resetButton = new Button("Reset filters");
        resetButton.setOnAction(event -> this.resetFilters());
        buttonsBox.getChildren().add(this.applyButton);
        buttonsBox.getChildren().add(resetButton);

        final FlowPane filtersPane = new FlowPane();
        filtersPane.getChildren().addAll(this.filters);
        this.getChildren().add(filtersPane);
        filtersPane.setHgap(FILTERS_HSPACING);
        filtersPane.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(filtersPane, Priority.ALWAYS);
    }

    /**
     * Event called when the "Apply filters" button is clicked.
     * @return a property to handle the apply filters event.
     */
    public ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
        return this.applyButton.onActionProperty();
    }

    /**
     * Set apply event handler.
     * @param handler the handler to handle apply event.
     */
    public void setOnAction(final EventHandler<ActionEvent> handler) {
        this.onActionProperty().set(handler);
    }

    /**
     * Returns the apply event handler.
     * @return the apply event handler.
     */
    public EventHandler<ActionEvent> getOnAction() {
        return onActionProperty().get();
    }
}
