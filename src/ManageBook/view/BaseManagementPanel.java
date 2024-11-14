package ManageBook.view;

import MainApp.model.Book;
import MainApp.model.LibraryModelManage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public abstract class BaseManagementPanel extends JPanel {
    protected JPanel northPanel;
    protected JButton addBookButton;
    private String placeholder;
    private String iconPath;
    private String text;
    private LibraryModelManage libraryModelManage;
    private ManagementBookView managementBookView;
    private ArrayList<Book> filteredBooks = null;
    private Timer typingTimer;

    public BaseManagementPanel(String placeholder, String iconPath, String text, ManagementBookView managementBookView) {
        this.libraryModelManage = new LibraryModelManage();
        this.text = text;
        this.placeholder = placeholder;
        this.iconPath = iconPath;
        this.managementBookView = managementBookView;
        this.northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northPanel.setBackground(new Color(150, 180, 255));
        northPanel.setBorder(null);

        JPanel searchField = createSearchPanel();
        JButton addBookButton = createAddBookButton();

        northPanel.add(searchField);
        northPanel.add(addBookButton);

        this.setLayout(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);
    }

    public BaseManagementPanel(String placeholder, String iconPath, String text) {
        this.text = text;
        this.placeholder = placeholder;
        this.iconPath = iconPath;
        this.northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northPanel.setBackground(new Color(150, 180, 255));
        northPanel.setBorder(null);

        JPanel searchField = createSearchPanel();
        JButton addBookButton = createAddBookButton();

        northPanel.add(searchField);
        northPanel.add(addBookButton);

        this.setLayout(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);
    }

    protected JPanel createSearchPanel() {
        JTextField searchField = new JTextField(20);
        searchField.setBorder(null);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setForeground(Color.GRAY);
        searchField.setText(placeholder);

        typingTimer = new Timer(500, e -> {
            if (searchField.getText().isEmpty()) {
                restoreTable();
            }
        });
        typingTimer.setRepeats(false);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                typingTimer.restart();
                filterTable(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                typingTimer.restart();
                filterTable(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                typingTimer.restart();
                filterTable(searchField.getText());
            }
        });

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
                    restoreTable();
                }
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        searchPanel.setBackground(new Color(150, 180, 255));

        return searchPanel;
    }

    private void filterTable(String query) {
        if (query.isEmpty()) {
            filteredBooks = null;
            managementBookView.updateTable(libraryModelManage.getBooksList());
        } else {
            filteredBooks = libraryModelManage.searchBooks(query);
            managementBookView.updateTable(filteredBooks);
        }
    }

    public void restoreTable() {
        managementBookView.updateTable(libraryModelManage.getBooksList());
    }

    protected JButton createAddBookButton() {
        addBookButton = new JButton(new ImageIcon(getClass().getResource(iconPath)));
        addBookButton.setBackground(null);
        addBookButton.setFocusPainted(false);
        addBookButton.setBorderPainted(false);
        addBookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBookButton.setContentAreaFilled(false);
        addBookButton.setOpaque(false);
        addBookButton.setBorder(null);
        addBookButton.setToolTipText(text);
        addBookButton.setPreferredSize(new Dimension(30, 30));

        return addBookButton;
    }

    public JButton getAddBookButton() {
        return addBookButton;
    }
}
