package ManageBook.controller;

import ManageBook.view.ManagementBookView;
import ManageBook.view.AddBook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementBookController implements ActionListener, AddBookListener {
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

        if (e.getSource()==managementBookView.getAddBookButton()) {
            System.out.println("Add Book button clicked!");
            addBook = new AddBook();
            AddBookController addBookController = new AddBookController(addBook, this);
            addBook.setVisible(true);
        }


    }


    @Override
    public void onBookAdded() {
        System.out.println("A new book has been added!");
        managementBookView.addBook(addBook.getBookFromPanel());
    }

    @Override
    public void onBookCancelled() {
        System.out.println("Add book action was cancelled");
        // Any action needed for cancellation can go here
    }
}