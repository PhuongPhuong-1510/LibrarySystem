package IssueBook.view;

import IssueBook.controller.IssueBookController;
import LoginPage.view.OvalButton;
import MainApp.model.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class IssueBookView extends JPanel {
    private JTextField bookTitleField;
    private JTextField authorField;
    private JTextField languageField;
    private JTextField totalField;
    private JTextField studentNameField;
    private JTextField contactPhoneField;
    private JTextField contactEmailField;
    private JTextField majorField;
    private JTextField branchField;
    private JButton dueDateButton;
    private JButton issueDateButton;
    private JTextField issueDateField;
    private JTextField dueDateField;
    private JButton issueButton;
    private JButton clearButton;
    private JTextField bookIdField;
    private JTextField studentIdField;
    private LibraryModelManage libraryModelManage;
    private JTextField categoryField;


    public IssueBookView(LibraryModelManage libraryModelManage) {
        if (libraryModelManage == null) {
            throw new IllegalArgumentException("LibraryModelManage cannot be null");
        }
        this.libraryModelManage = libraryModelManage;

        setupMainPanel();
        add(createBookPanel());
        add(createStudentPanel());
        add(createIssuePanel());
        setVisible(true);
        new IssueBookController(this);

        bookIdField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    updateBookInfo(); // Chỉ cập nhật thông tin sách
                }
            }
        });

        studentIdField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    updateStudentInfo();
                }
            }
        });
    }

    private void updateBookInfo() {
        String bookID = this.bookIdField.getText();

        Book book = libraryModelManage.searchBookByID(bookID);

        if (book != null) {
            bookTitleField.setText(book.getBookName());
            authorField.setText(book.getAuthor());
            languageField.setText(book.getLanguage());
            categoryField.setText(book.getCategory());
            totalField.setText(String.valueOf(book.getTotal()));
        } else {
            JOptionPane.showMessageDialog(null, "book not found", "Error", JOptionPane.ERROR_MESSAGE);
            bookTitleField.setText("");
            authorField.setText("");
            languageField.setText("");
            categoryField.setText("");
            totalField.setText("");
        }
    }

    private void updateStudentInfo() {
        String studentID = this.studentIdField.getText();

        Student student = libraryModelManage.searchStudentByID(studentID);

        if (student != null) {
            studentNameField.setText(student.getName());
            contactPhoneField.setText(student.getPhone());
            contactEmailField.setText(student.getEmail());
            majorField.setText("Student");
            branchField.setText("Student");
        } else {
            JOptionPane.showMessageDialog(null, "student not found", "Error", JOptionPane.ERROR_MESSAGE);
            studentNameField.setText("");
            contactPhoneField.setText("");
            contactEmailField.setText("");
            majorField.setText("");
            branchField.setText("");
        }
    }

    public void updateBookAndStudentInfo(String bookID, String studentID) {
        // Cập nhật thông tin sách
        Book book = libraryModelManage.searchBookByID(bookID);

        if (book != null) {
            bookTitleField.setText(book.getBookName());
            authorField.setText(book.getAuthor());
            languageField.setText(book.getLanguage());
            categoryField.setText(book.getCategory());
            totalField.setText(String.valueOf(book.getTotal()));
        } else {
            JOptionPane.showMessageDialog(null, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
            bookTitleField.setText("");
            authorField.setText("");
            languageField.setText("");
            categoryField.setText("");
            totalField.setText("");
        }

        // Cập nhật thông tin sinh viên
        Student student = libraryModelManage.searchStudentByID(studentID);

        if (student != null) {
            studentNameField.setText(student.getName());
            contactPhoneField.setText(student.getPhone());
            contactEmailField.setText(student.getEmail());
            majorField.setText("Student");
            branchField.setText("Student");
        } else {
            JOptionPane.showMessageDialog(null, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
            studentNameField.setText("");
            contactPhoneField.setText("");
            contactEmailField.setText("");
            majorField.setText("");
            branchField.setText("");
        }
    }


    private void setupMainPanel() {
        setLayout(new GridLayout(1, 3, 10, 10));
        setBackground(new Color(230, 230, 250));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }



    private JPanel createBookPanel() {
        JPanel bookPanel = new JPanel();
        bookPanel.setBackground(new Color(246, 222, 236));
        bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bookPanel.setLayout(null);

        JPanel titlePanel = createImageLabel("/IssueBook/view/icon/issueBook.png", "BOOK DETAILS", new Color(246, 222, 236), new Color(238, 58, 140));
        titlePanel.setBounds(0, 50, 400, 200);
        bookPanel.add(titlePanel);

        Color labelColor = new Color(205, 50, 120);



        bookPanel.add(createLabelAtPosition("Book Title: ", 25, 250, 200, 30, labelColor));
        bookTitleField = createTextField(150, 250, 200, 30,false);
        bookPanel.add(bookTitleField);

        bookPanel.add(createLabelAtPosition("Author: ", 25, 290, 200, 30, labelColor));
        authorField = createTextField(150, 290, 200, 30,false);
        bookPanel.add(authorField);

        bookPanel.add(createLabelAtPosition("Category: ", 25, 330, 200, 30, labelColor));
        categoryField = createTextField(150, 330, 200, 30,false);
        bookPanel.add(categoryField);

        bookPanel.add(createLabelAtPosition("Language: ", 25, 370, 200, 30, labelColor));
        languageField = createTextField(150, 370, 200, 30,false);
        bookPanel.add(languageField);

        bookPanel.add(createLabelAtPosition("Total: ", 25, 410, 200, 30, labelColor));
        totalField = createTextField(150, 410, 200, 30,false);
        bookPanel.add(totalField);

        return bookPanel;
    }
    private JPanel createStudentPanel() {
        JPanel studentPanel = new JPanel();
        studentPanel.setBackground(new Color(202, 225, 255));
        studentPanel.setLayout(null);

        JPanel titlePanel = createImageLabel("/IssueBook/view/icon/issueStudent.png", "STUDENT DETAILS", new Color(202, 225, 255), new Color(0, 191, 255));
        titlePanel.setBounds(0, 50, 400, 200);
        studentPanel.add(titlePanel);

        Color labelColor = new Color(0, 178, 238);



        studentPanel.add(createLabelAtPosition("Student Name: ", 25, 250, 200, 30, labelColor));
        studentNameField = createTextField(150, 250, 100, 30,false);
        studentPanel.add(studentNameField);

        studentPanel.add(createLabelAtPosition("Contact Phone: ", 25, 290, 200, 30, labelColor));
        contactPhoneField = createTextField(150, 290, 100, 30,false);
        studentPanel.add(contactPhoneField);

        studentPanel.add(createLabelAtPosition("Contact Email: ", 25, 330, 200, 30, labelColor));
        contactEmailField = createTextField(155, 320, 100, 50,false);
        studentPanel.add(contactEmailField);

        studentPanel.add(createLabelAtPosition("Major: ", 25, 370, 200, 30, labelColor));
        majorField = createTextField(150, 370, 100, 30,false);
        studentPanel.add(majorField);

        studentPanel.add(createLabelAtPosition("Branch: ", 25, 410, 200, 30, labelColor));
        branchField = createTextField(150, 410, 100, 30,false);
        studentPanel.add(branchField);

        return studentPanel;
    }




    private JPanel createIssuePanel() {
        JPanel issuePanel = initializeIssuePanel();
        JPanel titlePanel = createTitlePanel();
        issuePanel.add(titlePanel);

        Color labelColor = new Color(205, 145, 158);

        addIssueDetailsFields(issuePanel, labelColor);
        addDatePickers(issuePanel);
        addButtons(issuePanel);

        return issuePanel;
    }

    private JPanel initializeIssuePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 248, 255));
        panel.setLayout(null);
        return panel;
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = createImageLabel("/IssueBook/view/icon/issue.png", "ISSUE BOOK", new Color(248, 248, 255), new Color(238, 162, 173));
        titlePanel.setBounds(20, 50, 400, 200);
        return titlePanel;
    }

    private void addIssueDetailsFields(JPanel issuePanel, Color labelColor) {
        issuePanel.add(createLabelAtPosition("Book Id: ", 25, 250, 200, 30, labelColor));
        bookIdField = createTextField(150, 250, 100, 30, true);
        issuePanel.add(bookIdField);

        issuePanel.add(createLabelAtPosition("Student Id: ", 25, 290, 200, 30, labelColor));
        studentIdField = createTextField(150, 290, 100, 30, true);
        issuePanel.add(studentIdField);
    }

    private void addDatePickers(JPanel issuePanel) {
        Color backgroundColor = new Color(255, 192, 203);

        issuePanel.add(createLabelAtPosition("Issue Date: ", 25, 330, 200, 30, new Color(205, 145, 158)));
        issueDateField = createTextField(150, 330, 100, 30, true);
        issueDateField.setBackground(backgroundColor);
        issueDateButton = createDatePickerButton(260, 341);
        issueDateButton.setToolTipText("Select borrowing date");

        issuePanel.add(issueDateField);
        issuePanel.add(issueDateButton);

        issuePanel.add(createLabelAtPosition("Due Date: ", 25, 370, 200, 30, new Color(205, 145, 158)));
        dueDateField = createTextField(150, 370, 100, 30, true);
        dueDateButton = createDatePickerButton(260, 386);
        dueDateButton.setToolTipText("Select return date");

        issuePanel.add(dueDateField);
        issuePanel.add(dueDateButton);
    }

    private JButton createDatePickerButton(int x, int y) {
        JButton button = new JButton();
        button.setBounds(x, y, 20, 20);
        button.setBackground(new Color(255, 192, 203));
        return button;
    }


    private void addButtons(JPanel issuePanel) {
        issueButton = createButton("ISSUE", 50, 470);
        clearButton = createButton("CLEAR", 240, 470);

        issuePanel.add(issueButton);
        issuePanel.add(clearButton);
    }




    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));  // Sử dụng font SansSerif với kiểu chữ bình thường và cỡ 16
        return label;
    }
    private JLabel createLabelAtPosition(String text, int x, int y, int width, int height,Color labelColor) {
        JLabel label = createLabel(text);
        label.setForeground(labelColor);
        label.setBounds(x, y, width, height);  // Đặt vị trí và kích thước cho label
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height, boolean hasBorder) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);

        if (hasBorder) {
            textField.setOpaque(false);
            textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(181, 66, 171, 221)));  // Đặt viền dưới

        } else {
            textField.setOpaque(false);
            textField.setBorder(null);
        }

        return textField;
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new OvalButton(text);
        button.setBackground(new Color(255,130,171));
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, 100, 30);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }




    private JPanel createImageLabel(String path, String title, Color background, Color titleColor) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(background);

        Dimension fixedSize = new Dimension(400, 200);
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
        linePanel.setPreferredSize(new Dimension(250, 4));

        JPanel lineContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        lineContainer.setBackground(background);
        lineContainer.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // Khoảng cách 5px ở trên để gần tiêu đề hơn
        lineContainer.add(linePanel);

        titleContainer.add(lineContainer);

        imagePanel.add(titleContainer, BorderLayout.CENTER);

        return imagePanel;
    }



    public void issueBook() {
        String bookID = this.bookIdField.getText() + "";
        String studentID = this.studentIdField.getText() + "";
        String issueDateString = this.issueDateField.getText();
        String dueDateString = this.dueDateField.getText();
        String status = "issued";

        if (issueDateString.isEmpty() || dueDateString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Issue Date and Due Date cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date issueDate = Date.valueOf(issueDateString);
        Date dueDate = Date.valueOf(dueDateString);


        if (libraryModelManage.checkStudentAndBookEmpty(bookID, studentID)) {
            String issueId = this.libraryModelManage.creatIssueID();
            Issue issue = new Issue(issueId, bookID, studentID, issueDate, dueDate, status);
            this.libraryModelManage.addIssueToDatabase(issue);

            updateBookStatus(bookID);
        }
    }

    private void updateBookStatus(String bookID) {
        Book book = libraryModelManage.searchBookByID(bookID);
        if (book != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Add issue successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            book.setCurent("Borrowed");
            libraryModelManage.editBookInDatabase(book);
        }
    }




    public void removeData() {
        // Xóa thông tin sách
        bookIdField.setText("");
        bookTitleField.setText("");
        authorField.setText("");
        languageField.setText("");
        totalField.setText("");

        // Xóa thông tin sinh viên
        studentIdField.setText("");
        studentNameField.setText("");
        contactPhoneField.setText("");
        contactEmailField.setText("");
        majorField.setText("");
        branchField.setText("");

        // Xóa thông tin mượn sách
        issueDateField.setText("");
        dueDateField.setText("");
    }

    public void setBookIdField(String bookId) {
        if (this.bookIdField != null) {
            this.bookIdField.setText(bookId); // Đặt giá trị chuỗi vào JTextField
        }
    }

    public void setStudentIdField(String studentId) {
        if (this.studentIdField != null) {
            this.studentIdField.setText(studentId); // Đặt giá trị chuỗi vào JTextField
        }
    }



    public void setDueDateField(String dueDate) {
        this.dueDateField.setText(dueDate);
    }


    public void setIssueDateField(String issueDateField) {
        this.issueDateField.setText(issueDateField);
    }

    public JButton getDueDateButton() {
        return dueDateButton;
    }

    public JButton getIssueDateButton() {
        return issueDateButton;
    }

    public JTextField getBookTitleField() {
        return bookTitleField;
    }

    public JTextField getAuthorField() {
        return authorField;
    }

    public JTextField getLanguageField() {
        return languageField;
    }

    public JTextField getTotalField() {
        return totalField;
    }

    public JTextField getStudentNameField() {
        return studentNameField;
    }

    public JTextField getContactPhoneField() {
        return contactPhoneField;
    }

    public JTextField getContactEmailField() {
        return contactEmailField;
    }

    public JTextField getMajorField() {
        return majorField;
    }

    public JTextField getBranchField() {
        return branchField;
    }

    public JTextField getIssueDateField() {
        return issueDateField;
    }

    public JTextField getDueDateField() {
        return dueDateField;
    }

    public JButton getIssueButton() {
        return issueButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }
}
