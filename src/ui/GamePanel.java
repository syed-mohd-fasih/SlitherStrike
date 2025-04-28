package ui;

import game.*;
import utils.CollisionManager;
import utils.TimeManager;
import utils.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

        // Initialize Game
        game = new Game(GRID_WIDTH, GRID_HEIGHT, mode); // Add levels later

        // Initialize Timer Manager
        timeManager = new TimeManager(selectedMode, this);
        timer = timeManager.getTimer();

        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP:
                        game.getSnake().setDirection(Snake.Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        game.getSnake().setDirection(Snake.Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        game.getSnake().setDirection(Snake.Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.getSnake().setDirection(Snake.Direction.RIGHT);
                        break;
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
        g2d.translate(0, 50); // Move down to make space for header

        // Draw background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);

        // Draw grid lines (optional, for better visibility)
        g2d.setColor(Color.DARK_GRAY);
        for (int x = 0; x <= GRID_WIDTH; x++) {
            g2d.drawLine(x * BLOCK_SIZE, 0, x * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);
        }
        for (int y = 0; y <= GRID_HEIGHT; y++) {
            g2d.drawLine(0, y * BLOCK_SIZE, GRID_WIDTH * BLOCK_SIZE, y * BLOCK_SIZE);
        }

        // Draw snake, fruit, obstacles, and power-ups
        gameDraw(g2d);

        g2d.dispose();
    }

    private void gameDraw(Graphics2D g2d) {
        // Draw Snake
        g2d.setColor(Color.GREEN);
        for (Point p : game.getSnake().getBody()) {
            g2d.fillRect(p.x * BLOCK_SIZE, p.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }

        // Draw Fruit
        g2d.setColor(Color.RED);
        Point fruitPos = game.getFruit().getPosition();
        g2d.fillOval(fruitPos.x * BLOCK_SIZE, fruitPos.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

        // Draw Power-Ups
        g2d.setColor(Color.YELLOW);
        for (PowerUp powerUp : game.getPowerUps()) {
            g2d.fillOval(powerUp.getPosition().x * BLOCK_SIZE, powerUp.getPosition().y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }

        // Draw Obstacles
        g2d.setColor(new Color(0, 0, 139)); // Dark Blue Color
        for (Obstacle obstacle : game.getObstacles()) {
            Point pos = obstacle.getPosition();
            g2d.fillRect(pos.x * BLOCK_SIZE, pos.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
        repaint();

        if (game.isGameOver()) {
            timer.stop(); // Stop the game loop

            // Get parent panel and switch to GameOverPanel
            Container parent = getParent();
            if (parent instanceof JPanel) {
                CardLayout cardLayout = (CardLayout) parent.getLayout();
                JPanel mainPanel = (JPanel) parent;

                GameOverPanel gameOverPanel = new GameOverPanel(
                        username, // Hardcoded for now
                        selectedLevel,
                        selectedMode,
                        game.getScoreManager().getScore()
                );

                mainPanel.add(gameOverPanel, "GameOverPanel");
                cardLayout.show(mainPanel, "GameOverPanel");
            }
        }
    }
}
