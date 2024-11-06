package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Đường dẫn đến file SQLite (giả sử trong thư mục resources)
    private static final String URL = "jdbc:sqlite:resources/librarymanage.db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Kết nối thành công!");
        } else {
            System.out.println("Kết nối thất bại.");
        }
    }
}
