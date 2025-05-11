package ui;

import user.User;
import user.UserManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;

public class ScoreCard extends JPanel {
    private final String[] levels = {"Level 1", "Level 2", "Level 3", "Level 4"};
    private final String[] difficulties = {"Easy", "Medium", "Hard"};
    private final String[] specialModes = {"Flip Map Mode", "Invisibility Mode", "Unlockable Quadrants Mode"};

    public ScoreCard(CardLayout cardLayout, JPanel mainPanel) {
        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout()); // Switch to BorderLayout to support scrollable center

        JLabel titleLabel = new JLabel("Score Board");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Top panel for title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(30, 30, 30));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);

        // Scrollable center panel
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(new Color(30, 30, 30));

        // Add level scorecards
        for (String level : levels) {
            scrollContent.add(createLevelScoreCard(level, difficulties));
            scrollContent.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Add special levels
        scrollContent.add(createLevelScoreCard("Special Level", specialModes));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(30, 30, 30));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scroll

        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(50, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(250, 60));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        // Bottom panel for back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 30));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createLevelScoreCard(String levelName, String[] diffs) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(40, 40, 40));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(700, 180));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(100, 200, 255)),
                        levelName,
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 20),
                        new Color(100, 200, 255)
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        for (String diff : diffs) {
            String topUser = "N/A";
            int topScore = 0;

            for (User user : UserManager.getAllUsers()) {
                int score = user.getHighScore(levelName, diff);
                if (score > topScore) {
                    topScore = score;
                    topUser = user.getUsername();
                }
            }

            JLabel label = new JLabel(diff + " →  " + topUser + "  |  Score: " + topScore);
            label.setFont(new Font("Consolas", Font.PLAIN, 16));
            label.setForeground(new Color(200, 200, 200));
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            card.add(label);
        }

        return card;
    }
}
