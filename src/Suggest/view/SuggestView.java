package Suggest.view;

import API.ChiTiet;
import HomePage.view.CustomScrollBarUI;
import MainApp.model.Book;
import MainApp.model.Issue;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestView extends JPanel {
    private Student student;
    private String studentId;

    private LibraryModelManage libraryModelManage;
    private Color colorPanel=new Color(255, 240, 245);
    private Color colorBack=new Color(238, 210, 238);

    public SuggestView(Student student, LibraryModelManage libraryModelManage) {
        this.student = student;
        studentId=student.getID();
        this.libraryModelManage = libraryModelManage;
        this.setBackground(colorBack);
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(colorBack);
        mainPanel.setPreferredSize(new Dimension(1000,650));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // BoxLayout cho các category
        JScrollPane scrollPane = createScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Chỉ cuộn dọc
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Không cuộn ngang

        addCategoryPanels(mainPanel);

        setLayout(new BorderLayout()); // Cần set layout cho SuggestView
        add(scrollPane, BorderLayout.CENTER); // Thêm JScrollPane vào JPanel

    }



    private void addCategoryPanels(JPanel mainPanel) {
        List<Book> bookRecommend = getRecommendBooks();



        List<Book> bookTrend = getTrendBooks();
        List<Book> bookNew = getNewsBooks();

        mainPanel.add(createCategoryPanel("TRENDING", bookTrend));
        mainPanel.add(createCategoryPanel("NEW ARRIVALS", bookNew));
        mainPanel.add(createCategoryPanel("RECOMMENDED", bookRecommend));
    }


    private JPanel createCategoryPanel(String title, List<Book> books) {
        JPanel categoryPanel = createCategoryPanelLayout();
        categoryPanel.setBackground(colorBack);
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        categoryPanel.add(createCategoryTitle(title), BorderLayout.NORTH);
        categoryPanel.add(createBooksScrollPane(books), BorderLayout.CENTER);
        return categoryPanel;
    }

    private JPanel createCategoryPanelLayout() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setBackground(colorBack);

        categoryPanel.setLayout(new BorderLayout());
        categoryPanel.setPreferredSize(new Dimension(400, 400));
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return categoryPanel;
    }

    private JLabel createCategoryTitle(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(255,125,112,255));
        return titleLabel;
    }

    private JScrollPane createBooksScrollPane(List<Book> books) {
        JPanel booksPanel = createBooksPanel(books);
        JScrollPane booksScrollPane =createScrollPane(booksPanel);
        booksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        booksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        booksScrollPane.setPreferredSize(new Dimension(400, 300));
        return booksScrollPane;
    }

    private JPanel createBooksPanel(List<Book> books) {
        JPanel booksPanel = new JPanel();
        int count=0;
        booksPanel.setBackground(colorPanel);

        booksPanel.setLayout(new BoxLayout(booksPanel, BoxLayout.X_AXIS));


        for (Book book : books) {
            booksPanel.add(createBookPanel(book));
            count++;
        }
        booksPanel.setPreferredSize(new Dimension(200*count, 100));


        return booksPanel;
    }

    private JPanel createBookPanel(Book book) {
        JPanel bookPanel = new JPanel();
        bookPanel.setBackground(new Color(255, 240, 245));
        bookPanel.setLayout(null); // Sắp xếp theo chiều dọc
        bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 2, 5)); // Giảm padding nhẹ

        // Tạo JLayeredPane chứa ảnh và hotLabel
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        layeredPane.setBounds(5, 10, 120, 160); // Đặt vị trí và kích thước cho ảnh sách

        // Tạo ảnh sách
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(book.getImagePath()));
            Image scaledImage = imageIcon.getImage().getScaledInstance(100, 136, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Image not found: " + book.getImagePath());
            imageLabel.setText("No Image");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        imageLabel.setBounds(5, 10, 100, 136); // Đặt vị trí và kích thước cho ảnh sách
        layeredPane.add(imageLabel, Integer.valueOf(0)); // Lớp 0: Ảnh sách

        // Lớp trên: Ảnh GIF chữ "HOT"
        JLabel hotLabel = new JLabel();
        try {
            ImageIcon hotIcon = new ImageIcon(getClass().getResource("/Suggest/view/icon/hot.gif")); // Đường dẫn tới ảnh GIF
            hotLabel.setIcon(hotIcon);
        } catch (Exception e) {
            System.err.println("HOT GIF not found!");
        }
        hotLabel.setBounds(0, 0, 60, 60); // Đặt ảnh GIF ở góc trên cùng bên trái
        layeredPane.add(hotLabel, Integer.valueOf(1)); // Lớp 1: Ảnh GIF chữ "HOT"

        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa tiêu đề
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setBounds(5,170,120,10);
        bookPanel.add(layeredPane);
        bookPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MainApp.model.Book book1 = libraryModelManage.searchBookByID(book.getBookID());
                showBookDetails(book1.getURL());
            }
        });


        return bookPanel;
    }
    private void showBookDetails(String URL) {

        if (URL == null || URL.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The URL for this book is missing.");
            return;
        }

        // Tiến hành tạo mã QR nếu URL hợp lệ
        JFrame qrCodeFrame = new JFrame("Book ID");
        qrCodeFrame.setSize(360, 400);
        qrCodeFrame.setUndecorated(true); // Loại bỏ viền cửa sổ
        qrCodeFrame.setBackground(new Color(0, 0, 0, 0)); // Làm trong suốt toàn bộ JFrame
        qrCodeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        qrCodeFrame.setLocationRelativeTo(null); // Đặt giữa màn hình

        JPanel qrCodePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Không vẽ nền gì thêm để đảm bảo trong suốt
            }
        };
        qrCodePanel.setOpaque(false);
        qrCodePanel.setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setSize(360, 400);

        JPanel backgroundPanel = createBackgroundPanel("/ManageBook/icon/qrbg.png"); // Hàm tạo JPanel với ảnh nền
        backgroundPanel.setOpaque(false);
        backgroundPanel.setBounds(0, 0, 360, 360);

        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        try {
            // Tạo ảnh QR code từ URL hợp lệ
            BufferedImage qrCodeImage = generateQRCodeImage(URL, 220, 220); // Tạo ảnh QR code
            JLabel lblQRCode = new JLabel(new ImageIcon(qrCodeImage));
            lblQRCode.setBounds(72, 98, 220, 220); // Đặt vị trí và kích thước
            qrCodePanel.add(lblQRCode);
        } catch (Exception k) {
            JLabel lblQRCode = new JLabel("QR Code Error");
            lblQRCode.setForeground(Color.RED);
            qrCodePanel.add(lblQRCode);
            k.printStackTrace();

        }
        qrCodePanel.add(layeredPane);
        qrCodePanel.setBounds(0, 0, 360, 400);
        qrCodeFrame.getContentPane().add(qrCodePanel);
        qrCodeFrame.setVisible(true); // Hiển thị JFrame
    }

    private JPanel createBackgroundPanel(String path) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(path));
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

    }


    private BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        int[] enclosingRectangle = findEnclosingRectangle(bitMatrix);

        int x = enclosingRectangle[0];
        int y = enclosingRectangle[1];
        int qrWidth = enclosingRectangle[2];
        int qrHeight = enclosingRectangle[3];

        BitMatrix croppedMatrix = cropBitMatrix(bitMatrix, x, y, qrWidth, qrHeight);

        BufferedImage image = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_RGB);
        int greenColor = new Color(0,0,0).getRGB();
        int whiteColor = Color.WHITE.getRGB();

        for (int i = 0; i < qrWidth; i++) {
            for (int j = 0; j < qrHeight; j++) {
                // Sử dụng mã màu
                image.setRGB(i, j, croppedMatrix.get(i, j) ? greenColor : whiteColor);
            }
        }

        return image;
    }


    private BitMatrix cropBitMatrix(BitMatrix bitMatrix, int x, int y, int width, int height) {
        BitMatrix croppedMatrix = new BitMatrix(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(x + i, y + j)) {
                    croppedMatrix.set(i, j);
                }
            }
        }
        return croppedMatrix;
    }

    // Hàm tìm vùng bao quanh nội dung QR Code
    private int[] findEnclosingRectangle(BitMatrix bitMatrix) {
        int top = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        int bottom = Integer.MIN_VALUE;
        int right = Integer.MIN_VALUE;

        for (int y = 0; y < bitMatrix.getHeight(); y++) {
            for (int x = 0; x < bitMatrix.getWidth(); x++) {
                if (bitMatrix.get(x, y)) {
                    if (x < left) left = x;
                    if (y < top) top = y;
                    if (x > right) right = x;
                    if (y > bottom) bottom = y;
                }
            }
        }

        return new int[]{left, top, right - left + 1, bottom - top + 1};
    }


    private List<Book> getNewsBooks() {
        List<Book> books = new ArrayList<>();

        ArrayList<MainApp.model.Book> booksList = libraryModelManage.getBooksList();

        if (booksList == null || booksList.isEmpty()) {
            System.out.println("Danh sách sách rỗng.");
            return books;
        }

        int numberOfBooksToDisplay = Math.min(6, booksList.size());

        for (int i = booksList.size() - 1; i >= booksList.size() - numberOfBooksToDisplay; i--) {
            MainApp.model.Book book = booksList.get(i);

            if (book != null) {
                books.add(new Book(book.getImage(), book.getBookName(),book.getURL(),book.getBookID()));
            }
        }

        return books;
    }

    private List<Book> getTrendBooks() {
        List<Book> books = new ArrayList<>();

        ArrayList<MainApp.model.Book> booksList = libraryModelManage.getBooksList();
        ArrayList<Issue> issuesList = libraryModelManage.getIssuesList();

        if (booksList == null || booksList.isEmpty() || issuesList == null || issuesList.isEmpty()) {
            return books;
        }

        Map<String, MainApp.model.Book> bookMap = new HashMap<>();
        for (MainApp.model.Book book : booksList) {
            bookMap.put(book.getBookID(), book);
        }

        // Đếm số lần mượn cho từng bookID
        Map<String, Integer> borrowCountMap = new HashMap<>();
        for (Issue issue : issuesList) {
            String bookID = issue.getIssueBookID();
            if (bookMap.containsKey(bookID)) { // Chỉ đếm nếu bookID có trong booksList
                borrowCountMap.put(bookID, borrowCountMap.getOrDefault(bookID, 0) + 1);
            }
        }

        // Sắp xếp sách theo số lần mượn giảm dần
        List<Map.Entry<String, Integer>> sortedBorrowCount = borrowCountMap.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(10) // Chỉ lấy top 10
                .toList();

        // Tạo danh sách sách từ danh sách đã sắp xếp
        for (Map.Entry<String, Integer> entry : sortedBorrowCount) {
            String bookID = entry.getKey();
            MainApp.model.Book book = bookMap.get(bookID);
            books.add(new Book(book.getImage(), book.getBookName(),book.getURL(),book.getBookID()));
        }

        return books;
    }











    private List<Book> getRecommendBooks() {
        List<Book> books = new ArrayList<>();

        ArrayList<MainApp.model.Book> booksList = libraryModelManage.getBorrowedBooksByStudent(studentId);

        for (int i =booksList.size()-1;i>=0; i--) {
            MainApp.model.Book book = booksList.get(i);
            books.add(new Book(book.getImage(), book.getBookName(),book.getURL(),book.getBookID()));
        }
     return books;
    }

    class Book {
        private String imagePath;
        private String title;
        private String URL;
        private String bookID;

        public Book(String imagePath, String title,String URL,String bookID) {
            this.bookID=bookID;
            this.URL=URL;
            this.imagePath = imagePath;
            this.title = title;
        }

        public String getBookID() {
            return bookID;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getTitle() {
            return title;
        }

        public String getURL() {
            return URL;
        }
    }
    public JScrollPane createScrollPane(JPanel table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(colorBack); // Màu nền cho vùng hiển thị của JScrollPane


        CustomScrollBarUI verticalScrollBarUI = new CustomScrollBarUI();
        verticalScrollBarUI.setColors(new Color(205, 201, 201), new Color(232, 232, 232));
        scrollPane.getVerticalScrollBar().setUI(verticalScrollBarUI); // Ghi đè UI cho thanh cuộn dọc

        CustomScrollBarUI horizontalScrollBarUI = new CustomScrollBarUI();
        horizontalScrollBarUI.setColors(new Color(205, 201, 201), new Color(232, 232, 232));
        scrollPane.getHorizontalScrollBar().setUI(horizontalScrollBarUI);

        scrollPane.setPreferredSize(new Dimension(1200, getHeight()));
        scrollPane.setBorder(null);

        return scrollPane;
    }
}
