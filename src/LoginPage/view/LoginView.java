package LoginPage.view;

import LoginPage.controller.LoginController;
import LoginPage.model.LoginModel;
import MainApp.model.Admin;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import MainApp.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class LoginView extends JPanel {
    private  LoginModel loginModel;
    private  MainView mainView;
    private JTextField txtUserName;
    private JTextField txtPassWord;
    private JButton btnLogin;
    private JButton btnSignUp;
    private JButton btnForgot;
    private LibraryModelManage libraryModelManage;

    public LoginView(MainView mainView) {
        this.mainView = mainView;
        this.loginModel = new LoginModel();
        initializeUIComponents();
        new LoginController(this);
        this.libraryModelManage = new LibraryModelManage();
    }

    private void initializeUIComponents() {
        setSize(1200, 632);
        setLayout(new BorderLayout());

        JLayeredPane layeredPane = createImagePane();
        add(layeredPane, BorderLayout.WEST);

        JPanel loginPanel = createLoginPanel();
        loginPanel.setFocusable(true);
        loginPanel.requestFocus();
        add(loginPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JLayeredPane createImagePane() {
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/LoginPage/view/icon/lms.png"))));
        imageLabel.setLayout(null);
        imageLabel.setBounds(0, 0, 750, 632);

        JLabel welcomeLabel = createTextLabel("WELCOME TO", new Font("Tahoma", Font.PLAIN, 22), Color.RED, 280, 15, 300, 34);
        JLabel libraryLabel = createTextLabel("ADVANCE LIBRARY", new Font("Tahoma", Font.PLAIN, 22), Color.BLUE, 260, 50, 300, 50);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(750, 632));
        layeredPane.setLayout(null);
        layeredPane.add(imageLabel, Integer.valueOf(0));
        layeredPane.add(welcomeLabel, Integer.valueOf(1));
        layeredPane.add(libraryLabel, Integer.valueOf(1));

        return layeredPane;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(null);
        loginPanel.setBackground(new Color(150, 180, 255));

        JLabel lblWelcome = createTextLabel("Welcome!", new Font("Tahoma", Font.PLAIN, 30), Color.WHITE, 150, 30, 200, 50);
        JLabel lblLoginMsg = createTextLabel("Login To Your Account", new Font("Tahoma", Font.PLAIN, 18), Color.WHITE, 130, 80, 250, 30);

        JLabel lblUsername = createIconLabel("Username :", "/LoginPage/view/icon/userName.png", 50, 180, 150, 45);
        JLabel lblPassword = createIconLabel("Password :", "/LoginPage/view/icon/passWord.png", 50, 240, 150, 45);

        txtUserName = createPlaceholderField("Enter your email", 200, 185);
        txtPassWord = createPlaceholderField("Enter your password", 200, 235);

        btnLogin = createButton("LOGIN", new Color(255, 94, 77), 120, 340);
        btnSignUp = createButton("SIGNUP", new Color(192, 192, 192), 240, 340);
        btnForgot = createForgotPasswordButton();

        loginPanel.add(lblWelcome);
        loginPanel.add(lblLoginMsg);
        loginPanel.add(lblUsername);
        loginPanel.add(txtUserName);
        loginPanel.add(lblPassword);
        loginPanel.add(txtPassWord);
        loginPanel.add(btnLogin);
        loginPanel.add(btnSignUp);
        loginPanel.add(btnForgot);

        return loginPanel;
    }

    private JLabel createTextLabel(String text, Font font, Color color, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JLabel createIconLabel(String text, String iconPath, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, width, height);
        label.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath))));
        return label;
    }

    private JTextField createPlaceholderField(String placeholderText, int x, int y) {
        JTextField textField = new PlaceholderPasswordField(placeholderText, 0);
        textField.setBounds(x, y, 200, 30);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        return textField;
    }

    private JButton createButton(String text, Color background, int x, int y) {
        JButton button = new OvalButton(text);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, 100, 30);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createForgotPasswordButton() {
        JButton button = new JButton("Forgot Password");
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 15));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBounds(120, 390, 250, 60);
        button.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/LoginPage/view/icon/forgotPassword.png"))));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        return button;
    }


    public JButton getLoginButton() {
        return btnLogin;
    }

    public JButton getSignupButton() {
        return btnSignUp;
    }

    public JButton getBtnForgot() {
        return btnForgot;
    }

    public MainView getMainView() {
        return mainView;
    }

    public boolean checkLogin(){
        String username = txtUserName.getText();
        String password = txtPassWord.getText();
        if(!username.equals("") && !password.equals("")) {
            ArrayList<Admin> admins = libraryModelManage.getAdminsList();
            for (Admin admin : admins) {
                if (admin.getEmail().equals(username) && admin.getPassword().equals(password)) {
                    return true;
                }
            }
            ArrayList<Student> students = libraryModelManage.getStudentsList();
            for (Student student : students) {
                if (student.getEmail().equals(username) && student.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}
