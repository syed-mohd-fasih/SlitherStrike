package ui;

import javax.swing.*;
import java.awt.*;

public class FreePlayLevelMenu extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public FreePlayLevelMenu(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setBackground(new Color(30, 30, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Free Play");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Add Level Sections
        addLevelSection("Level 1");
        addLevelSection("Level 2");
        addLevelSection("Level 3");
        addLevelSection("Level 4");

        // Back Button to navigate to the GameModeMenu
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "GameModeMenu"));

        // Add components to layout
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(backButton);
    }

    private void addLevelSection(String level) {
        // Panel for each level
        JPanel levelPanel = new JPanel();
        levelPanel.setBackground(new Color(50, 50, 50));
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));

        JLabel levelLabel = new JLabel(level);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 24));
        levelLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Difficulty Buttons for each level
        JButton easyButton = createDifficultyButton("Easy");
        JButton mediumButton = createDifficultyButton("Medium");
        JButton hardButton = createDifficultyButton("Hard");

        // Action listeners for each difficulty button
        easyButton.addActionListener(e -> showGameScreen(level, "Easy"));
        mediumButton.addActionListener(e -> showGameScreen(level, "Medium"));
        hardButton.addActionListener(e -> showGameScreen(level, "Hard"));

        // Add components to the level panel
        levelPanel.add(levelLabel);
        levelPanel.add(easyButton);
        levelPanel.add(mediumButton);
        levelPanel.add(hardButton);

        // Add the level panel to the main layout
        add(levelPanel);
    }

    private JButton createDifficultyButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(200, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));
            }
        });

        return button;
    }

    private void showGameScreen(String level, String difficulty) {
        // Create the GameScreen panel with the level and difficulty info
        GamePanel gamePanel = new GamePanel(level, difficulty);

        // Add GameScreen to the main panel and show it
        mainPanel.add(gamePanel, "GamePanel");
        cardLayout.show(mainPanel, "GamePanel");
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(250, 60));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));
            }
        });

        return button;
    }
}
