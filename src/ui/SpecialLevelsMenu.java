package ui;

import utils.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class SpecialLevelsMenu extends JPanel {

    public SpecialLevelsMenu(CardLayout cardLayout, JPanel mainPanel) {
//        setBackground(new Color(30, 30, 30));
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Image background = ResourceManager.MAIN_BG;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Title
        JLabel titleLabel = new JLabel("Special Levels");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Add Special Level Buttons
        JButton invisibilityButton = createSpecialButton("Invisibility Mode");
        JButton unlockableLevelsButton = createSpecialButton("Unlockable Quadrants Mode");
        JButton flipMapButton = createSpecialButton("Flip Map Mode");

        // Action listeners for each button
        invisibilityButton.addActionListener(e -> showGameScreen("Invisibility Mode", mainPanel));
        unlockableLevelsButton.addActionListener(e -> showGameScreen("Unlockable Quadrants Mode", mainPanel));
        flipMapButton.addActionListener(e -> showGameScreen("Flip Map Mode", mainPanel));

        // Back Button to navigate to the game mode menu
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "GameModeMenu"));

        // Add components to layout
        panel.add(titleLabel);
        panel.add(invisibilityButton);
        panel.add(unlockableLevelsButton);
        panel.add(flipMapButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(backButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
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
