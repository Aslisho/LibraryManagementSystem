import java.awt.Dimension;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;

public class ReportGenerator {
    private final String fileName;
    private final List<Book> books;
    private final List<String> borrowedBooks;
    private final List<User> users;

    public ReportGenerator(String reportTitle, String fileName, List<Book> books, 
                          List<String> borrowedBooks, List<User> users) {

        this.fileName = fileName;
        this.books = books;
        this.borrowedBooks = borrowedBooks;
        this.users = users;
    }

    public void generateBooksReport() {
        try {
            // Create report file
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            
            // Write header
            writer.println("=".repeat(50));
            writer.println("LIBRARY BOOKS REPORT");
            writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("=".repeat(50));
            writer.println();
            
            // Write book statistics
            writer.println("BOOKS STATISTICS");
            writer.println("-".repeat(30));
            writer.println("Total Books: " + books.size());
            writer.println("Total Borrowed Books: " + borrowedBooks.size());
            writer.println();
            
            // Write available books
            writer.println("AVAILABLE BOOKS");
            writer.println("-".repeat(30));
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                writer.println("Book #" + (i + 1));
                writer.println("Title: " + book.getTitle());
                writer.println("Author: " + book.getAuthor());
                writer.println("ISBN: " + book.getIsbn());
                writer.println("-".repeat(20));
            }
            writer.println();
            
            // Write borrowed books
            writer.println("BORROWED BOOKS");
            writer.println("-".repeat(30));
            for (int i = 0; i < borrowedBooks.size(); i++) {
                writer.println("Book #" + (i + 1));
                writer.println(borrowedBooks.get(i));
                writer.println("-".repeat(20));
            }
            
            // Write footer
            writer.println();
            writer.println("=".repeat(50));
            writer.println("End of Report");
            writer.println("=".repeat(50));
            
            writer.close();
            
            // Generate on-screen report
            StringBuilder screenReport = new StringBuilder();
            screenReport.append("=== LIBRARY BOOKS REPORT ===\n\n");
            screenReport.append("Total Books: ").append(books.size()).append("\n");
            screenReport.append("Total Borrowed Books: ").append(borrowedBooks.size()).append("\n\n");
            
            screenReport.append("Available Books:\n");
            screenReport.append("-".repeat(20)).append("\n");
            for (Book book : books) {
                screenReport.append("- ").append(book.toString()).append("\n");
            }
            
            screenReport.append("\nBorrowed Books:\n");
            screenReport.append("-".repeat(20)).append("\n");
            for (String book : borrowedBooks) {
                screenReport.append("- ").append(book).append("\n");
            }
    
            // Display report in scrollable text area
            JTextArea textArea = new JTextArea(screenReport.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            
            JOptionPane.showMessageDialog(null, scrollPane, "Books Report", JOptionPane.INFORMATION_MESSAGE);
            
            // Inform user about the file report
            JOptionPane.showMessageDialog(null, 
                "A detailed report has been generated: " + fileName, 
                "Report Generated", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error generating report: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void generateMembersReport() {
        try {
            // Create report file
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, true)); // Append mode
            
            // Write header
            writer.println("\n" + "=".repeat(50));
            writer.println("LIBRARY MEMBERS REPORT");
            writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("=".repeat(50));
            writer.println();
            
            // Write member statistics
            writer.println("MEMBER STATISTICS");
            writer.println("-".repeat(30));
            long adminCount = users.stream().filter(u -> u.getRole().equals("admin")).count();
            long userCount = users.stream().filter(u -> u.getRole().equals("user")).count();
            writer.println("Total Members: " + users.size());
            writer.println("Administrators: " + adminCount);
            writer.println("Regular Users: " + userCount);
            writer.println();
            
            // Write detailed member list
            writer.println("MEMBER LIST");
            writer.println("-".repeat(30));
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                writer.println("Member #" + (i + 1));
                writer.println("Username: " + user.getUsername());
                writer.println("Role: " + user.getRole());
                writer.println("-".repeat(20));
            }
            
            // Write footer
            writer.println();
            writer.println("=".repeat(50));
            writer.println("End of Report");
            writer.println("=".repeat(50));
            
            writer.close();
            
            // Generate on-screen report
            StringBuilder screenReport = new StringBuilder();
            screenReport.append("=== LIBRARY MEMBERS REPORT ===\n\n");
            screenReport.append("Member Statistics:\n");
            screenReport.append("-".repeat(20)).append("\n");
            screenReport.append("Total Members: ").append(users.size()).append("\n");
            screenReport.append("Administrators: ").append(adminCount).append("\n");
            screenReport.append("Regular Users: ").append(userCount).append("\n\n");
            
            screenReport.append("Member List:\n");
            screenReport.append("-".repeat(20)).append("\n");
            for (User user : users) {
                screenReport.append("- ")
                           .append(user.getUsername())
                           .append(" (")
                           .append(user.getRole())
                           .append(")\n");
            }
    
            // Display report in scrollable text area
            JTextArea textArea = new JTextArea(screenReport.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            
            JOptionPane.showMessageDialog(null, scrollPane, "Members Report", JOptionPane.INFORMATION_MESSAGE);
            
            // Inform user about the file report
            JOptionPane.showMessageDialog(null, 
                "The report has been appended to: " + fileName, 
                "Report Generated", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error generating report: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}