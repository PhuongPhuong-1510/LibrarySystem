package ManageBook.controller;

import ManageBook.view.AddBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddBookController implements ActionListener, MouseListener {
    private final AddBook addBook;

    public AddBookController(AddBook addBook) {
        this.addBook = addBook;
        initializeListeners();
    }

    private void initializeListeners() {
        addBook.getCancelButton().addActionListener(this);
        addBook.getSubmitButton().addActionListener(this);
        addBook.getUploadCoverButton().addActionListener(this);

        // Add mouse listeners for hover effect
        addBook.getUploadCoverButton().addMouseListener(this);
        addBook.getSubmitButton().addMouseListener(this);
        addBook.getCancelButton().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "UPLOAD COVER":
                System.out.println("Upload cover button clicked");
                addBook.chooseImage(e);
                String path = addBook.getImagePath();
                System.out.println("Image path: " + path);
                break;
            case "CANCLE":
                System.out.println("Cancel button clicked");
                addBook.setVisible(false);
                break;
            case "SUBMIT":
                System.out.println("Submit button clicked");
<<<<<<< HEAD
=======
                if(this.addBook.titleField.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter a title");
                }else{
                    this.addBook.getBookFromPanel();
                }
>>>>>>> 524ad9bea3a9e8c8443977595a9d6397da6e84ca
                addBook.revalidate();
                addBook.repaint();
                break;


            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == addBook.getSubmitButton()) {
            styleButtonHover(addBook.getSubmitButton(), Font.BOLD, 16);
        } else if (e.getSource() == addBook.getCancelButton()) {
            styleButtonHover(addBook.getCancelButton(), Font.BOLD, 16);
        } else if (e.getSource() == addBook.getUploadCoverButton()) {
            styleButtonHover(addBook.getUploadCoverButton(), Font.BOLD, 16);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Đặt lại kiểu nút khi chuột di ra ngoài
        if (e.getSource() == addBook.getSubmitButton()) {
            styleButtonDefault(addBook.getSubmitButton());
        } else if (e.getSource() == addBook.getCancelButton()) {
            styleButtonDefault(addBook.getCancelButton());
        } else if (e.getSource() == addBook.getUploadCoverButton()) {
            styleButtonDefault(addBook.getUploadCoverButton());
        }
    }

    private void styleButtonHover(JButton button, int fontStyle, int fontSize) {
        button.setBackground(new Color(255,193,193));
        button.setFont(new Font("Tahoma", fontStyle, fontSize));
        button.revalidate();
        button.repaint();
    }

    private void styleButtonDefault(JButton button) {
        // Đặt lại kiểu nút về mặc định
        button.setBackground(new Color(250, 128 ,114));
        button.setFont(new Font("Tahoma", Font.PLAIN, 16)); // Kiểu chữ mặc định
        button.revalidate();
        button.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}
