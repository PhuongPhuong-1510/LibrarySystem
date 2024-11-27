package ManageStudent.controller;


import ManageStudent.view.AddStudentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddStudentController implements ActionListener, MouseListener {
    private final AddStudentView addStudentView;
    private final AddStudentListener addStudentListener;

    public AddStudentController(AddStudentView addStudentView, AddStudentListener addStudentListener) {
        this.addStudentView = addStudentView;
        this.addStudentListener = addStudentListener;

        initializeListeners();
    }
    private void initializeListeners() {

        addStudentView.getCancelButton().addActionListener(this);
        addStudentView.getSubmitButton().addActionListener(this);

        addStudentView.getSubmitButton().addMouseListener(this);
        addStudentView.getCancelButton().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "CANCEL":
                System.out.println("Cancel button clicked");
                addStudentView.setVisible(false);
                break;
            case "SUBMIT":

                if(addStudentView.validateInputFields()) {
                    addStudentView.setVisible(true);
                    addStudentListener.onStudentAdded();
                }
                addStudentView.revalidate();
                addStudentView.repaint();
                break;


            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == addStudentView.getSubmitButton()) {
            styleButtonHover(addStudentView.getSubmitButton(), new Color(255, 50, 0), 120, 40, Font.BOLD, 14);
        } else if (e.getSource() == addStudentView.getCancelButton()) {
            styleButtonHover(addStudentView.getCancelButton(), new Color(192, 192, 192), 120, 40, Font.BOLD, 14);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == addStudentView.getSubmitButton()) {
            styleButtonHover(addStudentView.getSubmitButton(), new Color(255, 0, 0), 100, 30, Font.PLAIN, 16);
        } else if (e.getSource() == addStudentView.getCancelButton()) {
            styleButtonHover(addStudentView.getCancelButton(), new Color(211, 211, 211), 100, 30, Font.PLAIN, 16);
        }
    }



    private void styleButtonHover(JButton button, Color background, int width, int height, int fontStyle, int fontSize) {
        button.setBackground(background);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font("Tahoma", fontStyle, fontSize));
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

