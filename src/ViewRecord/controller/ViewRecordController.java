package ViewRecord.controller;

import IssueBook.view.DatePickerDemo;
import IssueBook.view.IssueBookView;
import ViewRecord.view.ViewRecordView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

public class ViewRecordController implements ActionListener, MouseListener {
    private ViewRecordView viewRecordView;
    private LocalDate issueDate;
    private LocalDate dueDate;


    public ViewRecordController(ViewRecordView viewRecordView) {
        this.viewRecordView = viewRecordView;
        this.initializeListeners();
    }

    private void initializeListeners() {
        viewRecordView.getDueDateButton().addActionListener(this);
        viewRecordView.getIssueDateButton().addActionListener(this);

        viewRecordView.getAllRecordButton().addMouseListener(this);
        viewRecordView.getSearchButton().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewRecordView.getIssueDateButton()) {
            DatePickerDemo jDialogIssue = new DatePickerDemo();
            jDialogIssue.setBounds(965, 180, 400, 200);
            jDialogIssue.setModal(true);
            jDialogIssue.setVisible(true);

            issueDate = jDialogIssue.getSelectedDate();
            viewRecordView.getIssueDateField().setText(issueDate.toString());

        }

        if (e.getSource() == viewRecordView.getDueDateButton()) {
            DatePickerDemo jDialogDue = new DatePickerDemo();
            jDialogDue.setBounds(965, 230, 400, 200);
            jDialogDue.setModal(true);
            jDialogDue.setVisible(true);


            dueDate = jDialogDue.getSelectedDate();
            viewRecordView.getDueDateField().setText(dueDate.toString());



        }
    }






    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == viewRecordView.getSearchButton()) {
            styleButtonHover(viewRecordView.getSearchButton(), Font.PLAIN, 13);
        } else if (e.getSource() == viewRecordView.getAllRecordButton()) {
            styleButtonHover(viewRecordView.getAllRecordButton(), Font.PLAIN, 13);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() ==viewRecordView.getSearchButton()) {
            styleButtonDefault(viewRecordView.getSearchButton());
        } else if (e.getSource() == viewRecordView.getAllRecordButton()) {
            styleButtonDefault(viewRecordView.getAllRecordButton());
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
        button.setFont(new Font("Tahoma", Font.BOLD, 13));
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