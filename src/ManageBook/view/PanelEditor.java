package ManageBook.view;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class PanelEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel currentPanel;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof JPanel) {
            currentPanel = (JPanel) value; // Lưu trữ panel hiện tại
            return currentPanel;
        }
        currentPanel = new JPanel(); // Trả về JPanel trống nếu không phải JPanel
        return currentPanel;
    }

    @Override
    public Object getCellEditorValue() {
        return currentPanel; // Trả về panel đang được chỉnh sửa
    }
}