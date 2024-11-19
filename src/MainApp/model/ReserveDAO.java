package MainApp.model;

import dataBase.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class ReserveDAO {
    private ArrayList<Reserve> reservesList = new ArrayList<>();

    // Load reservations from the database
    public void loadReservesFromDatabase() {
        String query = "SELECT * FROM reserve";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String reserveID = resultSet.getString("reserveID");
                String bookID = resultSet.getString("bookID");
                String id = resultSet.getString("id");
                Date reservedDate = resultSet.getDate("reservedDate");
                Date dueDate = resultSet.getDate("dueDate");

                Reserve reserve = new Reserve(reserveID, bookID, id, reservedDate, dueDate);
                reservesList.add(reserve);
            }

            System.out.println("Data has been loaded into the reserves list.");

        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    // Add a new reservation
    public void addReserve(Reserve reserve) {
        String query = "INSERT INTO reserve (reserveID, bookID, id, reservedDate, dueDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, reserve.getReserveID());
            preparedStatement.setString(2, reserve.getBookID());
            preparedStatement.setString(3, reserve.getId());
            preparedStatement.setDate(4, reserve.getReservedDate());
            preparedStatement.setDate(5, reserve.getDueDate());

            preparedStatement.executeUpdate();
            System.out.println("Reservation added to the database.");

        } catch (SQLException e) {
            System.out.println("Error adding reservation to database: " + e.getMessage());
        }
    }

    // Delete a reservation
    public void deleteReserve(String reserveID) {
        String query = "DELETE FROM reserve WHERE reserveID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, reserveID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Reservation deleted successfully.");
            } else {
                System.out.println("Reservation with given ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
        }
    }

    // Getter for reservesList
    public ArrayList<Reserve> getReservesList() {
        return reservesList;
    }
}

