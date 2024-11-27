package HomePage.controller;

import API.ApiView;
import HomePage.view.HomePageView;
import IssueBook.view.AppContext;
import IssueBook.view.IssueBookView;
import LMSNotification.view.NotificationView;
import MainApp.model.LibraryModelManage;
import ManageBook.view.ManagementBookView;
import ManageStudent.view.ManagementStudentView;
import ReturnBook.view.ReturnBookView;
import ViewRecord.view.ViewRecordView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

/**
 * Controller cho màn hình chính của ứng dụng (HomePage).
 * Điều khiển các sự kiện liên quan đến menu và các chức năng giao diện.
 */
public class HomePageController implements ActionListener, MouseListener {
    private final HomePageView homePageView;
    private final LibraryModelManage libraryModelManage;
    private JMenu selectedMenu = null;

    /**
     * Constructor của HomePageController.
     *
     * @param homePageView Đối tượng giao diện chính của HomePage.
     */
    public HomePageController(HomePageView homePageView) {
        this.libraryModelManage = homePageView.getMainView().getLibraryModelManage();
        this.homePageView = homePageView;
        AppContext.getInstance().setHomePageView(homePageView);
        initializeListeners(); // Gắn các sự kiện
    }

    /**
     * Gắn các listener cho các thành phần giao diện để xử lý sự kiện.
     */
    private void initializeListeners() {
        homePageView.getHamburgerButton().addActionListener(this);
        homePageView.getHamburgerButton().addMouseListener(createButtonHoverListener(
                new Color(208, 204, 207), new Color(150, 180, 255)));

        // Thêm hiệu ứng hover cho các menu
        addMenuHoverEffect(homePageView.getjMenuHomePage());
        addMenuHoverEffect(homePageView.getjMenuLogout());
        addMenuHoverEffect(homePageView.getjMenuIssueBook());
        addMenuHoverEffect(homePageView.getjMenuMGMTSutudents());
        addMenuHoverEffect(homePageView.getjMenuReturnBook());
        addMenuHoverEffect(homePageView.getjMenuLMSDashBoard());
        addMenuHoverEffect(homePageView.getjMenuMGMTBooks());
        addMenuHoverEffect(homePageView.getjMenuViewRecords());
        addMenuHoverEffect(homePageView.getjMenuSearchApi());
    }

    /**
     * Tạo một MouseAdapter cho hiệu ứng hover khi di chuột qua nút.
     *
     * @param hoverColor   Màu sắc khi di chuột.
     * @param defaultColor Màu sắc mặc định khi không di chuột.
     * @return MouseAdapter đã được cấu hình.
     */
    private MouseAdapter createButtonHoverListener(Color hoverColor, Color defaultColor) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                homePageView.getHamburgerButton().setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                homePageView.getHamburgerButton().setBackground(defaultColor);
            }
        };
    }

    /**
     * Thêm hiệu ứng hover cho các thành phần menu trong giao diện.
     *
     * @param menu JMenu cần thêm hiệu ứng hover.
     */
    private void addMenuHoverEffect(JMenu menu) {
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedMenu != menu) {
                    if (selectedMenu != null) {
                        selectedMenu.setOpaque(false);
                        selectedMenu.setBackground(new Color(80, 80, 80));
                    }
                    selectedMenu = menu;
                    menu.setOpaque(true);
                    menu.setBackground(new Color(185, 173, 173));
                }

                // Hiển thị hộp thoại "Loading..."
                showLoadingDialog();

                // Thực thi tác vụ trong luồng riêng (SwingWorker)
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        // Điều hướng đến màn hình tương ứng khi click menu
                        if (menu == homePageView.getjMenuLogout()) {
                            System.out.println("Logout clicked!");
                            homePageView.getMainView().showCard("Login", null);
                        }
                        if (menu == homePageView.getjMenuHomePage()) {
                            System.out.println("HomePage clicked");
                            homePageView.toggleMenuBar();
                            homePageView.setMainHomePanel(homePageView.createMainHome());
                        }
                        if (menu == homePageView.getjMenuMGMTBooks()) {
                            System.out.println("MenuMGMTBooks clicked");
                            ManagementBookView managementBookView = new ManagementBookView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            managementBookView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(managementBookView);
                        }
                        if (menu == homePageView.getjMenuMGMTSutudents()) {
                            System.out.println("MenuStudent clicked");
                            ManagementStudentView managementStudentView = new ManagementStudentView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            managementStudentView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(managementStudentView);
                        }
                        if (menu == homePageView.getjMenuIssueBook()) {
                            System.out.println("Issue Button clicked");
                            IssueBookView issueBookView = new IssueBookView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            issueBookView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(issueBookView);
                        }
                        if (menu == homePageView.getjMenuReturnBook()) {
                            System.out.println("Return Button clicked");
                            ReturnBookView returnBookView = new ReturnBookView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            returnBookView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(returnBookView);
                        }
                        if (menu == homePageView.getjMenuViewRecords()) {
                            System.out.println("View Record Button clicked");
                            ViewRecordView viewRecordView = new ViewRecordView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            viewRecordView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(viewRecordView);
                        }
                        if (menu == homePageView.getjMenuSearchApi()) {
                            System.out.println("Search API Button clicked");
                            ApiView apiView = new ApiView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            apiView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(apiView);
                        }
                        if(menu==homePageView.getjMenuLMSDashBoard())
                        {
                            System.out.println(("LMS Notification Button clicked"));
                            NotificationView notificationView = new NotificationView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            notificationView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(),homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(notificationView);
                        }
                        return null;
                    }


                    @Override
                    protected void done() {
                        hideLoadingDialog();
                    } // Ẩn hộp thoại khi hoàn tất
                }.execute();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (selectedMenu != menu) {
                    menu.setOpaque(true);
                    menu.setBackground(new Color(173, 216, 230)); // Màu xanh dương nhạt khi di chuột vào
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (selectedMenu != menu) { // Chỉ phục hồi màu gốc nếu menu chưa được chọn
                    menu.setOpaque(false);
                    menu.setBackground(new Color(80, 80, 80)); // Màu nền khi không di chuột vào
                } else {
                    menu.setOpaque(true);
                    menu.setBackground(new Color(185, 173, 173)); // Màu khi được chọn
                }
            }
        });


    }

    private JDialog loadingDialog;
    private JProgressBar progressBar;
    private JPanel panel;
    private int dotCount = 0;
    private final int maxDots = 3;
    private final int dotSize = 20;
    private SwingWorker<Void, Integer> searchWorker;

    /**
     * Hiển thị hộp thoại "Loading..." với hiệu ứng hình ảnh và tiến trình.
     */
    public void showLoadingDialog() {
        // Tạo dialog
        loadingDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(homePageView), "Loading...", true);
        loadingDialog.setUndecorated(true); // Loại bỏ khung cửa sổ
        loadingDialog.setSize(250, 300); // Kích thước dialog
        loadingDialog.setLocationRelativeTo(homePageView);
        loadingDialog.setBackground(new Color(0, 0, 0, 0)); // ARGB: alpha = 0 (trong suốt)

        // Tạo nội dung cho hộp thoại
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ các dấu chấm tròn theo tiến trình
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLUE);

                // Vẽ các dấu chấm tròn
                for (int i = 0; i < dotCount; i++) {
                    int x = 40 + i * (dotSize + 10);
                    int y = getHeight() / 2 - dotSize / 2;
                    g2d.fillOval(x, y, dotSize, dotSize);
                }
            }
        };
        panel.setOpaque(false);
        panel.setLayout(null);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // Căn giữa ảnh
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/LMSNotification/view/icon/1.gif"))); // Đường dẫn ảnh
        imageLabel.setIcon(icon);
        imageLabel.setBounds(0, 0, 240, 240); // Đặt vị trí và kích thước ảnh
        panel.add(imageLabel); // Thêm ảnh vào panel

        // Thêm JProgressBar
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false); // Không phải trạng thái không xác định
        progressBar.setMaximum(100); // Giới hạn tiến trình (tối đa 100%)
        progressBar.setValue(0); // Giá trị ban đầu
        progressBar.setBounds(0, 230, 240, 25); // Đặt vị trí và kích thước thanh tiến trình
        panel.add(progressBar);

        // Gắn panel vào dialog
        loadingDialog.add(panel);

        // Hiển thị dialog trong luồng riêng biệt
        new Thread(() -> loadingDialog.setVisible(true)).start();

        // Khởi tạo SwingWorker
        searchWorker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Thực hiện công việc trong nền (không có delay)
                for (int i = 0; i <= 100; i += 5) {
                    publish(i);
                    Thread.sleep(100); // Giới thiệu một thời gian nghỉ giữa các bước
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                progressBar.setValue(progress);

                dotCount = (dotCount + 1) % (maxDots + 1);
                panel.repaint(); // Vẽ lại panel để cập nhật dấu chấm

                // Kiểm tra nếu tiến trình đã đạt 100%
                if (progress == 100) {
                    hideLoadingDialog(); // Đóng dialog khi tiến trình hoàn tất
                }
            }

            @Override
            protected void done() {
                hideLoadingDialog();
            }
        };

        searchWorker.execute();
    }

    /**
     * Ẩn hộp thoại "Loading..." khi tiến trình hoàn tất.
     */
    private void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose(); // Đóng dialog
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homePageView.getHamburgerButton()) {
            homePageView.toggleMenuBar();
            System.out.println("Hamburger button clicked!");
        }
    }

    // Các phương thức trống cho MouseListener
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
