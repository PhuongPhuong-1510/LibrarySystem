package API;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AddBookPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JButton btnSearch;
    private JPanel panel_1;
    private JPanel panel_2;

    public AddBookPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setResizable(true);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setBackground(new Color(75, 0, 130));
        panel.setLayout(new BorderLayout(0, 0));

        textField = new JTextField();
        panel.add(textField, BorderLayout.CENTER);
        textField.setColumns(10);

        btnSearch = new JButton("Search Books");
        panel.add(btnSearch, BorderLayout.EAST);

        panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        panel_2 = new JPanel();
        panel_2.setLayout(new GridLayout(50, 1, 10, 10));

        JScrollPane jscrolpane = new JScrollPane(panel_2);
        panel_1.add(jscrolpane, BorderLayout.CENTER);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = textField.getText();
                if (!query.isEmpty()) {
                    searchAndDisplayBooks(query);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a book name!");
                }
            }
        });
    }

    private void searchAndDisplayBooks(String query) {
        String jsonResponse = GoogleBooksAPI.searchBooks(query);
        List<String[]> bookDetails = BookParser.getBookDetails(jsonResponse);

        panel_2.removeAll();

        for (String[] details : bookDetails) {
            String title = details[0];
            String authors = details[1];
            String language = details[2];
            String genre = details[3];
            String thumbnailUrl = details.length > 4 ? details[4] : ""; // Check for thumbnail URL

            JLabel titleLabel = new JLabel("Title: " + title);
            titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

            JLabel authorLabel = new JLabel("Author: " + authors);
            authorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

            JLabel languageLabel = new JLabel("Language: " + language);
            languageLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

            JLabel genreLabel = new JLabel("Genre: " + genre);
            genreLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

            JPanel bookPanel = new JPanel(new GridLayout(4, 1, 5, 5));
            bookPanel.setBackground(Color.WHITE);
            bookPanel.add(titleLabel);
            bookPanel.add(authorLabel);
            bookPanel.add(languageLabel);
            bookPanel.add(genreLabel);

            JPanel bookPanelImage = new JPanel(new BorderLayout(10, 10));
            bookPanelImage.setBackground(Color.WHITE);
            bookPanelImage.add(bookPanel, BorderLayout.CENTER);

            // Load and display thumbnail
            if (!thumbnailUrl.isEmpty()) {
                try {
                    URL url = new URL(thumbnailUrl);
                    Image image = ImageIO.read(url);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 150, Image.SCALE_SMOOTH));
                    JLabel imageLabel = new JLabel(icon);
                    bookPanelImage.add(imageLabel, BorderLayout.WEST);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            panel_2.add(bookPanelImage);
        }

        panel_2.revalidate();
        panel_2.repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AddBookPanel frame = new AddBookPanel();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}