package API;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        panel.setBackground(new Color(75,0,130));
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
        panel_2.setLayout(new GridLayout(50, 1, 10, 10)); // Adjust layout to fit book titles in a list format

        JScrollPane jscrolpane = new JScrollPane(panel_2);
        panel_1.add(jscrolpane, BorderLayout.CENTER);

        // Set button action
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
        List<String> bookTitles = BookParser.getBookTitles(jsonResponse);

        panel_2.removeAll(); // Clear previous search results

        for (String title : bookTitles) {
            JLabel bookLabel = new JLabel(title);
            bookLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
            panel_2.add(bookLabel);
        }

        panel_2.revalidate(); // Refresh the panel to show new results
        panel_2.repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddBookPanel frame = new AddBookPanel();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
