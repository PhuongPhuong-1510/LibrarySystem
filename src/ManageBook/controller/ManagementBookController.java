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
<<<<<<< HEAD

=======
//        managementBookView.getDeleteButton().addActionListener(this);
//        managementBookView.getEditButton().addActionListener(this);
>>>>>>> c291d5ec3511ea10eb575bda29e61e6638e5b65e
        managementBookView.getAddBookButton().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD

          if (e.getSource()==managementBookView.getAddBookButton()) {
=======
//        if (e.getSource() == managementBookView.getEditButton()) {
//            System.out.println("Edit button clicked!");
//        } else if (e.getSource() == managementBookView.getDeleteButton()) {
//            System.out.println("Delete button clicked!");
//        } else
            if (e.getSource()==managementBookView.getAddBookButton()) {
>>>>>>> c291d5ec3511ea10eb575bda29e61e6638e5b65e
            System.out.println("Add Book button clicked!");
              addBook = new AddBook();
              addBook.setVisible(true);
        }


    }

<<<<<<< HEAD


}
=======
    public void handleEditAction(int row) {
        System.out.println("Edit action triggered for row: " + row);
        // Perform edit logic here
    }

    public void handleDeleteAction(int row) {
        System.out.println("Delete action triggered for row: " + row);
        // Perform delete logic here
    }


}
>>>>>>> c291d5ec3511ea10eb575bda29e61e6638e5b65e
