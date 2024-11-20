package SignupPage.controller;

import MainApp.model.LibraryModelManage;
import SignupPage.view.SignupView;
import SignupPage.model.SignupModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignupController implements ActionListener, MouseListener {

    private final SignupView signupView;
    private final SignupModel signupModel;

    public SignupController(SignupView signupView) {
        this.signupView = signupView;
        this.signupModel = new SignupModel();

        signupView.getLoginButton().addMouseListener(this);
        signupView.getSignupButton().addMouseListener(this);
        signupView.getLoginButton().addActionListener(this);
        signupView.getSignupButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "LOGIN":
                handleLogin();
                break;
            case "SIGNUP":
                handleSignup();
                break;
        }
    }

    private void handleLogin() {
        int response = JOptionPane.showConfirmDialog(signupView, "Do you want to continue?", "Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            signupView.getMainView().showCard("Login", null);
        }
    }

    private void handleSignup() {
        gatherInputData();
        String[] errors = signupModel.validateInput();

        signupView.updateErrorMessages(errors[0], errors[1], errors[2], errors[3]);

        if (hasErrors(errors)) {
            showErrorMessages(errors);
        } else {
            signupView.mainView.libraryModelManage.addStudentToDatabase(signupView.getStudentFromPanel());
            showSuccessMessage();
            signupView.getMainView().showCard("Login", null);

        }
    }

    private void gatherInputData() {
        signupModel.setEmail(signupView.getUserName());
        signupModel.setPassword(signupView.getPassword());
        signupModel.setContact(signupView.getContactNumber());
        signupModel.setConfirmPassword(signupView.getConfirmPassword());
    }

    private boolean hasErrors(String[] errors) {
        for (String error : errors) {
            if (error != null) {
                return true;
            }
        }
        return false;
    }

    private void showErrorMessages(String[] errors) {
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");
        for (String error : errors) {
            if (error != null) {
                errorMessage.append("- ").append(error).append("\n");
            }
        }
        JOptionPane.showMessageDialog(signupView, errorMessage.toString(), "Error Notification", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage() {
        JOptionPane.showMessageDialog(signupView, "Registration Successful!", "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        setButtonHoverProperties(sourceButton, true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        setButtonHoverProperties(sourceButton, false);
    }

    private void setButtonHoverProperties(JButton button, boolean isHovered) {
        Color hoverColor;

        // Đặt màu nền cho nút Login và Signup
        if (button == signupView.getLoginButton()) {
            hoverColor = isHovered ? new Color(192, 192, 192) : new Color(211, 211, 211); // Màu trắng khi di chuột, xám nhạt khi không
        } else if (button == signupView.getSignupButton()) {
            hoverColor = isHovered ? new Color(255, 50, 0) : new Color(255, 0, 0); // Màu đỏ khi di chuột và khi không di chuột
        } else {
            hoverColor = button.getBackground(); // Màu nền mặc định nếu không phải nút Login hoặc Signup
        }

        Dimension size = isHovered ? new Dimension(120, 40) : new Dimension(100, 30);
        Font font = isHovered ? new Font("Tahoma", Font.PLAIN, 14) : new Font("Tahoma", Font.BOLD, 16);

        button.setBackground(hoverColor);
        button.setPreferredSize(size);
        button.setFont(font);
        button.revalidate();
        button.repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // Optional: Có thể xử lý logic khi nút được nhấn
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Optional: Có thể xử lý logic khi nút được nhấp vào
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Optional: Xử lý khi nhả nút
    }
}
