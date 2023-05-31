import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JPanel {
    private JButton startButton;
    private JButton explanationButton;
    private JButton exitButton;

    public GameMenu() {
        // Set the layout to null for custom positioning of components
        setLayout(null);

        // Create buttons
        startButton = new JButton("Start Game");
        explanationButton = new JButton("Game Explanation");
        exitButton = new JButton("Exit Game");

        // Set the bounds (position and size) of the buttons
        startButton.setBounds(100, 50, 150, 30);
        explanationButton.setBounds(100, 100, 150, 30);
        exitButton.setBounds(100, 150, 150, 30);

        // Add action listeners
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to start the game goes here
                System.out.println("Starting Tanks game...");
            }
        });

        explanationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to show game explanation goes here
                System.out.println("Showing game explanation...");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to exit the game goes here
                System.out.println("Exiting Tanks game...");
                System.exit(0);
            }
        });

        // Add the buttons to the panel
        add(startButton);
        add(explanationButton);
        add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set background color
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());

        // Set font and color for the game name
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);

        // Draw the game name at the center of the panel
        String gameName = "Tanks";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(gameName)) / 2;
        int y = getHeight() / 3;
        g2d.drawString(gameName, x, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Tanks Menu");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null); // Center the frame

                GameMenu menu = new GameMenu();
                frame.getContentPane().add(menu);
                frame.setVisible(true);
            }
        });
    }
}
