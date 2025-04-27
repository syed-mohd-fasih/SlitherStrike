package ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private String selectedLevel;
    private String selectedMode;

    public GamePanel(String level, String mode) {
        this.selectedLevel = level;
        this.selectedMode = mode;

        setBackground(new Color(30, 30, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Game Screen");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Display the selected level and special mode
        JLabel levelLabel = new JLabel("Level: " + selectedLevel);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel modeLabel = new JLabel("Mode: " + selectedMode);
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modeLabel.setForeground(Color.WHITE);
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add components to the layout
        add(titleLabel);
        add(levelLabel);
        add(modeLabel);

        // Back Button to go back to the GameModeMenu
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(50, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(250, 60));
        backButton.addActionListener(e -> {
            // Go back to the SpecialLevelsMenu (or modify this to your desired menu)
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            JPanel mainPanel = (JPanel) getParent();
            cardLayout.show(mainPanel, "GameModeMenu");
        });

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(backButton);
    }

    // Method to update the selected level and mode dynamically
    public void setGameInfo(String level, String mode) {
        this.selectedLevel = level;
        this.selectedMode = mode;
        revalidate();
        repaint();
    }
}
