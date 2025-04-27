package ui;

import javax.swing.*;
import java.awt.*;

public class GameModeMenu extends JPanel {

    public GameModeMenu(CardLayout cardLayout, JPanel mainPanel) {
        setBackground(new Color(30, 30, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Choose Game Mode");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        JButton freePlayButton = createButton("Free Play");
        JButton specialLevelsButton = createButton("Special Levels");
        JButton backButton = createButton("Back");

        freePlayButton.addActionListener(e -> cardLayout.show(mainPanel, "FreePlayLevelMenu"));
        specialLevelsButton.addActionListener(e -> cardLayout.show(mainPanel, "SpecialLevelsMenu"));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        add(titleLabel);
        add(freePlayButton);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(specialLevelsButton);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(backButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(250, 60));  // Adjust button size

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
