package API;

import MainApp.model.Book;
import MainApp.model.LibraryModelManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ApiView extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JPanel panel;
    private JTextField textField;
    private LibraryModelManage libraryModelManage;

    /**
     * Create the panel.
     */


    public ApiView(LibraryModelManage libraryModelManage) {
        ApiController apiController = new ApiController(this);
        setLayout(null);
        this.libraryModelManage = libraryModelManage;
        libraryModelManage.getBooksList();

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


    public void searchBooks(String query) {
        if (!isInternetAvailable()) {
            JOptionPane.showMessageDialog(this, "No Internet connection. Please check your connection and try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        showLoadingDialog();

        searchWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    String jsonResponse = GoogleBooksAPI.searchBooks(query);
                    List<String[]> books = BookParser.getBookDetails(jsonResponse);

                    SwingUtilities.invokeLater(() -> {
                        panel.removeAll();

                        for (int i = 0; i < books.size() && i < 50; i++) {
                            String[] book = books.get(i);
                            final String title = book[0];
                            final String author = book[1];
                            final String language = book[2];
                            final String category = book[3];
                            final String imageUrl = book[4];
                            final String infoLink = book[5];

                            String displayedTitle = title.length() > 50 ? title.substring(0, 50) + "..." : title;

                            JPanel panel_1 = new JPanel(new BorderLayout(0, 0));
                            panel_1.setBackground(Color.WHITE);

                            JPanel panel_2 = new JPanel(new GridLayout(1, 2, 10, 10));
                            panel_1.add(panel_2, BorderLayout.EAST);

                            JButton btnAdd = new JButton("Add");
                            btnAdd.setBackground(new Color(75, 0, 130));
                            btnAdd.setForeground(Color.WHITE);
                            btnAdd.addActionListener(e -> {
                                showLoadingDialog();

                                SwingWorker<Void, Void> addBookWorker = new SwingWorker<>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        String bookID = libraryModelManage.creatBookID();
                                        String bookName = title;
                                        String bookAuthor = author;
                                        String bookCategory = category;
                                        String bookLanguage = language;
                                        int total = 1;
                                        String current = "Still";
                                        String bookPosition = "B2";

                                        String fileName = null;

                                        try {
                                            String directoryPath = "src/ManageBook/icon/"; // Thư mục lưu ảnh
                                            Files.createDirectories(Paths.get(directoryPath)); // Tạo thư mục nếu chưa tồn tại

                                            if (imageUrl == null || imageUrl.isEmpty()) {
                                                JOptionPane.showMessageDialog(null, "No image URL provided for this book.", "Error", JOptionPane.ERROR_MESSAGE);
                                                return null;
                                            }

                                            URL url = new URL(imageUrl); // URL ảnh bìa
                                            String sanitizedBookName = bookName.replaceAll("[^a-zA-Z0-9]", "_"); // Tạo tên file hợp lệ
                                            fileName = directoryPath + sanitizedBookName + ".jpg";

                                            InputStream in = url.openStream();
                                            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
                                            in.close();
                                            fileName = fileName.replace("src", "");
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Failed to download book image.", "Error", JOptionPane.ERROR_MESSAGE));
                                            return null;
                                        }

                                        // Tạo đối tượng Book và lưu vào database
                                        Book bookk = new Book(bookID, bookName, fileName, bookAuthor, bookCategory, bookLanguage, total, current, bookPosition);
                                        libraryModelManage.addBookToDatabase(bookk);
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        hideLoadingDialog();
                                        JOptionPane.showMessageDialog(null, "Book \"" + title + "\" has been added successfully!",
                                                "Book Added", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                };

                                addBookWorker.execute();
                            });


                            panel_2.add(btnAdd);

                            JButton btnSee = new JButton("See");
                            btnSee.setBackground(new Color(0, 0, 128));
                            btnSee.setForeground(Color.WHITE);
                            btnSee.addActionListener(e -> {
                                JFrame chiTietFrame = new JFrame("Chi Tiết");
                                chiTietFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                chiTietFrame.setSize(1000, 600);
                                chiTietFrame.setLocationRelativeTo(null);
                                ChiTiet chiTietPanel = new ChiTiet(title, author, language, category, imageUrl, infoLink);
                                chiTietFrame.getContentPane().add(chiTietPanel);
                                chiTietFrame.setVisible(true);
                            });
                            panel_2.add(btnSee);

                            JMenuItem mntmNewMenuItem = new JMenuItem(displayedTitle);
                            panel_1.add(mntmNewMenuItem, BorderLayout.CENTER);
                            mntmNewMenuItem.setBackground(Color.WHITE);
                            mntmNewMenuItem.setFont(new Font("Tahoma", Font.BOLD, 20));

                            panel.add(panel_1);
                        }

                        panel.revalidate();
                        panel.repaint();
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(ApiView.this,
                            "An error occurred while searching. Please try again.",
                            "Error", JOptionPane.ERROR_MESSAGE));
                }
                return null;
            }

            @Override
            protected void done() {
                hideLoadingDialog();
            }
        };

        searchWorker.execute();
    }

    private boolean isInternetAvailable() {
        try {
            URL url = new URL("http://www.google.com");
            InputStream inputStream = url.openStream();
            inputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public String getSearchQuery() {
        return textField.getText().trim();
    }

    private JDialog loadingDialog;
    private SwingWorker<Void, Void> searchWorker;
    private JProgressBar progressBar; // Thanh tiến trình

    private void showLoadingDialog() {
        loadingDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Loading...", true);
        loadingDialog.setUndecorated(true);
        loadingDialog.setSize(250, 60);
        loadingDialog.setBackground(new Color(255, 255, 255));
        loadingDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JLabel label = new JLabel("Loading...", JLabel.CENTER);
        label.setBackground(new Color(255, 255, 255));
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(label);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(new Color(255, 255, 255));
        progressBar.setPreferredSize(new Dimension(20, 20));
        panel.add(progressBar);

        loadingDialog.add(panel);

        loadingDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (searchWorker != null) {
                    searchWorker.cancel(true);
                }
            }
        });

        new Thread(() -> loadingDialog.setVisible(true)).start();
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
        }
    }




}