//package ViewRecord.view;
//
//import ManageBook.view.BaseBookTableView;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import java.awt.*;
//
//public class TableViewRecord extends BaseBookTableView {
//    private final int imageColumn1;
//    private final int imageColumn2;
//
//    public TableViewRecord(String[] columnNames, Object[][] data, int rowHeight, int imageColumn1, int imageColumn2) {
//        super(columnNames, data, -1, rowHeight, -1);
//        this.imageColumn1 = imageColumn1;
//        this.imageColumn2 = imageColumn2;
//    }
//
//    @Override
//    protected JTable createTable(Object[][] data, String[] columnNames) {
//        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                // Không cho phép chỉnh sửa
//                return false;
//            }
//        };
//
//        JTable table = new JTable(model) {
//            @Override
//            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//                Component c = super.prepareRenderer(renderer, row, column);
//
//                if (selectedRow == row && !isImageColumn(column)) {
//                    c.setBackground(new Color(232, 232, 232));
//                } else {
//                    if (c instanceof JComponent && !(c instanceof JPanel)) {
//                        setupDefaultCellAppearance((JComponent) c);
//                    }
//                }
//
//                return c;
//            }
//        };
//        return table;
//    }
//
//
//    private boolean isImageColumn(int column) {
//        return column == imageColumn1 || column == imageColumn2;
//    }
//}
package ViewRecord.view;

import ManageBook.view.BaseBookTableView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableViewRecord extends BaseBookTableView {
    private final int imageColumn1;
    private final int imageColumn2;
    private final int rowHeight; // Thêm biến rowHeight

    public TableViewRecord(String[] columnNames, Object[][] data, int rowHeight, int imageColumn1, int imageColumn2) {
        super(columnNames, data, -1, rowHeight, -1);
        this.rowHeight = rowHeight; // Lưu chiều cao hàng
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
                if (isImageColumn(column)) {
                    Object value = getValueAt(row, column);
                    return createImageComponent(value);
                }
                return super.prepareRenderer(renderer, row, column);
            }
        };

        // Sử dụng rowHeight để đặt chiều cao hàng
        table.setRowHeight(rowHeight > 0 ? rowHeight : 30); // Mặc định là 30 nếu không có giá trị
        return table;
    }

    /**
     * Kiểm tra xem cột có phải là cột chứa hình ảnh không
     */
    private boolean isImageColumn(int column) {
        return column == imageColumn1 || column == imageColumn2;
    }

    /**
     * Tạo component hiển thị hình ảnh trong bảng
     */
    private Component createImageComponent(Object value) {
        if (value instanceof String) {
            String imagePath = (String) value;
            ImageIcon icon = new ImageIcon(imagePath);
            JLabel label = new JLabel(icon);
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
        return new JLabel("No Image");
    }
}
