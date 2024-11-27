package MainApp.model;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LibraryModelManage {
    public String luaChon;
    private ArrayList<Book> booksList;
    private ArrayList<Student> studentsList;
    private ArrayList<Admin> adminsList;
    private ArrayList<Issue> issuesList;
    private ArrayList<Reserve> reserveList;
    private ArrayList<Signup> signupList;

    // Bộ nhớ đệm hình ảnh
    private final Map<String, ImageIcon> imageCache;

    public LibraryModelManage() {
        booksList = new ArrayList<>();
        studentsList = new ArrayList<>();
        adminsList = new ArrayList<>();
        issuesList = new ArrayList<>();
        reserveList = new ArrayList<>();
        signupList = new ArrayList<>();


        imageCache = new HashMap<>(); // Khởi tạo bộ nhớ đệm
        preloadImages(); // Tải ảnh khi khởi tạo

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
        editBook(book);
    }


    public void editBook(Book updatedBook) {
        if (updatedBook == null || updatedBook.getBookID() == null) {
            System.out.println("Invalid book data.");
            return;
        }
        for (Book book : booksList) {
            if (book.getBookID().equals(updatedBook.getBookID())) {
                book.setBookName(updatedBook.getBookName());
                book.setAuthor(updatedBook.getAuthor());
                book.setCurent(updatedBook.getCurent());
                book.setImage(updatedBook.getImage());
                book.setLanguage(updatedBook.getLanguage());
                book.setTotal(updatedBook.getTotal());
                book.setCategory(updatedBook.getCategory());
                book.setPosition(updatedBook.getPosition());
                book.setURL(updatedBook.getURL());
                break;
            }
        }
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
        //ArrayList<Book> bookslist = getBooksList();
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

    // Thêm sinh viên
    public void addStudentToDatabase(Student student) {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.addStudent(student);
        studentsList.add(student); // Cập nhật danh sách cục bộ
    }

    // Sửa thông tin sinh viên
    public void editStudentInDatabase(Student student) {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.editStudent(student);
        editStudent(student);

        // Cập nhật trong danh sách cục bộ
        studentsList.stream()
                .filter(s -> s.getID().equals(student.getID()))
                .findFirst()
                .ifPresent(existingStudent -> {
                    int index = studentsList.indexOf(existingStudent);
                    studentsList.set(index, student);
                });
    }

    public void editStudent(Student updatedStudent) {
        if (updatedStudent == null || updatedStudent.getID() == null) {
            System.out.println("Invalid student data.");
            return;
        }
        for (Student student : studentsList) {
            if (student.getID().equals(updatedStudent.getID())) {
                student.setName(updatedStudent.getName());
                student.setEmail(updatedStudent.getEmail());
                student.setPassword(updatedStudent.getPassword());
                student.setPhone(updatedStudent.getPhone());
                student.setGender(updatedStudent.getGender());
                student.setDateOfBirth(updatedStudent.getDateOfBirth());
                student.setMajor(updatedStudent.getMajor());
                student.setBranch(updatedStudent.getBranch());
                break;
            }
        }
    }


    // Xóa sinh viên
    public void deleteStudentFromDatabase(String studentID) {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.deleteStudent(studentID);

        // Loại bỏ khỏi danh sách cục bộ
        studentsList.removeIf(student -> student.getID().equals(studentID));
    }

    // Tìm kiếm sinh viên
    public ArrayList<Student> searchStudents(String keyword) {
        ArrayList<Student> searchResults = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (Student student : studentsList) {
            if (student.getID().toLowerCase().contains(keyword) ||
                    student.getName().toLowerCase().contains(keyword) ||
                    student.getEmail().toLowerCase().contains(keyword) ||
                    student.getPhone().toLowerCase().contains(keyword)) {
                searchResults.add(student);
            }
        }

        return searchResults;
    }

    // Tạo ID sinh viên mới
    public String createStudentID() {
        int newID = 1;
        ArrayList<Student> studentslist = getStudentsList();
        Set<String> existingIDs = studentslist.stream()
                .map(Student::getID)
                .collect(Collectors.toSet());

        String newStudentID;
        while (true) {
            newStudentID = String.format("S%03d", newID); // Format ID với 3 chữ số
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



    public ArrayList<Issue> getIssuesList() {
        if (issuesList.isEmpty()) {
            loadIssuesFromDatabase();
        }
        return issuesList;
    }

    private void loadIssuesFromDatabase() {
        IssueDAO issueDAO = new IssueDAO();
        issueDAO.loadIssuesFromDatabase();
        issuesList = issueDAO.getIssuesList();
    }


    public void addIssueToDatabase(Issue issue) {
        IssueDAO issueDAO = new IssueDAO();
        issueDAO.addIssue(issue);
        issuesList.add(issue);
        loadIssuesFromDatabase();
    }

    public void editIssueInDatabase(Issue issue) {
        IssueDAO issueDAO = new IssueDAO();
        issueDAO.editIssue(issue);
        editIssue(issue);
    }
    public void editIssue(Issue updatedIssue) {
        if (updatedIssue == null || updatedIssue.getIssueID() == null) {
            System.out.println("Invalid issue data.");
            return;
        }
        for (Issue issue : issuesList) {
            if (issue.getIssueID().equals(updatedIssue.getIssueID())) {
                issue.setIssueBookID(updatedIssue.getIssueBookID());
                issue.setIssueStudentID(updatedIssue.getIssueStudentID());
                issue.setIssueDate(updatedIssue.getIssueDate());
                issue.setDueDate(updatedIssue.getDueDate());
                issue.setStatus(updatedIssue.getStatus());
                break;
            }
        }

    }



    public String creatIssueID() {
        int newID = 1;
        Set<String> existingIDs = getIssuesList().stream()
                .map(Issue::getIssueID)
                .collect(Collectors.toSet());

        String newIssueID;
        while (true) {
            newIssueID = String.format("I%03d", newID);
            System.out.println("Checking ID: " + newIssueID);
            if (!existingIDs.contains(newIssueID)) {
                break;
            }
            newID++;
        }
        return newIssueID;
    }

    public Issue searchIssueByID(String issueID) {
        for (Issue issue : getIssuesList()) {
            if (issue.getIssueID().equals(issueID)) {
                return issue;
            }
        }
        return null;
    }


    public boolean checkStudentAndBookEmpty(String bookID, String studentID) {
        ArrayList<Book> bookslist = getBooksList();
        boolean isBookPresent = bookslist.stream()
                .anyMatch(book -> book.getBookID().equals(bookID));
        if (!isBookPresent) {
            JOptionPane.showMessageDialog(null, "Book not found.");
            return false;
        }

        boolean isBookStill = bookslist.stream()
                .anyMatch(book -> book.getBookID().equals(bookID) && ("Still".equals(book.getCurent())||"Reserved".equals(book.getCurent())));
        if (!isBookStill) {
            JOptionPane.showMessageDialog(null, "Book is not in 'Still' state.");
            return false;
        }

        ArrayList<Student> studentslist = getStudentsList();
        boolean isStudentPresent = studentslist.stream()
                .anyMatch(student -> student.getID().equals(studentID));
        if (!isStudentPresent) {
            JOptionPane.showMessageDialog(null, "Student not found.");
            return false;
        }

        bookslist.stream()
                .filter(book -> book.getBookID().equals(bookID))
                .findFirst()
                .ifPresent(book -> {
                    book.setCurent("Borrowed");
                    BookDAO bookDAO = new BookDAO();
                    bookDAO.editBook(book);
                });

        return true;
    }

    public Book searchBookByID(String bookID) {
        ArrayList<Book> bookslist = getBooksList();
        for (Book book : bookslist) {
            if (book.getBookID().equals(bookID)) {
                return book;
            }
        }
        return null;
    }

    public Student searchStudentByID(String studentID) {
        ArrayList<Student> studentslist = getStudentsList();
        for (Student student : studentslist) {
            if (student.getID().equals(studentID)) {
                return student;
            }
        }
        return null; // Return null if the student is not found
    }

    public boolean checkStudentByEmail(String studentEmail) {;
        for (Student student : studentsList) {
            if (student.getEmail().equals(studentEmail)) {
                return true;
            }
        }
        return false; // Return null if the student is not found
    }

    public boolean checkStudentByPhone(String studentPhone) {;
        for (Student student : studentsList) {
            if (student.getPhone().equals(studentPhone)) {
                return true;
            }
        }
        return false; // Return null if the student is not found
    }

    public ArrayList<Book> searchBooks(String bookId, String bookName, String author, String genre) {
        return booksList.stream()
                .filter(book -> (bookId.isEmpty() || book.getBookID().contains(bookId)) &&
                        (bookName.isEmpty() || book.getBookName().toLowerCase().contains(bookName.toLowerCase())) &&
                        (author.isEmpty() || book.getAuthor().toLowerCase().contains(author.toLowerCase())) &&
                        (genre.equals("All") || book.getCategory().equalsIgnoreCase(genre)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Student searchStudentByEmailPassword(String studentEmail, String studentPassword) {
        ArrayList<Student> studentsList = getStudentsList(); // Lấy danh sách sinh viên
        for (Student student : studentsList) {
            // So sánh email và mật khẩu
            if (student.getEmail().equals(studentEmail) &&
                    student.getPassword().equals(studentPassword)) {
                return student; // Trả về đối tượng sinh viên nếu tìm thấy
            }
        }
        return null; // Trả về null nếu không tìm thấy sinh viên phù hợp
    }


    public Issue searchIssueByBookStudentStatus(String bookID, String studentID, String status) {
        ArrayList<Issue> issueslist = getIssuesList();
        for (Issue issue : issueslist) {
            if (issue.getIssueBookID().equals(bookID) && issue.getIssueStudentID().equals(studentID) && issue.getStatus().equals(status)) {
                return issue;
            }
        }
        JOptionPane.showMessageDialog(null, "Issue not found.");
        return null; // Return null if no matching issue is found
    }



    // đăng kí mượn sách

    public ArrayList<Reserve> getReserveList() {
        if (reserveList.isEmpty()) {
            loadReservesFromDatabase();
        }
        return reserveList;
    }

    private void loadReservesFromDatabase() {
        ReserveDAO reserveDAO = new ReserveDAO();
        reserveDAO.loadReservesFromDatabase();
        reserveList = reserveDAO.getReservesList();
    }

    public void addReserveToDatabase(Reserve reserve) {
        ReserveDAO reserveDAO = new ReserveDAO();
        reserveDAO.addReserve(reserve);
        reserveList.add(reserve);
    }

    public void deleteReserveFromDatabase(String reserveID) {
        ReserveDAO reserveDAO = new ReserveDAO();
        reserveDAO.deleteReserve(reserveID);
        reserveList.removeIf(reserve -> reserve.getReserveID().equals(reserveID)); // Update the local list
    }

    public Reserve searchReserveByID(String reserveID) {
        for (Reserve reserve : getReserveList()) {
            if (reserve.getReserveID().equals(reserveID)) {
                return reserve;
            }
        }
        return null;
    }

    public Reserve searchReserveByBookID(String bookID) {
        for (Reserve reserve : getReserveList()) {
            if (reserve.getBookID().equals(bookID)) {
                return reserve;
            }
        }
        return null;
    }




    public String createReserveID() {
        int newID = 1;
        ArrayList<Reserve> reservelist = getReserveList();
        Set<String> existingIDs = reservelist.stream()
                .map(Reserve::getReserveID)
                .collect(Collectors.toSet());

        String newReserveID;
        while (true) {
            newReserveID = String.format("R%03d", newID);
            if (!existingIDs.contains(newReserveID)) {
                break;
            }
            newID++;
        }
        return newReserveID;
    }
    public void reloadReserveList() {
        loadReservesFromDatabase(); // Tải lại dữ liệu từ cơ sở dữ liệu
    }





    //    danh sách đăng kí
    public ArrayList<Signup> getSignupList() {
        if (signupList.isEmpty()) {
            loadSignupsFromDatabase();
        }
        return signupList;
    }

    private void loadSignupsFromDatabase() {
        SignupDAO signupDAO = new SignupDAO();
        signupDAO.loadSignupsFromDatabase();
        signupList = signupDAO.getSignupsList();
    }

    public void addSignupToDatabase(Signup signup) {
        SignupDAO signupDAO = new SignupDAO();
        signupDAO.addSignup(signup);
        signupList.add(signup);
    }

    public void deleteSignupFromDatabase(String email) {
        SignupDAO signupDAO = new SignupDAO();
        signupDAO.deleteSignup(email);
        signupList.removeIf(signup -> signup.getEmail().equals(email));
    }

    public Signup searchSignupByEmail(String email) {
        for (Signup signup : getSignupList()) {
            if (signup.getEmail().equals(email)) {
                return signup;
            }
        }
        return null;
    }


    public void reloadsignupList() {
        loadSignupsFromDatabase(); // Tải lại dữ liệu từ cơ sở dữ liệu
    }
    public ArrayList<Book> getBorrowedBooksByStudent(String studentID) {
        IssueDAO issueDAO = new IssueDAO();
        BookDAO bookDAO = new BookDAO();

        // Bước 1: Lấy danh sách bookID mà sinh viên đã mượn
        ArrayList<String> borrowedBookIDs = issueDAO.getBooksBorrowedByStudent(studentID);

        // Bước 2: Lấy thông tin chi tiết của các sách từ danh sách bookID
        return bookDAO.getBooksByIDs(borrowedBookIDs, borrowedBookIDs);
    }



    /**
     * Tải toàn bộ ảnh từ thư mục /ManageBook/icon
     */
    private void preloadImages() {
        String iconFolderPath = System.getProperty("user.dir") + "/src/ManageBook/icon/";
        File iconFolder = new File(iconFolderPath);

        if (iconFolder.exists() && iconFolder.isDirectory()) {
            File[] files = iconFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (isImageFile(file)) {
                        String relativePath = "/ManageBook/icon/" + file.getName();
                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                        imageCache.put(relativePath, icon);
                        System.out.println("Cached image: " + relativePath);
                    }
                }
            }
        }
    }

    /**
     * Kiểm tra file có phải là ảnh không
     */
    private boolean isImageFile(File file) {
        String[] imageExtensions = {".png", ".jpg", ".jpeg", ".gif"};
        String fileName = file.getName().toLowerCase();
        for (String ext : imageExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Thêm một ảnh vào bộ nhớ đệm
     */
    public void addImageToCache(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            System.err.println("Error: Provided relative path is null or empty.");
            return;
        }

        if (!imageCache.containsKey(relativePath)) {
            try {
                // Chuyển đổi đường dẫn tương đối thành đường dẫn chuẩn hóa
                String basePath = "src/"; // Đường dẫn gốc của dự án
                String fullPath = Paths.get(basePath, relativePath).normalize().toString();
                System.out.println(fullPath);

                ImageIcon icon = new ImageIcon(fullPath); // Tạo ImageIcon từ đường dẫn đầy đủ
                imageCache.put(relativePath, icon);       // Lưu trữ icon trong cache

                System.out.println("Added image to cache: " + relativePath);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error: Failed to load image at path: " + relativePath);
            }
        } else {
            System.out.println("Image already exists in cache: " + relativePath);
        }
    }


    /**
     * Xóa một ảnh khỏi bộ nhớ đệm
     */
    public void removeImageFromCache(String relativePath) {
        if (imageCache.containsKey(relativePath)) {
            imageCache.remove(relativePath);
            System.out.println("Removed image from cache: " + relativePath);
        } else {
            System.out.println("Image not found in cache: " + relativePath);
        }
    }

    /**
     * Lấy ảnh từ bộ nhớ đệm
     */
    public ImageIcon getImageFromCache(String relativePath) {
        return imageCache.getOrDefault(relativePath, null);
    }

    /**
     * Lấy toàn bộ bộ nhớ đệm (debug hoặc hiển thị danh sách ảnh)
     */
    public Map<String, ImageIcon> getImageCache() {
        return imageCache;
    }
    public void loadImageToCache(String imagePath) {
        if (!imageCache.containsKey(imagePath)) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
                imageCache.put(imagePath, icon);
            } catch (Exception e) {
                System.out.println("Failed to load image: " + imagePath);
            }
        }
    }


}