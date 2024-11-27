package UserHistory.view;

import MainApp.model.*;
import ManageBook.view.BaseBookTableView;
import ManageBook.view.PanelEditor;
import ViewRecord.view.TableViewRecord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class HistoryView extends JPanel {

    private Student student;
    private LibraryModelManage libraryModelManage;
    private ArrayList<Issue> issueList ;
    private ArrayList<Issue> issueListFiltered;
    private ArrayList<Reserve> reserveList ;
    //private BaseBookTableView table;

    public HistoryView(Student student, LibraryModelManage libraryModelManage) {
        this.issueList = libraryModelManage.getIssuesList();
        this.reserveList = libraryModelManage.getReserveList();
        this.student = student;
        this.libraryModelManage = libraryModelManage;
        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JPanel reservePanel = createPanel(
                "List of Reserved Books",
                new String[]{"Book ID", "Book Name", "Reservation Date", "Due Date","Action"},
                reserveList
        );
        reservePanel.setBounds(15, 10, 550, 545);
        layeredPane.add(reservePanel, Integer.valueOf(1));

        issueListFiltered = issueLlist(student.getID());

        JPanel issuedPanel = createPanel(
                "List of Issued Books",
                new String[]{"Book ID", "Book Name", "Issued Date", "Due Date","Action"},
                issueListFiltered
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

        BaseBookTableView table = createTable(columns, data);
        panel.add(table, BorderLayout.CENTER);

        return panel;
    }

    private JLabel createTitleLabel(String title) {
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setBorder(new EmptyBorder(10, 0, 10, 0)); // Top and bottom spacing
        return label;
    }

    private BaseBookTableView createTable(String[] columns, Object[][] data) {
        return new BaseBookTableView(columns, data, 4, 150, -1) {
            @Override
            protected void centerTableCells(JTable table) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    if (i != 4) {
                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    }
                }
            }

            @Override
            protected void configureColumnRenderers(JTable table) {
                table.setDefaultRenderer(Object.class, createMultiLineRenderer());
                table.getColumnModel().getColumn(1).setCellRenderer(createTooltipRenderer());
                table.getColumnModel().getColumn(4).setCellRenderer(createPanelRenderer());
                table.getColumnModel().getColumn(4).setCellEditor(new PanelEditor());
            }

            @Override
            protected void setTableColumnWidths(JTable table) {
                int[] columnWidths = {80, 100, 140, 105, 90};
                for (int i = 0; i < table.getColumnCount(); i++) {
                    TableColumn column = table.getColumnModel().getColumn(i);
                    column.setPreferredWidth(columnWidths[i]);
                }
            }

            @Override
            protected JTable createTable(Object[][] data, String[] columnNames) {
                DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == editColumn;
                    }
                };

                return new JTable(model) {
                    @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component c = super.prepareRenderer(renderer, row, column);
                        if (selectedRow == row) {
                            c.setBackground(new Color(232, 232, 232));
                        } else if (c instanceof JComponent && !(c instanceof JPanel)) {
                            setupDefaultCellAppearance((JComponent) c);
                        }
                        return c;
                    }
                };
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
                            reserve.getDueDate(),
                            createDeleteAction()
                    });
                }
            } else if (record instanceof Issue) {
                Issue issue = (Issue) record;

                    filteredList.add(new Object[]{
                            issue.getIssueBookID(),
                            getBookName(issue.getIssueBookID()),
                            issue.getIssueDate(),
                            issue.getDueDate(),
                            createReturnAction()
                    });

            }
        }

        return filteredList.toArray(new Object[0][0]);
    }
    public JButton createActionButton(String iconPath, Color bgColor) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(iconPath)));

        button.setBackground(null);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(null);
        button.setPreferredSize(new Dimension(30, 30));


        return button;
    }
    public JPanel createReturnAction() {
        JPanel actionPanel = new JPanel(new BorderLayout());
        JButton returnButton = createActionButton("/UserHistory/view/icon/return.png", new Color(255, 240, 245));

        returnButton.addActionListener(e -> {
            int rowIndex = getSelectedRowIndex(returnButton);
            String bookID = getBookIDFromActionButton(returnButton);
            String issueID = getIssueIdFromBookId(bookID);

            Issue issue = libraryModelManage.searchIssueByID(issueID);
            issue.setStatus("Returned");
            Book book = libraryModelManage.searchBookByID(bookID);
            book.setCurent("Still");
            libraryModelManage.editBookInDatabase(book);
            libraryModelManage.editIssueInDatabase(issue);
            removeIssueByID(issueID);


            System.out.println("Return Book"+rowIndex+bookID+issueID+removeIssueByID(issueID));

            DefaultTableModel tableModel = (DefaultTableModel) ((JTable) returnButton.getParent().getParent()).getModel();
            tableModel.removeRow(rowIndex);
            JOptionPane.showMessageDialog(this, "Return successfully!", "Return Book", JOptionPane.INFORMATION_MESSAGE);

            refreshTableData();


        });

        actionPanel.add(returnButton, BorderLayout.CENTER);
        return actionPanel;
    }
    public boolean removeIssueByID(String issueID) {
        for (int i = 0; i < issueListFiltered.size(); i++) {
            System.out.println("Checking issueID: " + issueID + " against: " + issueListFiltered.get(i).getIssueID());
            if (issueListFiltered.get(i).getIssueID().equals(issueID)) {
                issueListFiltered.remove(i);
                return true;
            }
        }
        return false;
    }


    public ArrayList issueLlist(String studentID) {
        ArrayList issueLlist = new ArrayList();
        for (int i = 0; i < issueList.size(); i++) {
            if (issueList.get(i).getIssueStudentID().equals(studentID) && issueList.get(i).getStatus().equals("issued") ) {
               issueLlist.add(issueList.get(i));
               System.out.println(issueList.get(i).getIssueID());
            }
        }
        return issueLlist;

    }


    public JPanel createDeleteAction() {
        JPanel actionPanel = new JPanel(new BorderLayout());
        JButton deleteButton = createActionButton("/UserHistory/view/icon/delete2.png", new Color(255, 240, 245));

        deleteButton.addActionListener(e -> {
            int rowIndex = getSelectedRowIndex(deleteButton);
            String bookID = getBookIDFromActionButton(deleteButton);
            String reserveId = getReserveIdFromBookId(bookID);

            libraryModelManage.deleteReserveFromDatabase(reserveId);
            Book book = libraryModelManage.searchBookByID(bookID);
            book.setCurent("Still");
            libraryModelManage.editBookInDatabase(book);
            reserveList.remove(libraryModelManage.searchReserveByID(reserveId));

            DefaultTableModel tableModel = (DefaultTableModel) ((JTable) deleteButton.getParent().getParent()).getModel();
            tableModel.removeRow(rowIndex);

            System.out.println("Delete Reservation"+rowIndex+bookID+reserveId);

                JOptionPane.showMessageDialog(this, "Reservation deleted successfully!", "Delete Reservation", JOptionPane.INFORMATION_MESSAGE);
            refreshTableData();

        });

        actionPanel.add(deleteButton, BorderLayout.CENTER);
        return actionPanel;
    }



    public String getReserveIdFromBookId(String bookID) {
        if (reserveList != null) {
            for (Reserve reserve : reserveList) {
                if (reserve.getBookID().equals(bookID)) {
                    return reserve.getReserveID();
                }
            }
        }
        return null;
    }

    public String getIssueIdFromBookId(String bookID) {
        if (issueList != null) {
            for(Issue issue : issueList) {
                if (issue.getIssueBookID().equals(bookID) &&
                        !issue.getStatus().equals("Reserved") &&
                        !issue.getStatus().equals("Returned")) {
                    return issue.getIssueID();
                }
            }
        }
        return null;
    }

    private String getBookIDFromActionButton(JButton button) {
        Container parent = button.getParent();
        while (parent != null && !(parent instanceof JTable)) {
            parent = parent.getParent();
        }

        if (parent instanceof JTable) {
            JTable table = (JTable) parent;
            int rowIndex = table.getSelectedRow();
            if (rowIndex != -1) {
                return table.getValueAt(rowIndex, 0).toString();
            }
        }
        return null;
    }


    private int getSelectedRowIndex(JButton button) {
        Container parent = button.getParent();
        while (parent != null && !(parent instanceof JTable)) {
            parent = parent.getParent();
        }

        if (parent instanceof JTable) {
            JTable table = (JTable) parent;
            return table.getSelectedRow();
        }

        return -1;
    }

    private void refreshTableData() {
        JPanel reservePanel = createPanel(
                "List of Reserved Books",
                new String[]{"Book ID", "Book Name", "Reservation Date", "Due Date", "Action"},
                libraryModelManage.getReserveList()
        );
        reservePanel.setBounds(15, 10, 550, 545);

        issueListFiltered = issueLlist(student.getID());

        JPanel issuedPanel = createPanel(
                "List of Issued Books",
                new String[]{"Book ID", "Book Name", "Issued Date", "Due Date", "Action"},
                issueListFiltered
        );
        issuedPanel.setBounds(635, 10, 550, 545);

        this.removeAll();
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        layeredPane.add(reservePanel, Integer.valueOf(1));
        layeredPane.add(issuedPanel, Integer.valueOf(1));

        JPanel backgroundPanel = createBackgroundPanel("/UserHistory/view/icon/background1.gif");
        backgroundPanel.setBounds(0, 0, 1200, 600);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        layeredPane.setPreferredSize(new Dimension(1200, 700));
        this.add(layeredPane, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }




}