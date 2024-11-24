package MainApp.model;

import java.sql.Date;

public class Issue {
    private String issueID;
    private String issueBookID;
    private String bookName;
    private String bookImage;
    private String issueStudentID;
    private String studentName;
    private String studentImage;
    private Date issueDate;
    private Date dueDate;
    private String status;

    public Issue(int issueID, int bookID, int studentID, String bookName, String bookImage, String studentName, String studentImage, Date issueDate, Date dueDate, String status) {

    }
    public Issue(String issueID, String issueBook, String issueStudent, Date issueDate, Date dueDate, String status) {
        this.issueID = issueID;
        this.issueBookID = issueBook;
        this.issueStudentID = issueStudent;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;

    }
    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }
    public String getIssueBookID() {
        return issueBookID;
    }
    public void setIssueBookID(String issueBookID) {
        this.issueBookID = issueBookID;
    }
    public String getIssueStudentID() {
        return issueStudentID;
    }

    public void setIssueStudentID(String issueStudentID) {
        this.issueStudentID = issueStudentID;
    }
    public Date getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
