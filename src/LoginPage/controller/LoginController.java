package LoginPage.controller;

import LoginPage.view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginController implements ActionListener, MouseListener {
    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        initializeListeners();
    }

    private void initializeListeners() {
        loginView.getLoginButton().addActionListener(this);
        loginView.getSignupButton().addActionListener(this);
        loginView.getBtnForgot().addActionListener(this);

        loginView.getLoginButton().addMouseListener(this);
        loginView.getSignupButton().addMouseListener(this);
        loginView.getBtnForgot().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "LOGIN":
                System.out.println("Login button clicked!");
                //if(loginView.checkLogin()) {
                    loginView.getMainView().showCard("HomePage");
//                }else{
//                    JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu sai", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
//
//                }
                break;
            case "SIGNUP":
                System.out.println("Signup button clicked!");
                loginView.getMainView().showCard("Signup");
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == loginView.getLoginButton()) {
            styleButtonHover(loginView.getLoginButton(), new Color(255, 50, 0), 120, 40, Font.PLAIN, 14);
        } else if (e.getSource() == loginView.getSignupButton()) {
            styleButtonHover(loginView.getSignupButton(), new Color(192, 192, 192), 120, 40, Font.PLAIN, 14);
        } else if (e.getSource() == loginView.getBtnForgot()) {
            styleForgotButtonHover();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == loginView.getLoginButton()) {
            styleButtonHover(loginView.getLoginButton(), new Color(255, 0, 0), 100, 30, Font.BOLD, 16);
        } else if (e.getSource() == loginView.getSignupButton()) {
            styleButtonHover(loginView.getSignupButton(), new Color(211, 211, 211), 100, 30, Font.BOLD, 16);
        } else if (e.getSource() == loginView.getBtnForgot()) {
            resetForgotButtonStyle();
        }
    }

    private void styleButtonHover(JButton button, Color background, int width, int height, int fontStyle, int fontSize) {
        button.setBackground(background);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font("Tahoma", fontStyle, fontSize));
        button.revalidate();
        button.repaint();
    }

    private void styleForgotButtonHover() {
        loginView.getBtnForgot().setFont(new Font("Tahoma", Font.BOLD, 15));
        loginView.getBtnForgot().setText("<html><u>Forgot Password</u></html>");
        adjustButtonSize(loginView.getBtnForgot(), -2, 2);
    }

    private void resetForgotButtonStyle() {
        loginView.getBtnForgot().setFont(new Font("Tahoma", Font.PLAIN, 14));
        loginView.getBtnForgot().setText("Forgot Password");
        loginView.getBtnForgot().setForeground(Color.WHITE);
        adjustButtonSize(loginView.getBtnForgot(), 2, -2);
    }

    private void adjustButtonSize(JButton button, int yAdjust, int heightAdjust) {
        button.setBounds(
                button.getX(),
                button.getY() + yAdjust,
                button.getWidth(),
                button.getHeight() + heightAdjust
        );
        button.revalidate();
        button.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}
