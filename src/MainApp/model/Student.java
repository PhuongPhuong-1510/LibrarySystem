package MainApp.model;

import java.util.ArrayList;

public class Student extends User {
    private boolean gender;
    private String dateOfBirth;
    private String cardPhoto;
    private String major;
    private String branch;
    private ArrayList<Book> borrowedBooksList;

    public Student(String ID, String name,  String email, String password, String phone, boolean gender, String cardPhoto, String dateOfBirth, String major, String branch) {
        super(ID, name, email, password, phone);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.cardPhoto = cardPhoto;
        this.major = major;
        this.branch = branch;
        this.borrowedBooksList = new ArrayList<>();
    }

    // Getter và Setter cho gender
    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    // Getter và Setter cho dateOfBirth
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Getter và Setter cho cardPhoto
    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    // Getter và Setter cho major
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    // Getter và Setter cho branch
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    // Getter và Setter cho borrowedBooksList
    public ArrayList<Book> getBorrowedBooksList() {
        return borrowedBooksList;
    }

    public void setBorrowedBooksList(ArrayList<Book> borrowedBooksList) {
        this.borrowedBooksList = borrowedBooksList;
    }
}
