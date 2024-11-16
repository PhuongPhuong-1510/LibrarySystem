package UserMain.controller;

import UserAccount.view.AccountView;
import UserHistory.view.HistoryView;
import UserMain.view.UserView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserController implements ActionListener, MouseListener {
    private final UserView userView;
    private JMenu selectedMenu = null;

    public UserController(UserView userView) {
        this.userView = userView;
        initializeListeners();
    }

    private void initializeListeners() {
        userView.getHamburgerButton().addActionListener(this);
        userView.getHamburgerButton().addMouseListener(createButtonHoverListener(
                new Color(208, 204, 207), new Color(150, 180, 255)));

        addMenuHoverEffect(userView.getjMenuHomePage());
        addMenuHoverEffect(userView.getjMenuHistory());
        addMenuHoverEffect(userView.getjMenuInfo());
        addMenuHoverEffect(userView.getjMenuOut());


        userView.getBtnSearch().addMouseListener(this);
        userView.getBtnRegister().addMouseListener(this);


    }

    private MouseAdapter createButtonHoverListener(Color hoverColor, Color defaultColor) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getHamburgerButton().setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                userView.getHamburgerButton().setBackground(defaultColor);
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
                if (menu == userView.getjMenuOut()) {
                    System.out.println("Logout clicked!");
                    userView.getMainView().showCard("Login");
                }
                if (menu==userView.getjMenuHomePage())
                {
                    System.out.println("HomePage clicked");
                    userView.toggleMenuBar();
                    userView.setMainHomePanel(userView.createMainHome());
                }
                if(menu==userView.getjMenuHistory())
                {
                    System.out.println(("History Button clicked"));
                    HistoryView historyView = new HistoryView();
                    userView.toggleMenuBar();
                    historyView.setPreferredSize(new Dimension(userView.getMainHomePanel().getWidth(),userView.getMainHomePanel().getHeight()));
                    userView.setMainHomePanel(historyView);
                }
                if(menu==userView.getjMenuInfo())
                {
                    System.out.println(("Info Button clicked"));
                    AccountView accountView = new AccountView();
                    userView.toggleMenuBar();
                    accountView.setPreferredSize(new Dimension(userView.getMainHomePanel().getWidth(),userView.getMainHomePanel().getHeight()));
                    userView.setMainHomePanel(accountView);
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
        if (e.getSource() == userView.getHamburgerButton()) {
            userView.toggleMenuBar();
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
        if (e.getSource() == userView.getBtnRegister()) {
            styleButtonHover(userView.getBtnRegister(), Font.PLAIN, 13);
        } else if (e.getSource() == userView.getBtnSearch()) {
            styleButtonHover(userView.getBtnSearch(), Font.PLAIN, 13);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() ==userView.getBtnRegister()) {
            styleButtonDefault(userView.getBtnRegister());
        } else if (e.getSource() == userView.getBtnSearch()) {
            styleButtonDefault(userView.getBtnSearch());
        }
    }

    private void styleButtonHover(JButton button, int fontStyle, int fontSize) {
        button.setBackground(new Color(255,69,0));
        button.setFont(new Font("Tahoma", fontStyle, fontSize));
        button.revalidate();
        button.repaint();
    }

    private void styleButtonDefault(JButton button) {
        button.setBackground(new Color(99,184,255));
        button.setFont(new Font("Tahoma", Font.BOLD, 13));
        button.revalidate();
        button.repaint();
    }
}

