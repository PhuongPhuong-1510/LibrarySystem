package API;

import HomePage.view.CustomScrollBarUI;
import LoginPage.view.OvalButton;
import MainApp.model.Book;
import MainApp.model.LibraryModelManage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class ApiView extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JPanel panel;
    private final JScrollPane scrollPane;
    private OvalTextField textField;
    private LibraryModelManage libraryModelManage;

    /**
     * Create the panel.
     */


    public ApiView(LibraryModelManage libraryModelManage) {
        ApiController apiController = new ApiController(this);
        setLayout(null);
        this.libraryModelManage = libraryModelManage;
        libraryModelManage.getBooksList();

        this.setBackground(new Color(251,228,244,255));


        JLabel  imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // Căn giữa ảnh
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/API/icon/bird (1).gif"))); // Đường dẫn ảnh
        imageLabel.setIcon(icon);
        imageLabel.setBackground(new Color( 0,0,0,0));
        imageLabel.setBounds(0, 200, 204, 204); // Đặt vị trí và kích thước ảnh
        this.add(imageLabel);

        JLabel backLabel = new JLabel();
        backLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon icon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/API/icon/google.png"))); // Đường dẫn ảnh
        backLabel.setIcon(icon1);
        backLabel.setBackground(new Color( 0,0,0,0));
        backLabel.setBounds(320, 10, 500, 250);
        this.add(backLabel);








        textField = new OvalTextField(50);
        textField.setPlaceholder("SEARCH BOOK ONLINE");
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBounds(250, 200, 750, 50);
        textField.addActionListener(apiController);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleSearch();
            }

            private void handleSearch() {
                String query = textField.getText().trim();

                if (query.isEmpty()) {
                    scrollPane.setVisible(false);
                    panel.removeAll(); // Xóa các thành phần trong panel
                    panel.revalidate(); // Cập nhật lại giao diện
                    panel.repaint();
                }
                else
                {
                    scrollPane.setVisible(true);

                }
            }
        });

        add(textField);
        textField.setColumns(10);




        panel = new JPanel();
        panel.setBackground(new Color(0,0,0,0));
        panel.setLayout(new GridLayout(50, 1, 10, 10));

        scrollPane = createScrollPane(panel);
        scrollPane.setBounds(250, 280, 750, 200);
        scrollPane.setBackground(new Color(251,228,244,255));

        scrollPane.setVisible(false);
        add(scrollPane);



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

                            final String description = getBookDescription(jsonResponse);


                            String displayedTitle = title.length() > 50 ? title.substring(0, 50) + "()" : title;

                            JPanel panel_1 = new JPanel(new BorderLayout(0, 0));
                            panel_1.setBackground(new Color(252,162,188,255));
                            panel_1.setPreferredSize(new Dimension(730, 40)); // Thiết lập kích thước ưu tiên


                            JPanel panel_2 = new JPanel(new GridLayout(1, 2, 10, 10));
                            panel_2.setBackground(Color.WHITE);
                            panel_1.add(panel_2, BorderLayout.EAST);

                            JButton btnAdd = new OvalButton("ADD");
                            btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            btnAdd.setBackground(new Color(169,206,251,255));
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
                                        String infolink = infoLink;
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
                                            System.out.println(fileName+"////////////////");
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Failed to download book image.", "Error", JOptionPane.ERROR_MESSAGE));
                                            return null;
                                        }

                                        // Tạo đối tượng Book và lưu vào database
                                        Book bookk = new Book(bookID, bookName, fileName, bookAuthor, bookCategory, bookLanguage, total, current, bookPosition, infolink);
                                        libraryModelManage.addImageToCache(fileName);
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

                            JButton btnSee = new OvalButton("SEE");
                            btnSee.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            btnSee.setBackground(new Color(230,181,200,255));
                            btnSee.setForeground(Color.WHITE);
                            btnSee.addActionListener(e -> {
                                showLoadingDialog();

                                SwingWorker<Void, Void> seeBookWorker = new SwingWorker<>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        Thread.sleep(500);
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        hideLoadingDialog();

                                        JFrame chiTietFrame = new JFrame();
                                        chiTietFrame.setUndecorated(true);
                                        chiTietFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                        chiTietFrame.setSize(600, 500);
                                        chiTietFrame.setLocationRelativeTo(null);

                                        ChiTiet chiTietPanel = new ChiTiet(chiTietFrame, title, author, language, category, imageUrl, infoLink, description);
                                        chiTietFrame.getContentPane().add(chiTietPanel);

                                        chiTietFrame.setVisible(true);

                                    }
                                };

                                seeBookWorker.execute();
                            });

                            panel_2.add(btnSee);

                            JMenuItem mntmNewMenuItem = new JMenuItem(displayedTitle);
                            panel_1.add(mntmNewMenuItem, BorderLayout.CENTER);
                            mntmNewMenuItem.setBackground(new Color(255,255,255));
                            mntmNewMenuItem.setForeground(new Color(05));
                            mntmNewMenuItem.setFont(new Font("Tahoma", Font.PLAIN, 18));

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
    public static String getBookDescription(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray items = jsonObject.getJSONArray("items");
            if (items.length() > 0) {
                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
                if (volumeInfo.has("description")) {
                    return volumeInfo.getString("description");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No description available.";
    }



    public String getSearchQuery() {
        return textField.getText().trim();
    }

    private JDialog loadingDialog;
    private SwingWorker<Void, Void> searchWorker;
    private JProgressBar progressBar; // Thanh tiến trình

    private void showLoadingDialog() {
        loadingDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Loading...", true);
        loadingDialog.setUndecorated(true); // Loại bỏ khung cửa sổ
        loadingDialog.setSize(250, 300); // Kích thước dialog
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setBackground(new Color(0, 0, 0, 0)); // ARGB: alpha = 0 (trong suốt)

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

            }
        };
        panel.setOpaque(false);
        panel.setLayout(null);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // Căn giữa ảnh
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/LMSNotification/view/icon/1.gif"))); // Đường dẫn ảnh
        imageLabel.setIcon(icon);
        imageLabel.setBounds(0, 0, 240, 240); // Đặt vị trí và kích thước ảnh
        panel.add(imageLabel); // Thêm ảnh vào panel

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(new Color(255, 255, 255));
        progressBar.setBounds(0,230,240,25);
        panel.add(progressBar);

        JButton cancelButton = createButton("X",200,0);
        cancelButton.addActionListener(e -> {
            if (searchWorker != null) {
                cancelButton.setBackground(Color.RED);
                searchWorker.cancel(true);
            }
            hideLoadingDialog();
        });
        panel.add(cancelButton);

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
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(150, 180, 255));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText("STOP");
        button.setBounds(x, y, 50, 50); // Size and position of the button
        return button;
    }

    public JScrollPane createScrollPane(JPanel table) {
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

}