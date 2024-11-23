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

    public HistoryView(Student student, LibraryModelManage libraryModelManage) {
        this.student = student;
        this.libraryModelManage = libraryModelManage;
        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JPanel reservePanel = createPanel(
                "List of Reserved Books",
                new String[]{"Book ID", "Book Name", "Reservation Date", "Due Date","Action"},
                libraryModelManage.getReserveList()
        );
        reservePanel.setBounds(15, 10, 550, 545);
        layeredPane.add(reservePanel, Integer.valueOf(1));

        JPanel issuedPanel = createPanel(
                "List of Issued Books",
                new String[]{"Book ID", "Book Name", "Issued Date", "Due Date","Action"},
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
                            createAction()
                    });
                }
            } else if (record instanceof Issue) {
                Issue issue = (Issue) record;
                if (Objects.equals(issue.getIssueStudentID(), student.getID())) {
                    filteredList.add(new Object[]{
                            issue.getIssueBookID(),
                            getBookName(issue.getIssueBookID()),
                            issue.getIssueDate(),
                            issue.getDueDate(),
                            createAction()
                    });
                }
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
    public JPanel createAction() {
        JPanel actionPanel = new JPanel(new BorderLayout());
        JButton cardButton = createActionButton("/UserHistory/view/icon/action.png", new Color(255, 240, 245));

        actionPanel.add(cardButton);
        return actionPanel;
    }

}