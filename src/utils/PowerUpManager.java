package utils;

import java.util.*;

import game.*;

public class PowerUpManager {
    private final Game game;
    private final Map<PowerUp.Type, ActivePowerUp> activePowerUps;
    private final int duration = 7500; // 7.5 seconds

    private static final int MIN_SPAWN_INTERVAL = 10000; // 10 seconds
    private long lastSpawnTime = 0;
    private int spawnedPowerUpsCount = 0; // NEW: count how many spawned
    private final Random random = new Random();

    public PowerUpManager(Game game) {
        this.game = game;
        this.activePowerUps = new HashMap<>();
    }

    public void applyPowerUp(PowerUp powerUp) {
        PowerUp.Type type = powerUp.getType();

        if (activePowerUps.containsKey(type)) {
            // Already active, reset timer
            activePowerUps.get(type).resetTimer();
        } else {
            // New activation
            activateEffect(type);
            activePowerUps.put(type, new ActivePowerUp(type));
        }
    }

    private void activateEffect(PowerUp.Type type) {
        switch (type) {
            case SPEED_UP -> game.getSnake().increaseSpeed();
            case INVINCIBILITY -> game.getSnake().setInvincible(true);
            case DOUBLE_SCORE -> game.getScoreManager().enableDoubleScore(); // (We'll fix ScoreManager)
        }
    }

    private void deactivateEffect(PowerUp.Type type) {
        switch (type) {
            case SPEED_UP -> game.getSnake().resetSpeed();
            case INVINCIBILITY -> game.getSnake().setInvincible(false);
            case DOUBLE_SCORE -> game.getScoreManager().disableDoubleScore();
        }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        Iterator<Map.Entry<PowerUp.Type, ActivePowerUp>> iterator = activePowerUps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<PowerUp.Type, ActivePowerUp> entry = iterator.next();
            if (entry.getValue().isExpired()) {
                deactivateEffect(entry.getKey());
                iterator.remove();
            }
        }

        // Don't spawn if we've reached the max allowed for this level
        int maxPowerUps = getMaxPowerUpsForLevel(game.getDifficulty());
        if (spawnedPowerUpsCount >= maxPowerUps) {
            return;
        }

        // Spawn logic
        if (currentTime - lastSpawnTime > MIN_SPAWN_INTERVAL) {
            int spawnChance = getSpawnChance(game.getDifficulty());

            if (random.nextInt(100) < spawnChance) {
                game.spawnPowerUp();
                spawnedPowerUpsCount++; // Increase total spawned count
                lastSpawnTime = currentTime;
            }
        }
    }

    private int getMaxPowerUpsForLevel(String difficulty) {
        return switch (difficulty.toLowerCase()) {
            case "easy" -> 3;
            case "medium" -> 5;
            case "hard" -> 7;
            default -> 3;
        };
    }

    private int getSpawnChance(String difficulty) {
        return switch (difficulty.toLowerCase()) {
            case "easy" -> 20;    // 20% chance
            case "medium" -> 30;  // 30% chance
            case "hard" -> 50;    // 50% chance
            default -> 20;
        };
    }

    public void reset() {
        spawnedPowerUpsCount = 0;
        lastSpawnTime = System.currentTimeMillis();
        activePowerUps.clear();
    }

    private class ActivePowerUp {
        private long activationTime;

        public ActivePowerUp(PowerUp.Type type) {
            this.activationTime = System.currentTimeMillis();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - activationTime > duration;
        }

        public void resetTimer() {
            activationTime = System.currentTimeMillis();
        }
    }

    public Map<PowerUp.Type, Long> getActivePowerUpTimers() {
        Map<PowerUp.Type, Long> timers = new HashMap<>();
        long currentTime = System.currentTimeMillis();

        for (Map.Entry<PowerUp.Type, ActivePowerUp> entry : activePowerUps.entrySet()) {
            long remaining = duration - (currentTime - entry.getValue().activationTime);
            if (remaining > 0) {
                timers.put(entry.getKey(), remaining);
            }
        }

        return timers;
    }

}
