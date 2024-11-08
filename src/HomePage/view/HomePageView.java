package HomePage.view;

import HomePage.controller.HomePageController;
import HomePage.model.HomePageModel;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import MainApp.view.MainView;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;
import MainApp.model.StudentDAO;

public class HomePageView extends JPanel {
    private HomePageModel homePageModel;
    private MainView mainView;
    private JButton hamburgerButton;
    private HomePageController homePageController;
    private boolean isMenuVisible = false;
    private JMenuBar menuBar;
    private JPanel mainHomePanel;
    private JPanel homePagePanel;
    private LibraryModelManage libraryModelManage;


    private JMenu jMenuHomePage, jMenuLogout, jMenuLMSDashBoard, jMenuMGMTBooks, jMenuMGMTSutudents,
            jMenuIssueBook, jMenuReturnBook, jMenuViewBooks, jMenuViewRecords, jMenuDefault;

    public HomePageView(MainView mainView) {
        this.mainView = mainView;
        this.homePageModel = new HomePageModel();
        this.libraryModelManage = new LibraryModelManage();
        init();
        this.homePageController = new HomePageController(this);

    }

    private void init() {
        setSize(1200, 632);
        setLayout(new BorderLayout());

        homePagePanel = new JPanel(new BorderLayout());
        homePagePanel.add(createMenuBar(), BorderLayout.WEST);
        homePagePanel.add(createHomeMenu(), BorderLayout.NORTH);
        if (mainHomePanel == null) {
            mainHomePanel = createMainHome();
        }
        homePagePanel.add(mainHomePanel,BorderLayout.CENTER);

        add(homePagePanel);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setLayout(null);
        menuBar.setBackground(new Color(80, 80, 80));
        menuBar.setPreferredSize(new Dimension(215, 632));
        menuBar.setBorder(null);
        menuBar.setVisible(false);

        jMenuHomePage = createMenu("Home Page", "/HomePage/view/icon/icons8_Home_26px_2.png", 0);
        jMenuHomePage.setBackground(new Color(185,173,173));
        jMenuHomePage.setOpaque(true);
        jMenuLMSDashBoard = createMenu("LMS Notification", "/HomePage/view/icon/notification.png", 50);
        jMenuMGMTBooks = createMenu("Manage Books", "/HomePage/view/icon/icons8_Books_26px.png", 130);
        jMenuMGMTSutudents = createMenu("Manage Students", "/HomePage/view/icon/icons8_Read_Online_26px.png", 180);
        jMenuIssueBook = createMenu("Issue Book", "/HomePage/view/icon/icons8_Sell_26px.png", 230);
        jMenuReturnBook = createMenu("Return Book", "/HomePage/view/icon/icons8_Return_Purchase_26px.png", 280);
        jMenuViewRecords = createMenu("View Records", "/HomePage/view/icon/icons8_View_Details_26px.png", 330);
        jMenuViewBooks = createMenu("View Issued Books", "/HomePage/view/icon/icons8_Books_26px.png", 380);
        jMenuDefault = createMenu("Defaulter list", "/HomePage/view/icon/icons8_Conference_26px.png", 430);
        jMenuLogout = createMenu("Logout", "/HomePage/view/icon/icons8_Exit_26px_2.png", 480);

        JLabel lblMenu = new JLabel("Features");
        lblMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMenu.setBounds(10, 110, 300, 20);
        menuBar.add(lblMenu);

        return menuBar;
    }

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

    private JPanel createHomeMenu() {
        JPanel homeMenu = new JPanel(null);
        homeMenu.setBackground(new Color(150, 180, 255));
        homeMenu.setPreferredSize(new Dimension(1200, 50));

        hamburgerButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/HomePage/view/icon/icons8_menu_48px_1.png"))));
        hamburgerButton.setBounds(10, 8, 40, 40);
        hamburgerButton.setBorderPainted(false);
        hamburgerButton.setBackground(new Color(150, 180, 255));
        homeMenu.add(hamburgerButton);

        JLabel lblIcon = new JLabel("|");
        lblIcon.setFont(new Font("Tahoma", Font.PLAIN, 45));
        lblIcon.setForeground(Color.BLACK);
        lblIcon.setBounds(55, 0, 50, 45);
        homeMenu.add(lblIcon);

        JLabel imgAdmin = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/HomePage/view/icon/male_user_50px.png"))));
        imgAdmin.setBounds(935, 0, 50, 50);
        homeMenu.add(imgAdmin);

        JLabel lblAdmin = new JLabel("Welcome, Admin");
        lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAdmin.setBounds(985, 20, 120, 20);
        lblAdmin.setForeground(Color.WHITE);
        homeMenu.add(lblAdmin);

        createDateTimeLabels(homeMenu);

        return homeMenu;
    }

    private void createDateTimeLabels(JPanel panel) {
        JLabel timeLabel = new JLabel();
        JLabel dateLabel = new JLabel();

        timeLabel.setBounds(70, 5, 80, 20);
        dateLabel.setBounds(70, 30, 80, 20);

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
        mainPanel.add(createInfoPanel(),BorderLayout.NORTH);
        mainPanel.add(createTablesPanel(),BorderLayout.WEST);
        mainPanel.add(createPieChart(),BorderLayout.CENTER);
        return mainPanel;
    }


    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(1, 4, 100, 20));
        infoPanel.setBackground(new Color(230,230,250));
        infoPanel.setPreferredSize(new Dimension(getWidth(), 150));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] infoTitles = {"No Of Books", "No Of Students", "Issued Books", "Defaulter List"};
        String[] icons = {
                "/HomePage/view/icon/icons8_Book_Shelf_50px.png",
                "/HomePage/view/icon/icons8_People_50px.png",
                "/HomePage/view/icon/icons8_Sell_50px.png",
                "/HomePage/view/icon/icons8_List_of_Thumbnails_50px.png"
        };

        for (int i = 0; i < infoTitles.length; i++) {
            infoPanel.add(createInfoTile(infoTitles[i], icons[i], i % 2 == 0));
        }

        return infoPanel;
    }

    private JPanel createInfoTile(String title, String iconPath, boolean isPink) {
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

        JLabel countLabel = new JLabel("10", JLabel.CENTER);
        countLabel.setFont(new Font("Arial Rounded", Font.BOLD, 40));
        panel.add(countLabel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStudentDetails() {
        if (this.libraryModelManage == null) {
            throw new IllegalStateException("LibraryModelManage chưa được khởi tạo!");
        }
        String[] studentColumnNames = {"Student ID", "Student Name", "Student Email", "Contact Number"};

        ArrayList<Student> students = this.libraryModelManage.getStudentsList();
        Object[][] studentData = new Object[students.size()][4];

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            studentData[i][0] = student.getID();
            studentData[i][1] = student.getName();
            studentData[i][2] = student.getEmail();
            studentData[i][3] = student.getPhone();
        }
        
        return createTablePanel(studentData, studentColumnNames, 5,"Student Details");
    }

    private JPanel createBookDetailsTable() {
        String[] bookColumnNames = {"Book ID", "Book Name", "Author", "Quantity"};
        Object[][] bookData = {
                {"4", "PHP", "Rose", "16"},
                {"5", "HTML", "Bruce", "49"},
                {"6", "CSS", "Daniel", "0"},
                {"7", "Golang programming", "Jack", "45"},
                {"8", "Golang ", "Hi", "456"},

        };
        return createTablePanel(bookData, bookColumnNames, 5,"Book Details");
    }

    private JPanel createTablePanel(Object[][] data, String[] columnNames, int rowCount,String title) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tất cả các ô đều không thể chỉnh sửa
            }
        };

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
        table.setRowHeight(30);
        table.setGridColor(new Color(193 ,255, 193)); // Màu của các đường lưới (không cần nếu đã có viền cho mỗi ô)
        table.setShowGrid(false); // Hiển thị đường lưới

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

        centerTableCells(table);
        setTableHeaderAlignment(table);
        setTableColumnWidths(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.setPreferredSize(new Dimension(600, table.getRowHeight() * rowCount));
        scrollPane.setBorder(new LineBorder(new Color(193, 255, 193), 2));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // Luôn hiển thị thanh cuộn dọc
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Không hiển thị thanh cuộn ngang


        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(238, 174, 238));

        JLabel jLabelTitle=new JLabel(title);
        jLabelTitle.setFont(new Font("Tahoma",Font.BOLD,12) );
        jLabelTitle.setForeground(Color.BLACK);
        panel.add(jLabelTitle,BorderLayout.NORTH);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setPreferredSize(scrollPane.getPreferredSize());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }





    private void centerTableCells(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void setTableHeaderAlignment(JTable table) {
        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

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

    private JPanel createTablesPanel() {
        JPanel tablesPanel = new JPanel();
        tablesPanel.setBackground(new Color(230,230,250));;
        tablesPanel.setLayout(new GridLayout(2, 1, 10, 10));
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tablesPanel.add(createStudentDetails());
        tablesPanel.add(createBookDetailsTable());
        return tablesPanel;
    }
    private JPanel createPieChart()
    {
        JPanel jPanel=new JPanel();
        jPanel.setLayout(new BorderLayout());
        double[] values = {30,
                20,
                15,
                10,
                25};
        Color[] colors = {new Color(238,232,170),
                new Color(0,250,0),
                new Color( 30,144,255),
                new Color(238 ,174, 238)	,
                new Color(255,20,147)};
        String[] labels = {"PHP",
                "HTML",
                "CSS",
                "Java for everyone",
                "Learn Python"};
        PieChartExample pieChart = new PieChartExample(values, colors, labels);
        pieChart.setBackground(new Color(230,230,250));
        jPanel.setBackground(new Color(230,230,250));
        jPanel.add(pieChart,BorderLayout.CENTER);
        jPanel.setVisible(true);

        JLabel jLabel=new JLabel("Issued Book details");
        jLabel.setFont(new Font("Tahoma",Font.BOLD,20));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel.add(jLabel,BorderLayout.NORTH);
        jPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), // Viền đen
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Khoảng cách bên trong 10px từ viền
        ));
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10)); // Khoảng cách với cạnh JFrame
        outerPanel.add(jPanel, BorderLayout.CENTER); // Đặt jPanel vào giữa outerPanel
        outerPanel.setBackground(new Color(230,230,250));
        jPanel.revalidate();
        jPanel.repaint();
        return outerPanel;

    }



    public JButton getHamburgerButton() {

        return hamburgerButton;
    }

    public void toggleMenuBar() {
        isMenuVisible = !isMenuVisible;
        menuBar.setVisible(isMenuVisible);
        revalidate();
        repaint();

    }
    public void setMainHomePanel(JPanel newPanel) {
        if (mainHomePanel != null) {
            homePagePanel.remove(mainHomePanel);
        }
        mainHomePanel = newPanel;
        homePagePanel.add(mainHomePanel, BorderLayout.CENTER);
        homePagePanel.revalidate();
        homePagePanel.repaint();
    }



    public void setHamburgerButton(JButton hamburgerButton) {
        this.hamburgerButton = hamburgerButton;
    }


    public JMenu getjMenuHomePage() {
        return jMenuHomePage;
    }

    public void setjMenuHomePage(JMenu jMenuHomePage) {
        this.jMenuHomePage = jMenuHomePage;
    }

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

    public JMenu getjMenuViewBooks() {
        return jMenuViewBooks;
    }

    public void setjMenuViewBooks(JMenu jMenuViewBooks) {
        this.jMenuViewBooks = jMenuViewBooks;
    }

    public JMenu getjMenuViewRecords() {
        return jMenuViewRecords;
    }

    public void setjMenuViewRecords(JMenu jMenuViewRecords) {
        this.jMenuViewRecords = jMenuViewRecords;
    }

    public JMenu getjMenuDefault() {
        return jMenuDefault;
    }

    public void setjMenuDefault(JMenu jMenuDefault) {
        this.jMenuDefault = jMenuDefault;
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
