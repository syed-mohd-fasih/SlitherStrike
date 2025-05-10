package game;

import java.awt.*;
import java.util.Random;

public class PowerUp {
    private Point position;
    private Type type;
    private long spawnTime;
    private long activationTime;

    public enum Type {
        SPEED_UP, INVINCIBILITY, DOUBLE_SCORE, EXPLODING_FRUIT, FREEZE_TIME
    }

    public PowerUp(int x, int y, Type type) {
        this.position = new Point(x, y);
        this.type = type;
        this.spawnTime = System.currentTimeMillis();
    }

    public Point getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    public long getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(long activationTime) {
        this.activationTime = activationTime;
    }

    public static PowerUp generateRandomPowerUp(int gridWidth, int gridHeight) {
        Random rand = new Random();
        int x = rand.nextInt(gridWidth);
        int y = rand.nextInt(gridHeight);
        Type type = Type.values()[rand.nextInt(Type.values().length)];
        return new PowerUp(x, y, type);
    }
}
