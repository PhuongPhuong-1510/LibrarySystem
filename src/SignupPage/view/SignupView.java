package SignupPage.view;

import LoginPage.view.OvalButton;
import LoginPage.view.PlaceholderPasswordField;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import MainApp.view.MainView;
import SignupPage.model.SignupModel;

import javax.swing.*;
import java.awt.*;

public class SignupView extends JPanel {
    private SignupModel signupModel;

    private JTextField txtUserName;
    private JTextField txtFullName;
    private JPasswordField txtPassWord;
    private JPasswordField txtConfirmPass;
    private JTextField txtContactNumber;
    private JButton btnLogin;
    private JButton btnSignUp;
    public MainView mainView;

    private JLabel lblEmailError;
    private JLabel lblPasswordError;
    private JLabel lblConfirmPasswordError;
    private JLabel lblContactError;

    public SignupView(MainView mainView) {
        this.mainView = mainView;
        this.init();
        new SignupPage.controller.SignupController(this);
    }

    private void init() {
        setLayout(new BorderLayout());
        add(createLayeredPane(), BorderLayout.WEST);
        add(createLoginPanel(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(750, 632));
        layeredPane.setLayout(null);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/SignupPage/view/icon/imgLabel.png")));
        imageLabel.setBounds(0, 0, 750, 632);
        layeredPane.add(imageLabel, Integer.valueOf(0));

        JLabel welcomeLabel = createLabel("WELCOME TO", new Font("Tahoma", Font.PLAIN, 22), Color.RED, 290, 10);
        layeredPane.add(welcomeLabel, Integer.valueOf(1));

        JLabel libraryLabel = createLabel("ADVANCE LIBRARY", new Font("Tahoma", Font.PLAIN, 22), Color.BLUE, 270, 45);
        layeredPane.add(libraryLabel, Integer.valueOf(1));

        return layeredPane;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(150, 180, 255));

        loginPanel.add(createLabel("Signup Page", new Font("Tahoma", Font.PLAIN, 30), Color.WHITE, 150, 30));
        loginPanel.add(createLabel("Create New Account Here", new Font("Tahoma", Font.PLAIN, 18), Color.WHITE, 130, 80));

        lblEmailError = createErrorLabel();
        lblPasswordError = createErrorLabel();
        lblConfirmPasswordError = createErrorLabel();
        lblContactError = createErrorLabel();

        txtFullName=createTextField("Enter your fullname",200,185);
        txtUserName = createTextField("abc@gmail.com", 200, 235);
        txtPassWord = createPasswordField("Enter your new password", 200, 290);
        txtConfirmPass = createPasswordField("Enter confirm password", 200, 335);
        txtContactNumber = createTextField("0***", 205, 390);

        loginPanel.add(createLabel("Full Name: ",new Font("Tahoma",Font.PLAIN,16),Color.WHITE,50,170));
        loginPanel.add(txtFullName);
        loginPanel.add(createLabel("Email:", new Font("Tahoma", Font.PLAIN, 16), Color.WHITE, 50, 240));
        loginPanel.add(txtUserName);
        loginPanel.add(createLabel("Password:", new Font("Tahoma", Font.PLAIN, 15), Color.WHITE, 50, 280));
        loginPanel.add(txtPassWord);
        loginPanel.add(createLabel("Confirm Password:", new Font("Tahoma", Font.PLAIN, 15), Color.WHITE, 50, 335));
        loginPanel.add(txtConfirmPass);
        loginPanel.add(createLabel("Contact Number:", new Font("Tahoma", Font.PLAIN, 15), Color.WHITE, 55, 390));
        loginPanel.add(txtContactNumber);
        loginPanel.add(createSignUpButton());
        loginPanel.add(createLoginButton());

        // Adding error labels
        loginPanel.add(lblEmailError);
        loginPanel.add(lblPasswordError);
        loginPanel.add(lblConfirmPasswordError);
        loginPanel.add(lblContactError);

        setErrorLabelPositions();

        loginPanel.setFocusable(true);
        loginPanel.requestFocus();

        return loginPanel;
    }

    private JLabel createLabel(String text, Font font, Color color, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBounds(x, y, 300, 50);
        return label;
    }

    private JTextField createTextField(String placeholder, int x, int y) {
        JTextField textField = new PlaceholderPasswordField(placeholder, 0);
        textField.setBounds(x, y, 200, 30);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        return textField;
    }

    private JPasswordField createPasswordField(String placeholder, int x, int y) {
        JPasswordField passwordField = new PlaceholderPasswordField(placeholder, 1);
        passwordField.setBounds(x, y, 200, 30);
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        return passwordField;
    }

    private JLabel createErrorLabel() {
        JLabel errorLabel = new JLabel("!");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        errorLabel.setVisible(false);
        return errorLabel;
    }

    private JButton createSignUpButton() {
        btnSignUp = new OvalButton("SIGNUP");
        btnSignUp.setBackground(new Color(255, 94, 77));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setBounds(120, 480, 100, 30);
        btnSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btnSignUp;
    }

    private JButton createLoginButton() {
        btnLogin = new OvalButton("LOGIN");
        btnLogin.setBackground(new Color(192, 192, 192));
        btnLogin.setBounds(240, 480, 100, 30);
        btnLogin.setToolTipText("Do you already have an account?");
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btnLogin;
    }

    private void setErrorLabelPositions() {
        lblEmailError.setBounds(410, 185, 30, 30);
        lblPasswordError.setBounds(410, 240, 30, 30);
        lblConfirmPasswordError.setBounds(410, 280, 30, 30);
        lblContactError.setBounds(410, 325, 30, 30);
    }

    public JButton getLoginButton() {
        return btnLogin;
    }

    public JButton getSignupButton() {
        return btnSignUp;
    }

    public MainView getMainView() {
        return mainView;
    }

    public String getUserName() {
        return txtUserName.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassWord.getPassword());
    }

    public String getConfirmPassword() {
        return new String(txtConfirmPass.getPassword());
    }

    public String getContactNumber() {
        return txtContactNumber.getText();
    }

    public void showErrorMessages(String messages) {
        JOptionPane.showMessageDialog(this, messages, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void updateErrorMessages(String emailError, String passwordError, String confirmPasswordError, String contactError) {
        updateErrorLabel(lblEmailError, emailError);
        updateErrorLabel(lblPasswordError, passwordError);
        updateErrorLabel(lblConfirmPasswordError, confirmPasswordError);
        updateErrorLabel(lblContactError, contactError);
    }

    private void updateErrorLabel(JLabel label, String errorMessage) {
        label.setVisible(errorMessage != null);
        if (errorMessage != null) {
            label.setToolTipText(errorMessage);
        }
    }

    public Student getStudentFromPanel() {
        String id = mainView.libraryModelManage.createStudentID();
        String fullName = txtFullName.getText().trim();
        String userName = txtUserName.getText().trim();
        String password = txtPassWord.getText().trim();
        String contactNumber = txtContactNumber.getText().trim();

        return new Student(id, fullName, userName, password, contactNumber, true, "", "", "", "");
    }

}
