package LMSNotification.view;


import LoginPage.view.OvalButton;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import LoginPage.view.PlaceholderPasswordField;
import MainApp.model.LibraryModelManage;
import MainApp.model.Signup;
import MainApp.model.Student;
import ManageStudent.controller.AddStudentController;
import SignupPage.controller.SignupController;

public class AddStudentFromSignup extends JFrame {
    private JTextField txtStudentName;
    private JTextField txtDate;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtBranch;
    private JTextField txtMajor;
    private JComboBox<String> genderComboBox;
    private JButton btnSubmit;
    private JButton btnCancel;
    private LibraryModelManage libraryModelManage;
    private Signup signup;

    public AddStudentFromSignup(LibraryModelManage libraryModelManage, Signup signup)  {
        initializeUIComponents();
        //new AddStudentController(this);
        this.libraryModelManage = libraryModelManage;
        this.signup = signup;
    }

    private void initializeUIComponents() {
        setSize(1200, 632);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Đóng dialog khi bấm nút đóng
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);

        setLayout(new BorderLayout());

        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));


        JLayeredPane layeredPane = createImagePane();
        layeredPane.setBackground(Color.cyan);
        mainPanel.add(layeredPane, BorderLayout.WEST);

        JPanel addPanel = createLoginPanel();
        addPanel.setFocusable(true);
        addPanel.requestFocus();
        mainPanel.add(addPanel, BorderLayout.CENTER);

        this.add(mainPanel);

        setVisible(true);
    }

    private JLayeredPane createImagePane() {
        JLabel imageLabel = new JLabel();
        imageLabel.setBackground(Color.cyan);
        imageLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ManageStudent/view/icon/addStudent.jpg"))));
        imageLabel.setLayout(null);
        imageLabel.setBounds(0, 0, 595, 632);

        JLabel welcomeLabel = createTextLabel("WELCOME TO", new Font("Tahoma", Font.PLAIN, 22), Color.RED, 180, 15, 300, 34);
        JLabel libraryLabel = createTextLabel("MANAGE STUDENT", new Font("Tahoma", Font.PLAIN, 22), Color.BLUE, 160, 50, 300, 50);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(595, 632));
        layeredPane.setLayout(null);
        layeredPane.add(imageLabel, Integer.valueOf(0));
        layeredPane.add(welcomeLabel, Integer.valueOf(1));
        layeredPane.add(libraryLabel, Integer.valueOf(1));

        return layeredPane;
    }

    private JPanel createLoginPanel() {
        JPanel addPanel = new JPanel(null);
        addPanel.setBackground(new Color(150, 180, 255));

        JLabel lblStudent = createTextLabel("STUDENT INFORMATION !", new Font("Tahoma", Font.PLAIN, 25), Color.WHITE, 80, 25, 500, 50);

        JLabel lblStudentname = createIconLabel("Student Name :", "/LoginPage/view/icon/userName.png", 30, 100, 180, 45);
        JLabel lblGender = createIconLabel("Gender :", "/ManageStudent/view/icon/genre.png", 30, 160, 180, 45);
        JLabel lblDate = createIconLabel("Date Of Birth :", "/ManageStudent/view/icon/dateStudent.png", 30, 220, 180, 45);
        JLabel lblPhone = createIconLabel("Phone Number :", "/ManageStudent/view/icon/phone.png", 30, 280, 180, 45);
        JLabel lblEmail = createIconLabel("Email :", "/ManageStudent/view/icon/email.png", 30, 340, 180, 45);
        JLabel lblBranch = createIconLabel("Branch :", "/ManageStudent/view/icon/branch.png", 30, 400, 180, 45);
        JLabel lblMajor = createIconLabel("Major :", "/ManageStudent/view/icon/major.png", 30, 460, 180, 45);

        txtStudentName = createPlaceholderField("Enter student name", 200, 110);

        genderComboBox = new JComboBox<>(new String[] {"Male", "Female"});
        genderComboBox.setBounds(200, 170, 200, 30);



        txtDate = createPlaceholderField("dd/mm/yyyy ", 200, 230);
        txtPhone = createPlaceholderField("0***", 200, 290);
        txtEmail = createPlaceholderField("abc@gmail.com", 200, 350);
        txtBranch = createPlaceholderField("Enter study branch", 200, 410);
        txtMajor = createPlaceholderField("Enter study major", 200, 470);


        btnSubmit = createButton("SUBMIT", new Color(255, 94, 77), 100, 550);
        btnCancel = createButton("CANCEL", new Color(192, 192, 192), 280, 550);

        btnCancel.addActionListener(e->{
            this.setVisible(false);
        });

        btnSubmit.addActionListener(e->{
            Student student = getStudentFromPanel();
        });



        addPanel.add(lblStudent);
        addPanel.add(lblStudentname);
        addPanel.add(lblGender);
        addPanel.add(lblDate);
        addPanel.add(lblPhone);
        addPanel.add(lblEmail);
        addPanel.add(lblBranch);
        addPanel.add(lblMajor);

        addPanel.add(txtStudentName);
        addPanel.add(genderComboBox);
        addPanel.add(txtDate);
        addPanel.add(txtPhone);
        addPanel.add(txtEmail);
        addPanel.add(txtBranch);
        addPanel.add(txtStudentName);
        addPanel.add(txtMajor);



        addPanel.add(btnSubmit);
        addPanel.add(btnCancel);



        return addPanel;
    }

    private JLabel createTextLabel(String text, Font font, Color color, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JLabel createIconLabel(String text, String iconPath, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, width, height);
        label.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath))));
        return label;
    }

    private JTextField createPlaceholderField(String placeholderText, int x, int y) {
        JTextField textField = new PlaceholderPasswordField(placeholderText);
        textField.setBounds(x, y, 200, 30);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        return textField;
    }

    private JButton createButton(String text, Color background, int x, int y) {
        JButton button = new OvalButton(text);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, 100, 30);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public Student getStudentFromPanel() {

        String id = libraryModelManage.createStudentID();
        String name = this.txtStudentName.getText().trim();
        boolean gender = "Male".equals(this.genderComboBox.getSelectedItem()); // True nếu là Male
        String dateOfBirth = this.txtDate.getText().trim();
        String phone = this.txtPhone.getText().trim();
        String email = this.txtEmail.getText().trim();
        String branch = this.txtBranch.getText().trim();
        String major = this.txtMajor.getText().trim();

        String password = signup.getPassword();

        String cardPhoto = gender
                ? "/ManageStudent/view/icon/boyicon.png"
                : "/ManageStudent/view/icon/girlicon.png";

        Student student = new Student(id, name, email, password, phone, gender, cardPhoto, dateOfBirth, major, branch);
        libraryModelManage.addStudentToDatabase(student);

        return student;
    }


    public boolean validateInputFields() {
        if (txtStudentName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student Name is required.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtDate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Date of Birth is required.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtPhone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone Number is required.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email is required.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtBranch.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Branch is required.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtMajor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Major is required.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void updateSignupStudent(String name, String email, String password, String phone, boolean gender, String dateBirth, String major, String branch) {

        txtStudentName.setText(name);
        txtEmail.setText(email);
        txtPhone.setText(phone);

        if (gender) {
            genderComboBox.setSelectedItem("Male");
        } else {
            genderComboBox.setSelectedItem("Female");
        }


        txtDate.setText(dateBirth);
        txtMajor.setText(major);
        txtBranch.setText(branch);
    }

}

