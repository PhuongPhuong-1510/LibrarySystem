package API;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.util.List;

public class ApiView extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JPanel panel;
    private JTextField textField;

    /**
     * Create the panel.
     */


    public ApiView() {
        ApiController apiController = new ApiController(this);
        setLayout(null);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.BOLD, 20));
        textField.setBounds(109, 46, 766, 67);
        add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Search");
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setBackground(new Color(0, 128, 64));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.addActionListener(apiController);
        btnNewButton.setBounds(904, 48, 102, 67);
        add(btnNewButton);


        panel = new JPanel();
        panel.setLayout(new GridLayout(50, 1, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(29, 152, 1149, 362);
        add(scrollPane);

        for (int i = 0; i < 50; i++) {
            JPanel panel_1 = new JPanel();
            panel_1.setLayout(new BorderLayout(0, 0));
            panel_1.setBackground(new Color(255, 255, 255));

            JPanel panel_2 = new JPanel();
            panel_1.add(panel_2, BorderLayout.EAST);
            panel_2.setLayout(new GridLayout(1, 2, 10, 10));


            JMenuItem mntmNewMenuItem = new JMenuItem("...");
            mntmNewMenuItem.setFont(new Font("Tahoma", Font.BOLD, 20));
            panel_1.add(mntmNewMenuItem, BorderLayout.CENTER);
            mntmNewMenuItem.setBackground(new Color(255, 255, 255));
            mntmNewMenuItem.setFont(new Font("Tahoma", Font.BOLD, 20));

            panel.add(panel_1);
        }

    }

    public void refresh() {

        String jsonResponse = GoogleBooksAPI.searchBooks("a"); // Tìm kiếm ngẫu nhiên với từ khóa "a"
        List<String[]> books = BookParser.getBookDetails(jsonResponse);

        panel.removeAll();

        for (int i = 0; i < books.size() && i < 50; i++) {
            String[] book = books.get(i);
            String title = book[0]; // Tên sách

            // Giới hạn tên sách tối đa 50 ký tự
            if (title.length() > 50) {
                title = title.substring(0, 50) + "...";
            }

            JPanel panel_1 = new JPanel();
            panel_1.setLayout(new BorderLayout(0, 0));
            panel_1.setBackground(new Color(255, 255, 255));

            JPanel panel_2 = new JPanel();
            panel_1.add(panel_2, BorderLayout.EAST);
            panel_2.setLayout(new GridLayout(1, 2, 10, 10));

            JButton btnNewButton_2 = new JButton("Add");
            btnNewButton_2.setBackground(new Color(75, 0, 130));
            btnNewButton_2.setForeground(new Color(255, 255, 255));
            panel_2.add(btnNewButton_2);

            JButton btnNewButton_3 = new JButton("See");
            btnNewButton_3.setBackground(new Color(0, 0, 128));
            btnNewButton_3.setForeground(new Color(255, 255, 255));
            panel_2.add(btnNewButton_3);

            JMenuItem mntmNewMenuItem = new JMenuItem(title);
            panel_1.add(mntmNewMenuItem, BorderLayout.CENTER);
            mntmNewMenuItem.setBackground(new Color(255, 255, 255));
            mntmNewMenuItem.setFont(new Font("Tahoma", Font.BOLD, 20));

            panel.add(panel_1);
        }

        // Cập nhật giao diện
        panel.revalidate();
        panel.repaint();
    }

    public void searchBooks(String query) {
        // Gọi API Google Books với từ khóa
        String jsonResponse = GoogleBooksAPI.searchBooks(query);
        List<String[]> books = BookParser.getBookDetails(jsonResponse);

        panel.removeAll();

        for (int i = 0; i < books.size() && i < 50; i++) {
            String[] book = books.get(i);
            final String title = book[0];  // Sử dụng biến final
            final String author = book[1];
            final String language = book[2];
            final String category = book[3];
            final String position = book[4];

            String displayedTitle = title;
            if (displayedTitle.length() > 50) {
                displayedTitle = displayedTitle.substring(0, 50) + "...";
            }

            JPanel panel_1 = new JPanel();
            panel_1.setLayout(new BorderLayout(0, 0));
            panel_1.setBackground(Color.WHITE);

            JPanel panel_2 = new JPanel();
            panel_1.add(panel_2, BorderLayout.EAST);
            panel_2.setLayout(new GridLayout(1, 2, 10, 10));

            JButton btnAdd = new JButton("Add");
            btnAdd.setBackground(new Color(75, 0, 130));
            btnAdd.setForeground(Color.WHITE);
            panel_2.add(btnAdd);

            JButton btnSee = new JButton("See");
            btnSee.setBackground(new Color(0, 0, 128));
            btnSee.setForeground(Color.WHITE);
            panel_2.add(btnSee);

            btnSee.addActionListener(e -> {
                JFrame chiTietFrame = new JFrame("Chi Tiết");
                chiTietFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                chiTietFrame.setSize(1000, 600);
                chiTietFrame.setLocationRelativeTo(null);
                ChiTiet chiTietPanel = new ChiTiet(title, author, language, category, position);
                chiTietFrame.getContentPane().add(chiTietPanel);
                chiTietFrame.setVisible(true);
            });

            JMenuItem mntmNewMenuItem = new JMenuItem(displayedTitle);
            panel_1.add(mntmNewMenuItem, BorderLayout.CENTER);
            mntmNewMenuItem.setBackground(Color.WHITE);
            mntmNewMenuItem.setFont(new Font("Tahoma", Font.BOLD, 20));

            panel.add(panel_1);
        }


        panel.revalidate();
        panel.repaint();
    }

    public String getSearchQuery() {
        return textField.getText().trim();
    }


}
