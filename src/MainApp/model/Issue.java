package MainApp.model;

public class Issue {
    private String issueID;
    private String issueBookID;
    private String issueStudentID;
    private String issueDate;
    private String dueDate;
    private String status;

    public Issue() {

    }
    public Issue(String issueID, String issueBook, String issueStudent, String issueDate, String dueDate, String status) {
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
    public String getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
