package MainApp.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class LibraryModelManage {
    public String luaChon;
    private ArrayList<Book> booksList;
    private ArrayList<Student> studentsList;
    private ArrayList<Admin> adminsList;

    public LibraryModelManage() {
        booksList = new ArrayList<>();
        studentsList = new ArrayList<>();
        adminsList = new ArrayList<>();
    }

    // Quản lý sách
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
        BookDAO bookDAO = new BookDAO();
        bookDAO.addBook(book);
        booksList.add(book);
    }

    public void editBookInDatabase(Book book) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.editBook(book);
    }

    public void deleteBookFromDatabase(String bookID) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.deleteBook(bookID);
        booksList.removeIf(book -> book.getBookID().equals(bookID)); // Update the local list
    }

    public ArrayList<Book> searchBooks(String keyword) {
        ArrayList<Book> searchResults = new ArrayList<>();
        keyword = keyword.toLowerCase(); // Để tìm kiếm không phân biệt chữ hoa/chữ thường

        for (Book book : booksList) {
            if (book.getBookID().toLowerCase().contains(keyword) ||
                    book.getBookName().toLowerCase().contains(keyword) ||
                    book.getAuthor().toLowerCase().contains(keyword) ||
                    book.getCategory().toLowerCase().contains(keyword)) {
                searchResults.add(book);
            }
        }

        return searchResults;
    }

    public String creatBookID() {
        int newID = 1;
        Set<String> existingIDs = booksList.stream()
                .map(Book::getBookID)
                .collect(Collectors.toSet());

        String newBookID;
        while (true) {
            newBookID = String.format("B%03d", newID); // Formats ID with leading zeros
            if (!existingIDs.contains(newBookID)) {
                break;
            }
            newID++;
        }
        return newBookID;
    }



    // Quản lý sinh viên
    public ArrayList<Student> getStudentsList() {
        if (studentsList.isEmpty()) {
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

    public String creatStudentID() {
        int newID = 1;
        Set<String> existingIDs = studentsList.stream()
                .map(Student::getID)
                .collect(Collectors.toSet());

        String newStudentID;
        while (true) {
            newStudentID = String.format("B%03d", newID); // Formats ID with leading zeros
            if (!existingIDs.contains(newStudentID)) {
                break;
            }
            newID++;
        }
        return newStudentID;
    }

    // Quản lý admin
    public ArrayList<Admin> getAdminsList() {
        if (adminsList.isEmpty()) {
            loadAdminsFromDatabase();
        }
        return adminsList;
    }

    private void loadAdminsFromDatabase() {
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.loadAdminsFromDatabase();
        adminsList = adminDAO.getAdminsList();
    }

    public void addAdminToDatabase(Admin admin) {
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.addAdmin(admin);
    }

    public String getLuaChon() {
        return luaChon;
    }
    public void setLuaChon(String luaChon) {
        this.luaChon = luaChon;
    }
}
