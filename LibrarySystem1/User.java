import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String password;
    private final String role;
    private final ArrayList<String> borrowedBooks;
    
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public ArrayList<String> getBorrowedBooks() { return borrowedBooks; }

    
}

