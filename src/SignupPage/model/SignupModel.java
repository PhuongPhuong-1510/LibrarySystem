package SignupPage.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class SignupModel {
    private String fullName;    // Tên đầy đủ
    private String email;
    private String contact;
    private String password;
    private String confirmPassword;
    private String dateOfBirth; // Ngày sinh
    private String gender;      // Giới tính

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String[] validateInput() {
        String[] errors = new String[7]; // Mảng chứa các lỗi

        errors[0] = validateFullName(fullName);
        errors[1] = validateGender(gender);
        errors[2] = validateDateOfBirth(dateOfBirth);
        errors[3] = validateEmail(email);
        errors[4] = validatePassword(password);
        errors[5] = validateConfirmPassword(password, confirmPassword);
        errors[6] = validateContact(contact);

        return errors;
    }

    private String validateFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Full name cannot be empty!";
        }
        return null; // Không có lỗi
    }

    private String validateEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
            return "Email must be in the correct format!";
        }
        return null; // Không có lỗi
    }

    private String validatePassword(String password) {
        if (password.length() < 8 ||
                !password.matches(".*[a-z].*") ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[0-9].*") ||
                !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return "Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers, and special characters!";
        }
        return null; // Không có lỗi
    }

    private String validateConfirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Confirm password does not match!";
        }
        return null; // Không có lỗi
    }

    private String validateContact(String contact) {
        if (contact.length() != 10 || !contact.startsWith("0")) {
            return "Phone number must start with 0 and have 10 digits!";
        }
        if (!contact.matches("\\d+")) {
            return "Phone number must contain only digits!";
        }
        return null;
    }

    private String validateDateOfBirth(String dateOfBirth) {
        try {
            LocalDate birthDate = LocalDate.parse(dateOfBirth); // Format phải là "yyyy-MM-dd"
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(birthDate, currentDate).getYears();

            if (age < 16) {
                return "Age must be at least 16 years!";
            }
        } catch (DateTimeParseException e) {
            return "Date of birth must be in the format yyyy-MM-dd!";
        }
        return null; // Không có lỗi
    }

    private String validateGender(String gender) {
        if (gender == null || gender.trim().isEmpty() ||
                (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"))) {
            return "Gender must be selected!";
        }
        return null;
    }
}