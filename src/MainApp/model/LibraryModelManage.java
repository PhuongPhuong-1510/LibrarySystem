package MainApp.model;

import java.util.ArrayList;

public class LibraryModelManage {
    private ArrayList<Book> booksList;
    private ArrayList<Student> studentsList;

    public LibraryModelManage() {
        booksList = new ArrayList<>();
        studentsList = new ArrayList<>();
    }


    public ArrayList<Book> getBooksList() {
        if (booksList.isEmpty()) {
            loadBooksFromDatabase();
        }
        return booksList;
    }
    private void loadBooksFromDatabase() {
        BookDAO bookDAO = new BookDAO();
        bookDAO.loadBooksFromDatabase();
        booksList = bookDAO.getBooksList();
    }
    public void addBookToDatabase(Book book) {
        // Thêm sách vào cơ sở dữ liệu
        BookDAO bookDAO = new BookDAO();
        bookDAO.addBook(book);

    }


    public ArrayList<Student> getStudentsList() {
        if (booksList.isEmpty()) {
            loadStudentsFromDatabase();
        }
        return studentsList;
    }
    private void loadStudentsFromDatabase() {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.loadStudentsFromDatabase();
        studentsList = studentDAO.getStudentsList();
    }
    public void addStudentToDatabase(Student student) {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.addStudent(student);

    }
}
