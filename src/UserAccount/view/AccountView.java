package UserAccount.view;

import LoginPage.view.OvalButton;
import UserAccount.controller.AccountController;

import javax.swing.*;
import java.awt.*;

public class AccountView extends JPanel {

    private JButton passButton;

    public AccountView() {
        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = createLayeredPane();
        this.add(layeredPane, BorderLayout.CENTER);
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
        JTextField txtUsername = createTextField("admin", 120, 50, 150, 25, false);
        panel.add(txtUsername);

        JLabel lblGender = createLabel("Gender:", new Font("Tahoma", Font.PLAIN, 14), 20, 100, 100, 20);
        panel.add(lblGender);
        JTextField txtGender = createTextField("Male", 120, 100, 150, 25, false);
        panel.add(txtGender);

        JLabel lblDob = createLabel("Date of Birth:", new Font("Tahoma", Font.PLAIN, 14), 20, 150, 100, 20);
        panel.add(lblDob);
        JTextField txtDob = createTextField("15/10/2005", 120, 150, 150, 25, false);
        panel.add(txtDob);

        JLabel lblMajor = createLabel("Major:", new Font("Tahoma", Font.PLAIN, 14), 20, 200, 100, 20);
        panel.add(lblMajor);
        JTextField txtMajor = createTextField("IT", 120, 200, 150, 25, false);
        panel.add(txtMajor);

        JLabel lblBranch = createLabel("Branch:", new Font("Tahoma", Font.PLAIN, 14), 20, 250, 100, 20);
        panel.add(lblBranch);
        JTextField txtBranch = createTextField("UET", 120, 250, 150, 25, false);
        panel.add(txtBranch);

        JLabel lblPhone = createLabel("Contact Number:", new Font("Tahoma", Font.PLAIN, 14), 20, 300, 150, 20);
        panel.add(lblPhone);
        JTextField txtPhone = createTextField("0123456789", 170, 300, 150, 25, false);
        panel.add(txtPhone);

        JLabel lblEmail = createLabel("Email:", new Font("Tahoma", Font.PLAIN, 14), 20, 350, 100, 20);
        panel.add(lblEmail);
        JTextField txtEmail = createTextField("admin@example.com", 120, 350, 150, 25, false);
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
        JPasswordField txtCurrentPassword = createPasswordField(150, 50, 150, 25);
        panel.add(txtCurrentPassword);

        JLabel lblNewPassword = createLabel("New Password:", new Font("Tahoma", Font.PLAIN, 14), 20, 100, 150, 20);
        panel.add(lblNewPassword);
        JPasswordField txtNewPassword = createPasswordField(150, 100, 150, 25);
        panel.add(txtNewPassword);

        JLabel lblConfirmPassword = createLabel("Confirm New Password:", new Font("Tahoma", Font.PLAIN, 14), 20, 150, 150, 20);
        panel.add(lblConfirmPassword);
        JPasswordField txtConfirmPassword = createPasswordField(180, 150, 150, 25);
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

    public JButton getPassButton() {
        return passButton;
    }
}
