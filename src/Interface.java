import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Interface {
    GamePanel panel;
    public boolean messageOn = false, gameOver = false;
    public String message = "";
    public int counter = 0;
    public double playTime;
    Font retroGaming, vampireWars;
    DecimalFormat format = new DecimalFormat("#0.00");
    File titleFont = new File("font/Vampire Wars.ttf"), mainFont = new File("font/Retro Gaming.ttf");
    Image heartFull = new ImageIcon("imgs/heart_full.png").getImage(), heartHalf = new ImageIcon("imgs/heart_half.png").getImage(),
            heartBlank = new ImageIcon("imgs/heart_blank.png").getImage();
    private Graphics2D g2;

    public Interface(GamePanel panel) {
        this.panel = panel;
        try {
            vampireWars = Font.createFont(Font.TRUETYPE_FONT, titleFont);
            retroGaming = Font.createFont(Font.TRUETYPE_FONT, mainFont);
        }catch (FontFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2)  {
        this.g2 = g2;
        if (panel.gameState == panel.playState && !gameOver) {
            playerLives();
            g2.setFont(retroGaming.deriveFont(30f));
            g2.setColor(Color.white);
            playTime += (double) 1 / 60;
            g2.drawString("Timer: " + format.format(playTime), (int) (panel.tankSize * 12.5), 65);
            if (messageOn == true) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, panel.tankSize / 2, panel.tankSize * 5);
                counter++;
                if (counter > 120) {
                    counter = 0;
                    messageOn = false;
                }
            }
        } else if (panel.gameState == panel.titleState && !gameOver) {

        } else if (panel.gameState == panel.endState && gameOver) {

        } else if (panel.gameState == panel.pauseState && !gameOver) {
            pauseScreen();
        }
        if (gameOver) {
            String overText = "Game is over";
            g2.setFont(vampireWars.deriveFont(80f));
            int textLenght = (int) g2.getFontMetrics().getStringBounds(overText, g2).getWidth();
            int xText = panel.screenWidth / 2 - textLenght / 2;
            int yText = panel.screenHeight / 2;
            g2.setColor(Color.YELLOW);
            g2.drawString(overText, xText, yText);
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void pauseScreen() {
        String pauseText = "PAUSED";
        g2.setColor(Color.WHITE);
        g2.setFont(vampireWars.deriveFont(80f));
        int textLenght = (int) g2.getFontMetrics().getStringBounds(pauseText, g2).getWidth();
        int xText = panel.screenWidth / 2 - textLenght / 2;
        int yText = panel.screenHeight / 2;
        g2.drawString(pauseText, xText, yText);
    }

    public void playerLives() {
        int x, y;
        x = panel.tankSize / 2;
        y = panel.tankSize / 2;
        for (int i = 0; i < panel.player.maxLives; i++) {
            g2.drawImage(heartBlank, x, y, panel.tankSize, panel.tankSize, null);
            x += panel.tankSize;
            if(x>=panel.screenWidth/2){
                x = panel.tankSize/2;
                y += panel.tankSize;
            }
        }
        x = panel.tankSize / 2;
        y = panel.tankSize / 2;
//        int n = 0;
//        if (panel.player.maxLives > 3) {
//            while (n < panel.player.lives) {
//                if (n < panel.player.lives) g2.drawImage(heartHalf, x, y, panel.tankSize, panel.tankSize, null);
//                n++;
//                if (n < panel.player.lives) g2.drawImage(heartFull, x, y, panel.tankSize, panel.tankSize, null);
//                n++;
//                x += panel.tankSize;
//            }
//        } else {
            for (int i = 0; i < panel.player.lives; i++) {
                g2.drawImage(heartFull, x, y, panel.tankSize, panel.tankSize, null);
                x += panel.tankSize;
                if(x>=panel.screenWidth/2){
                    x = panel.tankSize / 2;
                    y += panel.tankSize;
                }
            }
//        }
    }
}
