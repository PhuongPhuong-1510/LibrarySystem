package HomePage.controller;

import HomePage.view.HomePageView;
import IssueBook.view.IssueBookView;
import ManageBook.view.ManagementBookView;
import ManageStudent.view.ManagementStudentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePageController implements ActionListener, MouseListener {
    private final HomePageView homePageView;
    private JMenu selectedMenu = null;

    public HomePageController(HomePageView homePageView) {
        this.homePageView = homePageView;
        initializeListeners();
    }

    private void initializeListeners() {
        homePageView.getHamburgerButton().addActionListener(this);
        homePageView.getHamburgerButton().addMouseListener(createButtonHoverListener(
                new Color(208, 204, 207), new Color(150, 180, 255)));

        addMenuHoverEffect(homePageView.getjMenuHomePage());
        addMenuHoverEffect(homePageView.getjMenuDefault());
        addMenuHoverEffect(homePageView.getjMenuLogout());
        addMenuHoverEffect(homePageView.getjMenuIssueBook());
        addMenuHoverEffect(homePageView.getjMenuMGMTSutudents());
        addMenuHoverEffect(homePageView.getjMenuReturnBook());
        addMenuHoverEffect(homePageView.getjMenuLMSDashBoard());
        addMenuHoverEffect(homePageView.getjMenuMGMTBooks());
        addMenuHoverEffect(homePageView.getjMenuViewBooks());
        addMenuHoverEffect(homePageView.getjMenuViewRecords());
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
                if (menu == homePageView.getjMenuLogout()) {
                    System.out.println("Logout clicked!");
                    homePageView.getMainView().showCard("Login");
                }
                if (menu==homePageView.getjMenuHomePage())
                {
                    System.out.println("HomePage clicked");
                    homePageView.toggleMenuBar();
                    homePageView.setMainHomePanel(homePageView.createMainHome());
                }
                if(menu==homePageView.getjMenuMGMTBooks())
                {
                    System.out.println(("MenuMGMTBooks clicked"));
                    ManagementBookView managementBookView = new ManagementBookView();
                    homePageView.toggleMenuBar();
                    managementBookView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(),homePageView.getMainHomePanel().getHeight()));
                    homePageView.setMainHomePanel(managementBookView);
                }
                if(menu==homePageView.getjMenuMGMTSutudents())
                {
                    System.out.println(("MenuStudent clicked"));
                    ManagementStudentView managementStudentView = new ManagementStudentView();
                    homePageView.toggleMenuBar();
                    managementStudentView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(),homePageView.getMainHomePanel().getHeight()));
                    homePageView.setMainHomePanel(managementStudentView);
                }

                if(menu==homePageView.getjMenuIssueBook())
                {
                    System.out.println(("Issue Button clicked"));
                    IssueBookView issueBookView = new IssueBookView();
                    homePageView.toggleMenuBar();
                    issueBookView.setPreferredSize(new Dimension(homePageView.getMainHomePanel().getWidth(),homePageView.getMainHomePanel().getHeight()));
                    homePageView.setMainHomePanel(issueBookView);
                }

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
