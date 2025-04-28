package ui;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    public GameOverPanel(String username, String level, String difficulty, int score) {
        setBackground(new Color(30, 30, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Game Over");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        // Username
        JLabel userLabel = new JLabel("Username: " + username);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        // Level
        JLabel levelLabel = new JLabel("Level: " + level);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        // Difficulty
        JLabel difficultyLabel = new JLabel("Difficulty: " + difficulty);
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyLabel.setForeground(Color.WHITE);
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        // Score
        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        // Back Button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(50, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(250, 60));
        backButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            JPanel mainPanel = (JPanel) getParent();
            cardLayout.show(mainPanel, "MainMenu");
        });

        // Add everything
        add(titleLabel);
        add(userLabel);
        add(levelLabel);
        add(difficultyLabel);
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(backButton);
    }
}
