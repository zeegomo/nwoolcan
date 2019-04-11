package nwoolcan.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import nwoolcan.controller.Controller;
import nwoolcan.view.subview.SubView;

/**
 * The controller for a master table representing data of type {@link T}.
 * @param <T> the type of the data to display.
 */
@SuppressWarnings("NullAway")
public final class MasterTableController<T> extends SubViewController implements InitializableController<MasterTableViewModel<T>> {

    @FXML
    private TableView<T> masterTable;

    @FXML
    private FlowPane filtersPane;

    @FXML
    private SubView masterTableSubView;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public MasterTableController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final MasterTableViewModel<T> data) {
        data.getColumnDescriptors().stream().map(cd -> {
            TableColumn<T, String> col = new TableColumn<>(cd.getColumnName());
            col.setCellValueFactory(new PropertyValueFactory<>(cd.getFieldName()));
            return col;
        }).forEach(tc -> masterTable.getColumns().add(tc));

        // Add "action" column
        final TableColumn<T, Button> actionColumn = new TableColumn<>();
        actionColumn.setCellValueFactory(obj -> {
            final Button btn = new Button("View");
            btn.setOnAction(event -> this.overlayView(data.getDetailViewType(), obj.getValue()));
            return new SimpleObjectProperty<>(btn);
        });
        masterTable.getColumns().add(actionColumn);

        masterTable.setItems(FXCollections.observableArrayList(data.getValues()));
    }

    @Override
    protected SubView getSubView() {
        return this.masterTableSubView;
    }

    @FXML
    void btnBackClick(final ActionEvent event) {
        this.previousView();
    }
}
