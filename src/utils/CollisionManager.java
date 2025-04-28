package utils;

import game.*;

import java.awt.*;
import java.util.List;

public class CollisionManager {

    public static boolean checkWallCollision(Snake snake, int width, int height) {
        Point head = snake.getBody().getFirst();
        return head.x < 0 || head.x >= width || head.y < 0 || head.y >= height;
    }

    public static boolean checkSelfCollision(Snake snake) {
        Point head = snake.getBody().getFirst();
        for (int i = 1; i < snake.getBody().size(); i++) {
            if (head.equals(snake.getBody().get(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkObstacleCollision(Snake snake, List<Obstacle> obstacles) {
        Point head = snake.getBody().getFirst();
        for (Obstacle obstacle : obstacles) {
            if (head.equals(obstacle.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public static void checkPowerUpCollision(Snake snake, List<PowerUp> powerUps, List<PowerUp> toRemove, Game game) {
        Point head = snake.getBody().getFirst();
        for (PowerUp powerUp : powerUps) {
            if (head.equals(powerUp.getPosition())) {
                powerUp.applyEffect(game);  // Apply power-up effect
                toRemove.add(powerUp);
            }
        }
    }

    public static boolean checkFruitCollision(Snake snake, Fruit fruit, ScoreManager scoreManager) {
        Point head = snake.getBody().getFirst();
        if (head.equals(fruit.getPosition())) {
            snake.grow();
            scoreManager.addScore(10);  // Update score
            return true;
        }
        return false;
    }
}
