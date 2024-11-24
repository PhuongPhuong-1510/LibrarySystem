package IssueBook.controller;

import IssueBook.view.DatePickerDemo;
import IssueBook.view.IssueBookView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

public class IssueBookController implements ActionListener, MouseListener {
    private IssueBookView issueBookView;
    private LocalDate issueDate;
    private LocalDate dueDate;


    public IssueBookController(IssueBookView issueBookView) {
        this.issueBookView = issueBookView;
        this.initializeListeners();
    }

    private void initializeListeners() {
        issueBookView.getIssueDateButton().addActionListener(this);
        issueBookView.getDueDateButton().addActionListener(this);

        issueBookView.getIssueButton().addMouseListener(this);
        issueBookView.getClearButton().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueBookView.getIssueDateButton()) {
            DatePickerDemo jDialogIssue = new DatePickerDemo();
            jDialogIssue.setBounds(965, 524, 400, 200);
            jDialogIssue.setModal(true);
            jDialogIssue.setVisible(true);

            issueDate = jDialogIssue.getSelectedDate();  // Lưu vào biến thành viên

            if (issueDate != null) {
                issueBookView.getIssueDateField().setText(issueDate.toString()); // Cập nhật ngày mượn sách
            }
        }

        if (e.getSource() == issueBookView.getDueDateButton()) {
            DatePickerDemo jDialogDue = new DatePickerDemo();
            jDialogDue.setBounds(965, 565, 400, 200);
            jDialogDue.setModal(true);
            jDialogDue.setVisible(true);

            dueDate = jDialogDue.getSelectedDate();  // Lưu vào biến thành viên

            if (dueDate != null) {
                if (isDueDateValid(issueDate, dueDate)) {
                    issueBookView.getDueDateField().setText(dueDate.toString());
                } else {
                    JOptionPane.showMessageDialog(issueBookView, "The due date cannot be earlier than the issue date.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean isDueDateValid(LocalDate issueDate, LocalDate dueDate) {
        return !dueDate.isBefore(issueDate);  // Trả về true nếu ngày trả không sớm hơn ngày mượn
    }




    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == issueBookView.getIssueButton()) {
            styleButtonHover(issueBookView.getIssueButton(), Font.PLAIN, 16);
        } else if (e.getSource() == issueBookView.getClearButton()) {
            styleButtonHover(issueBookView.getClearButton(), Font.PLAIN, 16);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() ==issueBookView.getIssueButton()) {
            styleButtonDefault(issueBookView.getIssueButton());
        } else if (e.getSource() == issueBookView.getClearButton()) {
            styleButtonDefault(issueBookView.getClearButton());
        }
    }

    private void styleButtonHover(JButton button, int fontStyle, int fontSize) {
        button.setBackground(new Color(255,69,0));
        button.setFont(new Font("Tahoma", fontStyle, fontSize));
        button.revalidate();
        button.repaint();
    }

    private void styleButtonDefault(JButton button) {
        button.setBackground(new Color(255,130,171));
        button.setFont(new Font("Tahoma", Font.BOLD, 16)); // Kiểu chữ mặc định
        button.revalidate();
        button.repaint();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == issueBookView.getIssueButton()) {
            issueBookView.issueBook(); // Thêm thông tin vào cơ sở dữ liệu
        } else if (e.getSource() == issueBookView.getClearButton()) {
            issueBookView.removeData();  // Xóa dữ liệu các trường nhập liệu
            JOptionPane.showMessageDialog(issueBookView, "All fields cleared.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}


