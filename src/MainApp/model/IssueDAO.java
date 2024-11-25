
package MainApp.model;

import dataBase.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class IssueDAO {
    private ArrayList<Issue> issuesList = new ArrayList<>();

    public void loadIssuesFromDatabase() {
        issuesList.clear(); // Đảm bảo danh sách được làm trống trước khi tải mới dữ liệu
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM issue";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String issueID = resultSet.getString("issueID");
                String bookID = resultSet.getString("bookID");
                String studentID = resultSet.getString("id");
                Date issueDate = resultSet.getDate("isueDate");
                Date dueDate = resultSet.getDate("dueDate");
                String status = resultSet.getString("status");

                Issue issue = new Issue(issueID, bookID, studentID, issueDate, dueDate, status);
                issuesList.add(issue); // Thêm vào danh sách
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
            preparedStatement.setDate(4, issue.getIssueDate()); // Convert LocalDate to java.sql.Date
            preparedStatement.setDate(5, issue.getDueDate());
            preparedStatement.setString(6, issue.getStatus());

            preparedStatement.executeUpdate();
            System.out.println("Data added to the database.");
            loadIssuesFromDatabase(); // Refresh issues list

        } catch (SQLException e) {
            System.err.println("Error adding data to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void editIssue(Issue issue) {
        String query = "UPDATE issue SET bookID = ?, id = ?, isueDate = ?, dueDate = ?, status = ? WHERE issueID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, issue.getIssueBookID());
            preparedStatement.setString(2, issue.getIssueStudentID());
            preparedStatement.setDate(3, issue.getIssueDate()); // Chuyển LocalDate sang java.sql.Date
            preparedStatement.setDate(4, issue.getDueDate()); // Chuyển LocalDate sang java.sql.Date
            preparedStatement.setString(5, issue.getStatus());
            preparedStatement.setString(6, issue.getIssueID());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Issue data updated successfully in the database.");
            } else {
                System.out.println("Issue with the given ID not found in the database.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating issue in database: " + e.getMessage());
        }
    }

    public ArrayList<Issue> getIssuesList() {
        return issuesList;
    }
}