package MainApp.model;

import dataBase.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    private ArrayList<Student> studentsList = new ArrayList<>();

    // Phương thức tải danh sách sinh viên từ cơ sở dữ liệu
    public void loadStudentsFromDatabase() {
        // Sử dụng kết nối từ DatabaseConnection
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Truy vấn dữ liệu từ bảng "student"
            String query = "SELECT * FROM student";
            ResultSet resultSet = statement.executeQuery(query);

            // Duyệt qua từng bản ghi và tạo đối tượng Student
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");

                // Tạo đối tượng Student và thêm vào danh sách
                Student student = new Student(id, name, email, password, phone);
                studentsList.add(student);
            }

            System.out.println("Dữ liệu đã được tải vào danh sách studentsList.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Phương thức để thêm sinh viên vào cơ sở dữ liệu
    public void addStudent(Student student) {
        String query = "INSERT INTO student (id, name, email, password, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getID());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getPassword());
            preparedStatement.setString(5, student.getPhone());

            preparedStatement.executeUpdate();
            System.out.println("Đã thêm sinh viên vào cơ sở dữ liệu.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sinh viên vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Getter để truy cập danh sách sinh viên từ bên ngoài lớp
    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }
}
