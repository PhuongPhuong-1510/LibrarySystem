package ManageStudent.view;


import ManageBook.view.BaseBookTableView;
import ManageBook.view.BaseManagementPanel;
import ManageBook.view.PanelEditor;
import ManageStudent.controller.StudentController;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ManagementStudentView extends JPanel {
    private JPanel managementStudents;
    private JButton addStudentButton;
    private BaseBookTableView studentTableView;
    private BaseManagementPanel managementPanel;
    private int lastSelectedRow = -1;
    private int count=0;
    private JButton lastSelectedEditButton;




    public ManagementStudentView() {
        this.setLayout(new BorderLayout());
        this.init();
        new StudentController(this);
    }

    private void init() {
        managementStudents = new JPanel(new BorderLayout());


        String[] columnNames = createColumnNames();
        Object[][] data = fetchData();  // Phương thức để lấy dữ liệu

        studentTableView = new BaseBookTableView(columnNames, data,9,110,4) {

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

                int[] columnWidths = {80, 150, 80,100,100, 130, 150, 150, 80, 100};

                for (int i = 0; i < table.getColumnCount(); i++) {
                    column = table.getColumnModel().getColumn(i);
                    column.setPreferredWidth(columnWidths[i]);
                }
            }

        };
        managementPanel=new BaseManagementPanel("Search id, name, phone, email","/ManageStudent/view/icon/studentAdd.png","Add Student") {
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
        // Dữ liệu mẫu về sinh viên
        Object[][] data = new Object[][] {
                {"S001", "Nguyen Thi Lan", "female", "01/01/2000", null, "0123456789", "lannguyen@vnu.edu.vn", "Computer Science", "IT", createAction(0)},
                {"S002", "Tran Minh Tu", "male", "02/02/1999", null, "0987654321", "minhtu@vnu.edu.vn", "Electrical Engineering", "Engineering", createAction(1)},
                {"S003", "Pham Quyen", "female", "03/03/1998", null, "0912345678", "quyenpham@vnu.edu.vn", "Business Administration", "Business", createAction(2)},
                {"S004", "Le Hoang Nam", "male", "04/04/1997", null, "0876543210", "hoangnam@vnu.edu.vn", "Mechanical Engineering", "Engineering", createAction(3)},
                {"S005", "Bui Mai Linh", "female", "05/05/2001", null, "0765432109", "mailinh@vnu.edu.vn", "Law", "Law", createAction(4)}
        };

        for (Object[] row : data) {
            String gender = (String) row[2]; // Cập nhật chỉ số cột gender (cột thứ 2 sau Student ID và Name)
            row[4] = createImageLabel(gender); // Cập nhật ảnh cho gender (cột thứ 4 sau Date of Birth)
        }

        return data;
    }

    public JLabel createImageLabel(String gender) {
        String imagePath = gender.equalsIgnoreCase("female")
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
            toggleDeleteButton(deleteButton,editButton,row);

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
        } else {
            editButton.setIcon(new ImageIcon(getClass().getResource("/ManageStudent/view/icon/studentEdit.png")));
        }

        studentTableView.setSelectedRow(row);
        editButton.repaint();
    }

    private void toggleDeleteButton(JButton deleteButton,JButton editButton, int row) {
        System.out.println("DeleteStudent button clicked at row: " + row);
        count=0;
        studentTableView.setSelectedRow(row);
        editButton.setIcon(new ImageIcon(getClass().getResource("/ManageStudent/view/icon/studentEdit.png")));
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this student?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            System.out.println("Student deleted at row: " + row);
        } else {
            System.out.println("Student deletion canceled.");
        }

    }



        public JButton getAddStudentButton() {
        return addStudentButton;
    }

}