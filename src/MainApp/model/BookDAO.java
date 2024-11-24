package MainApp.model;

import dataBase.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import MainApp.model.Book;

public class BookDAO {
    private ArrayList<Book> booksList = new ArrayList<>();

    public void loadBooksFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM book";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String bookID = resultSet.getString("bookID");
                String bookName = resultSet.getString("bookName");
                String image = resultSet.getString("image");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                String language = resultSet.getString("language");
                int total = resultSet.getInt("total");
                String curent = resultSet.getString("curent");
                String position = resultSet.getString("Position");
                String URL = resultSet.getString("URL");

                Book book = new Book(bookID, bookName, image, author, category, language, total, curent, position, URL);
                booksList.add(book);
            }

            System.out.println("Dữ liệu đã được tải vào danh sách booksList.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        String query = "INSERT INTO book (bookID, bookName, image, author, category, language, total, curent, Position, URL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.getBookID());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setString(3, book.getImage());
            preparedStatement.setString(4, book.getAuthor());
            preparedStatement.setString(5, book.getCategory());
            preparedStatement.setString(6, book.getLanguage());
            preparedStatement.setInt(7, book.getTotal());
            preparedStatement.setString(8, book.getCurent());
            preparedStatement.setString(9, book.getPosition());
            preparedStatement.setString(10, book.getURL());

            preparedStatement.executeUpdate();
            System.out.println("Đã thêm sách vào cơ sở dữ liệu.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sách vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void editBook(Book book) {
        String query = "UPDATE book SET bookName = ?, image = ?, author = ?, category = ?, language = ?, total = ?, curent = ?, Position = ?, URL = ? WHERE bookID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getImage());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getCategory());
            preparedStatement.setString(5, book.getLanguage());
            preparedStatement.setInt(6, book.getTotal());
            preparedStatement.setString(7, book.getCurent());
            preparedStatement.setString(8, book.getPosition());
            preparedStatement.setString(9, book.getBookID());
            preparedStatement.setString(10, book.getURL());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book data updated successfully in the database.");
            } else {
                System.out.println("Book with given ID not found in the database.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating book in database: " + e.getMessage());
        }
    }

    public void deleteBook(String bookID) {
        String query = "DELETE FROM book WHERE bookID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bookID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Book deleted successfully from the database.");
            } else {
                System.out.println("Book with given ID not found in the database.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting book from database: " + e.getMessage());
        }
    }



    public ArrayList<Book> getBooksList() {
        return booksList;
    }
}
