package ManageBook.controller;

import ManageBook.view.ManagementBookView;
import ManageBook.view.AddBook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementBookController implements ActionListener, AddBookListener{

    private ManagementBookView managementBookView;
    private AddBook addBook;

    public ManagementBookController(ManagementBookView managementBookView) {
        this.managementBookView = managementBookView;
        this.initializeListeners();
    }

    private void initializeListeners() {
        managementBookView.getAddBookButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == managementBookView.getAddBookButton()) {
            System.out.println("Add Book button clicked!");
            addBook = new AddBook(); // Tạo mới mỗi lần nhấn nút
            AddBookController addBookController = new AddBookController(addBook, this); // Truyền vào thể hiện mới
            addBook.setVisible(true); // Hiển thị thể hiện mới
        }
    }

    @Override
    public void onBookAdded() {
        System.out.println("A new book has been added!");
        managementBookView.addBook(addBook.getBookFromPanel());
        // Update managementBookView here, e.g., refresh the book list
    }

    @Override
    public void onBookCancelled() {
        System.out.println("Add book action was cancelled");
        // Any action needed for cancellation can go here
    }

}

