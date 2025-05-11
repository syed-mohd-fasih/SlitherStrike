package ui;

import game.*;
import user.UserManager;
import utils.ResourceManager;
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
    private String username = UserManager.getCurrentUser().getUsername();

    private Timer timer;
    private Game game;
    private TimeManager timeManager;

    public GamePanel(String level, String mode) {
        this.selectedLevel = level;
        this.selectedMode = mode;

        setFocusable(true);
        setBackground(Color.BLACK);
        setLayout(null);

        game = new Game(GRID_WIDTH, GRID_HEIGHT, level, selectedMode);

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
        Graphics2D g2d = (Graphics2D) g.create();
        if (game.getCurrentSpecialLevel() != null && game.getCurrentSpecialLevel().isActive()) {
            game.getCurrentSpecialLevel().render(g, g2d, game);
        }
        g2d.dispose();
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

        Image gamebg = ResourceManager.GAME_BG;
        g2d.drawImage(gamebg, 0, 0, totalGridWidth, totalGridHeight, null);

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
            Image icon = switch (type) {
                case SPEED_UP -> ResourceManager.BOLT_ICON;
                case INVINCIBILITY -> ResourceManager.SHIELD_ICON;
                case DOUBLE_SCORE -> ResourceManager.DOUBLE_ICON;
                case EXPLODING_FRUIT -> ResourceManager.BOMB_ICON;
                default -> null;
            };

            if (icon != null) {
                g.drawImage(icon, x, y, size, size, null);
            } else {
                g.setColor(Color.GRAY); // Fallback in case image fails
                g.fillRect(x, y, size, size);
            }

            // Draw timer text
            g.setColor(Color.WHITE);
            g.drawString(remaining + "s", x + 5, y + size + 15);

            x += size + 15;
        }
    }

    private void gameDraw(Graphics2D g2d) {
        // Snake
        if (game.getSnake().isVisible()) {
            java.util.List<Point> body = game.getSnake().getBody();
            if (!body.isEmpty()) {
                Point head = game.getSnake().getBody().get(0);
                Image headImage = ResourceManager.SNAKE_HEAD;
                Image bodyImage = ResourceManager.SNAKE_BODY;

                if (headImage != null) {
                    // Convert direction to angle
                    double angle = switch (game.getSnake().getDirection()) {
                        case UP -> 0;
                        case RIGHT -> Math.PI / 2;
                        case DOWN -> Math.PI;
                        case LEFT -> -Math.PI / 2;
                    };
                    Graphics2D gCopyHead = (Graphics2D) g2d.create();

                    // Rotate and draw the image
                    int x = head.x * BLOCK_SIZE;
                    int y = head.y * BLOCK_SIZE;

                    // Rotate around center
                    gCopyHead.rotate(angle, x + BLOCK_SIZE / 2.0, y + BLOCK_SIZE / 2.0);
                    gCopyHead.drawImage(headImage, x, y, BLOCK_SIZE, BLOCK_SIZE, null);
                    gCopyHead.dispose();
                } else {
                    // fallback if image is missing
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(head.x * BLOCK_SIZE, head.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }

                // Draw the rest of the body
                Graphics2D gCopyBody = (Graphics2D) g2d.create();
                g2d.setColor(new Color(60, 196, 50));
                for (int i = 1; i < body.size(); i++) {
                    Point p = body.get(i);
                    if (bodyImage != null) {
                        gCopyBody.drawImage(bodyImage, p.x * BLOCK_SIZE, p.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, null);
                    } else {
                        g2d.fillRect(p.x * BLOCK_SIZE, p.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }

                gCopyBody.dispose();
            }
        }

        // Fruit with image
        Image fruitImage = ResourceManager.FRUIT_ICON;
        Point fruitPos = game.getFruit().getPosition();

        if (fruitImage != null) {
            g2d.drawImage(fruitImage,
                    fruitPos.x * BLOCK_SIZE,
                    fruitPos.y * BLOCK_SIZE,
                    BLOCK_SIZE,
                    BLOCK_SIZE,
                    null);
        } else {
            // fallback if image fails
            g2d.setColor(Color.RED);
            g2d.fillOval(fruitPos.x * BLOCK_SIZE, fruitPos.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }


        // Obstacles
        Image rockImage = ResourceManager.OBSTACLE_ICON;
        for (Obstacle obstacle : game.getObstacles()) {
            Point obstaclePosition = obstacle.getPosition();
            if (rockImage != null) {
                g2d.drawImage(rockImage, obstaclePosition.x * BLOCK_SIZE, obstaclePosition.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, null);
            } else {
                g2d.setColor(new Color(0, 0, 0));
                g2d.fillRect(obstaclePosition.x * BLOCK_SIZE, obstaclePosition.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        // PowerUps
        for (PowerUp powerUp : game.getPowerUps()) {
            int drawX = powerUp.getPosition().x * BLOCK_SIZE;
            int drawY = powerUp.getPosition().y * BLOCK_SIZE;
            Image icon = switch (powerUp.getType()) {
                case SPEED_UP -> ResourceManager.BOLT_ICON;
                case INVINCIBILITY -> ResourceManager.SHIELD_ICON;
                case DOUBLE_SCORE -> ResourceManager.DOUBLE_ICON;
                case EXPLODING_FRUIT -> ResourceManager.BOMB_ICON;
            };

            if (icon != null) {
                g2d.drawImage(icon, drawX, drawY, BLOCK_SIZE, BLOCK_SIZE, null);
            } else {
                // fallback shape if image fails
                g2d.setColor(Color.GRAY);
                g2d.fillOval(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
            }
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
