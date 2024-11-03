package MainApp.model;

import dataBase.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    private ArrayList<Student> studentsList = new ArrayList<>();

    public void loadStudentsFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM student";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("pasword");
                String phone = resultSet.getString("phone");

                Student student = new Student(id, name, email, password, phone);
                studentsList.add(student);
            }

            System.out.println("Dữ liệu đã được tải vào danh sách studentsList.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

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
            System.out.println("Đã thêm sv vào cơ sở dữ liệu.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sv vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }
}
