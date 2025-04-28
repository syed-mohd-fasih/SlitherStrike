package game;

import java.awt.*;
import java.util.Random;

public class PowerUp {
    private Point position;
    private Type type;

    public enum Type {
        SPEED_UP, INVINCIBILITY, DOUBLE_SCORE
    }

    public PowerUp(int x, int y, Type type) {
        position = new Point(x, y);
        this.type = type;
    }

    public Point getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }

    // Method to apply the power-up effect on the game
    public void applyEffect(Game game) {
        switch (type) {
            case SPEED_UP:
                game.getSnake().increaseSpeed();  // Implement this method in Snake class
                break;
            case INVINCIBILITY:
                game.getSnake().setInvincible(true);  // Implement this method in Snake class
                break;
            case DOUBLE_SCORE:
                game.getScoreManager().doubleScore();  // Implement this method in ScoreManager class
                break;
        }
    }

    // Randomly generate a power-up at a given position
    public static PowerUp generateRandomPowerUp(int gridWidth, int gridHeight) {
        Random rand = new Random();
        int x = rand.nextInt(gridWidth);
        int y = rand.nextInt(gridHeight);
        Type type = Type.values()[rand.nextInt(Type.values().length)];
        return new PowerUp(x, y, type);
    }
}
