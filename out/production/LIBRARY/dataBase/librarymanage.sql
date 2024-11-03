CREATE DATABASE librarymanage;
USE librarymanage;

CREATE TABLE book (
                      bookID VARCHAR(50) PRIMARY KEY,
                      bookName VARCHAR(255) NOT NULL,
                      image VARCHAR(255),
                      author VARCHAR(255),
                      category VARCHAR(100),
                      language VARCHAR(50),
                      total INT,
                      curent VARCHAR(50),
                      Position VARCHAR(50),
                      action VARCHAR(50)
);

INSERT INTO book (bookID, bookName, image, author, category, language, total, curent, Position, action)
VALUES
    ('B001', 'Java Programming', 'path/to/image1.jpg', 'Author 1', 'Programming', 'English', 10, 'Available', 'A1', 'Borrowable'),
    ('B002', 'Python Essentials', 'path/to/image2.jpg', 'Author 2', 'Programming', 'English', 15, 'Available', 'A2', 'Borrowable');