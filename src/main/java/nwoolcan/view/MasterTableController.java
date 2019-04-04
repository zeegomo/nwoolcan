package nwoolcan.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
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

    @Override
    public void initData(final MasterTableViewModel<T> data) {
        data.getColumnDescriptors().stream().map(cd -> {
            TableColumn<T, String> col = new TableColumn<>(cd.getColumnName());
            col.setCellValueFactory(new PropertyValueFactory<>(cd.getFieldName()));
            return col;
        }).forEach(tc -> masterTable.getColumns().add(tc));

        masterTable.setItems(FXCollections.observableArrayList(data.getValues()));
    }

    @Override
    protected SubView getSubView() {
        return this.masterTableSubView;
    }

}
