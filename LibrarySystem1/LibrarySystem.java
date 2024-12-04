import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class LibrarySystem {
    private static final ArrayList<Book> books = new ArrayList<>();
    private static final ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    private static final ArrayList<String> borrowedBooks = new ArrayList<>();

    static {
        users.add(new User("admin1", "admin123", "admin"));
        users.add(new User("admin2", "admin456", "admin"));
        users.add(new User("user1", "user123", "user"));
    }
    
    private static final class Constants {
        
        
        static final String LOGIN_NAMETAG = "© 2024 Library System - Log in";
        static final String REGISTRATION_NAMETAG = "© 2024 Library System - User Registration";
        static final String ADMIN_NAMETAG = "© 2024 Library System - Administration Panel";
        static final String USER_NAMETAG = "© 2024 Library System - User Dashboard";
        static final String AUTH_ADMIN_NAMETAG = "© 2024 Library System - Admin Authentication";
        static final String AUTH_USER_NAMETAG = "© 2024 Library System - User Authentication";
        static final String ADMIN_REG_NAMETAG = "© 2024 Library System - Admin Registration";
    }

    private static JLabel createNametag(String text) {
        JLabel nametag = new JLabel(text, SwingConstants.CENTER);
        nametag.setFont(new Font("Arial", Font.ITALIC, 10));
        nametag.setForeground(new Color(100, 100, 100));
        nametag.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return nametag;
    }

    private static JPanel createPanelWithNametag(int rows, int cols, String nametagText) {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel(new GridLayout(rows, cols));
        
        wrapperPanel.add(contentPanel, BorderLayout.CENTER);
        wrapperPanel.add(createNametag(nametagText), BorderLayout.SOUTH);
        
        return contentPanel;
    }

    public static void main(String[] args) {
        DataManager.loadData(users, books);
        SwingUtilities.invokeLater(() -> showLoginScreen());
    }

    private static void showLoginScreen() {
        JFrame frame = new JFrame("Library System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        JPanel panel = createPanelWithNametag(4, 1, Constants.LOGIN_NAMETAG);

        JButton adminButton = new JButton("Admin Login");
        JButton userButton = new JButton("User Login");
        JButton registerButton = new JButton("Register New User");

        adminButton.addActionListener(e -> {
            frame.dispose();
            authenticateUser("admin");
        });

        userButton.addActionListener(e -> {
            frame.dispose();
            authenticateUser("user");
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            showRegistrationScreen();
        });

        panel.add(new JLabel("Welcome to the Library System", SwingConstants.CENTER));
        panel.add(adminButton);
        panel.add(userButton);
        panel.add(registerButton);

        frame.add(panel.getParent());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void showRegistrationScreen() {
        JFrame frame = new JFrame("User Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
    
        JPanel panel = createPanelWithNametag(8, 1, Constants.REGISTRATION_NAMETAG);
        panel.setLayout(new GridLayout(8, 1));
    
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back to Login");
    
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
    
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }
    
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords don't match!");
                return;
            }
    
            if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
                JOptionPane.showMessageDialog(frame, "Username already exists!");
                return;
            }
    
            users.add(new User(username, password, "user"));
            DataManager.saveData(users, books);
            JOptionPane.showMessageDialog(frame, "Registration successful!");
            frame.dispose();
            showLoginScreen();
        });
    
        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });
    
        panel.add(new JLabel("Register New User", SwingConstants.CENTER));
        panel.add(new JLabel("Username:", SwingConstants.CENTER));
        panel.add(usernameField);
        panel.add(new JLabel("Password:", SwingConstants.CENTER));
        panel.add(passwordField);
        panel.add(new JLabel("Confirm Password:", SwingConstants.CENTER));
        panel.add(confirmPasswordField);
        panel.add(registerButton, SwingConstants.CENTER);
        panel.add(backButton);
    
        frame.add(panel.getParent());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private static void authenticateUser(String role) {
        JFrame frame = new JFrame(role.equals("admin") ? "Admin Login" : "User Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
        
        String nametagText = role.equals("admin") ? 
        Constants.AUTH_ADMIN_NAMETAG : Constants.AUTH_USER_NAMETAG;

        JPanel panel = createPanelWithNametag(6, 1, nametagText);

        panel.setLayout(new GridLayout(6, 1));

        

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        panel.add(new JLabel("Username:", SwingConstants.CENTER));
        panel.add(usernameField);
        panel.add(new JLabel("Password:", SwingConstants.CENTER));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = users.stream()
                .filter(u -> u.getUsername().equals(username) 
                    && u.getPassword().equals(password) 
                    && u.getRole().equals(role))
                .findFirst()
                .orElse(null);

            if (user != null) {
                currentUser = user;
                frame.dispose();
                if (role.equals("admin")) {
                    showAdminPanel();
                } else {
                    showUserPanel();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        frame.add(panel.getParent());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static void showAdminPanel() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = createPanelWithNametag(8, 1, Constants.ADMIN_NAMETAG);
        panel.setLayout(new GridLayout(8, 1));

        JButton addBookButton = new JButton("Add Book");
        JButton viewBooksButton = new JButton("View Books");
        JButton removeBookButton = new JButton("Remove Book");
        JButton booksReportButton = new JButton("Books Report");
        JButton membersReportButton = new JButton("Members Report");
        JButton registerAdminButton = new JButton("Register New Admin");
        JButton removeMemberButton = new JButton("Remove Member");
        JButton backButton = new JButton("Back");

        addBookButton.addActionListener(e -> addBook());
        viewBooksButton.addActionListener(e -> viewBooks());
        removeBookButton.addActionListener(e -> removeBook());
        booksReportButton.addActionListener(e -> generateBooksReport());
        membersReportButton.addActionListener(e -> generateMembersReport());
        registerAdminButton.addActionListener(e -> showAdminRegistrationScreen());
        removeMemberButton.addActionListener(e -> removeMember());

        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        panel.add(addBookButton);
        panel.add(viewBooksButton);
        panel.add(removeBookButton);
        panel.add(booksReportButton);
        panel.add(membersReportButton);
        panel.add(registerAdminButton);
        panel.add(removeMemberButton);
        panel.add(backButton);

        frame.add(panel.getParent());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


private static void removeMember() {
        StringBuilder membersList = new StringBuilder("Members List:\n");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            membersList.append(i + 1)
                      .append(". ")
                      .append(user.getUsername())
                      .append(" (")
                      .append(user.getRole())
                      .append(")\n");
        }

        String memberIndexStr = JOptionPane.showInputDialog(membersList + "\nEnter Member Number to Remove:");

        try {
            int memberIndex = Integer.parseInt(memberIndexStr) - 1;

            if (memberIndex >= 0 && memberIndex < users.size()) {
                User userToRemove = users.get(memberIndex);
            
                // Prevent removing the last admin
                long adminCount = users.stream()
                                     .filter(u -> u.getRole().equals("admin"))
                                     .count();
            
                if (userToRemove.getRole().equals("admin") && adminCount <= 1) {
                    JOptionPane.showMessageDialog(null, "Cannot remove the last administrator!");
                    return;
                }
            
                // Prevent removing currently logged-in user
                if (userToRemove.getUsername().equals(currentUser.getUsername())) {
                    JOptionPane.showMessageDialog(null, "Cannot remove your own account while logged in!");
                    return;
                }

                users.remove(memberIndex);
                DataManager.saveData(users, books);
                JOptionPane.showMessageDialog(null, "Member removed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid member number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
}

private static void showAdminRegistrationScreen() {
    JFrame frame = new JFrame("Admin Registration");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(300, 250);

    JPanel panel = createPanelWithNametag(7, 1, Constants.ADMIN_REG_NAMETAG);
    panel.setLayout(new GridLayout(7, 1));

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPasswordField = new JPasswordField();
    JButton registerButton = new JButton("Register Admin");
    JButton backButton = new JButton("Back");

    registerButton.addActionListener(e -> {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords don't match!");
            return;
        }

        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            JOptionPane.showMessageDialog(frame, "Username already exists!");
            return;
        }

        users.add(new User(username, password, "admin"));
        DataManager.saveData(users, books);
        JOptionPane.showMessageDialog(frame, "Admin registration successful!");
        frame.dispose();
    });

    backButton.addActionListener(e -> frame.dispose());

    panel.add(new JLabel("Username:", SwingConstants.CENTER));
    panel.add(usernameField);
    panel.add(new JLabel("Password:", SwingConstants.CENTER));
    panel.add(passwordField);
    panel.add(new JLabel("Confirm Password:", SwingConstants.CENTER));
    panel.add(confirmPasswordField);
    panel.add(registerButton);
    panel.add(backButton);

    frame.add(panel);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}


private static void generateBooksReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== LIBRARY BOOKS REPORT ===\n\n");
        report.append("Total Books: ").append(books.size()).append("\n\n");
        report.append("Books List:\n");
    
        for (int i = 0; i < books.size(); i++) {
            report.append(i + 1).append(". ").append(books.get(i)).append("\n");
        }
    
        report.append("\nBorrowed Books: ").append(borrowedBooks.size()).append("\n");
        for (int i = 0; i < borrowedBooks.size(); i++) {
            report.append(i + 1).append(". ").append(borrowedBooks.get(i)).append("\n");
        }

        // Display report in a scrollable text area
        JTextArea textArea = new JTextArea(report.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
    
        JOptionPane.showMessageDialog(null, scrollPane, "Books Report", JOptionPane.INFORMATION_MESSAGE);
}

private static void generateMembersReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== LIBRARY MEMBERS REPORT ===\n\n");
        report.append("Total Members: ").append(users.size()).append("\n\n");
        report.append("Members List:\n");
    
        // Count members by role
        long adminCount = users.stream().filter(u -> u.getRole().equals("admin")).count();
        long userCount = users.stream().filter(u -> u.getRole().equals("user")).count();
    
        report.append("Administrators: ").append(adminCount).append("\n");
        report.append("Regular Users: ").append(userCount).append("\n\n");
    
        report.append("Detailed Members List:\n");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            report.append(i + 1)
                  .append(". Username: ")
                  .append(user.getUsername())
                  .append(" (")
                  .append(user.getRole())
                  .append(")\n");
        }

        // Display report in a scrollable text area
        JTextArea textArea = new JTextArea(report.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
    
        JOptionPane.showMessageDialog(null, scrollPane, "Members Report", JOptionPane.INFORMATION_MESSAGE);
}

    private static void showUserPanel() {
        JFrame frame = new JFrame("User Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = createPanelWithNametag(4, 1, Constants.USER_NAMETAG);
        panel.setLayout(new GridLayout(4, 1));

        JButton viewBooksButton = new JButton("View Books");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton backButton = new JButton("Back");

        viewBooksButton.addActionListener(e -> viewBooks());
        borrowBookButton.addActionListener(e -> borrowBook());
        returnBookButton.addActionListener(e -> returnBook());
        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        panel.add(viewBooksButton);
        panel.add(borrowBookButton);
        panel.add(returnBookButton);
        panel.add(backButton);

        frame.add(panel.getParent());
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    private static void addBook() {
        String title = JOptionPane.showInputDialog("Enter Book Title:");
        String author = JOptionPane.showInputDialog("Enter Book Author:");

        if (title != null && author != null) {
            books.add(new Book(title, author,"000000000"));
            JOptionPane.showMessageDialog(null, "Book added successfully!");
        }
    }

    private static void viewBooks() {
        StringBuilder bookList = new StringBuilder("Books in the Library:\n");

        for (int i = 0; i < books.size(); i++) {
            bookList.append(i + 1).append(". ").append(books.get(i)).append("\n");
        }

        JOptionPane.showMessageDialog(null, bookList.length() > 0 ? bookList.toString() : "No books available.");
    }

    private static void removeBook() {
        String bookIndexStr = JOptionPane.showInputDialog("Enter Book Number to Remove:");

        try {
            int bookIndex = Integer.parseInt(bookIndexStr) - 1;

            if (bookIndex >= 0 && bookIndex < books.size()) {
                books.remove(bookIndex);
                JOptionPane.showMessageDialog(null, "Book removed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid book number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }

    private static void borrowBook() {
        String bookIndexStr = JOptionPane.showInputDialog("Enter Book Number to Borrow:");

        try {
            int bookIndex = Integer.parseInt(bookIndexStr) - 1;

            if (bookIndex >= 0 && bookIndex < books.size()) {
                borrowedBooks.add(books.get(bookIndex).toString());
                books.remove(bookIndex);
                JOptionPane.showMessageDialog(null, "Book borrowed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid book number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }

    private static void returnBook() {
        StringBuilder borrowedList = new StringBuilder("Borrowed Books:\n");

        for (int i = 0; i < borrowedBooks.size(); i++) {
            borrowedList.append(i + 1).append(". ").append(borrowedBooks.get(i)).append("\n");
        }

        if (borrowedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No borrowed books to return.");
            return;
        }

        String bookIndexStr = JOptionPane.showInputDialog(borrowedList + "\nEnter Book Number to Return:");

        try {
            int bookIndex = Integer.parseInt(bookIndexStr) - 1;

            if (bookIndex >= 0 && bookIndex < borrowedBooks.size()) {
                String returnedBook = borrowedBooks.remove(bookIndex);
                String[] bookDetails = returnedBook.split(" by ");
                books.add(new Book(bookDetails[0], bookDetails[1],"0000000"));
                JOptionPane.showMessageDialog(null, "Book returned successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid book number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }
    
}
