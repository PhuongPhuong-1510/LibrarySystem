package UserHistory.view;

import MainApp.model.*;
import ViewRecord.view.TableViewRecord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class HistoryView extends JPanel {

    private Student student;
    private LibraryModelManage libraryModelManage;

    public HistoryView(Student student, LibraryModelManage libraryModelManage) {
        this.student = student;
        this.libraryModelManage = libraryModelManage;
        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JPanel reservePanel = createPanel(
                "List of Reserved Books",
                new String[]{"Book ID", "Book Name", "Reservation Date", "Due Date"},
                libraryModelManage.getReserveList()
        );
        reservePanel.setBounds(15, 10, 550, 545);
        layeredPane.add(reservePanel, Integer.valueOf(1));

        JPanel issuedPanel = createPanel(
                "List of Issued Books",
                new String[]{"Book ID", "Book Name", "Issued Date", "Due Date"},
                libraryModelManage.getIssuesList()
        );
        issuedPanel.setBounds(635, 10, 550, 545);
        layeredPane.add(issuedPanel, Integer.valueOf(1));

        JPanel backgroundPanel = createBackgroundPanel("/UserHistory/view/icon/background1.gif");
        backgroundPanel.setBounds(0, 0, 1200, 600);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));


        layeredPane.setPreferredSize(new Dimension(1200, 700));

        this.add(layeredPane,BorderLayout.CENTER);
    }
    private JPanel createBackgroundPanel(String path) {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(path));
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        return backgroundPanel;
    }


    private JPanel createPanel(String title, String[] columns, ArrayList<?> records) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(142, 207, 243));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel label = createTitleLabel(title);
        label.setForeground(new Color(72, 118, 255));
        panel.add(label, BorderLayout.NORTH);

        // Lọc dữ liệu theo studentID
        Object[][] data = filterData(records);

        TableViewRecord tableViewRecord = createTable(columns, data);
        panel.add(tableViewRecord, BorderLayout.CENTER);

        return panel;
    }

    private JLabel createTitleLabel(String title) {
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setBorder(new EmptyBorder(10, 0, 10, 0)); // Top and bottom spacing
        return label;
    }

    private TableViewRecord createTable(String[] columns, Object[][] data) {
        return new TableViewRecord(columns, data, 100, 0, 0) {
            @Override
            protected void setTableColumnWidths(JTable table) {
                int[] columnWidths = {120, 130, 150, 115};
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                }
            }
        };
    }

    public String getBookName(String bookID) {
        Book book = libraryModelManage.searchBookByID(bookID);
        return book != null ? book.getBookName() : "Unknown";
    }


    private Object[][] filterData(ArrayList<?> records) {
        ArrayList<Object[]> filteredList = new ArrayList<>();

        for (Object record : records) {
            if (record instanceof Reserve) {
                Reserve reserve = (Reserve) record;
                if (Objects.equals(reserve.getId(), student.getID())) {
                    filteredList.add(new Object[]{
                            reserve.getBookID(),
                            getBookName(reserve.getBookID()),
                            reserve.getReservedDate(),
                            reserve.getDueDate()
                    });
                }
            } else if (record instanceof Issue) {
                Issue issue = (Issue) record;
                if (Objects.equals(issue.getIssueStudentID(), student.getID())) {
                    filteredList.add(new Object[]{
                            issue.getIssueBookID(),
                            getBookName(issue.getIssueBookID()),
                            issue.getIssueDate(),
                            issue.getDueDate()
                    });
                }
            }
        }

        return filteredList.toArray(new Object[0][0]);
    }


}