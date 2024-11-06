package MainApp.model;

import java.sql.Date;

public class BookBorrowingSlip {
    private String borrowedBookID;
    private String studentID;
    private String bookID;
    private Date borrowedDate;
    private Date returnDate;

    public BookBorrowingSlip() {
    }

    public BookBorrowingSlip(String borrowedBookID, String studentID, String bookID, Date borrowedDate, Date returnDate) {
        this.borrowedBookID = borrowedBookID;
        this.studentID = studentID;
        this.bookID = bookID;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }

    public String getBorrowedBookID() {
        return borrowedBookID;
    }

    public void setBorrowedBookID(String borrowedBookID) {
        this.borrowedBookID = borrowedBookID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowedBooks{" +
                "borrowedBookID='" + borrowedBookID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", borrowedDate=" + borrowedDate +
                ", returnDate=" + returnDate +
                '}';
    }
}

