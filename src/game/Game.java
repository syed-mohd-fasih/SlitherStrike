package game;

import utils.CollisionManager;
import utils.PowerUpManager;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Game {
    private Snake snake;
    private Fruit fruit;
    private List<Obstacle> obstacles;
    private List<PowerUp> powerUps;
    private List<PowerUp> activePowerUps;
    private ScoreManager scoreManager;

    private boolean gameOver;
    private int width, height;
    private Random random;
    private String difficulty;

    private PowerUpManager powerUpManager;

    private static final long POWER_UP_DURATION = 5000; // 5 seconds

    public Game(int width, int height, String difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.random = new Random();
        this.powerUpManager = new PowerUpManager(this);
        this.activePowerUps = new ArrayList<>();

        reset();
    }

    public void reset() {
        snake = new Snake();
        fruit = spawnFruit();
        obstacles = new ArrayList<>();
        powerUps = new ArrayList<>();
        activePowerUps.clear();
        scoreManager = new ScoreManager();
        gameOver = false;
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
            if (!hasActivePowerUp(PowerUp.Type.INVINCIBILITY)) {
                gameOver = true;
                return;
            }
        }

        if (CollisionManager.checkFruitCollision(snake, fruit, scoreManager)) {
            fruit = spawnFruit();
        }

        List<PowerUp> toRemove = new ArrayList<>();
        CollisionManager.checkPowerUpCollision(snake, powerUps, toRemove, this);
        powerUps.removeAll(toRemove);

        updateActivePowerUps();
        powerUpManager.update();
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

//    public void activatePowerUp(PowerUp powerUp) {
//        activePowerUps.add(powerUp);
//
//        switch (powerUp.getType()) {
//            case SPEED_UP -> snake.increaseSpeed();
//            case INVINCIBILITY -> {
//                // No immediate action, handled during collision checks
//            }
//            case DOUBLE_SCORE -> scoreManager.enableDoubleScore();
//        }
//    }

    private void updateActivePowerUps() {
        long currentTime = System.currentTimeMillis();
        Iterator<PowerUp> iterator = activePowerUps.iterator();

        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            if (currentTime - powerUp.getActivationTime() > POWER_UP_DURATION) {
                deactivatePowerUp(powerUp);
                iterator.remove();
            }
        }
    }

    private void deactivatePowerUp(PowerUp powerUp) {
        switch (powerUp.getType()) {
            case SPEED_UP -> snake.resetSpeed();
            case INVINCIBILITY -> {
                // No persistent effect to undo
            }
            case DOUBLE_SCORE -> scoreManager.disableDoubleScore();
        }
    }

    private boolean hasActivePowerUp(PowerUp.Type type) {
        for (PowerUp p : activePowerUps) {
            if (p.getType() == type) {
                return true;
            }
        }
        return false;
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

    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }

    public List<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public Map<PowerUp.Type, Long> getActivePowerUpTimers() {
        return powerUpManager.getActivePowerUpTimers();
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
