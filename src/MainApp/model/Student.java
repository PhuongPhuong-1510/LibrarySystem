package MainApp.model;

import java.util.ArrayList;

public class Student extends User{
    private ArrayList<Book> borrowedBooksList;
    public Student(String ID, String name, String email, String password) {
        super(ID, name, email, password);
        borrowedBooksList = new ArrayList<>();
    }
}
