package game;

public class ScoreManager {
    private int score;
    private boolean doubleScoreActive;

    public ScoreManager() {
        score = 0;
        doubleScoreActive = false;
    }

    public void addScore(int points) {
        if (doubleScoreActive) {
            points *= 2;
        }
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void enableDoubleScore() {
        doubleScoreActive = true;
    }

    public void disableDoubleScore() {
        doubleScoreActive = false;
    }
}
