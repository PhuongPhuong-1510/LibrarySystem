package MainApp.model;

import dataBase.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    private ArrayList<Student> studentsList = new ArrayList<>();

    // Phương thức tải danh sách sinh viên từ cơ sở dữ liệu
    public void loadStudentsFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM student";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                boolean gender = "Male".equalsIgnoreCase(resultSet.getString("gender"));
                String cardPhoto = resultSet.getString("cardImage");
                String dateOfBirth = resultSet.getString("dateBirth");
                String major = resultSet.getString("major");
                String branch = resultSet.getString("branch");

                Student student = new Student(id, name, email, password, phone, gender, cardPhoto, dateOfBirth, major, branch);
                studentsList.add(student);
            }

            System.out.println("Dữ liệu đã được tải vào danh sách studentsList.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Phương thức để thêm sinh viên vào cơ sở dữ liệu
    public void addStudent(Student student) {
        String query = "INSERT INTO student (id, name, email, password, phone, gender, cardImage, dateBirth, major, branch) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getID());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getPassword());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.setString(6, student.getGender() ? "Male" : "Female");
            preparedStatement.setString(7, student.getCardPhoto());
            preparedStatement.setString(8, student.getDateOfBirth());
            preparedStatement.setString(9, student.getMajor());
            preparedStatement.setString(10, student.getBranch());

            preparedStatement.executeUpdate();
            System.out.println("Đã thêm sinh viên vào cơ sở dữ liệu.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sinh viên vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void editStudent(Student student) {
        String query = "UPDATE student SET name = ?, email = ?, password = ?, phone = ?, gender = ?, " +
                "cardImage = ?, dateBirth = ?, major = ?, branch = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getPassword());
            preparedStatement.setString(4, student.getPhone());
            preparedStatement.setString(5, student.getGender() ? "Male" : "Female");
            preparedStatement.setString(6, student.getCardPhoto());
            preparedStatement.setString(7, student.getDateOfBirth());
            preparedStatement.setString(8, student.getMajor());
            preparedStatement.setString(9, student.getBranch());
            preparedStatement.setString(10, student.getID());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Đã cập nhật thông tin sinh viên thành công.");
            } else {
                System.out.println("Không tìm thấy sinh viên với ID: " + student.getID());
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật sinh viên: " + e.getMessage());
        }
    }

    public void deleteStudent(String studentId) {
        String query = "DELETE FROM student WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đã xóa sinh viên khỏi cơ sở dữ liệu.");
            } else {
                System.out.println("Không tìm thấy sinh viên với ID: " + studentId);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }

    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }
}
