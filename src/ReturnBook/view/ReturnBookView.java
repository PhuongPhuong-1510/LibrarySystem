package ReturnBook.view;

import LoginPage.view.OvalButton;
import MainApp.model.Book;
import MainApp.model.Issue;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
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
    private LibraryModelManage libraryModelManage;
    private JTextField bookIdField;
    private JTextField studentIdField;

    public ReturnBookView(LibraryModelManage libraryModelManage) {
        this.setupMainPanel();
        this.add(layeredPane,BorderLayout.CENTER);
        this.setVisible(true);
        new ReturnBookController(this);
        this.libraryModelManage = libraryModelManage;
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
        int labelX = 25;  // X position for labels
        int fieldX = 170; // X position for text fields (to align with labels)
        int labelWidth = 140;
        int fieldWidth = 200;
        int fieldHeight = 30;
        int yOffset = 250;  // Starting y-position for the first label and text field
        int ySpacing = 40;  // Vertical spacing between each label and text field

        // Adding labels and aligned text fields for each field
        bookPanel.add(createLabelAtPosition("Issue Id: ", labelX, yOffset, labelWidth, fieldHeight, labelColor));
        issueIdTitleField = createTextField(fieldX, yOffset, fieldWidth, fieldHeight, false);
        bookPanel.add(issueIdTitleField);

        bookPanel.add(createLabelAtPosition("Book Id: ", labelX, yOffset + ySpacing, labelWidth, fieldHeight, labelColor));
        bookIdTitleFiled = createTextField(fieldX, yOffset + ySpacing, fieldWidth, fieldHeight, false);
        bookPanel.add(bookIdTitleFiled);

        bookPanel.add(createLabelAtPosition("Book Name: ", labelX, yOffset + 2 * ySpacing, labelWidth, fieldHeight, labelColor));
        bookNameTitleFiled = createTextField(fieldX, yOffset + 2 * ySpacing, fieldWidth, fieldHeight, false);
        bookPanel.add(bookNameTitleFiled);

        bookPanel.add(createLabelAtPosition("Student Id: ", labelX, yOffset + 3 * ySpacing, labelWidth, fieldHeight, labelColor));
        studentIdTitleFiled = createTextField(fieldX, yOffset + 3 * ySpacing, fieldWidth, fieldHeight, false);
        bookPanel.add(studentIdTitleFiled);

        bookPanel.add(createLabelAtPosition("Student Name: ", labelX, yOffset + 4 * ySpacing, labelWidth, fieldHeight, labelColor));
        studentNameTitleFiled = createTextField(fieldX, yOffset + 4 * ySpacing, fieldWidth, fieldHeight, false);
        bookPanel.add(studentNameTitleFiled);

        bookPanel.add(createLabelAtPosition("Issue Date: ", labelX, yOffset + 5 * ySpacing, labelWidth, fieldHeight, labelColor));
        issueDateTitleFiled = createTextField(fieldX, yOffset + 5 * ySpacing, fieldWidth, fieldHeight, false);
        bookPanel.add(issueDateTitleFiled);

        bookPanel.add(createLabelAtPosition("Due Date: ", labelX, yOffset + 6 * ySpacing, labelWidth, fieldHeight, labelColor));
        dueDateTitleFiled = createTextField(fieldX, yOffset + 6 * ySpacing, fieldWidth, fieldHeight, false);
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
        bookIdField = createTextField(150, 250, 100, 30, true);
        issuePanel.add(bookIdField);

        issuePanel.add(createLabelAtPosition("Student Id: ", 25, 300, 200, 30, labelColor));
        studentIdField = createTextField(150, 290, 100, 30, true);
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

    public void updateIssue(){
        String bookID = bookIdField.getText()+"";
        String studentID = studentIdField.getText()+"";
        Issue issue = libraryModelManage.searchIssueByBookStudent(bookID, studentID);

        if (issue != null) {
            // Populate text fields with Issue details
            issueIdTitleField.setText(issue.getIssueID());
            bookIdTitleFiled.setText(issue.getIssueBookID());
            Book book = libraryModelManage.searchBookByID(issue.getIssueBookID());
            String bookName = book.getBookName();
            bookNameTitleFiled.setText(bookName);
            Student student = libraryModelManage.searchStudentByID(issue.getIssueStudentID());
            String studentName = student.getName();
            studentIdTitleFiled.setText(issue.getIssueStudentID());
            studentNameTitleFiled.setText(studentName);
            issueDateTitleFiled.setText(issue.getIssueDate().toString());
            dueDateTitleFiled.setText(issue.getDueDate().toString());
        } else {

            issueIdTitleField.setText("");
            bookIdTitleFiled.setText("");
            bookNameTitleFiled.setText("");
            studentIdTitleFiled.setText("");
            studentNameTitleFiled.setText("");
            issueDateTitleFiled.setText("");
            dueDateTitleFiled.setText("");

            JOptionPane.showMessageDialog(this, "No issue found for the given Book ID and Student ID.", "Issue Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void returnBook() {
        String bookID = bookIdField.getText()+"";
        String studentID = studentIdField.getText()+"";
        Issue issue = libraryModelManage.searchIssueByBookStudent(bookID, studentID);
        if (issue != null && !issue.getStatus().equals("Returned")) {
            issue.setStatus("Returned");
            libraryModelManage.editIssueInDatabase(issue);
            Book book = libraryModelManage.searchBookByID(issue.getIssueBookID());
            book.setCurent("Still");
            libraryModelManage.editBookInDatabase(book);
        }else{
            JOptionPane.showMessageDialog(this, "Can't not return.", "This status is Return", JOptionPane.WARNING_MESSAGE);
        }
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
