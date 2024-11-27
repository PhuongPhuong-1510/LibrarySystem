package MainApp.view;

import HomePage.view.HomePageView;
import LoginPage.view.LoginView;
import MainApp.controller.MainController;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import SignupPage.view.SignupView;
import UserMain.view.UserView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton closeButton, minimizeButton;
    private JLayeredPane layeredPane;
    private MainController mainController;
    private final LibraryModelManage libraryModelManage = new LibraryModelManage();



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
        //cardPanel.add(new UserView(this),"UserView");

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

    public void showCard(String cardName, Student student) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        // Use SwingWorker for background loading\
        showLoadingDialog();
        SwingWorker<JPanel, Void> worker = new SwingWorker<>() {
            @Override
            protected JPanel doInBackground() throws Exception {
                // Load the view in the background
                switch (cardName) {
                    case "Login":
                        return new LoginView(MainView.this);
                    case "Signup":
                        return new SignupView(MainView.this);
                    case "HomePage":
                        return new HomePageView(MainView.this);
                    case "UserView":
                        return new UserView(MainView.this, student);
                    default:
                        throw new IllegalArgumentException("Unknown card: " + cardName);
                }
            }

            @Override
            protected void done() {
                hideLoadingDialog();
                try {
                    // Add the loaded view to the cardPanel and show it
                    JPanel newCard = get();
                    cardPanel.add(newCard, cardName);
                    cl.show(cardPanel, cardName);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(MainView.this,
                            "Failed to load the view: " + cardName,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }

    private JDialog loadingDialog;
    private SwingWorker<Void, Void> searchWorker;
    private JProgressBar progressBar; // Thanh tiến trình

    private void showLoadingDialog() {
        loadingDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Loading...", true);
        loadingDialog.setUndecorated(true); // Loại bỏ khung cửa sổ
        loadingDialog.setSize(250, 300); // Kích thước dialog
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setBackground(new Color(0, 0, 0, 0)); // ARGB: alpha = 0 (trong suốt)

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

            }
        };
        panel.setOpaque(false);
        panel.setLayout(null);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // Căn giữa ảnh
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/LMSNotification/view/icon/1.gif"))); // Đường dẫn ảnh
        imageLabel.setIcon(icon);
        imageLabel.setBounds(0, 0, 240, 240); // Đặt vị trí và kích thước ảnh
        panel.add(imageLabel); // Thêm ảnh vào panel

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(new Color(255, 255, 255));
        progressBar.setBounds(0,230,240,25);
        panel.add(progressBar);

        JButton cancelButton = createButton("X",200,0);
        cancelButton.addActionListener(e -> {
            if (searchWorker != null) {
                cancelButton.setBackground(Color.RED);
                searchWorker.cancel(true);
            }
            hideLoadingDialog();
        });
        panel.add(cancelButton);

        loadingDialog.add(panel);

        loadingDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (searchWorker != null) {
                    searchWorker.cancel(true);
                }
            }
        });

        new Thread(() -> loadingDialog.setVisible(true)).start();
    }


    private void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
        }
    }

    public LibraryModelManage getLibraryModelManage() {
        if (this.libraryModelManage == null) {
            throw new IllegalStateException("LibraryModelManage is not initialized!");
        }
        return this.libraryModelManage;
    }


    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getMinimizeButton() {
        return minimizeButton;
    }
}
