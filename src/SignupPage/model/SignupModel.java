package SignupPage.model;

public class SignupModel {
    private String email;
    private String contact;
    private String password;
    private String confirmPassword;

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

    public String[] validateInput() {
        String[] errors = new String[4]; // Mảng chứa các lỗi

        errors[0] = validateEmail(email);
        errors[1] = validatePassword(password);
        errors[2] = validateConfirmPassword(password, confirmPassword);
        errors[3] = validateContact(contact);

        return errors; // Trả về mảng chứa các lỗi
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

}
