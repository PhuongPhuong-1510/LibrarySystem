package ManageBook.view;

import HomePage.view.CustomScrollBarUI;
import ManageBook.controller.ManagementBookController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

public class ManagementBookView extends JPanel {
    private JPanel managementBooks;
    private ManagementBookController managementBookController;
    private JButton addBookButton;

    public ManagementBookView() {
        this.setLayout(new BorderLayout());
        this.init();
        this.managementBookController = new ManagementBookController(this);
    }

    private void init() {
        managementBooks = new JPanel();
        managementBooks.setLayout(new BorderLayout(10,10));
        managementBooks.add(createBookDetailsPanel(), BorderLayout.CENTER);
        managementBooks.add(createNorthPanel(), BorderLayout.NORTH);
        managementBooks.setBackground(new Color(255,192,203));

        this.add(managementBooks, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public JPanel createBookDetailsPanel() {
        JPanel bookDetailsPanel = new JPanel();
        bookDetailsPanel.setLayout(new BorderLayout(15, 15));
        bookDetailsPanel.setBackground(new Color(255,192,203));
        bookDetailsPanel.setBorder(BorderFactory.createLineBorder(new Color(255,192,203)));

        // Using BoxLayout for bookListPanel to allow dynamic expansion
        JPanel bookListPanel = new JPanel();
        bookListPanel.setLayout(new GridLayout(100,1,15,15));
        bookListPanel.setBackground(new Color(255,192,203));
        //bookListPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // Loop to add 100 bookPanels with unique IDs from 1 to 100
        for (int i = 1; i <= 100; i++) {
            bookListPanel.add(bookPanel(i));
        }

        // Add bookListPanel to JScrollPane
        JScrollPane scrollPane = createScrollPane(bookListPanel);

        JPanel titlePanel = new JPanel(new BorderLayout(20, 20));
        titlePanel.setBackground(new Color(255, 240, 245));

        JLabel IDLaybel = new JLabel("BooK ID");
        IDLaybel.setForeground(Color.BLACK);
        IDLaybel.setFont(new Font("Serif", Font.BOLD, 20));


        JLabel titleLaybel = new JLabel("                                   Title");
        titleLaybel.setForeground(Color.BLACK);
        titleLaybel.setFont(new Font("Serif", Font.BOLD, 20));


        JLabel actionLaybel = new JLabel("Action    ");
        actionLaybel.setForeground(Color.BLACK);
        actionLaybel.setFont(new Font("Serif", Font.BOLD, 20));

        actionLaybel.setForeground(Color.BLACK);
        titlePanel.add(IDLaybel, BorderLayout.WEST);
        titlePanel.add(titleLaybel, BorderLayout.CENTER);
        titlePanel.add(actionLaybel, BorderLayout.EAST);

        bookDetailsPanel.add(scrollPane, BorderLayout.CENTER);
        bookDetailsPanel.add(titlePanel, BorderLayout.NORTH);
        return bookDetailsPanel;
    }

    private JScrollPane createScrollPane(JPanel table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(238, 210, 238)); // Màu nền cho vùng hiển thị của JScrollPane


        CustomScrollBarUI verticalScrollBarUI = new CustomScrollBarUI();
        verticalScrollBarUI.setColors(new Color(205, 201, 201), new Color(232, 232, 232));
        scrollPane.getVerticalScrollBar().setUI(verticalScrollBarUI); // Ghi đè UI cho thanh cuộn dọc

        CustomScrollBarUI horizontalScrollBarUI = new CustomScrollBarUI();
        horizontalScrollBarUI.setColors(new Color(205, 201, 201), new Color(232, 232, 232));
        scrollPane.getHorizontalScrollBar().setUI(horizontalScrollBarUI); // Ghi đè UI cho thanh cuộn ngang

        scrollPane.setPreferredSize(new Dimension(1200, getHeight()));
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }



    public JPanel bookPanel(int bookId) {
        JPanel bookPanel = new JPanel(new BorderLayout(80, 80));
        bookPanel.setBackground(new Color(255, 240, 245));
        bookPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 240, 245)));

        JLabel bookIDLaybel = new JLabel("S" + String.format("%03d", bookId));
        bookIDLaybel.setForeground(Color.BLACK);


        JLabel bookImageLabel = createImageLabel("/ManageBook/icon/1.jpg");

        JLabel bookNameLaybel = new JLabel("天使と悪魔" + bookId);
        bookNameLaybel.setForeground(Color.BLACK);
        bookNameLaybel.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel bookAuthorLaybel = new JLabel("author" + bookId);
        bookAuthorLaybel.setForeground(Color.BLACK);

        JLabel bookCategoryLaybel = new JLabel("category" + bookId);
        bookCategoryLaybel.setForeground(Color.BLACK);

        JLabel bookLanguageLaybel = new JLabel("language" + bookId);
        bookLanguageLaybel.setForeground(Color.BLACK);

        JLabel bookTotalLaybel = new JLabel("100");
        bookTotalLaybel.setForeground(Color.BLACK);

        JLabel bookCurentLaybel = new JLabel("curent" + bookId);
        bookCurentLaybel.setForeground(Color.BLACK);

        JLabel bookPositionLaybel = new JLabel("Position" + bookId);
        bookPositionLaybel.setForeground(Color.BLACK);

        JPanel actionPanel = createActionPanel(bookId);

        JPanel namePanel = new JPanel(new BorderLayout(5, 5));
        namePanel.setBackground(new Color(255, 240, 245));
        namePanel.add(bookNameLaybel, BorderLayout.NORTH);

        JPanel nameTitelPanel = new JPanel(new GridLayout(6,1,5,5));
        nameTitelPanel.setBackground(new Color(255, 240, 245));
        nameTitelPanel.add(bookAuthorLaybel);
        nameTitelPanel.add(bookCategoryLaybel);
        nameTitelPanel.add(bookLanguageLaybel);
        nameTitelPanel.add(bookTotalLaybel);
        nameTitelPanel.add(bookCurentLaybel);
        nameTitelPanel.add(bookPositionLaybel);

        namePanel.add(nameTitelPanel, BorderLayout.CENTER);

        JPanel bookTitelPanel = new JPanel(new BorderLayout(10, 10));
        bookTitelPanel.setBackground(new Color(255, 240, 245));
        bookTitelPanel.add(bookImageLabel, BorderLayout.WEST);
        bookTitelPanel.add(namePanel, BorderLayout.CENTER);



        bookPanel.add(bookIDLaybel, BorderLayout.WEST);
        bookPanel.add(bookTitelPanel, BorderLayout.CENTER);
        bookPanel.add(actionPanel, BorderLayout.EAST);

        return bookPanel;
    }


    private JLabel createImageLabel(String path) {
        ImageIcon icon;
        if (path != null && getClass().getResource(path) != null) {
            icon = new ImageIcon(getClass().getResource(path));
        } else {
            icon = new ImageIcon(new BufferedImage(100, 150, BufferedImage.TYPE_INT_ARGB)); // Placeholder
        }
        return new JLabel(icon);
    }

    private JPanel createActionPanel(int bookId) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(1,2,5,5));
        actionPanel.setBackground(new Color(255, 240, 245));

        JButton editButton = createActionButton("/ManageBook/icon/pen.jpg", "Edit");
        JButton deleteButton = createActionButton("/ManageBook/icon/bin.jpg", "Delete");

        editButton.addActionListener(e -> handleEditButtonClick(bookId));
        deleteButton.addActionListener(e -> handleDeleteButtonClick(bookId));

        actionPanel.add(editButton);
        //actionPanel.add(Box.createVerticalStrut(10));  // Khoảng cách giữa các nút
        actionPanel.add(deleteButton);

        return actionPanel;
    }

    private JButton createActionButton(String iconPath, String tooltip) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(iconPath)));
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(20, 20));
        return button;
    }

    private void handleDeleteButtonClick(int bookId) {
        System.out.println("Delete button clicked for Book ID: " + bookId);
    }

    private void handleEditButtonClick(int bookId) {
        System.out.println("Edit button clicked for Book ID: " + bookId);
    }

    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northPanel.setBackground(new Color(150, 180, 255));
        northPanel.setBorder(null);

        JPanel searchField = createSearchPanel();
        JButton addBookButton = createAddBookButton();

        northPanel.add(searchField);
        northPanel.add(addBookButton);

        return northPanel;
    }

    private JPanel createSearchPanel() {
        String placeholder = "Search id, author, genre, book title";

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setForeground(Color.GRAY);
        searchField.setText(placeholder);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText(placeholder);
                }
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        searchPanel.setBackground(new Color(150, 180, 255));

        return searchPanel;
    }

    private JButton createAddBookButton() {
        addBookButton = new JButton("Add Book");
        addBookButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        addBookButton.setBackground(new Color(0, 123, 255));
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBookButton.setPreferredSize(new Dimension(90, 30));
        addBookButton.setBorderPainted(false);
        addBookButton.setFocusPainted(false);
        return addBookButton;
    }

    public JButton getAddBookButton() {
        return addBookButton;
    }
}
