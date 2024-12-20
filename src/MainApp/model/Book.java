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
    private String URL;


    public Book(String id, String title, String imagePath, String author, String language, int total, String current, String position) {}

    public Book(String bookID, String bookName, String image, String author, String category, String language, int total, String curent, String Position, String URL) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.image = image;
        this.author = author;
        this.category = category;
        this.language = language;
        this.total = total;
        this.curent = curent;
        this.Position = Position;
        this.URL = URL;

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
    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }


}
