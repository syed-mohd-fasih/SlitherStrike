package game;

import speciallevels.*;
import user.UserManager;
import utils.CollisionManager;
import utils.PowerUpManager;

import java.util.*;
import java.util.List;

public class Game {
    private Snake snake;
    private Fruit fruit;
    private List<Obstacle> obstacles;
    private List<PowerUp> powerUps;
    private List<PowerUp> activePowerUps;

    private boolean gameOver;
    private final int width, height;
    private final Random random;
    private final String difficulty;
    private final String level;

    private final ScoreManager scoreManager;
    private final PowerUpManager powerUpManager;

    private SpecialLevelMode currentSpecialLevel;

    private static final long POWER_UP_DURATION = 5000; // 5 seconds

    public Game(int width, int height, String level, String difficulty) {
        this.width = width;
        this.height = height;
        this.random = new Random();
        this.difficulty = difficulty;
        this.level = level;

        this.powerUpManager = new PowerUpManager(this);
        this.scoreManager = new ScoreManager();

        this.activePowerUps = new ArrayList<>();

        this.snake = new Snake();
        this.fruit = spawnFruit();
        this.obstacles = new ArrayList<>();
        this.powerUps = new ArrayList<>();

        gameOver = false;

        spawnObstacles();

        switch (difficulty) {
            case "Invisibility Mode":
                activateSpecialLevel(new InvisibilityMode());
                break;
            case "Unlockable Quadrants Mode":
                activateSpecialLevel(new UnlockableQuadrantsMode());
                break;
            case "Flip Map Mode":
                activateSpecialLevel(new FlipMapMode());
                break;
        }
    }

    public void update() {
        if (gameOver) return;

        if (snake.getPendingDirection() != null) {
            snake.applyPendingDirection();
        }
        snake.move();

        if (CollisionManager.checkWallCollision(snake, width, height)
                || CollisionManager.checkSelfCollision(snake)
                || CollisionManager.checkObstacleCollision(snake, obstacles)) {
            if (!snake.isInvincible()) {
                gameOver = true;
                UserManager.getCurrentUser().setHighScore(level, difficulty, scoreManager.getScore());
                UserManager.saveUsers();
                UserManager.loadUsers();
                return;
            }
        }

        if (CollisionManager.checkFruitCollision(snake, fruit, scoreManager)) {
            fruit = spawnFruit();
        }

        List<PowerUp> toRemove = new ArrayList<>();
        CollisionManager.checkPowerUpCollision(snake, powerUps, toRemove, this);
        powerUps.removeAll(toRemove);

        powerUpManager.update();

        if (currentSpecialLevel != null && currentSpecialLevel.isActive()) {
            currentSpecialLevel.update(this);
        }
    }

    private Fruit spawnFruit() {
        int x, y;

        if (difficulty.equals("Unlockable Quadrants Mode")) {
            int score = scoreManager.getScore();
            if (score < 500) {
                x = random.nextInt(width / 2);
                y = random.nextInt(height / 2);
            } else if (score < 1000) {
                x = random.nextInt(width);
                y = random.nextInt(height / 2);
            } else {
                x = random.nextInt(width);
                y = random.nextInt(height);
            }
            return new Fruit(x, y);
        }

        x = random.nextInt(width);
        y = random.nextInt(height);
        return new Fruit(x, y);
    }

    private void spawnObstacles() {
        int count = switch (difficulty) {
            case "Easy" -> 5;
            case "Medium" -> 10;
            case "Hard" -> 15;
            default -> 12;
        };

        for (int i = 0; i < count; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            obstacles.add(new Obstacle(x, y));
        }
    }

    public void spawnPowerUp() {
        int x = width;
        int y = height;

        if (difficulty.equals("Unlockable Quadrants Mode")) {
            int score = scoreManager.getScore();
            if (score < 500) {
                x = width / 2;
                y = height / 2;
            } else if (score < 1000) {
                x = width;
                y = height / 2;
            } else {
                x = width;
                y = height;
            }
        }

        powerUps.add(PowerUp.generateRandomPowerUp(x, y));
    }

    private boolean hasActivePowerUp(PowerUp.Type type) {
        for (PowerUp p : activePowerUps) {
            if (p.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public void activateSpecialLevel(SpecialLevelMode mode) {
        this.currentSpecialLevel = mode;
        mode.activate(this);
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

    public SpecialLevelMode getCurrentSpecialLevel() {
        return currentSpecialLevel;
    }

    public void setGameOver(boolean b) {
        gameOver = b;
    }

    public int getMapHeight() {
        return height;
    }

    public int getMapWidth() {
        return width;
    }
}
