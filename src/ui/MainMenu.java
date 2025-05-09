package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainMenu() {
        setTitle("Snake Game - Main Menu");
        setSize(1280, 720);  // Default window size, can scale
        setResizable(true);  // Make the window resizable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Use CardLayout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels dynamically
        mainPanel.add(createMainMenuPanel(), "MainMenu");
        mainPanel.add(new GameModeMenu(cardLayout, mainPanel), "GameModeMenu");
        mainPanel.add(new FreePlayLevelMenu(cardLayout, mainPanel), "FreePlayLevelMenu");
        mainPanel.add(new SpecialLevelsMenu(cardLayout, mainPanel), "SpecialLevelsMenu");
        mainPanel.add(new ScoreCard(cardLayout, mainPanel), "ScoreCard");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Snake Game");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        JButton playButton = createButton("Play");
        JButton scorecardButton = createButton("Scorecard");
        JButton exitButton = createButton("Exit");

        playButton.addActionListener(e -> cardLayout.show(mainPanel, "GameModeMenu"));
        scorecardButton.addActionListener(e -> cardLayout.show(mainPanel, "ScoreCard"));
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(titleLabel);
        panel.add(playButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(scorecardButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(exitButton);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(250, 60));  // Use preferred size instead of fixed size

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
