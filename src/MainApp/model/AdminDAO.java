package MainApp.model;

import dataBase.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class AdminDAO {
    private ArrayList<Admin> adminsList = new ArrayList<>();

    public void loadAdminsFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM admin";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("pasword");
                String phone = resultSet.getString("phone");

                Admin admin = new Admin(id, name, email, password, phone);
                adminsList.add(admin);
            }

            System.out.println("Dữ liệu đã được tải vào danh sách studentsList.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void addAdmin(Admin admin) {
        String query = "INSERT INTO admin (id, name, email, password, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, admin.getID());
            preparedStatement.setString(2, admin.getName());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.setString(5, admin.getPhone());


            preparedStatement.executeUpdate();
            System.out.println("Đã thêm sv vào cơ sở dữ liệu.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sv vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public ArrayList<Admin> getAdminsList() {
        return adminsList;
    }
}
