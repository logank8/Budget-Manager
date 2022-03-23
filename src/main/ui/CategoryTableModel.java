package ui;

import model.Category;
import org.json.JSONObject;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CategoryTableModel extends AbstractTableModel {

    private final List<Category> categories;

    private BudgetGUI parent;

    private final String[] columnNames = new String[]{
            "Name", "Value"
    };

    @SuppressWarnings({"rawtypes", "checkstyle:SuppressWarnings"})
    private final Class[] columnClass = new Class[] {
            String.class, String.class
    };

    public CategoryTableModel(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return columnClass[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Category row = categories.get(rowIndex);
        if (columnIndex == 0) {
            return row.getTitle();
        } else if (columnIndex == 1) {
            return row.getValString();
        }
        return null;
    }

}
