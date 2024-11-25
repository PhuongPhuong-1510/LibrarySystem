package LMSNotification.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import HomePage.view.CustomScrollBarUI;
import HomePage.view.HomePageView;
import IssueBook.view.AppContext;
import IssueBook.view.IssueBookView;
import LMSNotification.controller.NotificationController;
import MainApp.model.Book;
import MainApp.model.LibraryModelManage;
import MainApp.model.Reserve;
import MainApp.model.Signup;
import ManageStudent.controller.AddStudentController;
import ManageStudent.controller.StudentController;
import ManageStudent.view.AddStudentView;
import ManageStudent.view.ManagementStudentView;

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

        configurePanel(backgroundPanel1, 1100, 0, 101, 100);
        backgroundPanel1.setBackground(new Color(35,69,109,255));
        layeredPane.add(backgroundPanel1, Integer.valueOf(1));

        configurePanel(notificationContainerPanel, 680, 100, 500, 450); // Đặt vị trí và kích thước
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

        JLabel titleLabel = new JLabel("NOTIFICATION");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20)); // Thiết lập font cho tiêu đề
        titleLabel.setForeground(new Color(238, 64, 0)); // Màu chữ đen cho tiêu đề
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa tiêu đề

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setOpaque(false);  // Đảm bảo panel không có nền

        containerPanel.add(titleLabel, BorderLayout.NORTH); // Thêm tiêu đề ở vị trí phía Bắc
        containerPanel.add(notificationPanel, BorderLayout.CENTER);

        containerPanel.setBorder(null);

        return containerPanel;
    }

    private ArrayList<String> createNotificationsList() {
        ArrayList<String> listNoti = new ArrayList<>();

        // Lấy danh sách Reserve và kiểm tra null
        ArrayList<Reserve> notifications = libraryModelManage.getReserveList();
        if (notifications != null) {
            for (Reserve reserve : notifications) {
                if (reserve != null) { // Kiểm tra Reserve object không null
                    LocalDate reservedDate = reserve.getReservedDate().toLocalDate();
                    LocalDate currentDate = LocalDate.now();
                    long daysAgo = ChronoUnit.DAYS.between(reservedDate, currentDate);

                    // Tạo thông báo Reserve
                    String stringReserveNoti = "<html>STUDENT WITH ID " + reserve.getId() +
                            " REQUESTED TO BORROW BOOK WITH ID " + reserve.getBookID() +
                            "<br>REGISTERED " + daysAgo + " DAYS AGO</html>";

                    listNoti.add(stringReserveNoti);
                }
            }
        }

        try {
            ArrayList<Signup> signups = libraryModelManage.getSignupList();
            if (signups != null) {
                for (Signup signup : signups) {
                    if (signup != null) {
                        String stringSignupNoti = "<html>STUDENT SIGNUP WITH EMAIL " + signup.getEmail() +
                                " REQUESTED TO BORROW BOOK WITH ID " + signup.getName() +
                                "<br>REGISTERED " + 11 + " DAYS AGO</html>";
                        listNoti.add(stringSignupNoti);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error processing signups: " + e.getMessage());
            e.printStackTrace();
        }




        for(String notion : listNoti) {
            System.out.println(notion);
        }

        return listNoti;
    }


    private JPanel createNotificationPanel(List<String> notifications) {
        JPanel notificationContentPanel = new JPanel();
        notificationContentPanel.setLayout(new BoxLayout(notificationContentPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout cho thông báo
        notificationContentPanel.setOpaque(true); // Đảm bảo màu nền được hiển thị
        notificationContentPanel.setBackground(new Color(255 ,246, 143)); // Màu nền cho panel (có thể thay đổi màu)

        notificationContentPanel.add(Box.createVerticalStrut(20)); // Tạo không gian từ lề trên

        for (String notification : notifications) {
            JLabel notificationLabel = createNotificationLabel(notification);

            notificationLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String notification = ((JLabel) e.getSource()).getText();
                    if (notification.contains("STUDENT SIGNUP WITH EMAIL")) {
                        handleSignupNotificationClick(notification);
                    } else {
                        handleNotificationClick(notification);
                    }
                }
            });


            notificationContentPanel.add(notificationLabel);
            notificationContentPanel.add(Box.createRigidArea(new Dimension(5, 10))); // Add spacing between notifications
        }

        int panelHeight = notifications.size() * 40;
        notificationContentPanel.setPreferredSize(new Dimension(500, panelHeight));

        JScrollPane scrollPane = createScrollPane(notificationContentPanel);
        scrollPane.setPreferredSize(new Dimension(700, 450));

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        containerPanel.setOpaque(false);

        return containerPanel;
    }

    private JLabel createNotificationLabel(String notification) {
        String htmlText = "<html>" +
                "<div style='font-size:12px; line-height:25px; margin-bottom:5px;'>"
                + notification.split("<br>")[0] + "</div>" +
                "<div style='font-size:7px; line-height:15px; color:black; '>"
                + notification.split("<br>")[1] + "</div>" +
                "</html>";

        JLabel notificationLabel = new JLabel(htmlText);
        notificationLabel.setForeground(new Color(255, 255, 255)); // Màu chữ
        notificationLabel.setOpaque(true); // Đảm bảo nền màu sẽ hiển thị
        notificationLabel.setBackground(new Color(238, 180, 180)); // Màu nền cho label

        notificationLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1, true)); // Viền có bo tròn

        notificationLabel.setHorizontalAlignment(SwingConstants.LEFT); // Căn trái văn bản
        notificationLabel.setVerticalAlignment(SwingConstants.CENTER);

        notificationLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Padding

        return notificationLabel;
    }




    protected JScrollPane createScrollPane(JPanel table) {
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



    public void toggleNotificationPanel() {
        boolean isVisible = notificationContainerPanel.isVisible();
        notificationContainerPanel.setVisible(!isVisible);
    }
    private void handleNotificationClick(String notification) {
        try {
            String plainText = notification.replaceAll("<[^>]+>", ""); // Xóa thẻ HTML

            String[] parts = plainText.split("REQUESTED TO BORROW BOOK WITH ID");

            if (parts.length < 2) {
                JOptionPane.showMessageDialog(null, "Invalid notification format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String studentId = parts[0].replace("STUDENT WITH ID", "").trim();

            String bookId = parts[1].split("REGISTERED")[0].trim();

            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to lend Book ID: " + bookId + " to Student ID: " + studentId + "?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                System.out.println("Lend Book ID: " + bookId + " to Student ID: " + studentId);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                HomePageView homePageView = AppContext.getInstance().getHomePageView();
                IssueBookView issueBookView = new IssueBookView(libraryModelManage);
                issueBookView.updateBookAndStudentInfo(bookId, studentId);
                issueBookView.setBookIdField(bookId);
                issueBookView.setStudentIdField(studentId);
                String issueDate = dateFormat.format(calendar.getTime());
                issueBookView.setIssueDateField(issueDate);

                calendar.add(Calendar.MONTH, 1);
                String dueDate = dateFormat.format(calendar.getTime());
                issueBookView.setDueDateField(dueDate);

                removeReserveByStudentAndBookId(studentId,bookId);
                refreshReserveTable();

                homePageView.setMainHomePanel(issueBookView);

            }
            else if (response == JOptionPane.NO_OPTION) {
                System.out.println("Action canceled.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void handleSignupNotificationClick(String notification) {
        try {
            // Xử lý thông báo và lấy thông tin email như trước
            String plainText = notification.replaceAll("<[^>]+>", "");
            String[] parts = plainText.split("STUDENT SIGNUP WITH EMAIL");
            if (parts.length < 2) {
                JOptionPane.showMessageDialog(null, "Invalid notification format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String input = parts[1].trim();
            int emailEndIndex = input.indexOf(" ");
            String email = input.substring(0, emailEndIndex);
            System.out.println(email + "///////////////");


            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to add a new student with Email: " + email + "?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                Signup signup = libraryModelManage.searchSignupByEmail(email);
                AddStudentFromSignup addStudentFromSignup = new AddStudentFromSignup(libraryModelManage, signup);
                addStudentFromSignup.updateSignupStudent(
                        signup.getName(),
                        email,
                        signup.getPassword(),
                        signup.getPhone(),
                        signup.getGender(),
                        signup.getDateBirth(),
                        signup.getMajor(),
                        signup.getBranch()
                );

                addStudentFromSignup.setVisible(true);
                libraryModelManage.deleteSignupFromDatabase(email);
                refreshReserveTable();
            } else {
                System.out.println("Action canceled.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }




    public void removeReserveByStudentAndBookId(String studentId, String bookId) {
        Reserve reserve = libraryModelManage.searchReserveByBookID(bookId);
        String reserveID = reserve.getReserveID();
        libraryModelManage.deleteReserveFromDatabase(reserveID);
    }

    public void refreshReserveTable() {
        //List<Reserve> updatedReserves = libraryModelManage.getReserveList();
        notificationContainerPanel.removeAll();  // Xóa tất cả thông báo hiện tại
        notificationContainerPanel.add(createNotificationPanel(createNotificationsList()));  // Vẽ lại thông báo
        notificationContainerPanel.revalidate();  // Cập nhật giao diện
        notificationContainerPanel.repaint();
    }




    public JPanel getBackgroundPanel1() {
        return backgroundPanel1;
    }
}