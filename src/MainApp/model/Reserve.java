package MainApp.model;

import java.sql.Date;

public class Reserve {
    private String reserveID;
    private String bookID;
    private String id;
    private Date reservedDate;
    private Date dueDate;

    public Reserve(String reserveID, String bookID, String id, Date reservedDate, Date dueDate) {
        this.reserveID = reserveID;
        this.bookID = bookID;
        this.id = id;
        this.reservedDate = reservedDate;
        this.dueDate = dueDate;
    }

    public String getReserveID() {
        return reserveID;
    }

    public void setReserveID(String reserveID) {
        this.reserveID = reserveID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Date reservedDate) {
        this.reservedDate = reservedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Reserve{" +
                "reserveID='" + reserveID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", id='" + id + '\'' +
                ", reservedDate=" + reservedDate +
                ", dueDate=" + dueDate +
                '}';
    }
}

