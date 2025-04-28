package game;

import java.awt.*;
import java.util.Random;

public class PowerUp {
    private Point position;
    private Type type;
    private int duration;
    private long spawnTime;

    public enum Type {
        SPEED_UP, INVINCIBILITY, DOUBLE_SCORE
    }

    public PowerUp(int x, int y, Type type) {
        this.position = new Point(x, y);
        this.type = type;
        this.duration = 10000; // 10 seconds
        this.spawnTime = System.currentTimeMillis();
    }

    public Point getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }

    public void applyEffect(Game game) {
        switch (type) {
            case SPEED_UP -> game.getSnake().increaseSpeed();
            case INVINCIBILITY -> game.getSnake().setInvincible(true);
            case DOUBLE_SCORE -> game.getScoreManager().doubleScore();
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - spawnTime > duration;
    }

    public static PowerUp generateRandomPowerUp(int gridWidth, int gridHeight) {
        Random rand = new Random();
        int x = rand.nextInt(gridWidth);
        int y = rand.nextInt(gridHeight);
        Type type = Type.values()[rand.nextInt(Type.values().length)];
        return new PowerUp(x, y, type);
    }
}
