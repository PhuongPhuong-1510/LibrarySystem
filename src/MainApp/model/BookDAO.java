package MainApp.model;

import dataBase.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import MainApp.model.Book;

public class BookDAO {
    private ArrayList<Book> booksList = new ArrayList<>();

    public void loadBooksFromDatabase() {
        // Sử dụng kết nối từ DatabaseConnection
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Truy vấn dữ liệu từ bảng "book"
            String query = "SELECT * FROM book";
            ResultSet resultSet = statement.executeQuery(query);

            // Duyệt qua từng bản ghi và tạo đối tượng Book
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
                String action = resultSet.getString("action");

                // Tạo đối tượng Book và thêm vào danh sách
                Book book = new Book(bookID, bookName, image, author, category, language, total, curent, position, action);
                booksList.add(book);
            }

            System.out.println("Dữ liệu đã được tải vào danh sách booksList.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Phương thức để thêm sách vào cơ sở dữ liệu
    public void addBook(Book book) {
        String query = "INSERT INTO book (bookID, bookName, image, author, category, language, total, curent, Position, action) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            preparedStatement.setString(10, book.getAction());

            preparedStatement.executeUpdate();
            System.out.println("Đã thêm sách vào cơ sở dữ liệu.");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sách vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Getter để truy cập booksList từ bên ngoài lớp
    public ArrayList<Book> getBooksList() {
        return booksList;
    }
}
