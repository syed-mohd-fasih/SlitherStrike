package ui;

import game.*;
import utils.TimeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class GamePanel extends JPanel implements ActionListener {

    private static final int BLOCK_SIZE = 25;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;

    private String selectedLevel;
    private String selectedMode;
    private String username = "Player1";

    private Timer timer;
    private Game game;
    private TimeManager timeManager;

    public GamePanel(String level, String mode) {
        this.selectedLevel = level;
        this.selectedMode = mode;

        setFocusable(true);
        setBackground(Color.BLACK);
        setLayout(null);

        game = new Game(GRID_WIDTH, GRID_HEIGHT, selectedMode);

        timeManager = new TimeManager(selectedMode, this);
        timer = timeManager.getTimer();

        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP -> game.getSnake().setDirection(Snake.Direction.UP);
                    case KeyEvent.VK_DOWN -> game.getSnake().setDirection(Snake.Direction.DOWN);
                    case KeyEvent.VK_LEFT -> game.getSnake().setDirection(Snake.Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> game.getSnake().setDirection(Snake.Direction.RIGHT);
                }
            }
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHeader(g);
        drawGameArea(g);
        drawActivePowerUps(g);
    }

    private void drawHeader(Graphics g) {
        g.setColor(new Color(50, 50, 50));
        g.fillRect(0, 0, getWidth(), 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString("User: " + username, 20, 30);

        String scoreText = "Score: " + game.getScoreManager().getScore();
        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, (getWidth() - scoreWidth) / 2, 30);

        String levelText = "Level: " + selectedLevel + " | " + selectedMode;
        int levelWidth = g.getFontMetrics().stringWidth(levelText);
        g.drawString(levelText, getWidth() - levelWidth - 20, 30);
    }

    private void drawGameArea(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int headerHeight = 50;

        int totalGridWidth = GRID_WIDTH * BLOCK_SIZE;
        int totalGridHeight = GRID_HEIGHT * BLOCK_SIZE;

        int xOffset = (getWidth() - totalGridWidth) / 2;
        int yOffset = headerHeight + ((getHeight() - headerHeight - totalGridHeight) / 2);

        g2d.translate(xOffset, yOffset);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, totalGridWidth, totalGridHeight);

        g2d.setColor(Color.DARK_GRAY);
        for (int x = 0; x <= GRID_WIDTH; x++) {
            g2d.drawLine(x * BLOCK_SIZE, 0, x * BLOCK_SIZE, totalGridWidth);
        }
        for (int y = 0; y <= GRID_HEIGHT; y++) {
            g2d.drawLine(0, y * BLOCK_SIZE, totalGridHeight, y * BLOCK_SIZE);
        }

        gameDraw(g2d);
        g2d.dispose();
    }

    private void drawActivePowerUps(Graphics g) {
        Map<PowerUp.Type, Long> activeTimers = game.getActivePowerUpTimers();

        int x = 20;
        int y = 60;
        int size = 32;

        for (Map.Entry<PowerUp.Type, Long> entry : activeTimers.entrySet()) {
            PowerUp.Type type = entry.getKey();
            long remaining = entry.getValue() / 1000;

            // Draw icon (placeholder color)
            g.setColor(switch (type) {
                case SPEED_UP -> Color.YELLOW;
                case INVINCIBILITY -> Color.CYAN;
                case DOUBLE_SCORE -> Color.MAGENTA;
            });
            g.fillRect(x, y, size, size);

            // Draw timer text
            g.setColor(Color.BLACK);
            g.drawString(remaining + "s", x + 5, y + size + 15);

            x += size + 15;
        }
    }

    private void gameDraw(Graphics2D g2d) {
        // Snake
        g2d.setColor(Color.GREEN);
        for (Point p : game.getSnake().getBody()) {
            g2d.fillRect(p.x * BLOCK_SIZE, p.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }

        // Fruit
        g2d.setColor(Color.RED);
        Point fruitPos = game.getFruit().getPosition();
        g2d.fillOval(fruitPos.x * BLOCK_SIZE, fruitPos.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

        // Obstacles
        g2d.setColor(new Color(0, 0, 139));
        for (Obstacle obstacle : game.getObstacles()) {
            Point pos = obstacle.getPosition();
            g2d.fillRect(pos.x * BLOCK_SIZE, pos.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }

        // PowerUps
        for (PowerUp powerUp : game.getPowerUps()) {
            switch (powerUp.getType()) {
                case SPEED_UP -> g2d.setColor(Color.YELLOW);
                case INVINCIBILITY -> g2d.setColor(Color.CYAN);
                case DOUBLE_SCORE -> g2d.setColor(Color.MAGENTA);
            }
            g2d.fillOval(powerUp.getPosition().x * BLOCK_SIZE, powerUp.getPosition().y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
        repaint();

        if (game.isGameOver()) {
            timer.stop();

            Container parent = getParent();
            if (parent instanceof JPanel) {
                CardLayout layout = (CardLayout) parent.getLayout();
                JPanel mainPanel = (JPanel) parent;

                GameOverPanel gameOverPanel = new GameOverPanel(
                        username,
                        selectedLevel,
                        selectedMode,
                        game.getScoreManager().getScore()
                );

                mainPanel.add(gameOverPanel, "GameOverPanel");
                layout.show(mainPanel, "GameOverPanel");
            }
        }
    }
}
