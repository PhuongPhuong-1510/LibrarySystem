package HomePage.view;

import HomePage.controller.HomePageController;
import HomePage.model.HomePageModel;
import MainApp.model.*;
import MainApp.view.MainView;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import java.util.stream.Collectors;

public class HomePageView extends JPanel {
    // Các trường dữ liệu cho model, view và controller
    private HomePageModel homePageModel;
    private MainView mainView;
    private JButton hamburgerButton;
    private HomePageController homePageController;
    private boolean isMenuVisible = false;
    private JMenuBar menuBar;
    private JPanel mainHomePanel;
    private JPanel homePagePanel;
    private LibraryModelManage libraryModelManage;

    // Các mục menu
    private JMenu jMenuHomePage, jMenuLogout, jMenuLMSDashBoard, jMenuMGMTBooks, jMenuMGMTSutudents,
            jMenuIssueBook, jMenuReturnBook, jMenuViewRecords;
    private JMenu jMenuItemAPI;

    /**
     * Constructor cho HomePageView.
     * Khởi tạo các thành phần và thiết lập giao diện người dùng.
     *
     * @param mainView Giao diện chính của ứng dụng.
     */
    public HomePageView(MainView mainView) {
        this.mainView = mainView;
        this.homePageModel = new HomePageModel();
        this.libraryModelManage = mainView.getLibraryModelManage();
        init(); // Khởi tạo các thành phần giao diện
        this.homePageController = new HomePageController(this);

    }

    /**
     * Khởi tạo giao diện trang chủ bằng cách thiết lập bố cục và các thành phần.
     */
    private void init() {
        setSize(1200, 632);
        setLayout(new BorderLayout());

        homePagePanel = new JPanel(new BorderLayout());
        homePagePanel.add(createMenuBar(), BorderLayout.WEST);
        homePagePanel.add(createHomeMenu(), BorderLayout.NORTH);

        // Tạo main home panel nếu chưa được tạo
        if (mainHomePanel == null) {
            mainHomePanel = createMainHome();
        }
        homePagePanel.add(mainHomePanel,BorderLayout.CENTER);

        add(homePagePanel);
        setVisible(true);
    }

    /**
     * Tạo một thanh menu bar cho giao diện người dùng.
     *
     * @return JMenuBar thanh menu đã được cấu hình.
     */
    private JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setLayout(null);
        menuBar.setBackground(new Color(80, 80, 80));
        menuBar.setPreferredSize(new Dimension(215, 632));
        menuBar.setBorder(null);
        menuBar.setVisible(false);

        // Tạo các mục menu với các biểu tượng và tên tương ứng
        jMenuHomePage = createMenu("Home Page", "/HomePage/view/icon/icons8_Home_26px_2.png", 0);
        jMenuHomePage.setBackground(new Color(185,173,173));
        jMenuHomePage.setOpaque(true);
        jMenuLMSDashBoard = createMenu("LMS Notification", "/HomePage/view/icon/notification.png", 50);
        jMenuItemAPI = createMenu("Search Book", "/HomePage/view/icon/searchAPI.png", 130);
        jMenuMGMTBooks = createMenu("Manage Books", "/HomePage/view/icon/icons8_Books_26px.png", 180);
        jMenuMGMTSutudents = createMenu("Manage Students", "/HomePage/view/icon/icons8_Read_Online_26px.png", 230);
        jMenuIssueBook = createMenu("Issue Book", "/HomePage/view/icon/icons8_Sell_26px.png", 280);
        jMenuReturnBook = createMenu("Return Book", "/HomePage/view/icon/icons8_Return_Purchase_26px.png", 330);
        jMenuViewRecords = createMenu("View Records", "/HomePage/view/icon/icons8_View_Details_26px.png", 380);
        jMenuLogout = createMenu("Logout", "/HomePage/view/icon/icons8_Exit_26px_2.png", 430);

        // Nhãn cho danh sách các tính năng trong menu
        JLabel lblMenu = new JLabel("Features");
        lblMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMenu.setBounds(10, 110, 300, 20);
        menuBar.add(lblMenu);

        return menuBar;
    }

    /**
     * Tạo một mục menu với tên, biểu tượng và vị trí xác định.
     *
     * @param title Tên của menu.
     * @param iconPath Đường dẫn tới biểu tượng của menu.
     * @param yPosition Vị trí theo chiều dọc của menu.
     * @return JMenu đã được tạo.
     */
    private JMenu createMenu(String title, String iconPath, int yPosition) {
        JMenu menu = new JMenu(title);
        menu.setFont(new Font("Tahoma", Font.PLAIN, 17));
        menu.setForeground(new Color(238,229, 222));
        menu.setBounds(0, yPosition, 300, 50);
        menu.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)))
                .getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        menu.setIconTextGap(10);
        menu.setHorizontalTextPosition(SwingConstants.RIGHT);
        menu.setVerticalTextPosition(SwingConstants.CENTER);
        menu.setHorizontalAlignment(SwingConstants.LEFT);
        menu.setVerticalAlignment(SwingConstants.CENTER);
        menu.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        menuBar.add(menu);
        return menu;
    }

    /**
     * Tạo menu chính của trang chủ bao gồm các nút và thông tin người dùng.
     *
     * @return JPanel chứa các thành phần của menu trang chủ.
     */
    private JPanel createHomeMenu() {
        JPanel homeMenu = new JPanel(null);
        homeMenu.setBackground(new Color(150, 180, 255));
        homeMenu.setPreferredSize(new Dimension(1200, 50));

        // Nút hamburger (menu ẩn)
        hamburgerButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/HomePage/view/icon/icons8_menu_48px_1.png"))));
        hamburgerButton.setBounds(10, 8, 40, 40);
        hamburgerButton.setBorderPainted(false);
        hamburgerButton.setBackground(new Color(150, 180, 255));
        homeMenu.add(hamburgerButton);

        // Biểu tượng người dùng
        JLabel lblIcon = new JLabel("|");
        lblIcon.setFont(new Font("Tahoma", Font.PLAIN, 45));
        lblIcon.setForeground(Color.BLACK);
        lblIcon.setBounds(55, 0, 50, 45);
        homeMenu.add(lblIcon);

        // Hình ảnh và tên người dùng (Admin)
        JLabel imgAdmin = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/HomePage/view/icon/male_user_50px.png"))));
        imgAdmin.setBounds(935, 0, 50, 50);
        homeMenu.add(imgAdmin);

        JLabel lblAdmin = new JLabel("Welcome, Admin");
        lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAdmin.setBounds(985, 20, 120, 20);
        lblAdmin.setForeground(Color.WHITE);
        homeMenu.add(lblAdmin);

        createDateTimeLabels(homeMenu); // Tạo các nhãn hiển thị ngày giờ

        return homeMenu;
    }

    /**
     * Tạo các nhãn hiển thị thời gian và ngày tháng.
     *
     * @param panel JPanel mà nhãn sẽ được thêm vào.
     */
    private void createDateTimeLabels(JPanel panel) {
        JLabel timeLabel = new JLabel();
        JLabel dateLabel = new JLabel();

        timeLabel.setBounds(70, 5, 80, 20);
        dateLabel.setBounds(70, 30, 80, 20);

        // Timer để cập nhật thời gian và ngày tháng
        Timer timer = new Timer(0, e -> {
            Date now = new Date();
            timeLabel.setText(new SimpleDateFormat("hh:mm:ss a").format(now));
            dateLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(now));
            timeLabel.repaint();
            dateLabel.repaint();
        });
        timer.start();

        panel.add(timeLabel);
        panel.add(dateLabel);
    }

    public JPanel createMainHome()
    {
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(230,230,250));
        mainPanel.setPreferredSize(new Dimension(getWidth(),582));

        // Thêm các bảng vào mainPanel
        mainPanel.add(createInfoPanel(),BorderLayout.NORTH);
        mainPanel.add(createTablesPanel(),BorderLayout.WEST);
        mainPanel.add(createPieChart(),BorderLayout.CENTER);

        return mainPanel;
    }

    /**
     * Tạo một bảng thông tin hiển thị các thống kê của thư viện như số lượng sách, sinh viên, sách đã mượn, và quản trị viên.
     *
     * @return JPanel chứa bảng thông tin với các thống kê khác nhau
     */
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(1, 4, 100, 20));
        infoPanel.setBackground(new Color(230,230,250));
        infoPanel.setPreferredSize(new Dimension(getWidth(), 150));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] infoTitles = {"No Of Books", "No Of Students", "Issued Books", "No Of Admins"};
        String[] icons = {
                "/HomePage/view/icon/icons8_Book_Shelf_50px.png",
                "/HomePage/view/icon/icons8_People_50px.png",
                "/HomePage/view/icon/icons8_Sell_50px.png",
                "/HomePage/view/icon/icons8_List_of_Thumbnails_50px.png"
        };

        // Lặp qua các tiêu đề và biểu tượng để thêm vào infoPanel
        for (int i = 0; i < infoTitles.length; i++) {
            String count = "";
            switch (i) {
                case 0:
                    ArrayList<Book> books = this.libraryModelManage.getBooksList();
                    count = String.valueOf(books.size());
                    break;
                case 1:
                    ArrayList<Student> students = this.libraryModelManage.getStudentsList();
                    count = String.valueOf(students.size());
                    break;
                case 2:
                    ArrayList<Issue> issues = this.libraryModelManage.getIssuesList();
                    count = String.valueOf(issues.size());
                    break;
                case 3:
                    ArrayList<Admin> admins = this.libraryModelManage.getAdminsList();
                    count = String.valueOf(admins.size());
                    break;
            }
            infoPanel.add(createInfoTile(infoTitles[i], icons[i], i % 2 == 0, count));
        }

        return infoPanel;
    }

    /**
     * Tạo một ô thông tin với tiêu đề, biểu tượng và số liệu thống kê.
     *
     * @param title Tiêu đề
     * @param iconPath Đường dẫn tới biểu tượng
     * @param isPink Kiểm tra màu nền
     * @param count Số liệu thống kê
     * @return JPanel chứa ô thông tin
     */
    private JPanel createInfoTile(String title, String iconPath, boolean isPink, String count) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230,230,250));;
        JLabel iconLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath))));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
        panel.add(iconLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setOpaque(true);
        panel.setBackground(isPink ? new Color(255, 192, 203) : new Color(191, 239, 255));
        titleLabel.setBackground(isPink ? Color.PINK : new Color(135, 206, 250));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel countLabel = new JLabel(count, JLabel.CENTER);
        countLabel.setFont(new Font("Arial Rounded", Font.BOLD, 40));
        panel.add(countLabel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Tạo một JPanel chứa bảng chi tiết thông tin sách trong thư viện.
     * @return JPanel chứa bảng chi tiết sách.
     * @throws IllegalStateException nếu đối tượng libraryModelManage chưa được khởi tạo.
     */
    private JPanel createStudentDetails() {

        // Kiểm tra nếu libraryModelManage chưa được khởi tạo
        if (this.libraryModelManage == null) {
            throw new IllegalStateException("LibraryModelManage chưa được khởi tao!");
        }

        // Định nghĩa tên cột cho bảng thông tin sách
        String[] bookColumnNames = {"Book ID", "Book Name", "Author", "Quantity"};

        // Lấy danh sách sách từ đối tượng libraryModelManage
        ArrayList<Book> books = this.libraryModelManage.getBooksList();
        Object[][] bookData = new Object[books.size()][4];

        // Điền dữ liệu vào mảng 2 chiều bookData từ danh sách sách
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            bookData[i][0] = book.getBookID();
            bookData[i][1] = book.getBookName();
            bookData[i][2] = book.getAuthor();
            bookData[i][3] = book.getTotal();
        }

        // Tạo và trả về JPanel chứa bảng chi tiết sách
        return createTablePanel(bookData, bookColumnNames, 5,"Book Details");
    }

    /**
     * Tạo một JPanel chứa bảng chi tiết thông tin sinh viên.
     * @return JPanel chứa bảng chi tiết sinh viên.
     * @throws IllegalStateException nếu đối tượng libraryModelManage chưa được khởi tạo.
     */
    private JPanel createBookDetailsTable() {

        // Kiểm tra nếu libraryModelManage chưa được khởi tạo
        if (this.libraryModelManage == null) {
            throw new IllegalStateException("LibraryModelManage chưa được khởi tạo!");
        }

        // Định nghĩa tên cột cho bảng thông tin sinh viên
        String[] studentColumnNames = {"Student ID", "Student Name", "Student Email", "Contact Number"};

        // Lấy danh sách sinh viên từ đối tượng libraryModelManage
        ArrayList<Student> students = this.libraryModelManage.getStudentsList();
        Object[][] studentData = new Object[students.size()][4];

        // Điền dữ liệu vào mảng 2 chiều studentData từ danh sách sinh viên
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            studentData[i][0] = student.getID();
            studentData[i][1] = student.getName();
            studentData[i][2] = student.getEmail();
            studentData[i][3] = student.getPhone();
        }

        // Tạo và trả về JPanel chứa bảng chi tiết sinh viên
        return createTablePanel(studentData, studentColumnNames, 5,"Student Details");
    }

    /**
     * Tạo bảng với dữ liệu và cài đặt giao diện cho bảng.
     *
     * @param data Dữ liệu cần hiển thị trong bảng.
     * @param columnNames Tên các cột của bảng.
     * @param rowCount Số dòng tối đa hiển thị trong bảng.
     * @param title Tiêu đề của bảng.
     * @return JPanel chứa bảng.
     */
    private JPanel createTablePanel(Object[][] data, String[] columnNames, int rowCount,String title) {
        // Tạo một DefaultTableModel với dữ liệu và tên cột
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tất cả các ô đều không thể chỉnh sửa
            }
        };

        // Tạo bảng JTable và cấu hình giao diện của từng ô
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    JComponent jc = (JComponent) c;
                    c.setFont(new Font("Tahoma",Font.PLAIN,13));
                    c.setBackground(new Color(193,255, 193)); // Ví dụ: màu tím nhạt
                    c.setForeground(Color.BLACK); // Đặt màu chữ
                    jc.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK)); // Đặt viền đậm cho mỗi ô
                }
                return c;
            }
        };

        // Cấu hình chiều cao hàng, màu của lưới và đường viền
        table.setRowHeight(30);
        table.setGridColor(new Color(193 ,255, 193)); // Màu của các đường lưới (không cần nếu đã có viền cho mỗi ô)
        table.setShowGrid(false); // Hiển thị đường lưới

        // Cấu hình tiêu đề bảng
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("Tahoma",Font.BOLD,12));
                label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(238,233,233)));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBackground(new Color(144,238,144));
                return label;
            }
        });

        // Căn chỉnh nội dung trong các ô và thiết lập độ rộng cột
        centerTableCells(table);
        setTableHeaderAlignment(table);
        setTableColumnWidths(table);

        // Tạo JScrollPane để hiển thị bảng và cài đặt các tính năng cuộn
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.setPreferredSize(new Dimension(600, table.getRowHeight() * rowCount));
        scrollPane.setBorder(new LineBorder(new Color(193, 255, 193), 2));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // Luôn hiển thị thanh cuộn dọc
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Không hiển thị thanh cuộn ngang

        // Tạo JPanel để chứa bảng và tiêu đề
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(238, 174, 238));

        // Tiêu đề của bảng
        JLabel jLabelTitle=new JLabel(title);
        jLabelTitle.setFont(new Font("Tahoma",Font.BOLD,12) );
        jLabelTitle.setForeground(Color.BLACK);
        panel.add(jLabelTitle,BorderLayout.NORTH);

        // Thêm JScrollPane vào JPanel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setPreferredSize(scrollPane.getPreferredSize());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    /**
     * Căn giữa các ô trong bảng JTable.
     * @param table JTable mà các ô của nó sẽ được căn giữa.
     */
    private void centerTableCells(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * Căn giữa tiêu đề của các cột trong bảng JTable.
     * @param table JTable mà tiêu đề của các cột sẽ được căn giữa.
     */
    private void setTableHeaderAlignment(JTable table) {
        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Đặt chiều rộng cho các cột trong bảng JTable.
     * @param table JTable mà chiều rộng của các cột sẽ được thiết lập.
     */
    private void setTableColumnWidths(JTable table) {
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0 -> column.setPreferredWidth(100);
                case 1 -> column.setPreferredWidth(200); // Student Email / Book Name
                case 2 -> column.setPreferredWidth(150); // Major / Author
                case 3 -> column.setPreferredWidth(150); // Contact Number / Quantity
            }
        }
    }

    /**
     * Tạo JPanel chứa các bảng cho chi tiết sinh viên và chi tiết sách.
     * @return JPanel chứa các bảng chi tiết sinh viên và sách.
     */
    private JPanel createTablesPanel() {
        JPanel tablesPanel = new JPanel();
        tablesPanel.setBackground(new Color(230,230,250));;
        tablesPanel.setLayout(new GridLayout(2, 1, 10, 10));
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tablesPanel.add(createStudentDetails());
        tablesPanel.add(createBookDetailsTable());
        return tablesPanel;
    }

    /**
     * Tạo biểu đồ tròn thể hiện các sách phổ biến nhất.
     * Biểu đồ sẽ dựa trên số lần mượn sách trong hệ thống.
     * @return JPanel chứa biểu đồ tròn các sách phổ biến.
     */
    private JPanel createPieChart() {
        ArrayList<Issue> issues = this.libraryModelManage.getIssuesList();
        HashMap<String, Integer> bookCountMap = new HashMap<>();

        // Đếm số lần mượn mỗi sách
        for (Issue issue : issues) {
            Book book = this.libraryModelManage.searchBookByID(issue.getIssueBookID());
            if (book != null) { // Kiểm tra nếu book không null
                String bookName = book.getBookName(); // Lấy tên sách
                bookCountMap.put(bookName, bookCountMap.getOrDefault(bookName, 0) + 1); // Tăng số lần mượn
            }
        }

        // Sắp xếp bookCountMap theo giá trị giảm dần và lấy 4 phần tử đầu tiên
        LinkedHashMap<String, Integer> sortedBookCountMap = bookCountMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(4)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        // Tính tổng số sách đã mượn
        int totalIssues = bookCountMap.values().stream().mapToInt(Integer::intValue).sum();

        // Khởi tạo các mảng values, labels và colors
        double[] values = new double[sortedBookCountMap.size() + 1];
        String[] labels = new String[sortedBookCountMap.size() + 1];
        Color[] colors = {
                new Color(238, 232, 170),
                new Color(0, 250, 0),
                new Color(30, 144, 255),
                new Color(238, 174, 238),
                new Color(255, 20, 147)
        };

        int index = 0;
        int sum = 0;
        for (Map.Entry<String, Integer> entry : sortedBookCountMap.entrySet()) {
            String bookName = entry.getKey();
            int count = entry.getValue();
            double percentage = (double) count / totalIssues * 100;

            sum += percentage;

            // Chia thành các từ và xuống dòng mỗi 5 từ
            String[] words = bookName.split(" ");
            StringBuilder wrappedText = new StringBuilder();

            int wordCount = 0;
            for (String word : words) {
                wrappedText.append(word).append(" ");
                wordCount++;

                // Sau mỗi 5 từ, xuống dòng
                if (wordCount == 5) {
                    wrappedText.append("\n"); // Chuyển sang dòng mới
                    wordCount = 0; // Reset lại đếm từ
                }
            }

            // Đính kèm phần trăm vào cuối tên sách
            wrappedText.append("(").append(String.format("%.2f", percentage)).append("%)");

            labels[index] = wrappedText.toString();
            values[index] = percentage;
            index++;
        }

        // Phần còn lại
        double otherPercentage = 100 - sum;
        values[index] = otherPercentage;
        labels[index] = "Other (" + String.format("%.2f", otherPercentage) + "%)";

        // Vẽ biểu đồ
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        PieChartExample pieChart = new PieChartExample(values, colors, labels);
        pieChart.setBackground(new Color(230, 230, 250));
        jPanel.setBackground(new Color(230, 230, 250));
        jPanel.add(pieChart, BorderLayout.CENTER);
        jPanel.setVisible(true);

        JLabel jLabel = new JLabel("Popular books");
        jLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setPreferredSize(new Dimension(200, 50));  // Thiết lập kích thước của JLabel

        jPanel.add(jLabel, BorderLayout.NORTH);
        jPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), // Viền đen
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Khoảng cách bên trong 10px từ viền
        ));

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10)); // Khoảng cách với cạnh JFrame
        outerPanel.add(jPanel, BorderLayout.CENTER); // Đặt jPanel vào giữa outerPanel
        outerPanel.setBackground(new Color(230, 230, 250));

        jPanel.revalidate();
        jPanel.repaint();
        return outerPanel;
    }

    /**
     * Lấy nút hamburger (nút menu).
     * @return Nút hamburger.
     */
    public JButton getHamburgerButton() {

        return hamburgerButton;
    }

    /**
     * Chuyển đổi trạng thái hiển thị menu (ẩn/hiện).
     */
    public void toggleMenuBar() {
        isMenuVisible = !isMenuVisible;
        menuBar.setVisible(isMenuVisible);
        revalidate();
        repaint();
    }

    /**
     * Thiết lập panel chính cho trang chủ.
     * @param newPanel JPanel mới sẽ được thêm vào trang chủ.
     */
    public void setMainHomePanel(JPanel newPanel) {
        if (mainHomePanel != null) {
            homePagePanel.remove(mainHomePanel);
        }
        mainHomePanel = newPanel;
        homePagePanel.add(mainHomePanel, BorderLayout.CENTER);
        homePagePanel.revalidate();
        homePagePanel.repaint();
    }

    /**
     * Lấy nút hamburger.
     * @param hamburgerButton Nút hamburger sẽ được thiết lập.
     */
    public void setHamburgerButton(JButton hamburgerButton) {
        this.hamburgerButton = hamburgerButton;
    }

    /**
     * Lấy menu trang chủ.
     * @return JMenu trang chủ.
     */
    public JMenu getjMenuHomePage() {
        return jMenuHomePage;
    }

    /**
     * Thiết lập menu trang chủ.
     * @param jMenuHomePage JMenu trang chủ mới.
     */
    public void setjMenuHomePage(JMenu jMenuHomePage) {
        this.jMenuHomePage = jMenuHomePage;
    }

    // Các phương thức getter, setter
    public JMenu getjMenuLogout() {
        return jMenuLogout;
    }

    public void setjMenuLogout(JMenu jMenuLogout) {
        this.jMenuLogout = jMenuLogout;
    }

    public JMenu getjMenuLMSDashBoard() {
        return jMenuLMSDashBoard;
    }

    public void setjMenuLMSDashBoard(JMenu jMenuLMSDashBoard) {
        this.jMenuLMSDashBoard = jMenuLMSDashBoard;
    }

    public JMenu getjMenuMGMTBooks() {
        return jMenuMGMTBooks;
    }

    public void setjMenuMGMTBooks(JMenu jMenuMGMTBooks) {
        this.jMenuMGMTBooks = jMenuMGMTBooks;
    }

    public JMenu getjMenuMGMTSutudents() {
        return jMenuMGMTSutudents;
    }

    public void setjMenuMGMTSutudents(JMenu jMenuMGMTSutudents) {
        this.jMenuMGMTSutudents = jMenuMGMTSutudents;
    }

    public JMenu getjMenuIssueBook() {
        return jMenuIssueBook;
    }

    public void setjMenuIssueBook(JMenu jMenuIssueBook) {
        this.jMenuIssueBook = jMenuIssueBook;
    }

    public JMenu getjMenuReturnBook() {
        return jMenuReturnBook;
    }

    public void setjMenuReturnBook(JMenu jMenuReturnBook) {
        this.jMenuReturnBook = jMenuReturnBook;
    }

    public JMenu getjMenuViewRecords() {
        return jMenuViewRecords;
    }

    public void setjMenuViewRecords(JMenu jMenuViewRecords) {
        this.jMenuViewRecords = jMenuViewRecords;
    }

    public JMenu getjMenuSearchApi(){
        return  jMenuItemAPI;
    }

    public MainView getMainView()
    {
        return mainView;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JPanel getMainHomePanel() {
        return mainHomePanel;
    }

    public boolean isMenuVisible() {
        return isMenuVisible;
    }

    public void setMenuVisible(boolean menuVisible) {
        isMenuVisible = menuVisible;

    }

}
