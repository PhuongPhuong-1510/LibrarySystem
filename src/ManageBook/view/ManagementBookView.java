package ManageBook.view;

import API.ChiTietController;
import MainApp.model.Book;
import MainApp.model.LibraryModelManage;
import ManageBook.controller.ManagementBookController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ManagementBookView extends JPanel {
    private JPanel managementBooks;
    private JButton addBookButton;
    public LibraryModelManage libraryModelManage;
    private BaseBookTableView bookTableView;
    private BaseManagementPanel managementPanel;
    private int count=0;
    private int lastSelectedRow=-1;
    JButton lastSelectedEditButton;
    private JButton lastSelectedImageButton = null;



    public ManagementBookView(LibraryModelManage libraryModelManage) {
        this.libraryModelManage = libraryModelManage;
        this.setLayout(new BorderLayout());
        this.init();
        this.updateTable(libraryModelManage.getBooksList());
        new ManagementBookController(this);
    }

    private void init() {
        managementBooks = new JPanel(new BorderLayout());


        String[] columnNames = createColumnNames();
        Object[][] data = fetchData();  // Phương thức để lấy dữ liệu

        bookTableView = new BaseBookTableView(columnNames, data,9,180,2) {

            @Override
            protected void centerTableCells(JTable table) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    if (i != 2 && i != 9) {
                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    }
                }
            }

            @Override
            protected void configureColumnRenderers(JTable table) {
                table.setDefaultRenderer(Object.class, createMultiLineRenderer());
                table.getColumnModel().getColumn(1).setCellRenderer(createTooltipRenderer());
                table.getColumnModel().getColumn(2).setCellRenderer(createLabelRenderer());
                table.getColumnModel().getColumn(9).setCellRenderer(createPanelRenderer());
                table.getColumnModel().getColumn(9).setCellEditor(new PanelEditor());

            }
            @Override
            protected void setTableColumnWidths(JTable table) {
                TableColumn column;

                int[] columnWidths = {85, 250, 120, 110, 110, 100, 95, 95, 90, 135};

                for (int i = 0; i < table.getColumnCount(); i++) {
                    column = table.getColumnModel().getColumn(i);
                    column.setPreferredWidth(columnWidths[i]);
                }
            }
        };

        managementPanel = new BaseManagementPanel("Search id, author, genre, title", "/ManageBook/icon/bookAdd.png", "Add Book", this, true) {
            @Override
            protected JPanel createSearchPanel() {
                return super.createSearchPanel();
            }

            @Override
            protected JButton createAddBookButton() {
                return super.createAddBookButton();
            }

        };
        addBookButton = managementPanel.getAddBookButton();



        managementBooks.add(bookTableView, BorderLayout.CENTER);
        managementBooks.add(managementPanel, BorderLayout.NORTH);

        this.add(managementBooks, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private String[] createColumnNames() {
        return new String[]{"Book ID",
                "Name Book",
                "Image",
                "Author",
                "Category",
                "Language",
                "Total",
                "Current",
                "Position",
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
            data[i][6] = book.getTotal();
            data[i][7] = book.getCurent();
            data[i][8] = book.getPosition();
            data[i][9] = createAction(i);
        }

        return data;
    }


    private JLabel createImageLabel(String path) {
        String relativePath = getRelativeImagePath(path);
        ImageIcon icon;

        if (relativePath != null && getClass().getResource(relativePath) != null) {
            icon = new ImageIcon(getClass().getResource(relativePath));
            icon.setDescription(relativePath);
        } else {
            System.out.println("Image not found at path: " + path);
            icon = new ImageIcon(new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB)); // Placeholder ảnh trống
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

    public JPanel createAction(int row) {
        JPanel actionPanel = new JPanel(new GridBagLayout());
        actionPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton editButton = createActionButton("/ManageBook/icon/bookEdit.png", new Color(255, 240, 245));
        JButton deleteButton = createActionButton("/ManageBook/icon/bookDelete.png", new Color(255, 240, 245));
        JButton imageButton = createActionButton("/ManageBook/icon/uploadImage.png", new Color(255, 240, 245));
        JButton QRcodeButton = createActionButton("/ManageBook/icon/qr1.png", new Color(255, 240, 245));

        editButton.setToolTipText("Edit Book");
        deleteButton.setToolTipText("Delete Book");
        imageButton.setToolTipText("Upload Cover");
        QRcodeButton.setToolTipText("QR Code");

        editButton.addActionListener(e -> {
            toggleEditButtonIcon(actionPanel, editButton, deleteButton, imageButton, row);
        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this book?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                JTable table = bookTableView.getTable();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                if (row >= 0 && row < model.getRowCount()) {
                    model.removeRow(row);
                    Book bookToRemove = libraryModelManage.getBooksList().get(row);
                    libraryModelManage.deleteBookFromDatabase(bookToRemove.getBookID());
                    updateTable(libraryModelManage.getBooksList());
                }
                if (model.getRowCount() == 0) {
                    table.clearSelection();
                }
            }
        });

        imageButton.addActionListener(e -> {
            toggleImageButton(deleteButton, editButton, row);
        });

        QRcodeButton.addActionListener(e -> {
            JTable table = bookTableView.getTable();
            String bookID = table.getValueAt(row, 0).toString();  // Assuming bookID is in the first column
            Book book = libraryModelManage.searchBookByID(bookID);
            String URL = book.getURL();



            JFrame qrCodeFrame = new JFrame("Book ID");
            qrCodeFrame.setSize(360, 400);
            qrCodeFrame.setUndecorated(true); // Loại bỏ viền cửa sổ
            qrCodeFrame.setBackground(new Color(0, 0, 0, 0)); // Làm trong suốt toàn bộ JFrame
            qrCodeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            qrCodeFrame.setLocationRelativeTo(null); // Đặt giữa màn hình

            JPanel qrCodePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Không vẽ nền gì thêm để đảm bảo trong suốt
                }
            };
            qrCodePanel.setOpaque(false);
            qrCodePanel.setLayout(null);

            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setLayout(null);
            layeredPane.setSize(360, 400);

            JPanel backgroundPanel = createBackgroundPanel("/ManageBook/icon/qrbg.png"); // Hàm tạo JPanel với ảnh nền
            backgroundPanel.setOpaque(false);
            backgroundPanel.setBounds(0, 0, 360, 360);

            layeredPane.add(backgroundPanel, Integer.valueOf(0));

            try {
                BufferedImage qrCodeImage = generateQRCodeImage(URL, 220, 220); // Tạo ảnh QR code
                JLabel lblQRCode = new JLabel(new ImageIcon(qrCodeImage));
                lblQRCode.setBounds(72, 98, 220, 220); // Đặt vị trí và kích thước
                qrCodePanel.add(lblQRCode);
            } catch (Exception k) {
                JLabel lblQRCode = new JLabel("QR Code Error");
                lblQRCode.setForeground(Color.RED);
                qrCodePanel.add(lblQRCode);
                k.printStackTrace();
            }

            qrCodePanel.setBounds(0, 0, 360, 400);
            layeredPane.add(qrCodePanel, Integer.valueOf(1));

            JButton closeButton = createButton("X", 310, 7);
            qrCodePanel.add(closeButton);
            closeButton.addActionListener(e1 -> qrCodeFrame.dispose());



            qrCodeFrame.add(layeredPane);
            qrCodeFrame.setVisible(true);

        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 7, 0, 7);
        gbc.anchor = GridBagConstraints.CENTER;

        actionPanel.add(editButton, gbc);
        gbc.gridx = 1;
        actionPanel.add(QRcodeButton, gbc);
        gbc.gridx = 2;
        actionPanel.add(deleteButton, gbc);

        return actionPanel;
    }






    private void toggleEditButtonIcon(JPanel actionPanel,JButton editButton,JButton deleteButton,JButton imageButton, int row) {
        if (row != lastSelectedRow) {
            if (lastSelectedEditButton != null) {
                lastSelectedEditButton.setIcon(new ImageIcon(getClass().getResource("/ManageBook/icon/bookEdit.png")));

                actionPanel.remove(imageButton);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 7, 0, 7);
                actionPanel.add(deleteButton, gbc);

            }

            lastSelectedRow = row;
            lastSelectedEditButton = editButton;
            count = 1;
        } else {
            count++;
        }

        if (count % 2 == 1) {
            actionPanel.remove(deleteButton);
            editButton.setToolTipText("Please Save! ");
            editButton.setIcon(new ImageIcon(getClass().getResource("/ManageBook/icon/completeBook.png")));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 7, 0, 7);
            actionPanel.add(imageButton, gbc);
            editButton.addActionListener(e -> {
                System.out.println("Saved");
                Book bookToEdit = getUpdatedBookFromRow(row) ;
                libraryModelManage.editBookInDatabase(bookToEdit);

            });
        } else {
            editButton.setIcon(new ImageIcon(getClass().getResource("/ManageBook/icon/bookEdit.png")));

            actionPanel.remove(imageButton);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 7, 0, 7);
            actionPanel.add(deleteButton, gbc);

        }

        bookTableView.setSelectedRow(row);
        editButton.repaint();
    }

    private void toggleImageButton(JButton deleteButton, JButton editButton, int row) {
        FileDialog fileDialog = new FileDialog((Frame) SwingUtilities.getWindowAncestor(this), "Choose Image", FileDialog.LOAD);
        fileDialog.setFile("*.jpg;*.jpeg;*.png;*.gif");
        fileDialog.setVisible(true);

        String filePath = fileDialog.getDirectory() + fileDialog.getFile();
        if (filePath != null && !filePath.isEmpty()) {
            // Lấy đường dẫn tương đối
            String relativePath = getRelativeImagePath(filePath);

            if (relativePath != null) {
                System.out.println("Selected relative path: " + relativePath);
                updateImageForRow(row, relativePath);
            } else {
                System.out.println("Invalid file path selected: " + filePath);
            }
        }
    }

    private void updateImageForRow(int row, String relativePath) {
        DefaultTableModel model = (DefaultTableModel) bookTableView.getTable().getModel();
        JLabel newImageLabel = createImageLabel(relativePath);
        model.setValueAt(newImageLabel, row, 2);
        bookTableView.revalidate();
        bookTableView.repaint();
    }




    public void chooseImage(ActionEvent e) {
        FileDialog fileDialog = new FileDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn ảnh", FileDialog.LOAD);
        fileDialog.setFile("*.jpg;*.jpeg;*.png;*.gif");
        fileDialog.setVisible(true);

        String filePath = fileDialog.getDirectory() + fileDialog.getFile();
        if (filePath != null && !filePath.isEmpty()) {
            displayImage(filePath);
        }
    }

    private void displayImage(String filePath) {
        ImageIcon imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Điều chỉnh kích thước ảnh hiển thị
        JLabel coverLabel = new JLabel(new ImageIcon(image));
        JOptionPane.showMessageDialog(this, coverLabel, "Image Preview", JOptionPane.PLAIN_MESSAGE);
    }


    public JButton getAddBookButton() {
        return addBookButton;
    }

    public void addBook(Book book) {
        DefaultTableModel model = (DefaultTableModel) bookTableView.getTable().getModel();
        String imagePath = getRelativeImagePath(book.getImage());
        if (imagePath == null) {
            imagePath = "/ManageBook/icon/default.png";
        }

        Object[] rowData = new Object[]{
                book.getBookID(),
                book.getBookName(),
                createImageLabel(imagePath),
                book.getAuthor(),
                book.getCategory(),
                book.getLanguage(),
                book.getTotal(),
                book.getCurent(),
                book.getPosition(),
                createAction(model.getRowCount())
        };

        model.addRow(rowData);

        bookTableView.revalidate();
        bookTableView.repaint();
    }

    private String getRelativeImagePath(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }
        String normalizedPath = imagePath.replace("\\", "/");

        String target = "/ManageBook/icon/";
        int relativePathIndex = normalizedPath.indexOf(target);
        if (relativePathIndex != -1) {
            return normalizedPath.substring(relativePathIndex);
        }

        return null;
    }



    private Book getUpdatedBookFromRow(int row) {
        DefaultTableModel model = (DefaultTableModel) bookTableView.getTable().getModel();

        String bookID = model.getValueAt(row, 0).toString();
        String bookName = model.getValueAt(row, 1).toString();

        Object imageObject = model.getValueAt(row, 2);
        String image = "";
        if (imageObject instanceof JLabel) {
            JLabel imageLabel = (JLabel) imageObject;
            ImageIcon icon = (ImageIcon) imageLabel.getIcon();
            if (icon != null && icon.getDescription() != null) {
                image = icon.getDescription();
                int relativePathIndex = image.indexOf("/ManageBook/icon/");
                if (relativePathIndex != -1) {
                    image = image.substring(relativePathIndex);
                }
            }
        }

        String author = model.getValueAt(row, 3).toString();
        String category = model.getValueAt(row, 4).toString();
        String language = model.getValueAt(row, 5).toString();
        int total = Integer.parseInt(model.getValueAt(row, 6).toString());
        String curent = model.getValueAt(row, 7).toString();
        String position = model.getValueAt(row, 8).toString();

        Book book = libraryModelManage.searchBookByID(bookID);
        String URL = book.getURL();

        return new Book(bookID, bookName, image, author, category, language, total, curent, position, URL);
    }


    public void updateTable(ArrayList<Book> books) {
        DefaultTableModel model = (DefaultTableModel) bookTableView.getTable().getModel();
        model.setRowCount(0);  // Xóa toàn bộ dữ liệu cũ

        for (Book book : books) {
            Object[] rowData = new Object[]{
                    book.getBookID(),
                    book.getBookName(),
                    createImageLabel(book.getImage()),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getLanguage(),
                    book.getTotal(),
                    book.getCurent(),
                    book.getPosition(),
                    createAction(model.getRowCount())
            };
            model.addRow(rowData);
        }

        bookTableView.revalidate();
        bookTableView.repaint();
    }
    private JPanel createBackgroundPanel(String path) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(path));
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

    }
    private void configurePanel(JPanel panel, int x, int y, int width, int height) {
        panel.setBounds(x, y, width, height);
    }
    private BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        int[] enclosingRectangle = findEnclosingRectangle(bitMatrix);

        int x = enclosingRectangle[0];
        int y = enclosingRectangle[1];
        int qrWidth = enclosingRectangle[2];
        int qrHeight = enclosingRectangle[3];

        BitMatrix croppedMatrix = cropBitMatrix(bitMatrix, x, y, qrWidth, qrHeight);

        BufferedImage image = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_RGB);
        int greenColor = new Color(84, 255, 159).getRGB();
        int whiteColor = Color.WHITE.getRGB();

        for (int i = 0; i < qrWidth; i++) {
            for (int j = 0; j < qrHeight; j++) {
                // Sử dụng mã màu
                image.setRGB(i, j, croppedMatrix.get(i, j) ? greenColor : whiteColor);
            }
        }

        return image;
    }


    private BitMatrix cropBitMatrix(BitMatrix bitMatrix, int x, int y, int width, int height) {
        BitMatrix croppedMatrix = new BitMatrix(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(x + i, y + j)) {
                    croppedMatrix.set(i, j);
                }
            }
        }
        return croppedMatrix;
    }

    // Hàm tìm vùng bao quanh nội dung QR Code
    private int[] findEnclosingRectangle(BitMatrix bitMatrix) {
        int top = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        int bottom = Integer.MIN_VALUE;
        int right = Integer.MIN_VALUE;

        for (int y = 0; y < bitMatrix.getHeight(); y++) {
            for (int x = 0; x < bitMatrix.getWidth(); x++) {
                if (bitMatrix.get(x, y)) {
                    if (x < left) left = x;
                    if (y < top) top = y;
                    if (x > right) right = x;
                    if (y > bottom) bottom = y;
                }
            }
        }

        return new int[]{left, top, right - left + 1, bottom - top + 1};
    }
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(84, 255, 159));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText("CLOSE");
        button.setBounds(x, y, 50, 50); // Size and position of the button
        return button;
    }

}