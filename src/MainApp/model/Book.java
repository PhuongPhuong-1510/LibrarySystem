package MainApp.model;

public class Book {
    private String bookID;
    private String bookName;
    private String image;
    private String author;
    private String category;
    private String language;
    private int total;
    private String curent;
    private String Position;
    private String action;

    public Book() {}

    public Book(String bookID, String bookName, String image, String author, String category, String language, int total, String curent, String Position, String action) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.image = image;
        this.author = author;
        this.category = category;
        this.language = language;
        this.total = total;
        this.curent = curent;
        this.Position = Position;
        this.action = action;
    }
    public String getBookID() {
        return bookID;
    }
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public String getCurent() {
        return curent;
    }
    public void setCurent(String curent) {
        this.curent = curent;
    }
    public String getPosition() {
        return Position;
    }
    public void setPosition(String position) {
        Position = position;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

}
