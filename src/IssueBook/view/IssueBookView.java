package IssueBook.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class IssueBookView extends JPanel {
    public IssueBookView() {
        setupMainPanel();
        add(createBookPanel());
        add(createStudentPanel());
        add(createIssuePanel());
        setVisible(true);
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
        bookPanel.setLayout(null); // Layout null

        JPanel titlePanel = createImageLabel("/IssueBook/view/icon/issueBook.png", "BOOK DETAILS", new Color(246, 222, 236),new Color(238,58,140));
        titlePanel.setBounds(0, 50, 400, 200); // Đặt kích thước và vị trí cho titlePanel
        bookPanel.add(titlePanel);

        Color labelColor=new Color(205,50,120);
        bookPanel.add(createLabelAtPosition("Book Id: ", 25, 210, 200, 30,labelColor));
        bookPanel.add(createLabelAtPosition("Book Title: ", 25, 250, 200, 30,labelColor));
        bookPanel.add(createLabelAtPosition("Author: ", 25, 290, 200, 30,labelColor));
        bookPanel.add(createLabelAtPosition("Category: ", 25, 330, 200, 30,labelColor));
        bookPanel.add(createLabelAtPosition("Language: ", 25, 370, 200, 30,labelColor));
        bookPanel.add(createLabelAtPosition("Total: ", 25, 410, 200, 30,labelColor));

        return bookPanel;
    }


    private JPanel createStudentPanel() {
        JPanel studentPanel = new JPanel();
        studentPanel.setBackground(new Color(202, 225, 255));
        studentPanel.setLayout(null);

        JPanel titlePanel = createImageLabel("/IssueBook/view/icon/issueStudent.png", "STUDENT DETAILS", new Color(202, 225, 255),new Color(0,191,255));
        titlePanel.setBounds(0, 50, 400, 200); // Đặt kích thước và vị trí cho titlePanel
        studentPanel.add(titlePanel);


        Color labelColor=new Color(0,178,238);
        studentPanel.add(createLabelAtPosition("Student ID: ", 25, 210, 200, 30,labelColor));
        studentPanel.add(createLabelAtPosition("Student Name: ", 25, 250, 200, 30,labelColor));
        studentPanel.add(createLabelAtPosition("Contact Phone: ", 25, 290, 200, 30,labelColor));
        studentPanel.add(createLabelAtPosition("Contact Email: ", 25, 330, 200, 30,labelColor));
        studentPanel.add(createLabelAtPosition("Major: ", 25, 370, 200, 30,labelColor));
        studentPanel.add(createLabelAtPosition("Branch: ", 25, 410, 200, 30,labelColor));


        return studentPanel;
    }

    private JPanel createIssuePanel() {
        JPanel issuePanel = new JPanel();
        issuePanel.setBackground(new Color(248, 248, 255));
        issuePanel.setLayout(null);

        JPanel titlePanel = createImageLabel("/IssueBook/view/icon/issue.png", "ISSUE BOOK", new Color(248, 248, 255),new Color(238,162,173));
        titlePanel.setBounds(0, 50, 400, 200); // Đặt kích thước và vị trí cho titlePanel
        issuePanel.add(titlePanel);


        Color labelColor=new Color(205,145,158);
        issuePanel.add(createLabelAtPosition("Book Id: ", 25, 250, 200, 30,labelColor));
        issuePanel.add(createLabelAtPosition("Student Id: ", 25, 290, 200, 30,labelColor));
        issuePanel.add(createLabelAtPosition("Issue Date:", 25, 330, 200, 30,labelColor));
        issuePanel.add(createLabelAtPosition("Due Date: ", 25, 370, 200, 30,labelColor));


        return issuePanel;
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

        // Panel chứa tiêu đề và đường kẻ
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setBackground(background);

        // Thêm tiêu đề vào titleContainer
        titleContainer.add(titleLabel);

        // Tạo đường kẻ trắng dưới tiêu đề
        JPanel linePanel = new JPanel();
        linePanel.setBackground(titleColor);
        linePanel.setPreferredSize(new Dimension(250, 4)); // Đặt kích thước đường kẻ

        // Container căn giữa đường kẻ và điều chỉnh khoảng cách với tiêu đề
        JPanel lineContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        lineContainer.setBackground(background);
        lineContainer.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // Khoảng cách 5px ở trên để gần tiêu đề hơn
        lineContainer.add(linePanel);

        // Thêm lineContainer vào titleContainer
        titleContainer.add(lineContainer);

        // Thêm titleContainer vào imagePanel
        imagePanel.add(titleContainer, BorderLayout.CENTER);

        return imagePanel;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Issue Book View");
        IssueBookView issueBookView = new IssueBookView();
        frame.add(issueBookView);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Thêm ComponentListener để in kích thước của titlePanel sau khi frame hiển thị
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                Component titlePanel = issueBookView.getComponent(0);  // Lấy titlePanel
                System.out.println("Kích thước thật sự của titlePanel: " + titlePanel.getSize());
            }
        });
    }
}
