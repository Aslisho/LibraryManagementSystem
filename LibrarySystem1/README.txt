# Library Management System

A Java-based library management system with a graphical user interface built using Swing. The system provides separate interfaces for administrators and regular users to manage books and library operations.

## Features

### User Management
- User registration
- Admin registration
- Login system with role-based access (admin/user)
- Member management (add/remove users)

### Admin Features
- Add new books
- Remove books
- View all books
- Generate books report
- Generate members report 
- Register new admin accounts
- Remove members

### User Features  
- View available books
- Borrow books
- Return books

### Data Persistence
- User data and book information is saved locally using serialization
- Data automatically loads on startup and saves after modifications

## Technical Details

### Core Classes
- **LibrarySystem**: Main system class containing the GUI and core functionality
- **Book**: Book model class with title, author and ISBN
- **Person**: Base person class with name, phone and birth date
- **User**: User model class extending Person
- **DataManager**: Handles data persistence using file I/O

### Data Storage
The system uses two data files:
- users.dat: Stores user information
- books.dat: Stores book information

### UI Components
Built using Java Swing with:
- JFrames for windows
- JPanel layouts
- Input forms
- Dialog boxes
- Event handling

## Getting Started

1. Compile all Java files
2. Run LibrarySystem.java to start the application
3. Default admin credentials:
   - Username: admin1
   - Password: admin123

## Dependencies
- Java SE Development Kit (JDK)
- Java Swing Library

## Authors
- Baidya Joydeep
- Elijah Junior Integrity  
- Habib Muhammad Tijjani
- Saha Pritom
- Zidan Abdullah Al
- MD SAJIN MAZBA ERAM
- Yusuf Abdulrahman Musa
- Muzofirshoev Aslisho

## Copyright
© 2024 Library System