import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame game = new JFrame("Танчики");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setResizable(false);

        GamePanel gamePanel = new GamePanel();

        game.add(gamePanel);

        game.pack();
        game.setLocationRelativeTo(null);
        game.setVisible(true);

        gamePanel.startGameThread();
    }
}