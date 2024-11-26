package ManageStudent.controller;

import ManageStudent.view.AddStudentView;
import ManageStudent.view.ManagementStudentView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentController implements ActionListener, AddStudentListener {
    private ManagementStudentView managementStudentView;
    private AddStudentView addStudentView;

    public StudentController(ManagementStudentView managementStudentView) {
        this.managementStudentView = managementStudentView;
        this.initializeListeners();
    }

    private void initializeListeners() {

        managementStudentView.getAddStudentButton().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==managementStudentView.getAddStudentButton()) {
            System.out.println("Add Student button clicked!");
            addStudentView = new AddStudentView(managementStudentView.libraryModelManage);
            AddStudentController addStudentController = new AddStudentController(addStudentView, this);
            addStudentView.setVisible(true);
        }


    }


    @Override
    public void onStudentAdded() {
        System.out.println("A new student has been added!");
        managementStudentView.addStudent(addStudentView.getStudentFromPanel());
        //addStudentView.libraryModelManage.addStudentToDatabase(addStudentView.getStudentFromPanel());
    }
}
