package ReturnBook.controller;


import IssueBook.view.DatePickerDemo;
import IssueBook.view.IssueBookView;
import ReturnBook.view.ReturnBookView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ReturnBookController implements ActionListener, MouseListener {
    private ReturnBookView returnBookView;
    private boolean isFindButtonClicked = false;



    public ReturnBookController(ReturnBookView returnBookView) {
        this.returnBookView = returnBookView;
        this.initializeListeners();
    }

    private void initializeListeners() {
        returnBookView.getReturnButton().addActionListener(this);
        returnBookView.getFindButton().addActionListener(this);

        returnBookView.getReturnButton().addMouseListener(this);
        returnBookView.getFindButton().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnBookView.getReturnButton()) {
            if (!isFindButtonClicked) {
                int confirm = JOptionPane.showConfirmDialog(
                        returnBookView,
                        "You have not updated issue information. Do you want to proceed?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.NO_OPTION) {
                    return; // Người dùng chọn không tiếp tục
                }
            }
            returnBookView.returnBook();
        }

        if (e.getSource() == returnBookView.getFindButton()) {
            returnBookView.updateIssue();
            isFindButtonClicked = true; // Đánh dấu rằng FindButton đã được bấm
        }
    }







    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == returnBookView.getFindButton()) {
            styleButtonHover(returnBookView.getFindButton(), Font.PLAIN, 16);
        } else if (e.getSource() == returnBookView.getReturnButton()) {
            styleButtonHover(returnBookView.getReturnButton(), Font.PLAIN, 16);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() ==returnBookView.getFindButton()) {
            styleButtonDefault(returnBookView.getFindButton());
        } else if (e.getSource() ==returnBookView.getReturnButton()) {
            styleButtonDefault(returnBookView.getReturnButton());
        }
    }

    private void styleButtonHover(JButton button, int fontStyle, int fontSize) {
        button.setBackground(new Color(229,70,70));
        button.setFont(new Font("Tahoma", fontStyle, fontSize));
        button.revalidate();
        button.repaint();
    }

    private void styleButtonDefault(JButton button) {
        button.setBackground((new Color(135, 206, 255)));
        button.setFont(new Font("Tahoma", Font.BOLD, 16)); // Kiểu chữ mặc định
        button.revalidate();
        button.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}


