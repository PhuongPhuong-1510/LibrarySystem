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

public class HomePageController implements ActionListener, MouseListener {
    private final HomePageView homePageView;
    private final LibraryModelManage libraryModelManage;
    private JMenu selectedMenu = null;


    public HomePageController(HomePageView homePageView) {
        this.libraryModelManage = new LibraryModelManage();
        this.homePageView = homePageView;
        AppContext.getInstance().setHomePageView(homePageView);
        initializeListeners();
    }

    private void initializeListeners() {
        homePageView.getHamburgerButton().addActionListener(this);
        homePageView.getHamburgerButton().addMouseListener(createButtonHoverListener(
                new Color(208, 204, 207), new Color(150, 180, 255)));

        addMenuHoverEffect(homePageView.getjMenuHomePage());
        addMenuHoverEffect(homePageView.getjMenuAboutProject());
        addMenuHoverEffect(homePageView.getjMenuLogout());
        addMenuHoverEffect(homePageView.getjMenuIssueBook());
        addMenuHoverEffect(homePageView.getjMenuMGMTSutudents());
        addMenuHoverEffect(homePageView.getjMenuReturnBook());
        addMenuHoverEffect(homePageView.getjMenuLMSDashBoard());
        addMenuHoverEffect(homePageView.getjMenuMGMTBooks());
//        addMenuHoverEffect(homePageView.getjMenuViewBooks());
        addMenuHoverEffect(homePageView.getjMenuViewRecords());
        addMenuHoverEffect(homePageView.getjMenuSearchApi());
    }

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

                showLoadingDialog();

                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
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
                    }
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

    public void showLoadingDialog() {
        // Tạo dialog
        loadingDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(homePageView), "Loading...", true);
        loadingDialog.setUndecorated(true); // Loại bỏ khung cửa sổ
        loadingDialog.setSize(250, 300); // Kích thước dialog
        loadingDialog.setLocationRelativeTo(homePageView);
        loadingDialog.setBackground(new Color(0, 0, 0, 0)); // ARGB: alpha = 0 (trong suốt)

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
