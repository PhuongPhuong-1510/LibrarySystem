package LoginPage.model;

public class LoginModel {
    private String userName;
    private String passWord;
    public boolean ktUser;

    public boolean getKtUser() {
        return ktUser;
    }
    public void setKtUser(boolean ktUser) {
        this.ktUser = ktUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
