package HomePage.controller;

import API.ApiView;
import HomePage.view.HomePageView;
import IssueBook.view.IssueBookView;
import MainApp.model.LibraryModelManage;
import ManageBook.view.ManagementBookView;
import ManageStudent.view.ManagementStudentView;
import ReturnBook.view.ReturnBookView;
import ViewRecord.view.ViewRecordView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePageController implements ActionListener, MouseListener {
    private final HomePageView homePageView;
    private final LibraryModelManage libraryModelManage;
    private JMenu selectedMenu = null;


    public HomePageController(HomePageView homePageView) {
        this.libraryModelManage = new LibraryModelManage();
        this.homePageView = homePageView;
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
                        selectedMenu.setBackground(new Color(80, 80, 80)); // Màu gốc của menu
                    }
                    selectedMenu = menu;
                    menu.setOpaque(true);
                    menu.setBackground(new Color(185, 173, 173)); // Màu khi được chọn
                }

                showLoadingDialog();
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        // Thực hiện tác vụ chính (mở giao diện con) ở đây
                        if (menu == homePageView.getjMenuLogout()) {
                            System.out.println("Logout clicked!");
                            homePageView.getMainView().showCard("Login", null);
                        } else if (menu == homePageView.getjMenuHomePage()) {
                            System.out.println("HomePage clicked");
                            homePageView.toggleMenuBar();
                            homePageView.setMainHomePanel(homePageView.createMainHome());
                        } else if (menu == homePageView.getjMenuMGMTBooks()) {
                            System.out.println("MenuMGMTBooks clicked");
                            ManagementBookView managementBookView = new ManagementBookView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            managementBookView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(managementBookView);
                        } else if (menu == homePageView.getjMenuMGMTSutudents()) {
                            System.out.println("MenuStudent clicked");
                            ManagementStudentView managementStudentView = new ManagementStudentView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            managementStudentView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(managementStudentView);
                        } else if (menu == homePageView.getjMenuIssueBook()) {
                            System.out.println("Issue Button clicked");
                            IssueBookView issueBookView = new IssueBookView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            issueBookView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(issueBookView);
                        } else if (menu == homePageView.getjMenuReturnBook()) {
                            System.out.println("Return Button clicked");
                            ReturnBookView returnBookView = new ReturnBookView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            returnBookView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(returnBookView);
                        } else if (menu == homePageView.getjMenuViewRecords()) {
                            System.out.println("View Record Button clicked");
                            ViewRecordView viewRecordView = new ViewRecordView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            viewRecordView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(viewRecordView);
                        } else if (menu == homePageView.getjMenuSearchApi()) {
                            System.out.println("Search API Button clicked");
                            ApiView apiView = new ApiView(libraryModelManage);
                            homePageView.toggleMenuBar();
                            apiView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(), homePageView.getMainHomePanel().getHeight()));
                            homePageView.setMainHomePanel(apiView);
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
                if (selectedMenu != menu) { // Chỉ thay đổi màu nếu menu chưa được chọn
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

    private void showLoadingDialog() {
        loadingDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(homePageView), "Loading...", true);
        loadingDialog.setUndecorated(true);
        loadingDialog.setSize(250, 60);
        loadingDialog.setLocationRelativeTo(homePageView);

        JPanel panel = new JPanel(new GridLayout(2,1,10,10));

        JLabel label = new JLabel("Loading...", JLabel.CENTER);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(label);
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel.add(progressBar);

        loadingDialog.add(panel);

        new Thread(() -> loadingDialog.setVisible(true)).start();
    }

    // Use this helper method to update the progress bar
    private void updateProgress(int progress) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
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
