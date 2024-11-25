package MainApp.model;

import dataBase.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class SignupDAO {

    private ArrayList<Signup> signupsList = new ArrayList<>();

    // Phương thức tải danh sách sinh viên từ cơ sở dữ liệu
    public void loadSignupsFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM sigup";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                boolean gender = "Male".equalsIgnoreCase(resultSet.getString("gender"));
                String cardPhoto = resultSet.getString("cardImage");
                String dateOfBirth = resultSet.getString("dateBirth");
                String major = resultSet.getString("major");
                String branch = resultSet.getString("branch");

                Signup signup = new Signup(name, email, password, phone, gender, cardPhoto, dateOfBirth, major, branch);
                signupsList.add(signup);
            }

            System.out.println("Dữ liệu đã được tải vào danh sách signup.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Phương thức để thêm sinh viên vào cơ sở dữ liệu
    public void addSignup(Signup signup) {
        String query = "INSERT INTO sigup ( name, email, password, phone, gender, cardImage, dateBirth, major, branch) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, signup.getName());
            preparedStatement.setString(2, signup.getEmail());
            preparedStatement.setString(3, signup.getPassword());
            preparedStatement.setString(4, signup.getPhone());
            preparedStatement.setString(5, signup.getGender() ? "Male" : "Female");
            preparedStatement.setString(6, signup.getCardImage());
            preparedStatement.setString(7, signup.getDateBirth());
            preparedStatement.setString(8, signup.getMajor());
            preparedStatement.setString(9, signup.getBranch());

            preparedStatement.executeUpdate();
            System.out.println("Đã thêm signup vào cơ sở dữ liệu.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm signup vào cơ sở dữ liệu: " + e.getMessage());
        }
    }



    public void deleteSignup(String email) {
        String query = "DELETE FROM sigup WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đã xóa đăng ki khỏi cơ sở dữ liệu.");
            } else {
                System.out.println("Không tìm thấy đăng kí với email: " + email);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa đăng kí: " + e.getMessage());
        }
    }

    public ArrayList<Signup> getSignupsList() {
        return signupsList;
    }
}
