package ManageBook.view;

import HomePage.view.CustomScrollBarUI;
import ManageBook.controller.ManagementBookController;


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

public class ManagementBookView extends JPanel {
    private JPanel managementBooks;
    private ManagementBookController managementBookController;
    private JButton editButton;
    private JButton deleteButton;
    private JButton addBookButton;


    public ManagementBookView() {
        this.setLayout(new BorderLayout());
        this.init();
        this.managementBookController =new ManagementBookController(this);
    }

    private void init() {
        managementBooks = new JPanel(new BorderLayout());
        managementBooks.add(createBookDetails(), BorderLayout.CENTER);
        managementBooks.add(createNorthPanel(), BorderLayout.NORTH);

        this.add(managementBooks, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public String convertToHtml(String input) {
        StringBuilder htmlString = new StringBuilder(input.length());
        for (char c : input.toCharArray()) {
            switch (c) {
                case '&' -> htmlString.append("&amp;");
                case '<' -> htmlString.append("&lt;");
                case '>' -> htmlString.append("&gt;");
                case '"' -> htmlString.append("&quot;");
                case '\'' -> htmlString.append("&#39;");
                case '\n' -> htmlString.append("<br>");
                default -> htmlString.append(c);
            }
        }
        return "<html>" + htmlString + "</html>";
    }



    private JPanel createBookDetails() {
        String[] columnNames = {"Book ID", "Name Book", "Image", "Author", "Category", "Language", "Total", "Current", "Position", "Action"};

        Object[][] data = {
                {1,
                        convertToHtml("Sunset\nJohn Doe's \"Sunset\" is a captivating exploration of nature’s beauty as seen through the tranquil moments of twilight."),
                        createImageLabel("/ManageBook/icon/1.jpg"),
                        "John Doe",
                        "Nature",
                        "English", 10, 5, "Shelf A1",
                        null
                },
                {2, convertToHtml("Ocean\nJane Smith's \"Ocean\" takes readers on an adventure across the seas."),
                        createImageLabel("/ManageBook/icon/1.jpg"),
                        "Jane Smith",
                        "Adventure",
                        "English", 15, 7, "Shelf B2",
                        null

                },
                {3, convertToHtml("Mountain\nMike Brown's \"Mountain\" captures the essence of high-altitude travel."), createImageLabel("/ManageBook/icon/1.jpg"), "Mike Brown", "Travel", "Spanish", 8, 4, "Shelf C3",
                        null}

        };

        return createTablePanel(data, columnNames, 2000);
    }



    private JLabel createImageLabel(String path) {
        ImageIcon icon;
        if (path != null && getClass().getResource(path) != null) {
            icon = new ImageIcon(getClass().getResource(path));
        } else {
            System.out.println("Image not found at path: " + path);
            icon = new ImageIcon(new BufferedImage(05, 05, BufferedImage.TYPE_INT_ARGB)); // Placeholder
        }
        return new JLabel(icon);
    }
    private JButton createActionButton(String iconPath, Color bgColor) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(iconPath)));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(20, 20));
        return button;
    }
    private JPanel createAction(int row) {
        JPanel actionPanel = new JPanel(new GridBagLayout());
        editButton = createActionButton("/ManageBook/icon/pen.jpg", new Color(255, 240, 245));
        deleteButton = createActionButton("/ManageBook/icon/bin.jpg", new Color(255, 240, 245));



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 7, 0, 7);
        gbc.anchor = GridBagConstraints.CENTER;

        actionPanel.add(editButton, gbc);
        gbc.gridx = 1;
        actionPanel.add(deleteButton, gbc);

        return actionPanel;
    }




    private JPanel createTablePanel(Object[][] data, String[] columnNames, int rowCount) {
        JTable table = createTable(data, columnNames);


        configureColumnRenderers(table);
        configureTable(table, rowCount);


        JScrollPane scrollPane = createScrollPane(table);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }


    // Tạo bảng với cấu hình không cho phép chỉnh sửa
    private JTable createTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
        };

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent && !(c instanceof JPanel)) {
                    setupDefaultCellAppearance((JComponent) c);
                }
                return c;
            }

        };
        return table;
    }

    // Cấu hình kiểu hiển thị cho các cột
    private void configureColumnRenderers(JTable table) {
        table.getColumnModel().getColumn(1).setCellRenderer(createTooltipRenderer());
        table.setDefaultRenderer(Object.class, createMultiLineRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(createLabelRenderer());
        table.getColumnModel().getColumn(9).setCellRenderer(createPanelRenderer());



    }

    // Thiết lập mặc định cho ô
    private void setupDefaultCellAppearance(JComponent component) {
        component.setFont(new Font("Rubik", Font.PLAIN, 13));
        component.setBackground(new Color(255, 240, 245));
        component.setForeground(Color.BLACK);
        component.setBorder(null);
    }

    // Renderer cho tooltip ở cột thứ hai
    private TableCellRenderer createTooltipRenderer() {
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
    private TableCellRenderer createMultiLineRenderer() {
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
    private TableCellRenderer createLabelRenderer() {
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

    private TableCellRenderer createPanelRenderer() {
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
    private void setupMultiLineAppearance(JTextArea textArea, JTable table, boolean isSelected) {
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(true);
        textArea.setEditable(false);
        textArea.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        textArea.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
    }

    // Điều chỉnh chiều cao hàng để phù hợp với JTextArea
    private void adjustRowHeight(JTable table, int row, JTextArea textArea) {
        int preferredHeight = textArea.getPreferredSize().height;
        if (table.getRowHeight(row) != preferredHeight) {
            table.setRowHeight(row, preferredHeight);
        }
    }


    private void configureTable(JTable table, int rowCount) {
        table.setRowHeight(180);
        table.setShowGrid(false); // Ẩn lưới giữa các ô
        table.setBackground(new Color(238, 210, 238));
        table.setIntercellSpacing(new Dimension(2, 10));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        centerTableCells(table);
        setTableColumnWidths(table);
        setTableHeaderAlignment(table);
    }


    private JScrollPane createScrollPane(JTable table) {
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


    private void centerTableCells(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 2&&i!=9) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

        }
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(createAction(i), i, 9); // Cột 9 là cột "Action"
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
                setForeground(new Color(54, 54, 54));
                setFont(new Font("Tahoma", Font.LAYOUT_NO_LIMIT_CONTEXT, 15));
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(238, 210, 238)));
                return component;
            }
        };

        // Đặt renderer cho tất cả các cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }


    private void setTableColumnWidths(JTable table) {
        TableColumn column;

        int[] columnWidths = {80, 340, 120, 80, 100, 80, 70, 80, 70, 90};

        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }

    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northPanel.setBackground(new Color(150, 180, 255));
        northPanel.setBorder(null);

        JPanel searchField = createSearchPanel();
        JButton addBookButton = createAddBookButton();

        northPanel.add(searchField);
        northPanel.add(addBookButton);

        return northPanel;
    }


    private JPanel createSearchPanel() {
        String placeholder = "Search id,author, genre, book title";

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setForeground(Color.GRAY);
        searchField.setText(placeholder);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText(placeholder);
                }
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        searchPanel.setBackground(new Color(150, 180, 255));


        return searchPanel;
    }


    private JButton createAddBookButton() {
        addBookButton = new JButton("Add Book");
        addBookButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        addBookButton.setBackground(new Color(0, 123, 255));
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBookButton.setPreferredSize(new Dimension(90, 30));
        addBookButton.setBorderPainted(false);
        addBookButton.setFocusPainted(false);
        return addBookButton;
    }

    public JButton getEditButton() {
        return editButton;
    }



    public JButton getDeleteButton() {
        return deleteButton;
    }


    public JButton getAddBookButton() {
        return addBookButton;
    }


}