//package dataBase;
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DatabaseConnection {
//    // Đường dẫn đến file SQLite (giả sử trong thư mục resources)
//    private static final String URL = "jdbc:sqlite:resources/librarymanage.db";
//
//    public static Connection getConnection() {
//        try {
//            return DriverManager.getConnection(URL);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void main(String[] args) {
//        Connection conn = getConnection();
//        if (conn != null) {
//            System.out.println("Kết nối thành công!");
//        } else {
//            System.out.println("Kết nối thất bại.");
//        }
//    }
//}
package dataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_PATH = "resources/librarymanage.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    // Hàm trả về kết nối mới
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
            return null;
        }
    }

    // Hàm kiểm tra (test)
    public static void main(String[] args) {
        Runnable task = () -> {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println(Thread.currentThread().getName() + ": Kết nối thành công!");
            } else {
                System.err.println(Thread.currentThread().getName() + ": Kết nối thất bại.");
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        Thread thread3 = new Thread(task, "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
