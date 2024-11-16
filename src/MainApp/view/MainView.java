package MainApp.view;

import HomePage.view.HomePageView;
import LoginPage.view.LoginView;
import MainApp.controller.MainController;
import SignupPage.view.SignupView;
import UserMain.view.UserView;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton closeButton, minimizeButton;
    private JLayeredPane layeredPane;
    private MainController mainController;


    public MainView() {
        setupFrame();
        setupLayeredPane();
        setupButtons();
        setupCardLayout();

        mainController = new MainController(this);
        cardLayout.show(cardPanel, "Login"); // Show the login view initially

        this.setVisible(true);
    }

    private void setupFrame() {
        this.setSize(1200, 632);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        ImageIcon icon = new ImageIcon(getClass().getResource("/LoginPage/view/icon/imgLabel.png"));
        this.setIconImage(icon.getImage());
    }

    private void setupLayeredPane() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1200, 632);
        this.setLayout(null); // Disable layout manager for manual positioning
        this.add(layeredPane);
    }

    private void setupButtons() {
        closeButton = createButton("X", 1150, 0);
        minimizeButton = createButton("-", 1100, 0);

        layeredPane.add(minimizeButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(closeButton, JLayeredPane.PALETTE_LAYER);
    }

    private void setupCardLayout() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBounds(0, 0, 1200, 632);

        cardPanel.add(new LoginView(this), "Login");
        cardPanel.add(new SignupView(this), "Signup");
        cardPanel.add(new HomePageView(this), "HomePage");
        cardPanel.add(new UserView(this),"UserView");

        layeredPane.add(cardPanel, JLayeredPane.DEFAULT_LAYER);

        ToolTipManager.sharedInstance().setInitialDelay(0); // No initial delay
        ToolTipManager.sharedInstance().setDismissDelay(3000); // Tooltip dismiss delay of 3 seconds
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(150, 180, 255));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(getButtonToolTip(text));
        button.setBounds(x, y, 50, 50); // Size and position of the button
        return button;
    }

    private String getButtonToolTip(String text) {
        switch (text) {
            case "X":
                return "Close Window";
            case "-":
                return "Minimize Window";
            default:
                return text + " Window";
        }
    }

    public void showCard(String cardName) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        switch (cardName) {
            case "Login":
                cardPanel.add(new LoginView(this), "Login");
                break;
            case "Signup":
                cardPanel.add(new SignupView(this), "Signup");
                break;
            case "HomePage":
                cardPanel.add(new HomePageView(this), "HomePage");
                break;
            case "UserView":
                cardPanel.add(new UserView(this),"UserView");
                break;
        }
        cl.show(cardPanel, cardName);
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getMinimizeButton() {
        return minimizeButton;
    }
}
