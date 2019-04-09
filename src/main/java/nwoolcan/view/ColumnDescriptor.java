package nwoolcan.view;

/**
 * Class for representing a column in javafx binding.
 */
public class ColumnDescriptor {

    private final String columnName;
    private final String fieldName;

    /**
     * Default constructor.
     * @param columnName column name.
     * @param fieldName field name.
     */
    public ColumnDescriptor(final String columnName, final String fieldName) {
        this.columnName = columnName;
        this.fieldName = fieldName;
    }

    /**
     * Returns the column name.
     * @return the column name.
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * Returns the field name which is exactly the same name of the class field to represent.
     * @return the field name.
     */
    public String getFieldName() {
        return this.fieldName;
    }
}
