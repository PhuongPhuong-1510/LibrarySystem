package MainApp.model;

public class Main {
    public static void main(String[] args) {
        // Tạo đối tượng LibraryModelManage
        LibraryModelManage libraryModel = new LibraryModelManage();

        // Tạo sách mới
        Book newBook = new Book("B007", "Effective Java", "path/to/image3.jpg", "Joshua Bloch", "Programming", "English", 20, "Available", "B1", "Borrowable");
        libraryModel.addBookToDatabase(newBook);

        // Lấy danh sách sách từ LibraryModelManage
        for (Book book : libraryModel.getBooksList()) {
            // In thông tin từng sách
            System.out.println("Book ID: " + book.getBookID());
            System.out.println("Book Name: " + book.getBookName());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Category: " + book.getCategory());
            System.out.println("Language: " + book.getLanguage());
            System.out.println("Total Copies: " + book.getTotal());
            System.out.println("Current Status: " + book.getCurent());
            System.out.println("Position: " + book.getPosition());
            System.out.println("Action: " + book.getAction());
            System.out.println("---------------------------");
        }

        Student newStudent = new Student("S004", "Tèo", "23020700@vnu.edu.vn", "12345", "0374169035");
        libraryModel.addStudentToDatabase(newStudent);

        Admin newAdmin = new Admin("A003", "Tài", "23020700@vnu.edu.vn", "12345", "0374169035");
        libraryModel.addAdminToDatabase(newAdmin);

    }
}
