package SignupPage.controller;

import IssueBook.view.DatePickerDemo;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import SignupPage.view.SignupView;
import SignupPage.model.SignupModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class SignupController implements ActionListener, MouseListener {

    private final SignupView signupView;
    private final SignupModel signupModel;
    private LocalDate birthDate;

    public SignupController(SignupView signupView) {
        this.signupView = signupView;
        this.signupModel = new SignupModel();

        signupView.getLoginButton().addMouseListener(this);
        signupView.getSignupButton().addMouseListener(this);
        signupView.getLoginButton().addActionListener(this);
        signupView.getSignupButton().addActionListener(this);
        signupView.getBtnDate().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == signupView.getBtnDate()) {
            System.out.println("Date Of Birth clicked");
            DatePickerDemo jDialogIssue = new DatePickerDemo();
            jDialogIssue.setBounds(965, 380, 400, 200);
            jDialogIssue.setModal(true);
            jDialogIssue.setVisible(true);

            birthDate = jDialogIssue.getSelectedDate();
            signupView.getTxtDateOfBirth().setText(birthDate.toString());
        }
        if (e.getActionCommand().equals("LOGIN")) {
            handleLogin();
        }
        if (e.getActionCommand().equals("SIGNUP")) {
            handleSignup();
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

        signupView.updateErrorMessages(errors[0], errors[1], errors[2], errors[3], errors[4], errors[5], errors[6]);

        if (hasErrors(errors)) {
            showErrorMessages(errors);
        } else {
            // Kiểm tra email đã tồn tại
            if (isEmailAlreadyRegistered(signupView.getUserName())) {
                JOptionPane.showMessageDialog(signupView,
                        "Email already exists. Please use a different email.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại đã tồn tại
            if (isPhoneNumberAlreadyRegistered(signupView.getContactNumber())) {
                JOptionPane.showMessageDialog(signupView,
                        "Phone number already exists. Please use a different phone number.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            signupView.mainView.getLibraryModelManage().addSignupToDatabase(signupView.getSignupFromPanel());
            showSuccessMessage();
            signupView.getMainView().showCard("Login", null);
        }
    }

    private boolean isEmailAlreadyRegistered(String email) {
        for (Student student : signupView.mainView.getLibraryModelManage().getStudentsList()) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPhoneNumberAlreadyRegistered(String phoneNumber) {
        for (Student student : signupView.mainView.getLibraryModelManage().getStudentsList()) {
            if (student.getPhone().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    private void gatherInputData() {
        signupModel.setFullName(signupView.getFullName());
        signupModel.setDateOfBirth(signupView.getDateOfBirth());
        signupModel.setGender(signupView.getGender());
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

        if (button == signupView.getLoginButton()) {
            hoverColor = isHovered ? new Color(192, 192, 192) : new Color(211, 211, 211); // Màu trắng khi di chuột, xám nhạt khi không
        } else if (button == signupView.getSignupButton()) {
            hoverColor = isHovered ? new Color(255, 50, 0) : new Color(255, 0, 0); // Màu đỏ khi di chuột và khi không di chuột
        } else {
            hoverColor = button.getBackground();
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}