package ManageBook.view;

import MainApp.model.Book;
import MainApp.model.LibraryModelManage;
import ManageBook.controller.ManagementBookController;


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

                int[] columnWidths = {85, 250, 120, 110, 110, 100, 95, 95, 90, 115};

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
        ImageIcon icon;
        if (path != null && getClass().getResource(path) != null) {
            icon = new ImageIcon(getClass().getResource(path));
        } else {
            System.out.println("Image not found at path: " + path);
            icon = new ImageIcon(new BufferedImage(05, 05, BufferedImage.TYPE_INT_ARGB)); // Placeholder
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


        editButton.setToolTipText("Edit Book");
        deleteButton.setToolTipText("Delete Book");
        imageButton.setToolTipText("Upload Cover");

        editButton.addActionListener(e -> {
            toggleEditButtonIcon(actionPanel,editButton,deleteButton,imageButton, row);
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

        // Thêm các nút vào bảng điều khiển (actionPanel)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 7, 0, 7);
        gbc.anchor = GridBagConstraints.CENTER;

        actionPanel.add(editButton, gbc);
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
                Book bookToEdit = getUpdatedBookFromRow(row) ; // Get the selected book
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
        chooseImage(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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

        bookTableView.revalidate();
        bookTableView.repaint();
    }

    private Book getUpdatedBookFromRow(int row) {
        DefaultTableModel model = (DefaultTableModel) bookTableView.getTable().getModel();

        String bookID = model.getValueAt(row, 0).toString();
        String bookName = model.getValueAt(row, 1).toString();

        // Trích xuất đường dẫn từ JLabel của ảnh
        Object imageObject = model.getValueAt(row, 2);
        String image = "";
        if (imageObject instanceof JLabel) {
            JLabel imageLabel = (JLabel) imageObject;
            ImageIcon icon = (ImageIcon) imageLabel.getIcon();
            image = icon.getDescription(); // Nếu ImageIcon có description là đường dẫn hình ảnh
        } else {
            image = imageObject.toString();
        }

        String author = model.getValueAt(row, 3).toString();
        String category = model.getValueAt(row, 4).toString();
        String language = model.getValueAt(row, 5).toString();
        int total = Integer.parseInt(model.getValueAt(row, 6).toString());
        String curent = model.getValueAt(row, 7).toString();
        String position = model.getValueAt(row, 8).toString();

        return new Book(bookID, bookName, image, author, category, language, total, curent, position);
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
}