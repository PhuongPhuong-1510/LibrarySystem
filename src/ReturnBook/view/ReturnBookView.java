package ReturnBook.view;

import LoginPage.view.OvalButton;
import ReturnBook.controller.ReturnBookController;

import javax.swing.*;
import java.awt.*;

public class ReturnBookView extends JPanel {

    private JLayeredPane layeredPane;
    private JTextField issueIdTitleField;
    private JTextField bookIdTitleFiled;
    private JTextField bookNameTitleFiled;
    private JTextField studentNameTitleFiled;
    private JTextField issueDateTitleFiled;
    private JTextField dueDateTitleFiled;
    private JButton findButton;
    private JButton returnButton;
    private JTextField studentIdTitleFiled;

    public ReturnBookView() {
        this.setupMainPanel();
        this.add(layeredPane,BorderLayout.CENTER);
        this.setVisible(true);
        new ReturnBookController(this);
    }

    private void setupMainPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 250));

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1200, 600)); // Thiết lập kích thước cho layeredPane

        JPanel backgroundPanel = createBackgroundPanel("/ReturnBook/view/icon/background.png");
        backgroundPanel.setBounds(0, 0, 1200, 600);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false); // Để thấy được ảnh nền
        contentPanel.setBounds(0, 0, 1200, 600);

        contentPanel.add(createBookPanel());
        contentPanel.add(createIssuePanel());

        // Thêm contentPanel vào layer 1
        layeredPane.add(contentPanel, Integer.valueOf(1));
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

    private JPanel createBookPanel() {
        JPanel bookPanel = new JPanel();
        bookPanel.setBounds(80, 15, 400, 550);
        bookPanel.setBackground(new Color(202, 225, 255));
        bookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        bookPanel.setLayout(null);

        JPanel titlePanel = createImageLabel("/ReturnBook/view/icon/bookDetail.png", "BOOK DETAILS", new Color(202, 225, 255), new Color(85,85,85));
        titlePanel.setBounds(50, 50, 300, 200);
        bookPanel.add(titlePanel);

        Color labelColor = new Color(54, 54, 139);
        bookPanel.add(createLabelAtPosition("Issue Id: ", 25, 250, 100, 30, labelColor));
        issueIdTitleField = createTextField(150, 250, 200, 30, false);
        bookPanel.add(issueIdTitleField);

        bookPanel.add(createLabelAtPosition("Book Id: ", 25, 290, 200, 30, labelColor));
        bookIdTitleFiled = createTextField(150, 280, 200, 30, false);
        bookPanel.add(bookIdTitleFiled);

        bookPanel.add(createLabelAtPosition("Book Name: ", 25, 330, 200, 30, labelColor));
        bookNameTitleFiled = createTextField(150, 310, 200, 30, false);
        bookPanel.add(bookNameTitleFiled);

        bookPanel.add(createLabelAtPosition("Student Id: ", 25, 370, 200, 30, labelColor));
        studentIdTitleFiled= createTextField(150, 340, 200, 30, false);
        bookPanel.add(studentIdTitleFiled);

        bookPanel.add(createLabelAtPosition("Student Name: ", 25, 410, 200, 30, labelColor));
        studentNameTitleFiled = createTextField(150, 370, 200, 30, false);
        bookPanel.add(studentNameTitleFiled);


        bookPanel.add(createLabelAtPosition("Issue Date: ", 25, 450, 200, 30, labelColor));
        issueDateTitleFiled = createTextField(150, 370, 200, 30, false);
        bookPanel.add(issueDateTitleFiled);


        bookPanel.add(createLabelAtPosition("Due Date: ", 25, 490, 200, 30, labelColor));
        dueDateTitleFiled = createTextField(150, 370, 200, 30, false);
        bookPanel.add(dueDateTitleFiled);


        return bookPanel;
    }

    private JPanel createIssuePanel() {
        JPanel issuePanel = initializeIssuePanel();
        JPanel titlePanel = createTitlePanel();
        issuePanel.add(titlePanel);

        Color labelColor = new Color(119, 136, 153);
        addIssueDetailsFields(issuePanel, labelColor);
        addButtons(issuePanel);

        return issuePanel;
    }

    private JPanel initializeIssuePanel() {
        JPanel panel = new JPanel();
        panel.setBounds(700, 15, 400, 550);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setBackground(new Color(248, 248, 255));
        panel.setLayout(null);
        return panel;
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = createImageLabel("/ReturnBook/view/icon/returnBook.png", " RETURN BOOK", new Color(248, 248, 255), new Color(112, 112, 112));
        titlePanel.setBounds(20, 30, 350, 200);
        return titlePanel;
    }

    private void addIssueDetailsFields(JPanel issuePanel, Color labelColor) {
        issuePanel.add(createLabelAtPosition("Book Id: ", 25, 260, 200, 30, labelColor));
        JTextField bookIdField = createTextField(150, 250, 100, 30, true);
        issuePanel.add(bookIdField);

        issuePanel.add(createLabelAtPosition("Student Id: ", 25, 300, 200, 30, labelColor));
        JTextField studentIdField = createTextField(150, 290, 100, 30, true);
        issuePanel.add(studentIdField);
    }

    private void addButtons(JPanel issuePanel) {
        findButton = createButton("FIND", 120, 400);
        returnButton = createButton("RETURN BOOK", 120, 450);

        issuePanel.add(findButton);
        issuePanel.add(returnButton);
    }

    private JLabel createLabelAtPosition(String text, int x, int y, int width, int height, Color labelColor) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
        label.setForeground(labelColor);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height, boolean hasBorder) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);

        if (hasBorder) {
            textField.setOpaque(false);
            textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(104, 131, 139)));
        } else {
            textField.setOpaque(false);
            textField.setBorder(null);
        }

        return textField;
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new OvalButton(text);
        button.setBackground(new Color(135, 206, 255));
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, 160, 30);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createImageLabel(String path, String title, Color background, Color titleColor) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(background);

        Dimension fixedSize = new Dimension(200, 130);
        imagePanel.setPreferredSize(fixedSize);

        java.net.URL imageUrl = getClass().getResource(path);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(titleColor);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        if (imageUrl != null) {
            titleLabel.setIcon(new ImageIcon(imageUrl));
            titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        } else {
            System.err.println("Không tìm thấy tệp ảnh: " + path);
        }

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setBackground(background);
        titleContainer.add(titleLabel);

        JPanel linePanel = new JPanel();
        linePanel.setBackground(titleColor);
        linePanel.setPreferredSize(new Dimension(200, 4));
        JPanel lineContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        lineContainer.setBackground(background);
        lineContainer.add(linePanel);

        titleContainer.add(lineContainer);
        imagePanel.add(titleContainer, BorderLayout.CENTER);

        return imagePanel;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    public JTextField getIssueIdTitleField() {
        return issueIdTitleField;
    }

    public JTextField getBookIdTitleFiled() {
        return bookIdTitleFiled;
    }

    public JTextField getBookNameTitleFiled() {
        return bookNameTitleFiled;
    }

    public JTextField getStudentNameTitleFiled() {
        return studentNameTitleFiled;
    }

    public JTextField getIssueDateTitleFiled() {
        return issueDateTitleFiled;
    }

    public JTextField getDueDateTitleFiled() {
        return dueDateTitleFiled;
    }

    public JButton getFindButton() {
        return findButton;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    public JTextField getStudentIdTitleFiled() {
        return studentIdTitleFiled;
    }
}
