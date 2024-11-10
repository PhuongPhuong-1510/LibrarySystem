package MainApp.model;

import java.util.ArrayList;

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
    }

    public void editBookInDatabase(Book book) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.editBook(book);
    }


    public String creatBookID(){
        BookDAO bookDAO = new BookDAO();
        String id = bookDAO.creatBookID();
        return id;
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
