package user;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String DATA_FILE = "data/userData.txt";
    private static final Map<String, User> users = new HashMap<>();
    private static User currentUser;

    static {
        loadUsers();
    }

    public static boolean login(String username) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username));
        }
        currentUser = users.get(username);
        return true;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Collection<User> getAllUsers() {
        return users.values();
    }

    public static void saveUsers() {
        try {
            File file = new File(DATA_FILE);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs(); // Create 'data/' directory if it doesn't exist
            }
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (User user : users.values()) {
                    writer.println(user.serialize());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUsers() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return; // Do nothing if file doesn't exist

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.deserialize(line);
                users.put(user.getUsername(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getHighestScoreAcrossUsers(String mode, String difficulty) {
        int max = 0;
        for (User user : users.values()) {
            int score = user.getHighScore(mode, difficulty);
            if (score > max) max = score;
        }
        return max;
    }
}
