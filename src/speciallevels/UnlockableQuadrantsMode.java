package speciallevels;

import game.*;

import java.awt.*;
import java.util.*;

public class UnlockableQuadrantsMode implements SpecialLevelMode {
    private boolean active;
    private final Set<Integer> unlockedQuadrants = new HashSet<>();

    @Override
    public void activate(Game game) {
        active = true;
        unlockedQuadrants.clear(); // Reset on activation
        unlockedQuadrants.add(1);  // Always unlocked
    }

    @Override
    public void update(Game game) {
        if (!active || game.isGameOver()) return;

        int score = game.getScoreManager().getScore();

        // Unlock quadrants based on score milestones
        if (score >= 500) unlockedQuadrants.add(2);
        if (score >= 750) unlockedQuadrants.add(3);
        if (score >= 1000) unlockedQuadrants.add(4);

        Point head = game.getSnake().getBody().getFirst();
        int midX = game.getMapWidth() / 2;
        int midY = game.getMapHeight() / 2;

        int currentQuadrant = getQuadrant(head, midX, midY);

        if (!unlockedQuadrants.contains(currentQuadrant)) {
            game.setGameOver(true);
            active = false;
        }
    }

    private int getQuadrant(Point p, int midX, int midY) {
        if (p.x < midX && p.y < midY) return 1;     // Top-left
        if (p.x >= midX && p.y < midY) return 2;    // Top-right
        if (p.x < midX && p.y >= midY) return 3;    // Bottom-left
        return 4;                                   // Bottom-right
    }

    @Override
    public void render(Graphics g, Graphics2D g2d, Game game) {
        if (!active) return;

        g2d.setColor(new Color(176, 176, 176, 170));
        g2d.fillRoundRect(1280 - 170, 60, 150, 30, 10, 10);

        // Draw progress text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("Quadrants unlocked: " + unlockedQuadrants.size() + "/4", 1280 - 162, 80);

        int headerHeight = 50;
        int totalGridWidth = 20 * 25;
        int totalGridHeight = 20 * 25;

        int xOffset = (1264 - totalGridWidth) / 2;
        int yOffset = headerHeight + ((681 - headerHeight - totalGridHeight) / 2);

        g2d.translate(xOffset, yOffset);

        int midX = totalGridWidth / 2;
        int midY = totalGridHeight / 2;

        g2d.setColor(new Color(255, 0, 0, 100));

        // Overlay locked quadrants
        if (!unlockedQuadrants.contains(2)) g2d.fillRect(midX, 0, midX, midY);
        if (!unlockedQuadrants.contains(3)) g2d.fillRect(0, midY, midX, midY);
        if (!unlockedQuadrants.contains(4)) g2d.fillRect(midX, midY, midX, midY);

        g2d.dispose();
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
