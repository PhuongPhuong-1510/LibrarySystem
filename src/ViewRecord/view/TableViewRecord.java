package ViewRecord.view;

import ManageBook.view.BaseBookTableView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableViewRecord extends BaseBookTableView {
    private final int imageColumn1;
    private final int imageColumn2;

    public TableViewRecord(String[] columnNames, Object[][] data, int rowHeight, int imageColumn1, int imageColumn2) {
        super(columnNames, data, -1, rowHeight, -1);
        this.imageColumn1 = imageColumn1;
        this.imageColumn2 = imageColumn2;
    }

    @Override
    protected JTable createTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Không cho phép chỉnh sửa
                return false;
            }
        };

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (selectedRow == row && !isImageColumn(column)) {
                    c.setBackground(new Color(232, 232, 232));
                } else {
                    if (c instanceof JComponent && !(c instanceof JPanel)) {
                        setupDefaultCellAppearance((JComponent) c);
                    }
                }

                return c;
            }
        };
        return table;
    }


    private boolean isImageColumn(int column) {
        return column == imageColumn1 || column == imageColumn2;
    }
}