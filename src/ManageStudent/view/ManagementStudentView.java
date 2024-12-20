package ManageStudent.view;


import MainApp.model.LibraryModelManage;
import MainApp.model.Student;
import ManageBook.view.BaseBookTableView;
import ManageBook.view.BaseManagementPanel;
import ManageBook.view.PanelEditor;
import ManageStudent.controller.StudentController;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ManagementStudentView extends JPanel {
    private JPanel managementStudents;
    private JButton addStudentButton;
    private BaseBookTableView studentTableView;
    private BaseManagementPanel managementPanel;
    private int lastSelectedRow = -1;
    private int count=0;
    private JButton lastSelectedEditButton;
    public LibraryModelManage libraryModelManage;
    private ArrayList<Student> filteredStudents = null;





    public ManagementStudentView(LibraryModelManage libraryModelManage) {
        this.libraryModelManage = libraryModelManage;
        this.setLayout(new BorderLayout());
        this.init();
        new StudentController(this);
    }

    private void init() {
        managementStudents = new JPanel(new BorderLayout());


        String[] columnNames = createColumnNames();
        Object[][] data = fetchData();  // Phương thức để lấy dữ liệu

        studentTableView = new BaseBookTableView(columnNames, data,9,120,4, "student") {

            @Override
            protected void centerTableCells(JTable table) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    if (i != 4 && i != 9) {
                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    }
                }
            }

            @Override
            protected void configureColumnRenderers(JTable table) {
                table.setDefaultRenderer(Object.class, createMultiLineRenderer());
                table.getColumnModel().getColumn(1).setCellRenderer(createTooltipRenderer());
                table.getColumnModel().getColumn(4).setCellRenderer(createLabelRenderer());
                table.getColumnModel().getColumn(9).setCellRenderer(createPanelRenderer());
                table.getColumnModel().getColumn(9).setCellEditor(new PanelEditor());

            }

            @Override
            protected void setTableColumnWidths(JTable table) {
                TableColumn column;

                int[] columnWidths = {100, 150, 90,120,100, 130, 150, 150, 80, 100};

                for (int i = 0; i < table.getColumnCount(); i++) {
                    column = table.getColumnModel().getColumn(i);
                    column.setPreferredWidth(columnWidths[i]);
                }
            }

        };
        managementPanel=new BaseManagementPanel("Search id, name, phone, email","/ManageStudent/view/icon/studentAdd.png","Add Student", this, false) {
            @Override
            protected JPanel createSearchPanel() {
                return super.createSearchPanel();
            }

            @Override
            protected JButton createAddBookButton() {
                return super.createAddBookButton();
            }

        };
        addStudentButton = managementPanel.getAddBookButton();





        managementStudents.add(studentTableView, BorderLayout.CENTER);
        managementStudents.add(managementPanel, BorderLayout.NORTH);

        this.add(managementStudents, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private String[] createColumnNames() {
        return new String[]{
                "Student ID",
                "Name Student",
                "Gender", // Chuyển cột Gender lên trước Date of Birth
                "Date of Birth", // Cột Ngày sinh
                "Card Photo",
                "Phone Number",
                "Email",
                "Major",
                "Branch",
                "Action"
        };
    }

    private Object[][] fetchData() {
        List<Student> students = libraryModelManage.getStudentsList(); // Lấy danh sách sinh viên từ model
        Object[][] data = new Object[students.size()][]; // Khởi tạo mảng 2 chiều để lưu dữ liệu

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            boolean ktGender = student.getGender();
            String gender;// Lấy giới tính để sử dụng cho ảnh
            if(ktGender){
                gender = "Male";
            }else{
                gender = "Female";
            }
            data[i] = new Object[]{
                    student.getID(),                // Student ID
                    student.getName(),              // Name Student
                    gender,                         // Gender
                    student.getDateOfBirth(),       // Date of Birth
                    createImageLabel(gender),       // Card Photo (ảnh giới tính)
                    student.getPhone(),       // Phone Number
                    student.getEmail(),             // Email
                    student.getMajor(),             // Major
                    student.getBranch(),            // Branch
                    createAction(i)                 // Action buttons
            };
        }

        return data;
    }

    public JLabel createImageLabel(String gender) {
        String imagePath = gender.equalsIgnoreCase("Female")
                ? "/ManageStudent/view/icon/girlicon.png"
                : "/ManageStudent/view/icon/boyicon.png";

        ImageIcon icon = (getClass().getResource(imagePath) != null)
                ? new ImageIcon(getClass().getResource(imagePath))
                : new ImageIcon(new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB)); // Ảnh placeholder nếu không tìm thấy

        return new JLabel(icon);
    }





    public JButton createActionButton(String iconPath) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(iconPath)));

        button.setBackground(null);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(null);
        button.setPreferredSize(new Dimension(35, 35));


        return button;
    }

    public JPanel createAction(int row) {
        JPanel actionPanel = new JPanel(new GridBagLayout());
        actionPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton editButton = createActionButton("/ManageStudent/view/icon/studentEdit.png");
        JButton deleteButton = createActionButton("/ManageStudent/view/icon/studentDelete.png");


        editButton.setToolTipText("Edit Student");
        deleteButton.setToolTipText("Delete Student");





        editButton.addActionListener(e -> {
            toggleEditButtonIcon(editButton,deleteButton, row);

        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this student?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                JTable table = studentTableView.getTable();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                if (row >= 0 && row < model.getRowCount()) {
                    Student studentToRemove = libraryModelManage.getStudentsList().get(row);
                    if(libraryModelManage.searchReserveByStudentID(studentToRemove.getID())==null
                        && libraryModelManage.searchIssueByStudentID(studentToRemove.getID())==null){
                         model.removeRow(row);
                         libraryModelManage.deleteStudentFromDatabase(studentToRemove.getID());
                         updateStudentTable(libraryModelManage.getStudentsList());
                    }else{
                        JOptionPane.showMessageDialog(
                                this, // Tham chiếu đến JFrame hoặc JPanel hiện tại
                                "Sinh viên đang mượn hoặc đăng chờ duyệt sách",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
                if (model.getRowCount() == 0) {
                    table.clearSelection();
                }
            }

        });




        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 7, 0, 7);
        gbc.anchor = GridBagConstraints.CENTER;

        actionPanel.add(editButton, gbc);
        gbc.gridx = 2;
        actionPanel.add(deleteButton, gbc);


        return actionPanel;
    }

    private void toggleImageButton(JButton deleteButton, JButton editButton, int row) {
    }

    private void toggleEditButtonIcon(JButton editButton,JButton deleteButton, int row) {
        if (row != lastSelectedRow) {
            if (lastSelectedEditButton != null) {
                lastSelectedEditButton.setIcon(new ImageIcon(getClass().getResource("/ManageStudent/view/icon/studentEdit.png")));
            }

            lastSelectedRow = row;
            lastSelectedEditButton = editButton;
            count = 1;
        } else {
            count++;
        }

        if (count % 2 == 1) {
            editButton.setToolTipText("Please Save! ");
            editButton.setIcon(new ImageIcon(getClass().getResource("/ManageStudent/view/icon/completeStudent.png")));
            editButton.addActionListener(e -> {
                System.out.println("Saved");
                Student studentToEdit = getUpdatedStudentFromRow(row) ;// Get the selected book
                if (studentToEdit != null) {

                    libraryModelManage.editStudentInDatabase(studentToEdit);
                    updateCardPhoto(row, studentToEdit.getGender());
                } else {
                    editButton.setIcon(new ImageIcon(getClass().getResource("/ManageStudent/view/icon/completeStudent.png")));
                    JOptionPane.showMessageDialog(
                            this, // Tham chiếu đến JFrame hoặc JPanel hiện tại
                            "Invalid gender input. Please enter only 'Male' or 'Female'.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }


            });
        } else {
            editButton.setIcon(new ImageIcon(getClass().getResource("/ManageStudent/view/icon/studentEdit.png")));
        }

        studentTableView.setSelectedRow(row);
        editButton.repaint();
    }

    private void updateCardPhoto(int row, boolean gender) {
        String imagePath = gender
                ? "/ManageStudent/view/icon/boyicon.png"
                : "/ManageStudent/view/icon/girlicon.png";

        DefaultTableModel model = (DefaultTableModel) studentTableView.getTable().getModel();
        JLabel updatedLabel = new JLabel(new ImageIcon(getClass().getResource(imagePath)));
        model.setValueAt(updatedLabel, row, 4);

        studentTableView.getTable().repaint();
    }


    private Student getUpdatedStudentFromRow(int row) {
        DefaultTableModel model = (DefaultTableModel) studentTableView.getTable().getModel();

        String studentID = model.getValueAt(row, 0).toString();
        String studentName = model.getValueAt(row, 1).toString();
        String genderStr = model.getValueAt(row, 2).toString();
        Student student = libraryModelManage.searchStudentByID(studentID);
        boolean gender = true;
        if( "Female".equals(genderStr)){
            gender = false;
        }else if( "Male".equals(genderStr)){
            gender = true;
        }else{
            return null;
        }
        String dateOfBirth = model.getValueAt(row, 3).toString();

        String cardPhoto = gender ? "/ManageStudent/view/icon/girlicon.png" : "/ManageStudent/view/icon/boyicon.png";

        String phoneNumber = model.getValueAt(row, 5).toString();
        String email = model.getValueAt(row, 6).toString();
        String major = model.getValueAt(row, 7).toString();
        String branch = model.getValueAt(row, 8).toString();

        return new Student(studentID, studentName, email, student.getPassword(), phoneNumber, gender, cardPhoto, dateOfBirth, major, branch);
    }

    public void updateStudentTable(List<Student> students) {
        DefaultTableModel model = (DefaultTableModel) studentTableView.getTable().getModel();
        model.setRowCount(0);

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            boolean gender = student.getGender(); // true = Male, false = Female

            Object[] rowData = new Object[]{
                    student.getID(),                       // Student ID
                    student.getName(),                     // Name
                    gender ? "Male" : "Female",            // Gender
                    student.getDateOfBirth(),              // Date of Birth
                    createImageLabel(gender ? "Male" : "Female"), // Card Photo (icon JLabel)
                    student.getPhone(),                    // Phone Number
                    student.getEmail(),                    // Email
                    student.getMajor(),                    // Major
                    student.getBranch(),                   // Branch
                    createAction(i)                        // Action buttons (Edit/Delete)
            };

            model.addRow(rowData);
        }


        // Đảm bảo bảng được vẽ lại sau khi cập nhật
        studentTableView.revalidate();
        studentTableView.repaint();
    }


    public void addStudent(Student student) {
        DefaultTableModel model = (DefaultTableModel) studentTableView.getTable().getModel();

        Object[] rowData = new Object[]{
                student.getID(),
                student.getName(),
                student.getGender() ? "Male" : "Female",
                student.getDateOfBirth(),
                createImageLabel(student.getGender() ? "Male" : "Female"),
                student.getPhone(),
                student.getEmail(),
                student.getMajor(),
                student.getBranch(),
                createAction(model.getRowCount())
        };

        model.addRow(rowData);

        studentTableView.revalidate();
        studentTableView.repaint();
    }

    private void filterTable(String query) {
        if (query.isEmpty()) {
            filteredStudents = null;
            updateStudentTable(libraryModelManage.getStudentsList());
        } else {
            filteredStudents = new ArrayList<>();
            for (Student student : libraryModelManage.getStudentsList()) {
                if (student.getID().toLowerCase().contains(query.toLowerCase())
                        || student.getName().toLowerCase().contains(query.toLowerCase())
                        || student.getPhone().toLowerCase().contains(query.toLowerCase())
                        || student.getEmail().toLowerCase().contains(query.toLowerCase())) {
                    filteredStudents.add(student);
                }
            }
            updateStudentTable(filteredStudents);
        }
    }

    private void restoreTable() {
        updateStudentTable(libraryModelManage.getStudentsList());
    }










    public JButton getAddStudentButton() {
        return addStudentButton;
    }

}