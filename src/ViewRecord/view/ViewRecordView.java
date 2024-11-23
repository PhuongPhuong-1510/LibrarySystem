package ViewRecord.view;


import LoginPage.view.OvalButton;
import ViewRecord.controller.ViewRecordController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

public class ViewRecordView extends JPanel {
    private JPanel mainView;
    private TableViewRecord tableView;
    private JPanel searchPanel;
    private JTextField bookIDField;
    private JTextField nameIDField;
    private JTextField issueDateField;
    private JTextField dueDateField;
    private JButton searchButton;
    private JButton allRecordButton;
    private JButton issueDateButton;
    private JButton dueDateButton;

    public ViewRecordView() {
        this.setLayout(new BorderLayout());
        init();
        new ViewRecordController(this);
    }

    private void init() {
        mainView = new JPanel(new BorderLayout());
        tableView = createTableView();

        searchPanel = createSearchPanel();

        mainView.add(tableView, BorderLayout.CENTER);
        mainView.add(searchPanel,BorderLayout.NORTH);


        this.add(mainView, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private TableViewRecord createTableView() {
        String[] columnNames = createColumnNames();
        Object[][] data = fetchData();

        return new TableViewRecord(columnNames, data, 150, 3, 6) {
            @Override
            protected void centerTableCells(JTable table) {
                setCellAlignment(table, SwingConstants.CENTER);
            }

            @Override
            protected void configureColumnRenderers(JTable table) {
                table.setDefaultRenderer(Object.class, createMultiLineRenderer());
                table.getColumnModel().getColumn(1).setCellRenderer(createTooltipRenderer());
                table.getColumnModel().getColumn(3).setCellRenderer(createLabelRenderer());
                table.getColumnModel().getColumn(6).setCellRenderer(createLabelRenderer());
            }

            @Override
            protected void setTableColumnWidths(JTable table) {
                int[] columnWidths = {95, 100, 125, 140, 115, 130, 140, 110, 100, 110};
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                }
            }
        };
    }

    private void setCellAlignment(JTable table, int alignment) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(alignment);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 3 && i != 6) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }

    private String[] createColumnNames() {
        return new String[] {
                "Issue ID", "Book ID", "Book Name", "Book Image", "Student ID",
                "Student Name", "Student Image", "Issue Date", "Due Date", "Status"
        };
    }

    private Object[][] fetchData() {
        return new Object[][] {
                {"ISS001", "BK001", "Java Programming", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST001", "John Doe", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-01", "2024-10-15", "Issued"},
                {"ISS002", "BK002", "Python Programming", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST002", "Jane Smith", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-03", "2024-10-17", "Returned"},
                {"ISS003", "BK003", "C++ Fundamentals", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST003", "Alice Brown", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-05", "2024-10-19", "Overdue"},
                {"ISS004", "BK004", "Data Structures", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST004", "Michael Johnson", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-07", "2024-10-21", "Issued"},
                {"ISS005", "BK005", "Operating Systems", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST005", "Emily Davis", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-09", "2024-10-23", "Returned"},
                {"ISS006", "BK006", "Machine Learning", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST006", "Robert Wilson", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-11", "2024-10-25", "Overdue"},
                {"ISS007", "BK007", "Network Security", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST007", "Linda Clark", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-13", "2024-10-27", "Issued"},
                {"ISS008", "BK008", "Artificial Intelligence", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST008", "David Martinez", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-15", "2024-10-29", "Returned"},
                {"ISS009", "BK009", "Web Development", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST009", "Jennifer Lee", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-17", "2024-10-31", "Issued"},
                {"ISS010", "BK010", "Database Systems", createImageLabel("/ViewRecord/view/icon/1.jpg"), "ST010", "James Anderson", createImageLabel("/ViewRecord/view/icon/1.jpg"), "2024-10-19", "2024-11-02", "Overdue"}
        };
    }

    private JLabel createImageLabel(String path) {
        ImageIcon icon;
        if (path != null && getClass().getResource(path) != null) {
            icon = new ImageIcon(getClass().getResource(path));
        } else {
            System.out.println("Image not found at path: " + path);
            icon = new ImageIcon(new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB)); // Placeholder image
        }
        return new JLabel(icon);
    }

    private JPanel createSearchPanel() {
        JPanel searchPane = new JPanel();
        searchPane.setBackground(new Color(150, 180, 255));
        searchPane.setLayout(null);

        bookIDField = createSearchField("Search Book ID", 700, 10);
        nameIDField = createSearchField("Search Name ID", 700, 50);
        issueDateField = createSearchField("Search Issue Date", 870, 10);
        dueDateField = createSearchField("Search Due Date", 870, 50);

        searchButton = createButton("SEARCH", 1050, 10);
        allRecordButton = createButton("ALL RECORD", 1050, 50);


        issueDateButton=createDatePickerButton(1005,10);
        dueDateButton=createDatePickerButton(1005,50);


        searchPane.add(bookIDField);
        searchPane.add(nameIDField);
        searchPane.add(issueDateField);
        searchPane.add(dueDateField);
        searchPane.add(searchButton);
        searchPane.add(allRecordButton);
        searchPane.add(issueDateButton);
        searchPane.add(dueDateButton);


        searchPane.setPreferredSize(new Dimension(1200, 85));


        return searchPane;


    }

    protected JTextField createSearchField(String placeholder,int x,int y) {
        JTextField searchField = new JTextField(15);
        searchField.setBounds(x,y,130,25);
        searchField.setBorder(BorderFactory.createEmptyBorder());

        searchField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBackground(new Color(255,245,238));
        searchField.setForeground(Color.GRAY);
        searchField.setOpaque(true);
        searchField.setHorizontalAlignment(JTextField.CENTER);
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


        return searchField;
    }
    private JButton createButton(String text, int x, int y) {
        JButton button = new OvalButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 13));
        button.setBackground(new Color(255,130,171));
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, 117, 25);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    private JButton createDatePickerButton(int x, int y) {
        JButton button = new JButton();
        button.setBounds(x, y, 25, 25);
        button.setBackground(new Color(51, 153, 253));
        return button;
    }

    public JPanel getMainView() {
        return mainView;
    }

    public TableViewRecord getTableView() {
        return tableView;
    }

    public JPanel getSearchPanel() {
        return searchPanel;
    }

    public JTextField getBookIDField() {
        return bookIDField;
    }

    public JTextField getNameIDField() {
        return nameIDField;
    }

    public JTextField getIssueDateField() {
        return issueDateField;
    }

    public JTextField getDueDateField() {
        return dueDateField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getAllRecordButton() {
        return allRecordButton;
    }

    public JButton getIssueDateButton() {
        return issueDateButton;
    }

    public JButton getDueDateButton() {
        return dueDateButton;
    }
}