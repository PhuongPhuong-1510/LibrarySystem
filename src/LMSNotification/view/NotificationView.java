package LMSNotification.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import LMSNotification.controller.NotificationController;
import MainApp.model.LibraryModelManage;

public class NotificationView extends JPanel {

    private final JPanel backgroundPanel1;
    private final LibraryModelManage libraryModelManage;
    private final JPanel notificationContainerPanel;

    public NotificationView(LibraryModelManage libraryModelManage) {
        this.libraryModelManage = libraryModelManage;
        this.setLayout(new BorderLayout());
        this.backgroundPanel1 = createBackgroundPanel("/LMSNotification/view/icon/notification.gif");

        this.notificationContainerPanel = createNotificationContainer();
        this.notificationContainerPanel.setVisible(false); // Ẩn panel thông báo ban đầu

        initializeView();
        new NotificationController(this);
    }

    private void initializeView() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JPanel backgroundPanel = createBackgroundPanel("/LMSNotification/view/icon/background2.gif");
        configurePanel(backgroundPanel, 0, 0, 1200, 900);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        configurePanel(backgroundPanel1, 910, 0, 101, 100);
        backgroundPanel1.setBackground(new Color(37,74,118,255));
        layeredPane.add(backgroundPanel1, Integer.valueOf(1));

        configurePanel(notificationContainerPanel, 750, 100, 400, 450); // Đặt vị trí và kích thước
        layeredPane.add(notificationContainerPanel, Integer.valueOf(2));

        layeredPane.setPreferredSize(new Dimension(1200, 900));
        this.add(layeredPane, BorderLayout.CENTER);
    }

    private JPanel createBackgroundPanel(String path) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(path));
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    private void configurePanel(JPanel panel, int x, int y, int width, int height) {
        panel.setBounds(x, y, width, height);
    }



    private JPanel createNotificationContainer() {
        List<String> notifications = createNotificationsList();

        JPanel notificationPanel = createNotificationPanel(notifications);

        JScrollPane scrollPane = createScrollPane(notificationPanel);

        JPanel containerPanel = createContainerPanel(scrollPane);
        containerPanel.setOpaque(false);
        containerPanel.setBorder(null);

        return containerPanel;
    }

    private List<String> createNotificationsList() {
        List<String> notifications = new ArrayList<>();

        notifications.add("New book added: Java Programming.");
        notifications.add("Your book is overdue. Please return it.");
        notifications.add("Library will be closed this weekend.");
        notifications.add("Upcoming event: Author meetup on Dec 10th.");
        notifications.add("Update: New collection of eBooks available.");
        notifications.add("New book added: Java Programming.");
        notifications.add("Your book is overdue. Please return it.");
        notifications.add("Library will be closed this weekend.");
        notifications.add("Upcoming event: Author meetup on Dec 10th.");
        notifications.add("Update: New collection of eBooks available."); notifications.add("New book added: Java Programming.");
        notifications.add("Your book is overdue. Please return it.");
        notifications.add("Library will be closed this weekend.");
        notifications.add("Upcoming event: Author meetup on Dec 10th.");
        notifications.add("Update: New collection of eBooks available.");
        return notifications;
    }

    // Phương thức tạo JPanel chứa các thông báo
    private JPanel createNotificationPanel(List<String> notifications) {



        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JPanel backgroundPanel = createBackgroundPanel("/LMSNotification/view/icon/background3.gif");
        configurePanel(backgroundPanel, 0, 0, 400, 450); // Điều chỉnh kích thước cho background
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        for (String notification : notifications) {
            JLabel notificationLabel = createNotificationLabel(notification);
            notificationLabel.setBackground(Color.RED);
            backgroundPanel.add(notificationLabel);
        }




        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(null);
        layeredPane.setBounds(0, 0, 400, 450);
        containerPanel.add(layeredPane);

        return containerPanel;
    }

    private JLabel createNotificationLabel(String notification) {
        JLabel notificationLabel = new JLabel(notification);
        notificationLabel.setForeground(Color.black);
        notificationLabel.setBackground(Color.red);
        notificationLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        notificationLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        return notificationLabel;
    }

    // Phương thức tạo JScrollPane bao quanh notificationPanel
    private JScrollPane createScrollPane(JPanel notificationPanel) {
        JScrollPane scrollPane = new JScrollPane(notificationPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    // Phương thức tạo container panel chứa JScrollPane
    private JPanel createContainerPanel(JScrollPane scrollPane) {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        containerPanel.setBorder(null);
        containerPanel.setOpaque(false);
        return containerPanel;
    }

    public void toggleNotificationPanel() {
        boolean isVisible = notificationContainerPanel.isVisible();
        notificationContainerPanel.setVisible(!isVisible);
    }

    public JPanel getBackgroundPanel1() {
        return backgroundPanel1;
    }
}
