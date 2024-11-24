package SignupPage.view;

import LoginPage.view.OvalButton;
import LoginPage.view.PlaceholderPasswordField;
import MainApp.model.Signup;
import MainApp.model.SignupDAO;
import MainApp.model.Student;
import MainApp.view.MainView;
import SignupPage.model.SignupModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SignupView extends JPanel {
    private SignupModel signupModel;

    private JTextField txtUserName;
    private JTextField txtFullName;
    private JPasswordField txtPassWord;
    private JPasswordField txtConfirmPass;
    private JTextField txtContactNumber;
    private JComboBox<String> cmbUserType;
    private JButton btnLogin;
    private JButton btnSignUp;
    private JTextField txtDateOfBirth;
    private JButton btnDate;
    public MainView mainView;

    private JLabel lblFullNameError;
    private JLabel lblDateOfBirthError;
    private JLabel lblGenderError;
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
        lblDateOfBirthError=createErrorLabel();
        lblGenderError=createErrorLabel();
        lblFullNameError=createErrorLabel();

        txtFullName=createTextField("Enter your fullname",200,125);
        txtDateOfBirth=createTextField("Select your date of birth",200,240);
        txtUserName = createTextField("abc@gmail.com", 200, 290);
        txtPassWord = createPasswordField("Enter your new password", 200, 345);
        txtConfirmPass = createPasswordField("Enter confirm password", 200, 390);
        txtContactNumber = createTextField("0***", 200, 445);


        loginPanel.add( createLabel("Gender: ",new Font("Tahoma",Font.PLAIN,16),Color.WHITE,50,175));
        cmbUserType = new JComboBox<>(new String[]{"Select","Male", "Female"});
        cmbUserType.setBackground(new Color(202,225,255));
        cmbUserType.setBounds(200, 185, 195, 30);
        loginPanel.add(cmbUserType);



        loginPanel.add(createLabel("Full Name: ",new Font("Tahoma",Font.PLAIN,16),Color.WHITE,50,120));
        loginPanel.add(txtFullName);
        loginPanel.add(createLabel( "Date Of Birth:",new Font("Tahoma",Font.PLAIN,16),Color.WHITE,50,235));
        loginPanel.add(txtDateOfBirth);
        loginPanel.add(createLabel("Email:", new Font("Tahoma", Font.PLAIN, 16), Color.WHITE, 50, 295));
        loginPanel.add(txtUserName);
        loginPanel.add(createLabel("Password:", new Font("Tahoma", Font.PLAIN, 15), Color.WHITE, 50, 335));
        loginPanel.add(txtPassWord);
        loginPanel.add(createLabel("Confirm Password:", new Font("Tahoma", Font.PLAIN, 15), Color.WHITE, 50, 390));
        loginPanel.add(txtConfirmPass);
        loginPanel.add(createLabel("Contact Number:", new Font("Tahoma", Font.PLAIN, 15), Color.WHITE, 50, 445));
        loginPanel.add(txtContactNumber);
        loginPanel.add(createSignUpButton());
        loginPanel.add(createLoginButton());



        btnDate= createDatePickerButton(405,248);
        loginPanel.add(btnDate);
        btnDate.setToolTipText("Select Date");
        setErrorLabelPositions();


        loginPanel.add(lblEmailError);
        loginPanel.add(lblPasswordError);
        loginPanel.add(lblConfirmPasswordError);
        loginPanel.add(lblContactError);
        loginPanel.add(lblFullNameError);
        loginPanel.add(lblGenderError);
        loginPanel.add(lblDateOfBirthError);



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
        JTextField textField = new PlaceholderPasswordField(placeholder);
        textField.setBounds(x, y, 200, 30);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        return textField;
    }

    private JPasswordField createPasswordField(String placeholder, int x, int y) {
        JTextField textField = new PlaceholderPassword(placeholder, 1);

        JPasswordField passwordField = (JPasswordField) textField;

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
        btnSignUp.setBounds(110, 535, 100, 30);
        btnSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btnSignUp;
    }

    private JButton createLoginButton() {
        btnLogin = new OvalButton("LOGIN");
        btnLogin.setBackground(new Color(192, 192, 192));
        btnLogin.setBounds(250, 535, 100, 30);
        btnLogin.setToolTipText("Do you already have an account?");
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btnLogin;
    }

    private void setErrorLabelPositions() {
        lblFullNameError.setBounds(410, 125, 30, 30);
        lblGenderError.setBounds(410, 180, 30, 30);
        lblDateOfBirthError.setBounds(410, 240, 30, 30);
        lblEmailError.setBounds(410, 290, 30, 30);
        lblPasswordError.setBounds(410, 345, 30, 30);
        lblConfirmPasswordError.setBounds(410, 390, 30, 30);
        lblContactError.setBounds(410, 445, 30, 30);
    }

    private JButton createDatePickerButton(int x, int y) {
        JButton button = new JButton();
        button.setBounds(x, y, 15, 15);
        button.setBackground(new Color(250, 128, 114));
        return button;
    }


    public void updateErrorMessages(
            String fullnameError,
            String genderError,
            String dateOfBirthError,
            String emailError,
            String passwordError,
            String confirmPasswordError,
            String contactError) {

        updateErrorLabel(lblFullNameError, fullnameError);
        updateErrorLabel(lblGenderError, genderError);
        updateErrorLabel(lblDateOfBirthError, dateOfBirthError);
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

    public Signup getSignupFromPanel() {
        String fullName = txtFullName.getText().trim();
        String userName = txtUserName.getText().trim();
        String password = txtPassWord.getText().trim();
        String contactNumber = txtContactNumber.getText().trim();
        String dateStr = txtDateOfBirth.getText().trim();
        String gender = (String) cmbUserType.getSelectedItem();

        boolean isMale = gender != null && gender.equalsIgnoreCase("Male");

        // Chuyển từ chuỗi "2024-11-07" sang kiểu Date
        Date dateOfBirth = Date.valueOf(dateStr); // Chuyển đổi trực tiếp từ chuỗi sang java.sql.Date

        return new Signup(fullName, userName, password, contactNumber, isMale, "", dateOfBirth, "", "");
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

    public String getFullName() {
        return new String(txtFullName.getText().trim());
    }

    public String getDateOfBirth() {
        return new String(txtDateOfBirth.getText().trim());
    }

    public String getGender() {
        return new String(cmbUserType.getSelectedItem() != null
                ? cmbUserType.getSelectedItem().toString().trim()
                : "");
    }


    public JButton getBtnDate() {
        return btnDate;
    }

    public JTextField getTxtDateOfBirth() {
        return txtDateOfBirth;
    }

}
