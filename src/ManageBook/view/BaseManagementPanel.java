package ManageBook.view;

import MainApp.model.Book;
import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import ManageStudent.view.ManagementStudentView;

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
    private ManagementBookView managementBookView;
    private ManagementStudentView managementStudentView;
    private ArrayList<Book> filteredBooks = null;
    private ArrayList<Student> filteredStudents = null;
    private Timer typingTimer;
    private boolean isBookView;

    public BaseManagementPanel(String placeholder, String iconPath, String text, ManagementBookView managementBookView, boolean isBookView) {
        this.text = text;
        this.placeholder = placeholder;
        this.iconPath = iconPath;
        this.managementBookView = managementBookView;
        this.isBookView = isBookView;
        initializePanel();
    }

    public BaseManagementPanel(String placeholder, String iconPath, String text, ManagementStudentView managementStudentView, boolean isBookView) {
        this.text = text;
        this.placeholder = placeholder;
        this.iconPath = iconPath;
        this.managementStudentView = managementStudentView;
        this.isBookView = isBookView;
        initializePanel();
    }

    public BaseManagementPanel(String placeholder, String iconPath, String text) {
        this.text = text;
        this.placeholder = placeholder;
        this.iconPath = iconPath;
        initializePanel();
    }

    private void initializePanel() {
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
        if (isBookView) {
            if (query.isEmpty()) {
                filteredBooks = null;
                managementBookView.updateTable(managementBookView.libraryModelManage.getBooksList());
            } else {
                filteredBooks = managementBookView.libraryModelManage.searchBooks(query);
                managementBookView.updateTable(filteredBooks);
            }
        } else {
            if (query.isEmpty()) {
                filteredStudents = null;
                managementStudentView.updateStudentTable(managementStudentView.libraryModelManage.getStudentsList());
            } else {
                filteredStudents = managementStudentView.libraryModelManage.searchStudents(query);
                managementStudentView.updateStudentTable(filteredStudents);
            }
        }
    }

    public void restoreTable() {
        if (isBookView) {
            managementBookView.updateTable(managementBookView.libraryModelManage.getBooksList());
        } else {
            managementStudentView.updateStudentTable(managementStudentView.libraryModelManage.getStudentsList());
        }
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
