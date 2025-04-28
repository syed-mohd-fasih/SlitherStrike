package game;

public class ScoreManager {
    private int score;

    public ScoreManager() {
        score = 0;
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void doubleScore() {
        score *= 2;  // Double the current score
    }
}
