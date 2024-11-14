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
//        // Kiểm tra dữ liệu đầu vào
//        if (student == null || student.getID() == null || student.getName() == null ||
//            student.getEmail() == null || student.getPassword() == null || student.getPhone() == null) {
//            System.out.println("Dữ liệu sinh viên không hợp lệ.");
//            return;
//        }

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
            e.printStackTrace(); // Hiện thị câu lệnh khi làm sai để dễ fix bug
        }
    }

    // Phương thức để xóa sinh viên ở cơ sỡ dữ liệu
    public void deleteStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            System.out.println("ID sinh viên không hợp lê.");
            return;
        }

        String query = "DELETE FROM student WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thiệt lập câu lệnh cho sql
            preparedStatement.setString(1, studentId);

            // Lệnh xóa
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đã xóa sinh viên khỏi dữ liệu.");
            } else {
                System.out.println("Không tìm thấy sinh viên với ID: " + studentId);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa sinh viên khỏi cơ sỡ dữ liệu: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    // Phương thức để chỉnh sửa thông tin cho sinh viên
    public void updateStudent(Student student) {
        // Kiểm tra dữ liệu đầu vào
        if (student == null || student.getID() == null || student.getName() == null ||
            student.getEmail() == null || student.getPassword() == null || student.getPhone() == null) {
            System.out.println("Dữ liệu sinh viên không hợp lệ.");
            return;
        }

        String query = "UPDATE student SET name = ?, email = ?, password = ?, phone = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getPassword());
            preparedStatement.setString(4, student.getPhone());
            preparedStatement.setString(5, student.getID());

            int rowsUpdateed = preparedStatement.executeUpdate();
            if (rowsUpdateed > 0) {
                System.out.println("Đã cập nhật thông tin sinh viên thành công.");
            } else {
                System.out.println("Không tìm thấy sinh viên với ID: " + student.getID());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật sinh viên: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getter để truy cập danh sách sinh viên từ bên ngoài lớp
    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }
}
