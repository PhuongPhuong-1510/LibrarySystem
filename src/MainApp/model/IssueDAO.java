package MainApp.model;

import dataBase.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class IssueDAO {
    private ArrayList<Issue> issuesList = new ArrayList<>();

    public void loadIssuesFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM issue";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String issueID = resultSet.getString("issueID");
                String bookID = resultSet.getString("bookID");
                String studentID = resultSet.getString("id");
                String isueDate = resultSet.getString("issueDate");
                String dueDate = resultSet.getString("dueDate");
                String status = resultSet.getString("status");

                Issue issue = new Issue(issueID, bookID, studentID, isueDate, dueDate, status);
            }

            System.out.println("Dữ liệu đã được tải vào danh sách issuesList.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void addIssue(Issue issue) {
        String query = "INSERT INTO issue (issueID, bookID, id, isueDate, dueDate, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, issue.getIssueID());
            preparedStatement.setString(2, issue.getIssueBookID());
            preparedStatement.setString(3, issue.getIssueStudentID());
            preparedStatement.setString(4, issue.getIssueDate());
            preparedStatement.setString(5, issue.getDueDate());
            preparedStatement.setString(6, issue.getStatus());

            preparedStatement.executeUpdate();
            System.out.println("Đã thêm trang thai vào cơ sở dữ liệu.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm trang thai vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public ArrayList<Issue> getIssuesList() {
        return issuesList;
    }
}
