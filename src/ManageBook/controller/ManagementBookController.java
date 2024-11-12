package ManageBook.controller;

import ManageBook.view.ManagementBookView;
import ManageBook.view.AddBook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementBookController implements ActionListener {
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
            addBook.setVisible(true);
        }


    }



}