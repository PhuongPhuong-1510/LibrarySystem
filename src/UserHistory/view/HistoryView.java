package UserHistory.view;

import ViewRecord.view.TableViewRecord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class HistoryView extends JPanel {

    public HistoryView() {
        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JPanel reservePanel = createPanel("List of Reserved Books",
                new String[]{"Book ID", "Book Name", "Reservation Date", "Due Date"},
                new Object[][]{
                        {"EM28CP1", "Chuyên đề kế toán", "15-05-2018", "17-05-2018"},
                        {"OL28CP3", "Vật lý đại cương 2", "15-05-2018", "17-05-2018"},
                        {"OL28CP4", "Vật lý đại cương 2", "15-05-2018", "17-05-2018"}
                });
        reservePanel.setBounds(15, 10, 550, 545);
        layeredPane.add(reservePanel, Integer.valueOf(1));

        JPanel issuedPanel = createPanel("List of Issued Books",
                new String[]{"Book ID", "Book Name", "Issued Date", "Due Date"},
                new Object[][]{});
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


    private JPanel createPanel(String title, String[] columns, Object[][] data) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(142,207,243));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel label = createTitleLabel(title);
        label.setForeground(new Color(72,118,255));
        panel.add(label, BorderLayout.NORTH);

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
}