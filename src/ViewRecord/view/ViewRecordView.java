
package ViewRecord.view;


import LoginPage.view.OvalButton;
import MainApp.model.Book;
import MainApp.model.Issue;
import MainApp.model.Student;
import ViewRecord.controller.ViewRecordController;
import MainApp.model.LibraryModelManage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    public LibraryModelManage libraryModelManage;

    public ViewRecordView(LibraryModelManage libraryModelManage) {
        this.libraryModelManage = libraryModelManage;
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
        // Giả sử bạn gọi fetchData với 4 tham số rỗng
        Object[][] data = fetchData("", "", "", "");


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

    public Object[][] fetchData(String bookID, String nameID, String issueDate, String dueDate) {
        try {
            ArrayList<Issue> issues = libraryModelManage.getIssuesList();
            ArrayList<Issue> filteredIssues = new ArrayList<>();

            for (Issue issue : issues) {
                // Kiểm tra điều kiện lọc
                boolean matchBookID = bookID.isEmpty() || issue.getIssueBookID().contains(bookID);
                boolean matchNameID = nameID.isEmpty() || issue.getIssueStudentID().contains(nameID);
                boolean matchIssueDate = issueDate.isEmpty() || issue.getIssueDate().toString().contains(issueDate);
                boolean matchDueDate = dueDate.isEmpty() || issue.getDueDate().toString().contains(dueDate);

                // Nếu tất cả điều kiện đều khớp, thêm vào danh sách
                if (matchBookID && matchNameID && matchIssueDate && matchDueDate) {
                    filteredIssues.add(issue);
                }
            }

            // Tạo mảng Object[][] từ dữ liệu đã lọc
            Object[][] rowData = new Object[filteredIssues.size()][10];
            for (int i = 0; i < filteredIssues.size(); i++) {
                Issue issue = filteredIssues.get(i);

                rowData[i][0] = issue.getIssueID();
                rowData[i][1] = issue.getIssueBookID();

                // Lấy thông tin sách
                Book book = libraryModelManage.searchBookByID(issue.getIssueBookID());
                if (book != null) {
                    rowData[i][2] = book.getBookName();
                    String bookImagePath = book.getImage();
                    rowData[i][3] = createImageLabel(bookImagePath);
                } else {
                    rowData[i][2] = "Unknown Book";
                    rowData[i][3] = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
                }

                Student student = libraryModelManage.searchStudentByID(issue.getIssueStudentID());
                if (student != null) {
                    rowData[i][5] = student.getName();
                    String studentImagePath;
                    if (student.getGender() == true) {
                        studentImagePath = "/ManageBook/icon/girlicon.png";
                    } else {
                        studentImagePath = "/ManageBook/icon/boyicon.png";
                    }
                    rowData[i][6] = createImageLabel(studentImagePath);
                } else {
                    rowData[i][5] = "Unknown Student";
                    rowData[i][6] = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
                }

                rowData[i][4] = issue.getIssueStudentID();
                rowData[i][7] = issue.getIssueDate();
                rowData[i][8] = issue.getDueDate();
                rowData[i][9] = issue.getStatus();
            }

            return rowData;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Object[0][0];
        }
    }

    public void updateTable(Object[][] data) {
        // Lấy tableModel cũ từ table hiện tại
        DefaultTableModel tableModel = (DefaultTableModel) tableView.getTable().getModel();

        // Clear old data
        tableModel.setRowCount(0);

        // Add new data
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        // Không cần thiết phải set lại columnIdentifiers, vì nó đã được thiết lập từ trước
        // Nếu cần thiết, có thể chỉ làm lại phần vẽ lại bảng
        tableView.revalidate();
        tableView.repaint();
    }




    private JLabel createImageLabel(String path) {
        String relativePath = getRelativeImagePath(path);
        ImageIcon icon;

        if (relativePath != null && getClass().getResource(relativePath) != null) {
            icon = new ImageIcon(getClass().getResource(relativePath));
            icon.setDescription(relativePath);
        } else {
            System.out.println("Image not found at path: " + path);
            icon = new ImageIcon(new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB)); // Placeholder ảnh trống
        }

        return new JLabel(icon);
    }

    private String getRelativeImagePath(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }
        String normalizedPath = imagePath.replace("\\", "/");

        String target = "/ManageBook/icon/";
        int relativePathIndex = normalizedPath.indexOf(target);
        if (relativePathIndex != -1) {
            return normalizedPath.substring(relativePathIndex);
        }

        return null;
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

    protected JTextField createSearchField(String placeholder, int x, int y) {
        JTextField searchField = new JTextField(15);
        searchField.setBounds(x, y, 130, 25);
        searchField.setBorder(BorderFactory.createEmptyBorder());
        searchField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        searchField.setBackground(new Color(255, 245, 238));
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
