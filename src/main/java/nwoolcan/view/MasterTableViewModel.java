package nwoolcan.view;

import java.util.Collections;
import java.util.List;

/**
 * Class that represent a generic view model to use as a master list with a {@link javafx.scene.control.TableView}.
 *
 * The column descriptors describe the columns of the table view and how are associated to the actual data.
 * @param <T> the type of one data row to display.
 */
public class MasterTableViewModel<T> {

    private final List<ColumnDescriptor> columnDescriptors;
    private final List<T> values;
    private final ViewType detailViewType;

    /**
     * Default constructor.
     * @param columnDescriptors a list of column descriptors, specifying the name of the column
     *                          and the respective field name in the type {@link T}.
     * @param values the list of {@link T} representing the actual data to be displayed.
     * @param detailViewType view type for the detail
     */
    public MasterTableViewModel(final List<ColumnDescriptor> columnDescriptors, final List<T> values, final ViewType detailViewType) {
        this.columnDescriptors = columnDescriptors;
        this.values = values;
        this.detailViewType = detailViewType;
    }

    /**
     * Returns a list of column descriptors, specifying the name of the column
     * and the respective field name in the type {@link T}.
     * @return the list of column descriptors.
     */
    public List<ColumnDescriptor> getColumnDescriptors() {
        return Collections.unmodifiableList(this.columnDescriptors);
    }

    /**
     * Returns a list of {@link T} representing the actual data to be displayed.
     * @return a list of {@link T} representing the actual data to be displayed.
     */
    public List<T> getValues() {
        return Collections.unmodifiableList(this.values);
    }

    /**
     * Returns the type of the view to display the detail of an object.
     * @return the type of the view to display the detail of an object.
     */
    public ViewType getDetailViewType() {
        return this.detailViewType;
    }
}
