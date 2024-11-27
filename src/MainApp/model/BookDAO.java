package MainApp.model;

import dataBase.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
            preparedStatement.setString(10, book.getBookID());
            preparedStatement.setString(9, book.getURL());

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
    public ArrayList<Book> getBooksByIDs(ArrayList<String> bookIDs, ArrayList<String> borrowedBookIDs) {
        ArrayList<Book> books = new ArrayList<>();
        Set<String> seenTitles = new HashSet<>(); // Để theo dõi các tiêu đề đã thêm

        if (bookIDs.isEmpty()) return books; // Nếu danh sách rỗng, trả về danh sách rỗng

        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT bookName, bookID, image, author, category, language, total, curent, Position, URL " +
                        "FROM book WHERE (bookID IN ("
        );

        for (int i = 0; i < bookIDs.size(); i++) {
            queryBuilder.append("?");
            if (i < bookIDs.size() - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(") OR (author IN (SELECT DISTINCT author FROM book WHERE bookID IN (");
        for (int i = 0; i < bookIDs.size(); i++) {
            queryBuilder.append("?");
            if (i < bookIDs.size() - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(")) OR category IN (SELECT DISTINCT category FROM book WHERE bookID IN (");
        for (int i = 0; i < bookIDs.size(); i++) {
            queryBuilder.append("?");
            if (i < bookIDs.size() - 1) queryBuilder.append(", ");
        }
        queryBuilder.append("))))");

        if (!borrowedBookIDs.isEmpty()) {
            queryBuilder.append(" AND bookID NOT IN (");
            for (int i = 0; i < borrowedBookIDs.size(); i++) {
                queryBuilder.append("?");
                if (i < borrowedBookIDs.size() - 1) queryBuilder.append(", ");
            }
            queryBuilder.append(")");
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

            int index = 1;

            // Gán giá trị cho bookIDs
            for (String bookID : bookIDs) {
                preparedStatement.setString(index++, bookID);
            }
            // Gán lại giá trị cho tác giả và thể loại
            for (String bookID : bookIDs) {
                preparedStatement.setString(index++, bookID);
            }
            for (String bookID : bookIDs) {
                preparedStatement.setString(index++, bookID);
            }
            // Gán giá trị cho borrowedBookIDs
            for (String borrowedBookID : borrowedBookIDs) {
                preparedStatement.setString(index++, borrowedBookID);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

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

                // Kiểm tra nếu tiêu đề đã xuất hiện
                if (!seenTitles.contains(bookName)) {
                    Book book = new Book(bookID, bookName, image, author, category, language, total, curent, position, URL);
                    books.add(book);
                    seenTitles.add(bookName); // Đánh dấu tiêu đề đã thêm
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }

        return books;
    }





}
