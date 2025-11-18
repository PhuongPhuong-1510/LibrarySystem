<h1 align="center">📚 Library Management System – Java Spring Boot (MVC)</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot&style=flat-square"/>
  <img src="https://img.shields.io/badge/Java-17-orange?logo=openjdk&style=flat-square"/>
  <img src="https://img.shields.io/badge/MVC-Architecture-blue?style=flat-square"/>
  <img src="https://img.shields.io/badge/Status-Active-success?style=flat-square"/>
</p>

<p align="center">
  ✨ A modern digital library system — designed with MVC architecture, secure, high-performance, and scalable ✨  
  <br>
  <sub>Made with ❤️ by a Java Developer</sub>
</p>

---

## 📘 Table of Contents
- [Introduction](#-introduction)
- [MVC Architecture](#️-mvc-architecture)
- [Key Features](#-key-features)
- [REST API](#-rest-api)
- [How to Run](#️-how-to-run)

---

## 🚀 Introduction

**Library Management System** is built with **Java Spring Boot** following the **MVC architecture** to simulate real-world library operations:

- 👨‍💼 **Admin**: manages books, users, statistics, notifications  
- 🎓 **Student/User**: borrow/return books, read online, receive notifications, update profile  

🎯 Focus: **User-friendly – Scalable – High Performance – Secure**

---

## 🏗️ MVC Architecture

### 🧩 **Model**
- Entities: `Book`, `User`, `BorrowRecord`, `Category`  
- DTOs for safe data transfer  
- JPA Repositories for database interactions  

### 🌐 **Controller**
- Handles API requests  
- Routes requests to the appropriate service layer  
- No business logic here  

### ⚙️ **Service**
- Contains all business logic  
- Validation, borrowing rules, user management  
- External API integration (ISBN metadata lookup)  

### 🎨 **View**
- JSON Response (backend API)  
- Optional UI with Thymeleaf/React  

---

## ✨ Key Features

### 📘 **1. Book Management**
- Full CRUD operations  
- Assign categories, authors  
- Auto update stock quantity  
- 🔍 Search + Pagination  
- 🔗 External API integration via ISBN  

---

### 🔄 **2. Borrow / Return**
- Online borrowing  
- Extend borrow duration  
- Borrow/return history  
- Stock check  
- Maximum borrow limit  

---

### 👤 **3. User Management**
- Roles: **Admin / User**  
- Profile update  
- Password change  
- ✉️ Email notifications on password change or borrow  

---

### 📖 **4. Online Reading**
- View PDF / EPUB  
- Fast loading  
- Basic anti-download  

---

### 🔔 **5. Notification System**
- Emails for borrow/return  
- Return reminders  
- Password reset notifications  
- Admin broadcast  

---

### ⚡ **6. Performance Optimization**
- Redis / Ehcache caching  
- Pagination for lists  
- Optimized Hibernate queries  
- AOP logging  

---

### 🔒 **7. Security**
- Spring Security + JWT Authentication  
- BCrypt password hashing  
- Role-based authorization  

---

## 📡 REST API

### 🔑 **Authentication**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | Login |
| POST | `/auth/register` | Register |
| POST | `/auth/change-password` | Change password |

### 📚 **Book**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/books` | Get list of books |
| POST | `/books` | Admin adds a book |
| PUT | `/books/{id}` | Update a book |
| DELETE | `/books/{id}` | Delete a book |

### 📘 **Borrow / Return**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/borrow` | Borrow a book |
| POST | `/return` | Return a book |
| GET | `/borrow/history` | Borrowing history |

---

## ⚙️ How to Run

### 1️⃣ Clone
```bash
git clone https://github.com/PhuongPhuong-1510/LibrarySystem.git
cd library-system




