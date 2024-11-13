package ManageBook.view;

import HomePage.view.CustomScrollBarUI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public abstract class BaseBookTableView extends JPanel {
    protected JPanel tablePanel;
    protected String[] columnNames;
    protected int rowCount;
    protected int editColumn;
    protected int imageColumn;
    protected int rowHeight;
    protected int selectedRow = -1;



    public BaseBookTableView(String[] columnNames, Object[][] data,int editColumn,int rowHeight,int imageColumn) {
        this.imageColumn=imageColumn;
        this.rowHeight=rowHeight;
        this.editColumn=editColumn;
        this.columnNames = columnNames;
        this.setLayout(new BorderLayout());
        this.tablePanel = createTablePanel(data, columnNames,rowCount);
        this.setLayout(new BorderLayout());
        JTable table=createTable(data,columnNames);
        configureColumnRenderers(table);
        configureTable(table,rowCount);
        JScrollPane scrollPane = createScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        this.add(tablePanel,BorderLayout.CENTER);

    }








    private JPanel createTablePanel(Object[][] data, String[] columnNames, int rowCount) {
        JTable table = createTable(data, columnNames);


        configureColumnRenderers(table);
        configureTable(table, rowCount);


        JScrollPane scrollPane = createScrollPane(table);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));


        return panel;
    }


    // Tạo bảng với cấu hình không cho phép chỉnh sửa
    protected JTable createTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return(column == editColumn || row == selectedRow) && column!=imageColumn;
            }
        };

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (selectedRow == row && column != editColumn) {
                    c.setBackground(new Color(232, 232, 232)); // Màu nền khi chọn hàng
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

    // Cấu hình kiểu hiển thị cho các cột
    protected void configureColumnRenderers(JTable table) {
        table.setDefaultRenderer(Object.class, createMultiLineRenderer());

    }

    // Thiết lập mặc định cho ô
    protected void setupDefaultCellAppearance(JComponent component) {
        component.setFont(new Font("Tahoma", Font.PLAIN, 13));
        component.setBackground(new Color(255, 240, 245));
        component.setForeground(new Color(79,79,79));
        component.setBorder(null);
    }

    // Renderer cho tooltip ở cột thứ hai
    protected TableCellRenderer createTooltipRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setToolTipText(value != null && value.toString().length() > 20 ? value.toString() : null);
                return c;
            }
        };
    }

    // Renderer cho JTextArea hỗ trợ ngắt dòng
    protected TableCellRenderer createMultiLineRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JTextArea textArea = new JTextArea(value != null ? value.toString() : "");
                setupMultiLineAppearance(textArea, table, isSelected);
                adjustRowHeight(table, row, textArea);
                return textArea;
            }
        };
    }

    // Renderer cho JLabel ở cột thứ ba
    protected TableCellRenderer createLabelRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (value instanceof JLabel) ? (JLabel) value : (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(255, 240, 245));


                label.setOpaque(true); // Để màu nền có hiệu lực

                return label;
            }
        };
    }

    protected TableCellRenderer createPanelRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof JPanel) {
                    JPanel panel = (JPanel) value;
                    panel.setBackground(new Color(255, 240, 245));


                    return panel;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
    }


    // Thiết lập JTextArea để hiển thị dạng ngắt dòng
    protected void setupMultiLineAppearance(JTextArea textArea, JTable table, boolean isSelected) {
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(true);
        textArea.setEditable(false);
        textArea.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        textArea.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
    }

    // Điều chỉnh chiều cao hàng để phù hợp với JTextArea
    protected void adjustRowHeight(JTable table, int row, JTextArea textArea) {
        int preferredHeight = textArea.getPreferredSize().height;
        if (table.getRowHeight(row) != preferredHeight) {
            table.setRowHeight(row, preferredHeight);
        }
    }


    protected void configureTable(JTable table, int rowCount) {
        table.setRowHeight(rowHeight);
        table.setShowGrid(false); // Ẩn lưới giữa các ô
        table.setBackground(new Color(238, 210, 238));
        table.setIntercellSpacing(new Dimension(2, 10));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        centerTableCells(table);
        setTableColumnWidths(table);
        setTableHeaderAlignment(table);
    }


    protected JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(238, 210, 238)); // Màu nền cho vùng hiển thị của JScrollPane


        CustomScrollBarUI verticalScrollBarUI = new CustomScrollBarUI();
        verticalScrollBarUI.setColors(new Color(205, 201, 201), new Color(232, 232, 232));
        scrollPane.getVerticalScrollBar().setUI(verticalScrollBarUI); // Ghi đè UI cho thanh cuộn dọc

        CustomScrollBarUI horizontalScrollBarUI = new CustomScrollBarUI();
        horizontalScrollBarUI.setColors(new Color(205, 201, 201), new Color(232, 232, 232));
        scrollPane.getHorizontalScrollBar().setUI(horizontalScrollBarUI); // Ghi đè UI cho thanh cuộn ngang

        scrollPane.setPreferredSize(new Dimension(1200, getHeight()));
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }


    protected void centerTableCells(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        }

    }

    private void setTableHeaderAlignment(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(238, 210, 238));
        header.setPreferredSize(new Dimension(header.getWidth(), 50));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBackground(new Color(255, 240, 245));
                setForeground(new Color(105  , 105  , 105  ));
                setFont(new Font("Tahoma", Font.BOLD, 15));
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(238, 210, 238)));
                return component;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }


    protected void setTableColumnWidths(JTable table) {
        TableColumn column;


        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }
    }
    public void setSelectedRow(int row) {
        this.selectedRow = row;
        repaint();  // Cập nhật lại giao diện
    }

}