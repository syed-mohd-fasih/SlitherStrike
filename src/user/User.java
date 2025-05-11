package user;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final String username;

    // Map structure: mode -> difficulty -> highScore
    private final Map<String, Map<String, Integer>> scores;

    public User(String username) {
        this.username = username;
        this.scores = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setHighScore(String mode, String difficulty, int score) {
        scores.putIfAbsent(mode, new HashMap<>());
        Map<String, Integer> difficultyMap = scores.get(mode);
        int current = difficultyMap.getOrDefault(difficulty, 0);
        if (score > current) {
            difficultyMap.put(difficulty, score);
        }
    }

    public int getHighScore(String mode, String difficulty) {
        return scores.getOrDefault(mode, new HashMap<>()).getOrDefault(difficulty, 0);
    }

    public Map<String, Map<String, Integer>> getAllScores() {
        return scores;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(":");
        for (Map.Entry<String, Map<String, Integer>> entry : scores.entrySet()) {
            String mode = entry.getKey();
            for (Map.Entry<String, Integer> diffScore : entry.getValue().entrySet()) {
                sb.append(mode).append("|").append(diffScore.getKey()).append("|").append(diffScore.getValue()).append(";");
            }
        }
        return sb.toString();
    }

    public static User deserialize(String line) {
        String[] parts = line.split(":");
        User user = new User(parts[0]);

        if (parts.length > 1) {
            String[] scoreParts = parts[1].split(";");
            for (String s : scoreParts) {
                if (s.isEmpty()) continue;
                String[] tokens = s.split("\\|");
                if (tokens.length == 3) {
                    String mode = tokens[0];
                    String difficulty = tokens[1];
                    int score = Integer.parseInt(tokens[2]);
                    user.setHighScore(mode, difficulty, score);
                }
            }
        }

        return user;
    }
}
