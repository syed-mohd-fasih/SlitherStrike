package utils;

import game.Game;
import game.PowerUp;

import java.util.Iterator;
import java.util.Random;

public class PowerUpManager {
    private static final int MIN_SPAWN_INTERVAL = 10000; // 10 seconds
    private long lastSpawnTime = 0;
    private int spawnedPowerUpsCount = 0; // NEW: count how many spawned
    private Random random = new Random();

    public void update(Game game) {
        long currentTime = System.currentTimeMillis();

        // Remove expired powerups
        Iterator<PowerUp> iterator = game.getPowerUps().iterator();
        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            if (powerUp.isExpired()) {
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
        // Call this when starting a new game/level
        spawnedPowerUpsCount = 0;
        lastSpawnTime = System.currentTimeMillis();
    }
}
