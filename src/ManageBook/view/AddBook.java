package ManageBook.view;

import LoginPage.view.OvalButton;
import MainApp.model.Book;
import MainApp.model.LibraryModelManage;
import ManageBook.controller.AddBookController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class AddBook extends JFrame {
    private JButton uploadCoverButton;
    private JButton submitButton;
    private JButton cancelButton;
    private AddBookController addBookController;
    private JLabel coverLabel;
    private String imagePath;
    private JTextArea descriptionArea;
    public JTextField titleField;
    private LibraryModelManage libraryModelManage;
    private JTextField authorText;
    private JTextField languageText;
    private JTextField totalText;
    private JTextField positionText;

    public AddBook(LibraryModelManage libraryModelManage) {
        this.init();
        //this.addBookController= new AddBookController(this);
        this.libraryModelManage = libraryModelManage;
    }

    private void init() {
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Đóng dialog khi bấm nút đóng
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        mainPanel.add(createPanel(), BorderLayout.CENTER);
        mainPanel.add(createCover(), BorderLayout.EAST);



        this.add(mainPanel);
        this.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel leftJPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        leftJPanel.setBackground(new Color(247,223,237));
        leftJPanel.setPreferredSize(new Dimension(500,500));
        leftJPanel.add(createBook());
        leftJPanel.add(createDescription());
        leftJPanel.add(createAuthor());
        leftJPanel.add(createQuantity());
        leftJPanel.add(createButton());
        leftJPanel.add(createIcon());
        return leftJPanel;
    }
    private JPanel createBook() {
        titleField = setCustomTextField();
        return createLabeledTextFieldPanel(new JLabel("Book Title:"), titleField);
    }


    private JPanel createDescription() {
        JLabel descriptionLabel=new JLabel("Short Description");
        descriptionArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return createLabeledTextFieldPanel(descriptionLabel,scrollPane) ;
    }

    private JPanel createAuthor() {
        JPanel writerPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        writerPanel.setBackground(new Color(247,223,237));
        authorText = setCustomTextField();
        JPanel authorPanel=createLabeledTextFieldPanel(new JLabel("Author: "),authorText);
        languageText =  setCustomTextField();
        JPanel languagePanel=createLabeledTextFieldPanel(new JLabel("Language: "),languageText);
        writerPanel.add(authorPanel);
        writerPanel.add(languagePanel);
        return writerPanel;
    }

    private JPanel createQuantity() {
        JPanel quantityPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // Chia làm 3 cặp nhãn-ô nhập liệu
        quantityPanel.setBackground(new Color(247,223,237));

        totalText = setCustomTextField();
        totalText.setText("1");
        totalText.setEditable(false);
        JPanel totalPanel = createLabeledTextFieldPanel(new JLabel("Total: "), totalText);


        positionText = setCustomTextField();
        JPanel positionPanel = createLabeledTextFieldPanel(new JLabel("Position: "), positionText);

        quantityPanel.add(totalPanel);
        //quantityPanel.add(currentPanel);
        quantityPanel.add(positionPanel);

        return quantityPanel;
    }

    private JPanel createButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,100,35));
        buttonPanel.setBackground(new Color(247,223,237));

        submitButton = createButton("SUBMIT");
        cancelButton = createButton("CANCLE");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }
    private JPanel createCover()
    {
        JPanel coverPanel = new JPanel(new BorderLayout());
        coverPanel.setPreferredSize(new Dimension(300, 500));

// Tạo TitledBorder với font tùy chỉnh
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Cover Upload");
        titledBorder.setTitleFont(new Font("Tahoma", Font.BOLD, 16)); // Thiết lập font cho tiêu đề
        titledBorder.setTitleColor(new Color(79,79,79)); // Thiết lập màu cho tiêu đề nếu cần
        coverPanel.setBorder(titledBorder);

        coverPanel.setBackground(new Color(214, 247, 252));

        coverLabel = new JLabel();
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coverLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        uploadCoverButton = createButton("UPLOAD COVER");
        coverPanel.add(coverLabel, BorderLayout.CENTER);
        coverPanel.add(uploadCoverButton, BorderLayout.SOUTH);

        return coverPanel;


    }
    private JTextField setCustomTextField() {
        JTextField textField=new JTextField();
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Đường kẻ dưới cùng
        textField.setOpaque(false);
        return textField;
    }
    private JPanel createLabeledTextFieldPanel(JLabel label, JComponent component) {
        label.setForeground(new Color(79,79,79));
        label.setFont(new Font("Tahma",Font.BOLD,15));
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(247,223,237));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST; // Căn lề trái
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);

        return panel;
    }
    private JButton createButton(String text) {
        JButton button = new OvalButton(text);
        button.setBackground(new Color(250, 128 ,114));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    private JPanel createIcon() {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(247,223,237));
        jPanel.setLayout(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/ManageBook/icon/icon.jpg")));
        JLabel iconLabel = new JLabel(imageIcon);
        jPanel.add(iconLabel, BorderLayout.CENTER);
        return jPanel;
    }
    public void chooseImage(ActionEvent e) {
        FileDialog fileDialog = new FileDialog(this, "Chọn ảnh", FileDialog.LOAD);
        fileDialog.setFile("*.jpg;*.jpeg;*.png;*.gif");
        fileDialog.setVisible(true);

        String fileName = fileDialog.getFile();
        String directory = fileDialog.getDirectory();

        if (fileName != null && !fileName.isEmpty()) {
            String sourcePath = directory + fileName;
            String destinationPath = "/ManageBook/icon/" + fileName; // Thư mục đích

            // Sao chép file vào thư mục đích
            try {
                Files.createDirectories(Paths.get("/ManageBook/icon/")); // Tạo thư mục nếu chưa tồn tại
                Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);

                // Hiển thị ảnh đã chọn
                imagePath = destinationPath; // Cập nhật đường dẫn ảnh
                displayImage(destinationPath);

                // Thêm ảnh vào cache
                libraryModelManage.addImageToCache(fileName);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Không thể tải ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void displayImage(String filePath) {
        ImageIcon imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage().getScaledInstance(coverLabel.getWidth(), coverLabel.getHeight(), Image.SCALE_SMOOTH);
        coverLabel.setIcon(new ImageIcon(image));
    }


    public Book getBookFromPanel(){
        String id = this.libraryModelManage.creatBookID();
        String title = this.titleField.getText()+"\n"+"( "+ this.descriptionArea.getText()+" )";
        String imagePath = getImagePath();
        String author = this.authorText.getText()+"";
        String language = this.languageText.getText()+"";
        int total = Integer.valueOf(totalText.getText()+"");
        String current = "Still";
        String position = this.positionText.getText()+"";
        Book book = new Book(id, title,imagePath, author,"Programing", language, total, current, position, "");
        libraryModelManage.addBookToDatabase(book);
        return book;
    }



    public JButton getUploadCoverButton() {
        return uploadCoverButton;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public String getImagePath() {
        return imagePath;
    }
//    public static void main( String args[])
//    {
//        new AddBook();
//    }
}