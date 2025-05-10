package speciallevels;

import game.Game;

import java.awt.*;

public class InvisibilityMode implements SpecialLevelMode {
    private long startTime;
    private boolean active;

    @Override
    public void activate(Game game) {
        startTime = System.currentTimeMillis();
        active = true;
    }

    @Override
    public void update(Game game) {
        long elapsed = System.currentTimeMillis() - startTime;

        // Cycle: 10s visible → 3s invisible → repeat
        long cycleTime = elapsed % 13000; // 10s visible + 3s invisible = 13s cycle

        if (cycleTime < 10000) game.getSnake().setVisible(true);
        else game.getSnake().setVisible(false);
    }

    @Override
    public void render(Graphics g, Graphics2D g2d, Game game) {
        long cycleTime = 13000; // 10s visible + 3s invisible
        long elapsed = (System.currentTimeMillis() - startTime) % cycleTime;

        String countdownText;
        if (elapsed < 10000) {
            countdownText = "Invisibility in: " + ((10000 - elapsed) / 1000 + 1) + "s";
        } else {
            countdownText = "Visible in: " + ((13000 - elapsed) / 1000 + 1) + "s";
        }

        g2d.setColor(new Color(176, 176, 176, 170));
        g2d.fillRoundRect(1280 - 170, 60, 150, 30, 10, 10);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(countdownText, 1280 - 160, 80);

        g2d.dispose();
    }


    @Override
    public boolean isActive() {
        return active;
    }
}
