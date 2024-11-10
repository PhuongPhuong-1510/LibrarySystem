package ManageBook.controller;

import ManageBook.view.AddBook;
import ManageBook.view.EditBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EditBookController implements ActionListener, MouseListener {
    private final EditBook editBook;
    private final EditBookListener listener;

    public EditBookController(EditBook editBook, EditBookListener listener) {
        this.editBook = editBook;
        this.listener = listener;
        initializeListeners();
    }

    private void initializeListeners() {
        editBook.getCancelButton().addActionListener(this);
        editBook.getSubmitButton().addActionListener(this);
        editBook.getUploadCoverButton().addActionListener(this);

        // Add mouse listeners for hover effect
        editBook.getUploadCoverButton().addMouseListener(this);
        editBook.getSubmitButton().addMouseListener(this);
        editBook.getCancelButton().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "UPLOAD COVER":
                System.out.println("Upload cover button clicked");
                editBook.chooseImage(e);
                String path = editBook.getImagePath();
                System.out.println("Image path: " + path);
                break;
            case "CANCLE":
                System.out.println("Cancel button clicked");
                editBook.setVisible(false);
                break;
            case "SUBMIT":
                System.out.println("Submit button clicked");
                listener.onBookEdit();
                editBook.revalidate();
                editBook.repaint();
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == editBook.getSubmitButton()) {
            styleButtonHover(editBook.getSubmitButton(), Font.BOLD, 16);
        } else if (e.getSource() == editBook.getCancelButton()) {
            styleButtonHover(editBook.getCancelButton(), Font.BOLD, 16);
        } else if (e.getSource() == editBook.getUploadCoverButton()) {
            styleButtonHover(editBook.getUploadCoverButton(), Font.BOLD, 16);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Đặt lại kiểu nút khi chuột di ra ngoài
        if (e.getSource() == editBook.getSubmitButton()) {
            styleButtonDefault(editBook.getSubmitButton());
        } else if (e.getSource() == editBook.getCancelButton()) {
            styleButtonDefault(editBook.getCancelButton());
        } else if (e.getSource() == editBook.getUploadCoverButton()) {
            styleButtonDefault(editBook.getUploadCoverButton());
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
