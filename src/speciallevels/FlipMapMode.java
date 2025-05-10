package speciallevels;

import game.Game;
import game.Fruit;
import game.Obstacle;
import game.PowerUp;

import java.awt.*;

public class FlipMapMode implements SpecialLevelMode {
    private boolean active;
    public boolean isVerticalFlip;
    public boolean isHorizontalFlip;

    private long lastFlipTime;
    private final int FLIP_INTERVAL = 10000; // 10 seconds in milliseconds
    private boolean nextFlipIsVertical = true; // Start with vertical

    @Override
    public void activate(Game game) {
        active = true;
        isVerticalFlip = false;
        isHorizontalFlip = false;
        lastFlipTime = System.currentTimeMillis();
    }

    @Override
    public void update(Game game) {
        if (!active) return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFlipTime >= FLIP_INTERVAL) {
            // Perform flip
            if (nextFlipIsVertical) {
                performVerticalFlip(game);
                isVerticalFlip = true;
                isHorizontalFlip = false;
            } else {
                performHorizontalFlip(game);
                isVerticalFlip = false;
                isHorizontalFlip = true;
            }

            nextFlipIsVertical = !nextFlipIsVertical;
            lastFlipTime = currentTime;
        }
    }

    private void performVerticalFlip(Game game) {
        int gridHeight = 20;

        // Flip fruit
        Fruit fruit = game.getFruit();
        Point pos = fruit.getPosition();
        fruit.setPosition(pos.x, gridHeight - 1 - pos.y);

        // Flip obstacles
        for (Obstacle obstacle : game.getObstacles()) {
            Point oPos = obstacle.getPosition();
            obstacle.setPosition(oPos.x, gridHeight - 1 - oPos.y);
        }

        // Flip Power ups
        for (PowerUp powerUp : game.getPowerUps()) {
            Point pPos = powerUp.getPosition();
            powerUp.setPosition(pPos.x, gridHeight - 1 - pPos.y);
        }
    }

    private void performHorizontalFlip(Game game) {
        int gridWidth = 20;

        // Flip fruit
        Fruit fruit = game.getFruit();
        Point pos = fruit.getPosition();
        fruit.setPosition(gridWidth - 1 - pos.x, pos.y);

        // Flip obstacles
        for (Obstacle obstacle : game.getObstacles()) {
            Point oPos = obstacle.getPosition();
            obstacle.setPosition(gridWidth - 1 - oPos.x, oPos.y);
        }

        // Flip Power ups
        for (PowerUp powerUp : game.getPowerUps()) {
            Point pPos = powerUp.getPosition();
            powerUp.setPosition(gridWidth - 1 - pPos.x, pPos.y);
        }
    }

    @Override
    public void render(Graphics g, Graphics2D g2d, Game game) {
        if (!active) return;

        long currentTime = System.currentTimeMillis();
        long timeSinceLastFlip = currentTime - lastFlipTime;
        long timeUntilNextFlip = (FLIP_INTERVAL - timeSinceLastFlip) / 1000;

        String nextFlipType = nextFlipIsVertical ? "Vertical" : "Horizontal";
        String text = "Flip in: " + timeUntilNextFlip + "s (" + nextFlipType + ")";

        g.setColor(new Color(176, 176, 176, 170));
        g.fillRoundRect(1280 - 170, 60, 150, 30, 10, 10);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.WHITE);

        g.drawString(text, 1280 - 160, 80);

        g.dispose();
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
