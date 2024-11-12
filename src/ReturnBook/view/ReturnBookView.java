package ReturnBook.view;

import LoginPage.view.OvalButton;

import javax.swing.*;
import java.awt.*;

public class ReturnBookView extends JPanel {
    private JTextField bookTitleField;
    private JTextField authorField;
    private JTextField languageField;
    private JTextField totalField;
    private JButton issueButton;
    private JButton clearButton;

    public ReturnBookView() {
        setupMainPanel();
        add(createBookPanel());
        add(createIssuePanel());
        setVisible(true);
    }

    private void setupMainPanel() {
        this.setLayout(null);
        setBackground(new Color(230, 230, 250));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }



    private JPanel createBookPanel() {
        JPanel bookPanel = new JPanel();
        bookPanel.setBounds(80,15,400,550);
        bookPanel.setBackground(new Color(246, 222, 236));
        bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bookPanel.setLayout(null);

        JPanel titlePanel = createImageLabel("/ReturnBook/view/icon/bookDetail.png", "BOOK DETAILS", new Color(246, 222, 236), new Color(238, 58, 140));
        titlePanel.setBounds(50, 50, 300, 200);
        bookPanel.add(titlePanel);

        Color labelColor = new Color(205, 50, 120);



        bookPanel.add(createLabelAtPosition("Book Title: ", 25, 250, 200, 30, labelColor));
        bookTitleField = createTextField(150, 250, 200, 30,false);
        bookPanel.add(bookTitleField);

        bookPanel.add(createLabelAtPosition("Author: ", 25, 290, 200, 30, labelColor));
        authorField = createTextField(150, 290, 200, 30,false);
        bookPanel.add(authorField);

        bookPanel.add(createLabelAtPosition("Category: ", 25, 330, 200, 30, labelColor));
        JTextField categoryField = createTextField(150, 330, 200, 30,false);
        bookPanel.add(categoryField);

        bookPanel.add(createLabelAtPosition("Language: ", 25, 370, 200, 30, labelColor));
        languageField = createTextField(150, 370, 200, 30,false);
        bookPanel.add(languageField);

        bookPanel.add(createLabelAtPosition("Total: ", 25, 410, 200, 30, labelColor));
        totalField = createTextField(150, 410, 200, 30,false);
        bookPanel.add(totalField);

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

        panel.setBounds(600,15,400,550);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        panel.setBackground(new Color(248, 248, 255));
        panel.setLayout(null);
        return panel;
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = createImageLabel("/ReturnBook/view/icon/returnBook.png", " RETURN BOOK", new Color(248, 248, 255), new Color(30, 144, 255));
        titlePanel.setBounds(0, 30, 380, 200);
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
        issueButton = createButton("FIND", 120, 400);
        clearButton = createButton("RETURN BOOK", 120, 450);

        issuePanel.add(issueButton);
        issuePanel.add(clearButton);
    }




    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));  // Sử dụng font SansSerif với kiểu chữ bình thường và cỡ 16
        return label;
    }
    private JLabel createLabelAtPosition(String text, int x, int y, int width, int height,Color labelColor) {
        JLabel label = createLabel(text);
        label.setForeground(labelColor);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height, boolean hasBorder) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);

        if (hasBorder) {
            textField.setOpaque(false);
            textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(104, 131, 139)));  // Đặt viền dưới

        } else {
            textField.setOpaque(false);
            textField.setBorder(null);
        }

        return textField;
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new OvalButton(text);
        button.setBackground(new Color(135,206,255));
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, 160, 30);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }




    private JPanel createImageLabel(String path, String title, Color background, Color titleColor) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(background);

        Dimension fixedSize = new Dimension(200, 200);
        imagePanel.setPreferredSize(fixedSize);
        imagePanel.setMaximumSize(fixedSize);
        imagePanel.setMinimumSize(fixedSize);

        java.net.URL imageUrl = getClass().getResource(path);
        JLabel titleLabel = createLabel(title);
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
        lineContainer.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // Khoảng cách 5px ở trên để gần tiêu đề hơn
        lineContainer.add(linePanel);

        titleContainer.add(lineContainer);

        imagePanel.add(titleContainer, BorderLayout.CENTER);

        return imagePanel;
    }

}
