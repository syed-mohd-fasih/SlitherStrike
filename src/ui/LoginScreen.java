package ui;

import user.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JPanel {
    private JTextField usernameField;
    private JButton loginButton;
    private JLabel messageLabel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public LoginScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(45, 45, 45));

        // Title label
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel);

        // Username input field
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.setBackground(new Color(45, 45, 45));

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernamePanel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernamePanel.add(usernameField);

        add(usernamePanel);

        // Message label for feedback
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(messageLabel);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loginButton.setBackground(new Color(50, 50, 50));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();

                // Exception handling for empty username
                if (username.isEmpty()) {
                    messageLabel.setText("Username cannot be empty!");
                } else {
                    // Attempt login
                    boolean success = UserManager.login(username);
                    if (success) {
                        messageLabel.setForeground(Color.GREEN);
                        messageLabel.setText("Login Successful!");

                        // After successful login, switch to the next panel (Main Menu)
                        cardLayout.show(mainPanel, "GameModeMenu");
                    } else {
                        messageLabel.setForeground(Color.RED);
                        messageLabel.setText("Login failed, please try again.");
                    }
                }
            }
        });

        add(loginButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
