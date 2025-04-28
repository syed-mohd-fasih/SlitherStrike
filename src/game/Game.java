package game;

import utils.CollisionManager;

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

    public Game(int width, int height, String difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        random = new Random();
        reset();
    }

    public void reset() {
        snake = new Snake();
        fruit = spawnFruit();
        obstacles = new ArrayList<>();
        powerUps = new ArrayList<>();
        scoreManager = new ScoreManager();
        gameOver = false;
        spawnObstacles(); // Spawning obstacles based on difficulty
    }

    public void update() {
        if (gameOver) return;

        if (snake.getPendingDirection() != null) {
            snake.applyPendingDirection();
        }

        snake.move();

        Point head = snake.getBody().getFirst();

        // Check collision with walls
        if (CollisionManager.checkWallCollision(snake, width, height)) {
            gameOver = true;
        }

        // Check collision with itself
        if (CollisionManager.checkSelfCollision(snake)) {
            gameOver = true;
            return;
        }

        // Check fruit collision
        if (CollisionManager.checkFruitCollision(snake, fruit, scoreManager)) {
            fruit = spawnFruit();  // Respawn fruit after collision
        }

        // Check obstacle collision
        if (CollisionManager.checkObstacleCollision(snake, obstacles)) {
            gameOver = true;
            return;
        }

        // Check power-up collisions
        List<PowerUp> toRemove = new ArrayList<>();
        CollisionManager.checkPowerUpCollision(snake, powerUps, toRemove, this);
        powerUps.removeAll(toRemove);

        // Spawn power-ups randomly based on difficulty
        if (random.nextInt(100) < getPowerUpSpawnRate()) {
            spawnPowerUp();
        }
    }

    private Fruit spawnFruit() {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        return new Fruit(x, y);
    }

    private void spawnObstacles() {
        int obstacleCount = getObstacleCount();
        for (int i = 0; i < obstacleCount; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            obstacles.add(new Obstacle(x, y));
        }
    }

    private void spawnPowerUp() {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        PowerUp powerUp = PowerUp.generateRandomPowerUp(width, height);
        powerUps.add(powerUp);
    }

    // Difficulty-based methods
    private int getObstacleCount() {
        return switch (difficulty) {
            case "easy" -> 3;
            case "medium" -> 6;
            case "hard" -> 10;
            default -> 3;
        };
    }

    private int getPowerUpSpawnRate() {
        return switch (difficulty) {
            case "easy" -> 10;  // Low spawn rate
            case "medium" -> 20;  // Moderate spawn rate
            case "hard" -> 30;  // High spawn rate
            default -> 10;
        };
    }

    // Getters for components
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

    public boolean isGameOver() {
        return gameOver;
    }
}
