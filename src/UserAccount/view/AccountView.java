package UserAccount.view;

import LoginPage.view.OvalButton;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import MainApp.model.StudentDAO;
import UserAccount.controller.AccountController;

import javax.swing.*;
import java.awt.*;

public class AccountView extends JPanel {

    private JButton passButton;
    private LibraryModelManage libraryModelManage;
    private Student student;
    private JTextField txtEmail;
    private JTextField txtUsername;
    private JTextField txtGender;
    private JTextField txtDob;
    private JTextField txtMajor;
    private JTextField txtBranch;
    private JTextField txtPhone;
    private JPasswordField txtCurrentPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;


    public AccountView(Student student, LibraryModelManage libraryModelManage) {
        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = createLayeredPane();
        this.add(layeredPane, BorderLayout.CENTER);
        this.student = student;
        this.libraryModelManage = libraryModelManage;
        populateStudentData();


        new AccountController(this);
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(1200, 700));

        JPanel backgroundPanel = createBackgroundPanel("/UserAccount/view/icon/background1.png");
        backgroundPanel.setBounds(0, 0, 1200, 600);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        JPanel accountPanel = createAccountPanel();
        accountPanel.setBounds(10, 10, 500, 500);
        layeredPane.add(accountPanel, Integer.valueOf(1));

        JPanel passPanel = createChangePasswordPanel();
        passPanel.setBounds(800, 10, 400, 400);
        layeredPane.add(passPanel, Integer.valueOf(1));

        return layeredPane;
    }


    private JPanel createBackgroundPanel(String path) {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(path));
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        return backgroundPanel;
    }

    private JPanel createAccountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setBackground(new Color(132,173,185));

        JLabel lblTitle = createLabel("Account Information", new Font("Tahoma", Font.BOLD, 16), 100, 10, 200, 30);
        panel.add(lblTitle);

        JLabel lblUsername = createLabel("Username:", new Font("Tahoma", Font.PLAIN, 14), 20, 50, 100, 20);
        panel.add(lblUsername);
         txtUsername = createTextField("admin", 120, 50, 150, 25, false);
        panel.add(txtUsername);

        JLabel lblGender = createLabel("Gender:", new Font("Tahoma", Font.PLAIN, 14), 20, 100, 100, 20);
        panel.add(lblGender);
         txtGender = createTextField("Male", 120, 100, 150, 25, false);
        panel.add(txtGender);

        JLabel lblDob = createLabel("Date of Birth:", new Font("Tahoma", Font.PLAIN, 14), 20, 150, 100, 20);
        panel.add(lblDob);
         txtDob = createTextField("15/10/2005", 120, 150, 150, 25, false);
        panel.add(txtDob);

        JLabel lblMajor = createLabel("Major:", new Font("Tahoma", Font.PLAIN, 14), 20, 200, 100, 20);
        panel.add(lblMajor);
         txtMajor = createTextField("IT", 120, 200, 150, 25, false);
        panel.add(txtMajor);

        JLabel lblBranch = createLabel("Branch:", new Font("Tahoma", Font.PLAIN, 14), 20, 250, 100, 20);
        panel.add(lblBranch);
         txtBranch = createTextField("UET", 120, 250, 150, 25, false);
        panel.add(txtBranch);

        JLabel lblPhone = createLabel("Contact Number:", new Font("Tahoma", Font.PLAIN, 14), 20, 300, 150, 20);
        panel.add(lblPhone);
         txtPhone = createTextField("0123456789", 170, 300, 150, 25, false);
        panel.add(txtPhone);

        JLabel lblEmail = createLabel("Email:", new Font("Tahoma", Font.PLAIN, 14), 20, 350, 100, 20);
        panel.add(lblEmail);
         txtEmail = createTextField("admin@example.com", 120, 350, 150, 25, false);
        panel.add(txtEmail);

        return panel;
    }


    private JPanel createChangePasswordPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(300, 350));
        panel.setBackground(new Color(247,244,248));


        JLabel lblTitle = createLabel("Change Password", new Font("Tahoma", Font.BOLD, 16), 50, 10, 200, 30);
        panel.add(lblTitle);

        JLabel lblCurrentPassword = createLabel("Current Password:", new Font("Tahoma", Font.PLAIN, 14), 20, 50, 150, 20);
        panel.add(lblCurrentPassword);
         txtCurrentPassword = createPasswordField(150, 50, 150, 25);
        panel.add(txtCurrentPassword);

        JLabel lblNewPassword = createLabel("New Password:", new Font("Tahoma", Font.PLAIN, 14), 20, 100, 150, 20);
        panel.add(lblNewPassword);
         txtNewPassword = createPasswordField(150, 100, 150, 25);
        panel.add(txtNewPassword);

        JLabel lblConfirmPassword = createLabel("Confirm New Password:", new Font("Tahoma", Font.PLAIN, 14), 20, 150, 150, 20);
        panel.add(lblConfirmPassword);
         txtConfirmPassword = createPasswordField(180, 150, 150, 25);
        panel.add(txtConfirmPassword);

        passButton=createButton("SUBMIT");
        passButton.setBounds(120,200,100,30);
        panel.add(passButton);

        return panel;
    }


    private JPasswordField createPasswordField(int x, int y, int width, int height) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(x, y, width, height);
        return passwordField;
    }







    private JLabel createLabel(String text, Font font, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField createTextField(String text, int x, int y, int width, int height, boolean editable) {
        JTextField textField = new JTextField(text);
        textField.setBounds(x, y, width, height);
        textField.setEditable(editable);
        return textField;
    }
    private JButton createButton(String text) {
        JButton button = new OvalButton(text);
        button.setBackground(new Color(132,173,185));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(80,25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    private void populateStudentData() {
        if (student != null) {
            txtUsername.setText(student.getName());

            txtGender.setText(student.getGender() ? "Male" : "Female"); // Hiển thị "Nam" nếu true, "Nữ" nếu false
            txtMajor.setText(student.getMajor());
            txtDob.setText(student.getDateOfBirth());
            txtBranch.setText(student.getBranch());
            txtPhone.setText(student.getPhone());
            txtEmail.setText(student.getEmail());
        } else {
            System.out.println("Student data is null.");
        }
    }

    public JButton getPassButton() {
        return passButton;
    }
    public void addChangePasswordListener() {
        if (student == null || student.getPassword() == null) {
            JOptionPane.showMessageDialog(this, "Cannot change the password as student information is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentPassword = new String(txtCurrentPassword.getPassword());
        String newPassword = new String(txtNewPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        if (!currentPassword.equals(student.getPassword())) {
            JOptionPane.showMessageDialog(this, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String validationMessage = validatePassword(newPassword);
        if (validationMessage != null) {
            JOptionPane.showMessageDialog(this, validationMessage, "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "New password and confirmation password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        student.setPassword(newPassword);
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.editStudent(student);

        JOptionPane.showMessageDialog(this, "Password successfully changed!", "Success", JOptionPane.INFORMATION_MESSAGE);

        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }

    private String validatePassword(String password) {
        if (password.length() < 8 ||
                !password.matches(".*[a-z].*") ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[0-9].*") ||
                !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return "Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers, and special characters!";
        }
        return null; // Không có lỗi
    }

}
