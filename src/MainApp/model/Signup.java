package MainApp.model;

import java.sql.Date;

public class Signup {
    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean gender;
    private String cardImage;
    private String dateBirth;
    private String major;
    private String branch;

    // Constructor
    public Signup(String name, String email, String password, String phone, boolean gender,
                  String cardImage, String dateBirth, String major, String branch) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.cardImage = cardImage;
        this.dateBirth = dateBirth;
        this.major = major;
        this.branch = branch;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean getGender() { return gender; }
    public void setGender(boolean gender) { this.gender = gender; }

    public String getCardImage() { return cardImage; }
    public void setCardImage(String cardImage) { this.cardImage = cardImage; }

    public String getDateBirth() { return dateBirth; }
    public void setDateBirth(String dateBirth) { this.dateBirth = dateBirth; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }
}
