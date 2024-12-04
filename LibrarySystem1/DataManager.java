import java.io.*;
import java.util.ArrayList;

public class DataManager {
    private static final String USERS_FILE = "users.dat";
    private static final String BOOKS_FILE = "books.dat";

    public static void saveData(ArrayList<User> users, ArrayList<Book> books) {
        try (ObjectOutputStream usersOut = new ObjectOutputStream(new FileOutputStream(USERS_FILE));
             ObjectOutputStream booksOut = new ObjectOutputStream(new FileOutputStream(BOOKS_FILE))) {
            usersOut.writeObject(users);
            booksOut.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData(ArrayList<User> users, ArrayList<Book> books) {
        try {
            if (new File(USERS_FILE).exists()) {
                try (ObjectInputStream usersIn = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
                    ArrayList<User> loadedUsers = (ArrayList<User>) usersIn.readObject();
                    users.clear();
                    users.addAll(loadedUsers);
                }
            }
            
            if (new File(BOOKS_FILE).exists()) {
                try (ObjectInputStream booksIn = new ObjectInputStream(new FileInputStream(BOOKS_FILE))) {
                    ArrayList<Book> loadedBooks = (ArrayList<Book>) booksIn.readObject();
                    books.clear();
                    books.addAll(loadedBooks);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}