package ManageBook.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public abstract class BaseManagementPanel extends JPanel{
    protected JPanel northPanel;
    protected JButton addBookButton;
    private String placeholder;
    private String iconPath;
    private String text;

    public BaseManagementPanel(String placeholder,String iconPath,String text) {
        this.text=text;
        this.placeholder=placeholder;
        this.iconPath=iconPath;
        this.northPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northPanel.setBackground(new Color(150, 180, 255));
        northPanel.setBorder(null);

        JPanel searchField = createSearchPanel();
        JButton addBookButton = createAddBookButton();

        northPanel.add(searchField);
        northPanel.add(addBookButton);

        this.setLayout(new BorderLayout());
        this.add(northPanel);

    }

    protected JPanel createSearchPanel() {

        JTextField searchField = new JTextField(20);
        searchField.setBorder(null);
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

    // Phương thức tạo nút "Add Book"
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

    // Getter cho nút "Add Book"
    public JButton getAddBookButton() {
        return addBookButton;
    }
}
