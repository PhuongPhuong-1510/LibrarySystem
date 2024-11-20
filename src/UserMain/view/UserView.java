package UserMain.view;


import LoginPage.view.OvalButton;
import MainApp.model.*;
import MainApp.view.MainView;
import ManageBook.view.BaseBookTableView;
import ManageBook.view.PanelEditor;
import UserMain.controller.UserController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class UserView extends JPanel {
    private MainView mainView;
    private JButton hamburgerButton;
    private JMenuBar menuBar;
    private boolean isMenuVisible = false;
    private JPanel mainHomePanel;
    private JPanel homePagePanel;


    private JMenu jMenuHomePage;
    private JMenu jMenuHistory;
    private JMenu jMenuInfo;
    private JMenu jMenuOut;


    private JTextField txtBookId, txtBookName, txtAuthor,txtGenre;
    private JComboBox<String> cboGenre;
    private BaseBookTableView bookTableView;
    private DefaultTableModel modelCart;
    private JTable tableCart;
    public LibraryModelManage libraryModelManage;
    private JButton btnRegister;
    private JButton btnSearch;
    public Student student;


    public UserView(MainView mainView, Student student, LibraryModelManage libraryModelManage) {
        this.libraryModelManage = libraryModelManage;
        this.student = student;
        this.mainView = mainView;
        this.mainView = mainView;
        init();
        new UserController(this);

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

        JLabel lblMenu1 = new JLabel("Features");
        lblMenu1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMenu1.setBounds(15, 20, 300, 20);
        menuBar.add(lblMenu1);


        jMenuHomePage = createMenu("Home Page", "/UserMain/view/icon/find.png", 50);
        jMenuHomePage.setBackground(new Color(185,173,173));
        jMenuHomePage.setOpaque(true);
        jMenuHistory  = createMenu("History", "/UserMain/view/icon/reserved.png", 100);


        JLabel lblMenu2 = new JLabel("System");
        lblMenu2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMenu2.setBounds(15, 165, 300, 20);
        menuBar.add(lblMenu2);


        jMenuInfo = createMenu("Account", "/UserMain/view/icon/info.png", 205);
        jMenuOut = createMenu("Log Out", "/UserMain/view/icon/out.png", 255);





        return menuBar;
    }

    private JMenu createMenu(String title, String iconPath, int yPosition) {
        JMenu menu = new JMenu(title);
        menu.setFont(new Font("Tahoma", Font.PLAIN, 17));
        menu.setForeground(new Color(211 ,211 , 211 ));
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
        imgAdmin.setBounds(920, 0, 50, 50);
        homeMenu.add(imgAdmin);

        JLabel lblAdmin = new JLabel("Welcome, Student");
        lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAdmin.setBounds(970, 20, 160, 20);
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
    public JPanel createMainHome() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 230, 250));
        mainPanel.setPreferredSize(new Dimension(800, 582));


        mainPanel.add(createSearchPanel(), BorderLayout.WEST);
        mainPanel.add(createCartPanel(), BorderLayout.EAST);
        mainPanel.add(createBooksPanel(), BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createSearchPanel() {
        JLayeredPane layeredPane = createLayeredPane("/UserMain/view/icon/icon1.png");
        JPanel panelSearch = createSearchContentPanel();
        layeredPane.add(panelSearch, Integer.valueOf(1));

        JPanel layeredPanel = new JPanel(new BorderLayout());
        layeredPanel.add(layeredPane, BorderLayout.CENTER);

        return layeredPanel;
    }

    private JPanel createCartPanel() {
        JLayeredPane layeredPane = createLayeredPane("/UserMain/view/icon/icon2.png");
        JPanel panelCart = createCartContentPanel();
        layeredPane.add(panelCart, Integer.valueOf(1));

        JPanel layeredPanel = new JPanel(new BorderLayout());
        layeredPanel.add(layeredPane, BorderLayout.CENTER);

        return layeredPanel;
    }

    private JPanel createBooksPanel() {
        JPanel panelBooks = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/UserMain/view/icon/icon3.png"));
                Image backgroundImage = backgroundIcon.getImage();

                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelBooks.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Book List",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                new Color(54,54,54)
        ));


        String[] columnNames = createColumnNames();
        Object[][] data = fetchData();

        bookTableView = new BaseBookTableView(columnNames, data, 7, 150, 2) {
            @Override
            protected void centerTableCells(JTable table) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    if (i != 2 && i != 7) {
                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    }
                }
            }

            @Override
            protected void configureColumnRenderers(JTable table) {
                table.setDefaultRenderer(Object.class, createMultiLineRenderer());
                table.getColumnModel().getColumn(1).setCellRenderer(createTooltipRenderer());
                table.getColumnModel().getColumn(2).setCellRenderer(createLabelRenderer());
                table.getColumnModel().getColumn(7).setCellRenderer(createPanelRenderer());
                table.getColumnModel().getColumn(7).setCellEditor(new PanelEditor());
            }

            @Override
            protected void setTableColumnWidths(JTable table) {
                int[] columnWidths = {80, 115, 115, 105, 90, 90, 95, 95, 90, 115};
                for (int i = 0; i < table.getColumnCount(); i++) {
                    TableColumn column = table.getColumnModel().getColumn(i);
                    column.setPreferredWidth(columnWidths[i]);
                }
            }

            @Override
            protected JTable createTable(Object[][] data, String[] columnNames) {
                DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == editColumn;
                    }
                };

                return new JTable(model) {
                    @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component c = super.prepareRenderer(renderer, row, column);
                        if (selectedRow == row) {
                            c.setBackground(new Color(232, 232, 232));
                        } else if (c instanceof JComponent && !(c instanceof JPanel)) {
                            setupDefaultCellAppearance((JComponent) c);
                        }
                        return c;
                    }
                };
            }
        };

        // Thêm bảng vào panel chính
        panelBooks.add(bookTableView, BorderLayout.CENTER);

        return panelBooks;
    }


    private String[] createColumnNames() {
        return new String[]{
                "Book ID",
                "Book Title",
                "Image",
                "Author",
                "Genre",
                "Language",
                "Current",
                "Action"};
    }

    private Object[][] fetchData() {
        ArrayList<Book> booksList = libraryModelManage.getBooksList();

        Object[][] data = new Object[booksList.size()][10];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            data[i][0] = book.getBookID();
            data[i][1] = book.getBookName();
            data[i][2] = createImageLabel(book.getImage());
            data[i][3] = book.getAuthor();
            data[i][4] = book.getCategory();
            data[i][5] = book.getLanguage();
            data[i][6] = book.getCurent();
            data[i][7] = createAction(book, i);
        }

        return data;
    }



    private JLabel createImageLabel(String path) {
        ImageIcon icon;
        if (path != null && getClass().getResource(path) != null) {
            icon = new ImageIcon(getClass().getResource(path));
        } else {
            System.out.println("Image not found at path: " + path);
            icon = new ImageIcon(new BufferedImage(05, 05, BufferedImage.TYPE_INT_ARGB)); // Placeholder
        }
        return new JLabel(icon);
    }

    public JButton createActionButton(String iconPath, Color bgColor) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(iconPath)));

        button.setBackground(null);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(null);
        button.setPreferredSize(new Dimension(30, 30));


        return button;
    }

    public JPanel createAction(Book book, int row) {
        JPanel actionPanel = new JPanel(new BorderLayout());
        JButton cardButton = createActionButton("/UserMain/view/icon/card.png", new Color(255, 240, 245));
        cardButton.setToolTipText("Reserve Book");


        boolean isDisabled = isBookReservedOrIssued(book.getBookID(), student.getID());
        cardButton.setEnabled(!isDisabled);

        if (!isDisabled) {
            cardButton.addActionListener(e -> {
                toggleCardButton(actionPanel, cardButton, row);
            });
        }

        actionPanel.add(cardButton);
        return actionPanel;
    }

    private boolean isBookReservedOrIssued(String bookId, String studentId) {
        for (Issue issue : libraryModelManage.getIssuesList()) {
            if (issue.getIssueBookID().equals(bookId) && issue.getIssueStudentID().equals(studentId)) {
                return true;
            }
        }
        for (Reserve reserve : libraryModelManage.getReserveList()) {
            if (reserve.getBookID().equals(bookId) && reserve.getId().equals(studentId)) {
                return true;
            }
        }

        return false;
    }
    private void toggleCardButton(JPanel actionPanel, JButton cardButton, int row) {
        System.out.println("Button clicked");
        bookTableView.setSelectedRow(row);
        String bookId = bookTableView.getCellValue(row, 0).toString();
        String bookTitle = bookTableView.getCellValue(row, 1).toString();

        addBookToTable(bookId, bookTitle);
        cardButton.setEnabled(false);

    }

    private JLayeredPane createLayeredPane(String iconPath) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(200, 582));

        URL imageURL = getClass().getResource(iconPath);
        if (imageURL != null) {
            JLabel background = new JLabel(new ImageIcon(imageURL));
            background.setBounds(0, 0, 200, 582);
            layeredPane.add(background, Integer.valueOf(0));
        } else {
            System.err.println("Image not found: " + iconPath);
        }

        return layeredPane;
    }

    private JPanel createSearchContentPanel() {
        JPanel panelSearch = new JPanel(new GridLayout(2, 1));

        Font titleFont = new Font("Arial", Font.BOLD, 16); // Font Arial, đậm, kích thước 16

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Search Book",
                TitledBorder.LEFT,
                TitledBorder.TOP
        );
        titledBorder.setTitleFont(titleFont);

        panelSearch.setBorder(titledBorder);
        panelSearch.setOpaque(false);
        panelSearch.setBounds(0, 0, 200, 582);

        JPanel panelSearchFields = createSearchFieldsPanel();
        panelSearch.add(panelSearchFields);

        JPanel panelSearchButton = new JPanel();
        panelSearchButton.setOpaque(false);
        btnSearch = createButton("SEARCH");
        btnSearch.addActionListener(e -> performSearch());
        panelSearchButton.add(btnSearch);
        panelSearch.add(panelSearchButton);

        return panelSearch;
    }


    private JTextField createTextField(int width, int height) {
        JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setPreferredSize(new Dimension(width, height));
        return textField;
    }

    private JPanel createSearchFieldsPanel() {
        JPanel panelSearchFields = new JPanel();
        panelSearchFields.setLayout(new BoxLayout(panelSearchFields, BoxLayout.Y_AXIS));
        panelSearchFields.setOpaque(false);

        // Book ID
        JPanel bookIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookIdPanel.add(new JLabel("Book ID:"));
        txtBookId = createTextField(100, 30);
        bookIdPanel.add(txtBookId);
        panelSearchFields.add(bookIdPanel);

        // Book Title
        JPanel bookNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookNamePanel.add(new JLabel("Book Title:"));
        txtBookName = createTextField(100, 30);
        bookNamePanel.add(txtBookName);
        panelSearchFields.add(bookNamePanel);

        // Author
        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        authorPanel.add(new JLabel("Author:"));
        txtAuthor = createTextField(100, 30);
        authorPanel.add(txtAuthor);
        panelSearchFields.add(authorPanel);

        // Genre
        JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genrePanel.add(new JLabel("Genre:"));
        cboGenre = new JComboBox<>(new String[]{"All"});
        cboGenre.setEditable(true); 
        genrePanel.add(cboGenre);
        panelSearchFields.add(genrePanel);



        return panelSearchFields;
    }



    private void addBookToTable(String bookId, String bookName) {
        modelCart.addRow(new Object[]{bookId, bookName});
    }

    private void addBooksToTable() {
        String[][] books = {

        };

        for (String[] book : books) {
            addBookToTable(book[0], book[1]);
        }
    }

    private JTable createCartTable() {
        modelCart = new DefaultTableModel(new String[]{"Mã sách", "Tên sách"}, 0);
        tableCart = new JTable(modelCart);

        tableCart.setShowGrid(false);
        tableCart.setGridColor(Color.WHITE);
        tableCart.setIntercellSpacing(new Dimension(0, 0));
        tableCart.setBackground(new Color(136,206,250));
        tableCart.setOpaque(false);

        tableCart.setRowHeight(30);

        tableCart.setBorder(null);

        tableCart.getTableHeader().setBorder(null);
        tableCart.getTableHeader().setBackground(new Color(99,184,255));
        tableCart.getTableHeader().setForeground(Color.WHITE);
        tableCart.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        return tableCart;
    }

    private JPanel createCartContentPanel() {
        JPanel panelCart = new JPanel(new BorderLayout());

        Font titleFont = new Font("Arial", Font.BOLD, 16);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Book Card",
                TitledBorder.LEFT,
                TitledBorder.TOP
        );
        titledBorder.setTitleFont(titleFont);

        panelCart.setBorder(titledBorder);
        panelCart.setOpaque(false);
        panelCart.setBounds(0, 0, 200, 582);

        JTable table = createCartTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panelCart.add(scrollPane, BorderLayout.CENTER);

        btnRegister = createButton("Register");
        panelCart.add(btnRegister, BorderLayout.SOUTH);
        btnRegister.addActionListener(e -> {
            System.out.println("Register button clicked!");
            System.out.println(student.getID());
            handleRegisterAction();

            ArrayList<Reserve> reserves = createReserveList();
            for (Reserve reserve : reserves) {

                String bookID = reserve.getBookID();
                Book book = libraryModelManage.searchBookByID(bookID);
                book.setCurent("Reserved");
                libraryModelManage.editBookInDatabase(book);

                String reserveID = libraryModelManage.createReserveID();
                reserve.setReserveID(reserveID);
                libraryModelManage.addReserveToDatabase(reserve);

            }

            System.out.println("All reserves added to the database.");
        });

        addBooksToTable();

        return panelCart;
    }

    private void handleRegisterAction() {
        String bookId = getTxtBookId().getText();
        String bookName = getTxtBookName().getText();

        if (bookId.isEmpty() || bookName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
        } else {
            System.out.println("Registering book: " + bookName);
        }
    }

    public ArrayList<Reserve> createReserveList() {
        ArrayList<Reserve> reserves = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Định dạng ngày tháng

        for (int i = 0; i < model.getRowCount(); i++) {
            String bookID = (String) model.getValueAt(i, 0);
            String studentID = this.student.getID();

            LocalDate reservedDate = LocalDate.now();
            LocalDate dueDate = reservedDate.plusDays(100); // Cộng thêm 100 ngày

            String formattedReservedDate = reservedDate.format(formatter);
            String formattedDueDate = dueDate.format(formatter);

            java.sql.Date sqlReservedDate = java.sql.Date.valueOf(reservedDate);
            java.sql.Date sqlDueDate = java.sql.Date.valueOf(dueDate);

            Reserve reserve = new Reserve(null, bookID, studentID, sqlReservedDate, sqlDueDate);
            reserves.add(reserve);
        }

        return reserves;
    }



    private JButton createButton(String text) {
        JButton button = new OvalButton(text);
        button.setBackground(new Color(99,184,255));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(80,25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
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

    private void performSearch() {
        String bookId = txtBookId.getText().trim();
        String bookName = txtBookName.getText().trim();
        String author = txtAuthor.getText().trim();
        String genre = cboGenre.getSelectedItem().toString();

        ArrayList<Book> searchResults = libraryModelManage.searchBooks(bookId, bookName, author, genre);
        updateTable(searchResults);
    }

    private void updateTable(ArrayList<Book> books) {
        DefaultTableModel model = (DefaultTableModel) bookTableView.getTable().getModel();
        model.setRowCount(0);
        for (Book book : books) {
            model.addRow(new Object[]{
                    book.getBookID(),
                    book.getBookName(),
                    createImageLabel(book.getImage()),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getLanguage(),
                    book.getCurent(),
                    createAction(book, model.getRowCount())
            });
        }
    }

    public MainView getMainView() {
        return mainView;
    }

    public JButton getHamburgerButton() {
        return hamburgerButton;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public boolean isMenuVisible() {
        return isMenuVisible;
    }

    public JPanel getMainHomePanel() {
        return mainHomePanel;
    }

    public JPanel getHomePagePanel() {
        return homePagePanel;
    }

    public JMenu getjMenuHomePage() {
        return jMenuHomePage;
    }

    public JMenu getjMenuHistory() {
        return jMenuHistory;
    }



    public JMenu getjMenuInfo() {
        return jMenuInfo;
    }



    public JMenu getjMenuOut() {
        return jMenuOut;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void setHamburgerButton(JButton hamburgerButton) {
        this.hamburgerButton = hamburgerButton;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void setMenuVisible(boolean menuVisible) {
        isMenuVisible = menuVisible;
    }




    public void setHomePagePanel(JPanel homePagePanel) {
        this.homePagePanel = homePagePanel;
    }

    public void setjMenuHomePage(JMenu jMenuHomePage) {
        this.jMenuHomePage = jMenuHomePage;
    }

    public void setjMenuHistory(JMenu jMenuHistory) {
        this.jMenuHistory = jMenuHistory;
    }



    public void setjMenuInfo(JMenu jMenuInfo) {
        this.jMenuInfo = jMenuInfo;
    }


    public void setjMenuOut(JMenu jMenuOut) {
        this.jMenuOut = jMenuOut;
    }

    public void setTxtBookId(JTextField txtBookId) {
        this.txtBookId = txtBookId;
    }

    public void setTxtBookName(JTextField txtBookName) {
        this.txtBookName = txtBookName;
    }

    public void setTxtAuthor(JTextField txtAuthor) {
        this.txtAuthor = txtAuthor;
    }

    public void setTxtGenre(JTextField txtGenre) {
        this.txtGenre = txtGenre;
    }

    public void setCboGenre(JComboBox<String> cboGenre) {
        this.cboGenre = cboGenre;
    }

    public void setBookTableView(BaseBookTableView bookTableView) {
        this.bookTableView = bookTableView;
    }

    public void setModelCart(DefaultTableModel modelCart) {
        this.modelCart = modelCart;
    }

    public void setTableCart(JTable tableCart) {
        this.tableCart = tableCart;
    }

    public void setLibraryModelManage(LibraryModelManage libraryModelManage) {
        this.libraryModelManage = libraryModelManage;
    }

    public void setBtnRegister(JButton btnRegister) {
        this.btnRegister = btnRegister;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JTextField getTxtBookId() {
        return txtBookId;
    }

    public JTextField getTxtBookName() {
        return txtBookName;
    }

    public JTextField getTxtAuthor() {
        return txtAuthor;
    }

    public JTextField getTxtGenre() {
        return txtGenre;
    }

    public JComboBox<String> getCboGenre() {
        return cboGenre;
    }

    public BaseBookTableView getBookTableView() {
        return bookTableView;
    }

    public DefaultTableModel getModelCart() {
        return modelCart;
    }

    public JTable getTableCart() {
        return tableCart;
    }

    public LibraryModelManage getLibraryModelManage() {
        return libraryModelManage;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public void setStudent(Student student) {
        this.student = student;
        // Cập nhật giao diện dựa trên student mới
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }
}

