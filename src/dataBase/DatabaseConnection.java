package dataBase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lớp quản lý kết nối đến cơ sở dữ liệu SQLite.
 * Đảm bảo tuân thủ các nguyên tắc coding convention.
 */
public class DatabaseConnection {
    // Đường dẫn đến file SQLite (giả sử trong thư mục resources)
    private static final String URL = "jdbc:sqlite:resources/librarymanage.db";

    /**
     * Phương thức tạo và trả về kết nối đến cơ sở dữ liệu SQLite.
     *
     * @return Đối tượng Connection nếu kết nối thành công, null nếu xảy ra lỗi.
     */
    public static Connection getConnection() {
        try {
            // Tạo kết nối với cơ sở dữ liệu thông qua URL
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            // In thông tin lỗi ra console nếu kết nối thất bại
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Phương thức main để kiểm tra kết nối với cơ sở dữ liệu.
     * In ra thông báo nếu kết nối thành công hoặc thất bại.
     *
     * @param args các đối số truyền từ dòng lệnh (không sử dụng ở đây).
     */
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            // In thông báo thành công nếu kết nối được thiết lập
            System.out.println("Kết nối thành công!");
        } else {
            // In thông báo thất bại nếu xảy ra lỗi
            System.out.println("Kết nối thất bại.");
        }
    }
}