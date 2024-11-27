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

/**
 * View panel xử lý việc mượn sách của người dùng.
 * Cung cấp giao diện để hiển thị thông tin sách và sinh viên, và thực hiện việc mượn sách.
 */
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

    /**
     * Constructor khởi tạo giao diện mượn sách.
     * @param libraryModelManage Đối tượng quản lý thư viện.
     * @throws IllegalArgumentException nếu đối tượng libraryModelManage là null.
     */
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

        // Lắng nghe sự kiện nhấn Enter để cập nhật thông tin sách và sinh viên
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

    /**
     * Cập nhật thông tin sách khi nhập mã sách.
     */
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

    /**
     * Cập nhật thông tin sinh viên khi nhập mã sinh viên.
     */
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

    /**
     * Cập nhật thông tin sách và sinh viên theo mã sách và mã sinh viên.
     * @param bookID Mã sách cần tìm.
     * @param studentID Mã sinh viên cần tìm.
     */
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

    /**
     * Thiết lập panel chính cho giao diện.
     */
    private void setupMainPanel() {
        setLayout(new GridLayout(1, 3, 10, 10));
        setBackground(new Color(230, 230, 250));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Tạo panel hiển thị thông tin sách.
     * @return JPanel chứa thông tin sách.
     */
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

    /**
     * Tạo panel hiển thị thông tin sinh viên.
     * @return JPanel chứa thông tin sinh viên.
     */
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

    /**
     * Tạo panel cho giao diện mượn sách, bao gồm các trường nhập thông tin và các nút chức năng.
     * @return JPanel chứa các thành phần mượn sách.
     */
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

    /**
     * Khởi tạo panel mượn sách với nền màu và layout mặc định.
     * @return JPanel đã được khởi tạo.
     */
    private JPanel initializeIssuePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 248, 255));
        panel.setLayout(null);
        return panel;
    }

    /**
     * Tạo panel tiêu đề với ảnh và tiêu đề "ISSUE BOOK".
     * @return JPanel chứa tiêu đề.
     */
    private JPanel createTitlePanel() {
        JPanel titlePanel = createImageLabel("/IssueBook/view/icon/issue.png", "ISSUE BOOK", new Color(248, 248, 255), new Color(238, 162, 173));
        titlePanel.setBounds(20, 50, 400, 200);
        return titlePanel;
    }

    /**
     * Thêm các trường thông tin mượn sách như mã sách và mã sinh viên vào panel.
     * @param issuePanel Panel chứa các trường thông tin.
     * @param labelColor Màu sắc của nhãn cho các trường.
     */
    private void addIssueDetailsFields(JPanel issuePanel, Color labelColor) {
        issuePanel.add(createLabelAtPosition("Book Id: ", 25, 250, 200, 30, labelColor));
        bookIdField = createTextField(150, 250, 100, 30, true);
        issuePanel.add(bookIdField);

        issuePanel.add(createLabelAtPosition("Student Id: ", 25, 290, 200, 30, labelColor));
        studentIdField = createTextField(150, 290, 100, 30, true);
        issuePanel.add(studentIdField);
    }

    /**
     * Thêm các bộ chọn ngày mượn và ngày trả vào panel.
     * @param issuePanel Panel chứa các bộ chọn ngày.
     */
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

    /**
     * Tạo nút chọn ngày với vị trí và màu sắc mặc định.
     * @param x Vị trí x của nút.
     * @param y Vị trí y của nút.
     * @return JButton nút chọn ngày.
     */
    private JButton createDatePickerButton(int x, int y) {
        JButton button = new JButton();
        button.setBounds(x, y, 20, 20);
        button.setBackground(new Color(255, 192, 203));
        return button;
    }

    /**
     * Thêm các nút chức năng "ISSUE" và "CLEAR" vào panel.
     * @param issuePanel Panel chứa các nút.
     */
    private void addButtons(JPanel issuePanel) {
        issueButton = createButton("ISSUE", 50, 470);
        clearButton = createButton("CLEAR", 240, 470);

        issuePanel.add(issueButton);
        issuePanel.add(clearButton);
    }

    /**
     * Tạo nhãn với văn bản chỉ định.
     * @param text Văn bản hiển thị trên nhãn.
     * @return JLabel nhãn đã tạo.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));  // Sử dụng font SansSerif với kiểu chữ bình thường và cỡ 16
        return label;
    }

    /**
     * Tạo nhãn với văn bản và vị trí chỉ định.
     * @param text Văn bản hiển thị trên nhãn.
     * @param x Vị trí x của nhãn.
     * @param y Vị trí y của nhãn.
     * @param width Chiều rộng của nhãn.
     * @param height Chiều cao của nhãn.
     * @param labelColor Màu sắc của nhãn.
     * @return JLabel nhãn đã tạo.
     */
    private JLabel createLabelAtPosition(String text, int x, int y, int width, int height,Color labelColor) {
        JLabel label = createLabel(text);
        label.setForeground(labelColor);
        label.setBounds(x, y, width, height);  // Đặt vị trí và kích thước cho label
        return label;
    }

    /**
     * Tạo một trường văn bản với vị trí và kích thước chỉ định.
     * @param x Vị trí x của trường văn bản.
     * @param y Vị trí y của trường văn bản.
     * @param width Chiều rộng của trường văn bản.
     * @param height Chiều cao của trường văn bản.
     * @param hasBorder Chỉ định có viền cho trường văn bản hay không.
     * @return JTextField trường văn bản đã tạo.
     */
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

    /**
     * Tạo một label chứa ảnh và tiêu đề với các màu nền và màu tiêu đề chỉ định.
     * @param path Đường dẫn tới hình ảnh.
     * @param title Tiêu đề của panel.
     * @param background Màu nền của panel.
     * @param titleColor Màu của tiêu đề.
     * @return JPanel chứa tiêu đề và hình ảnh.
     */
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

    /**
     * Phương thức này xử lý việc mượn sách: kiểm tra thông tin hợp lệ,
     * tạo mã mượn sách, và thêm thông tin vào cơ sở dữ liệu.
     */
    public void issueBook() {
        String bookID = this.bookIdField.getText() + "";
        String studentID = this.studentIdField.getText() + "";
        String issueDateString = this.issueDateField.getText();
        String dueDateString = this.dueDateField.getText();
        String status = "issued";

        // Kiểm tra nếu ngày mượn hoặc ngày trả để trống
        if (issueDateString.isEmpty() || dueDateString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Issue Date and Due Date cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi chuỗi ngày mượn và ngày trả thành kiểu Date
        Date issueDate = Date.valueOf(issueDateString);
        Date dueDate = Date.valueOf(dueDateString);

        // Kiểm tra xem sách và sinh viên có hợp lệ không
        if (libraryModelManage.checkStudentAndBookEmpty(bookID, studentID)) {
            String issueId = this.libraryModelManage.creatIssueID();
            Issue issue = new Issue(issueId, bookID, studentID, issueDate, dueDate, status);
            this.libraryModelManage.addIssueToDatabase(issue);

            updateBookStatus(bookID);
        }
    }


    /**
     * Cập nhật trạng thái của sách sau khi mượn.
     * @param bookID Mã sách cần cập nhật trạng thái.
     */
    private void updateBookStatus(String bookID) {
        // Hiển thị thông báo mượn sách thành công
        Book book = libraryModelManage.searchBookByID(bookID);
        if (book != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Add issue successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            // Đặt trạng thái sách thành "borrowed"
            book.setCurent("Borrowed");
            libraryModelManage.editBookInDatabase(book);
        }
    }

    /**
     * Xóa tất cả dữ liệu trong các trường nhập liệu.
     * Phương thức này được sử dụng để làm sạch các trường nhập liệu sau mỗi lần mượn sách.
     */
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

    /**
     * Đặt giá trị cho trường mã sách.
     * @param bookId Mã sách cần đặt vào trường.
     */
    public void setBookIdField(String bookId) {
        if (this.bookIdField != null) {
            this.bookIdField.setText(bookId); // Đặt giá trị chuỗi vào JTextField
        }
    }

    /**
     * Đặt giá trị cho trường mã sinh viên.
     * @param studentId Mã sinh viên cần đặt vào trường.
     */
    public void setStudentIdField(String studentId) {
        if (this.studentIdField != null) {
            this.studentIdField.setText(studentId); // Đặt giá trị chuỗi vào JTextField
        }
    }


    /**
     * Đặt giá trị cho trường ngày trả.
     * @param dueDate Ngày trả sách cần đặt vào trường.
     */
    // PHương thức phía sau là getter và setter
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