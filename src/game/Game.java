package game;

import utils.CollisionManager;
import utils.PowerUpManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game {
    private Snake snake;
    private Fruit fruit;
    private List<Obstacle> obstacles;
    private List<PowerUp> powerUps;
    private ScoreManager scoreManager;

    private boolean gameOver;
    private int width, height;
    private Random random;
    private String difficulty;

    private PowerUpManager powerUpManager;

    public Game(int width, int height, String difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.random = new Random();
        this.powerUpManager = new PowerUpManager();

        reset();
    }

    public void reset() {
        snake = new Snake();
        fruit = spawnFruit();
        obstacles = new ArrayList<>();
        powerUps = new ArrayList<>();
        scoreManager = new ScoreManager();
        gameOver = false;
        powerUpManager.reset();
        spawnObstacles();
    }

    public void update() {
        if (gameOver) return;

        if (snake.getPendingDirection() != null) {
            snake.applyPendingDirection();
        }
        snake.move();

        Point head = snake.getBody().getFirst();

        if (CollisionManager.checkWallCollision(snake, width, height)
                || CollisionManager.checkSelfCollision(snake)
                || CollisionManager.checkObstacleCollision(snake, obstacles)) {
            gameOver = true;
            return;
        }

        if (CollisionManager.checkFruitCollision(snake, fruit, scoreManager)) {
            fruit = spawnFruit();
        }

        List<PowerUp> toRemove = new ArrayList<>();
        CollisionManager.checkPowerUpCollision(snake, powerUps, toRemove, this);
        powerUps.removeAll(toRemove);

        // PowerUp Manager controls spawn and removal
        powerUpManager.update(this);
    }

    private Fruit spawnFruit() {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        return new Fruit(x, y);
    }

    private void spawnObstacles() {
        int count = switch (difficulty) {
            case "easy" -> 3;
            case "medium" -> 6;
            case "hard" -> 10;
            default -> 3;
        };

        for (int i = 0; i < count; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            obstacles.add(new Obstacle(x, y));
        }
    }

    public void spawnPowerUp() {
        powerUps.add(PowerUp.generateRandomPowerUp(width, height));
    }

    // Getters
    public Snake getSnake() {
        return snake;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
