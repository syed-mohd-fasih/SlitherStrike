package ui;

import javax.swing.*;
import java.awt.*;

public class SpecialLevelsMenu extends JPanel {

    public SpecialLevelsMenu(CardLayout cardLayout, JPanel mainPanel) {
        setBackground(new Color(30, 30, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Special Levels");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Add Special Level Buttons
        JButton invisibilityButton = createSpecialButton("Invisibility Mode");
        JButton explodingFruitButton = createSpecialButton("Exploding Fruit");
        JButton unlockableLevelsButton = createSpecialButton("Unlockable Levels");
        JButton flipMapButton = createSpecialButton("Flip Map");
        JButton nightModeButton = createSpecialButton("Night Mode");
        JButton frozenTimeButton = createSpecialButton("Frozen Time");
        JButton splitHeadsButton = createSpecialButton("Split Heads");

        // Action listeners for each button
        invisibilityButton.addActionListener(e -> showGameScreen("Invisibility Mode", mainPanel));
        explodingFruitButton.addActionListener(e -> showGameScreen("Exploding Fruit", mainPanel));
        unlockableLevelsButton.addActionListener(e -> showGameScreen("Unlockable Levels", mainPanel));
        flipMapButton.addActionListener(e -> showGameScreen("Flip Map", mainPanel));
        nightModeButton.addActionListener(e -> showGameScreen("Night Mode", mainPanel));
        frozenTimeButton.addActionListener(e -> showGameScreen("Frozen Time", mainPanel));
        splitHeadsButton.addActionListener(e -> showGameScreen("Split Heads", mainPanel));

        // Back Button to navigate to the game mode menu
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "GameModeMenu"));

        // Add components to layout
        add(titleLabel);
        add(invisibilityButton);
        add(explodingFruitButton);
        add(unlockableLevelsButton);
        add(flipMapButton);
        add(nightModeButton);
        add(frozenTimeButton);
        add(splitHeadsButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(backButton);
    }

    private void showGameScreen(String mode, JPanel mainPanel) {
        GamePanel gamePanel = new GamePanel("Special Level", mode);
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        mainPanel.add(gamePanel, "GamePanel");
        cardLayout.show(mainPanel, "GamePanel");
    }

    private JButton createSpecialButton(String text) {
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
