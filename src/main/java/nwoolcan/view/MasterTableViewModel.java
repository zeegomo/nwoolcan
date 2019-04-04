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

    /**
     * Default constructor.
     * @param columnDescriptors a list of column descriptors, specifying the name of the column
     *                          and the respective field name in the type {@link T}.
     * @param values the list of {@link T} representing the actual data to be displayed.
     */
    public MasterTableViewModel(final List<ColumnDescriptor> columnDescriptors, final List<T> values) {
        this.columnDescriptors = columnDescriptors;
        this.values = values;
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
}
