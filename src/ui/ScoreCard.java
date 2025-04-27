package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ScoreCard extends JPanel {
    public ScoreCard(CardLayout cardLayout, JPanel mainPanel) {
        setBackground(new Color(30, 30, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Score Card");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Table for displaying scores
        String[] columnNames = {"UserName", "Level", "Mode/Difficulty", "Score"};
        Object[][] data = {}; // Empty data for now, will be populated later

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable scoreTable = new JTable(model);
        scoreTable.setFillsViewportHeight(true);
        scoreTable.setBackground(new Color(50, 50, 50));
        scoreTable.setForeground(Color.WHITE);
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        // Back Button to navigate back to the Main Menu
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(50, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setPreferredSize(new Dimension(250, 60));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        // Add components to layout
        add(titleLabel);
        add(scrollPane);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(backButton);
    }
}
