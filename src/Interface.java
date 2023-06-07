import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Interface {
    GamePanel panel;
    public boolean messageOn = false, gameOver = false, loading = false;
    public String message = "";
    public int counter = 0, fill=0, titleScreenMode = 0, menuCommand = 0;
    public double playTime;
    Font retroGaming, vampireWars;
    DecimalFormat format = new DecimalFormat("#0.00");
    File titleFont = new File("font/Vampire Wars.ttf"), mainFont = new File("font/Retro Gaming.ttf");
    Image heartFull = new ImageIcon("imgs/heart_full.png").getImage(), heartHalf = new ImageIcon("imgs/heart_half.png").getImage(),
            heartBlank = new ImageIcon("imgs/heart_blank.png").getImage(), energy = new ImageIcon("imgs/energy.png").getImage(),
            background = new ImageIcon("imgs/background menu.jpg").getImage(), redMenuBar = new ImageIcon("imgs/redMenuBar.png").getImage();
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
        if (panel.gameState == panel.playState) {
            playerLives(); playerEnergy();
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
            titleScreen(g2);
        } else if (panel.gameState == panel.endState && gameOver) {
            gameOverScreen(g2);
        } else if (panel.gameState == panel.pauseState) {
            pauseScreen();
        }
    }

    private void titleScreen(Graphics2D g2) {
        g2.drawImage(background, 0, 0, panel.screenWidth, panel.screenHeight, null);
        if(titleScreenMode==0){
            g2.setColor(Color.red);
            g2.drawRoundRect(panel.screenWidth / 2 - panel.tankSize * 2, panel.screenHeight / 2 - panel.tankSize / 4, panel.tankSize * 4, panel.tankSize / 2, 17, 17);
            g2.fillRoundRect(panel.screenWidth / 2 - panel.tankSize * 2, panel.screenHeight / 2 - panel.tankSize / 4,
                    fill, panel.tankSize / 2, 17, 17);
            startTitleScreenTimer();
            int progressBarWidth = panel.tankSize * 4;
            int progressBarHeight = panel.tankSize / 4;
            int progressBarX = panel.screenWidth / 2 - progressBarWidth / 2;
            int progressBarY = panel.screenHeight / 2 + panel.tankSize / 4;

            int progress = (int) (fill / (panel.tankSize * 3.5) * 100);
            if (progress >= 100) {
                progress = 100;
            }
            String progressText = progress + "%";
            g2.setFont(retroGaming.deriveFont(16f));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(progressText);
            int textHeight = fm.getHeight();
            int textX = progressBarX + (progressBarWidth - textWidth) / 2;
            int textY = progressBarY + (progressBarHeight + textHeight) / 2 - fm.getDescent();
            g2.setColor(Color.BLACK);
            g2.drawString(progressText, textX--, textY--);
            g2.setColor(Color.white);
            g2.drawString(progressText, textX, textY);

            String loadingText = "Loading...";
            textX = centredX(loadingText);
            textY = progressBarY;
            g2.setColor(Color.BLACK);
            g2.drawString(loadingText, textX--, textY - panel.tankSize / 2-1);
            g2.setColor(Color.white);
            g2.drawString(loadingText, textX, textY - panel.tankSize / 2);
        }else if(titleScreenMode==1){
            String gameName = "Танки: Місія спалення Москви";
            g2.setFont(retroGaming.deriveFont(35f));
            int x = centredX(gameName);
            int y = panel.tankSize*3;
            g2.setColor(Color.black);
            g2.drawString(gameName, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(gameName, x, y);

            String text;
            g2.setFont(retroGaming.deriveFont(25f));
            text = "Play";
            x = centredX(text);
            y = panel.tankSize*6;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(menuCommand==0){
//                g2.drawImage(redMenuBar, (int) (x-panel.tankSize*1.5), (int) (y-panel.tankSize/1.6), panel.tankSize*4, panel.tankSize, null);
//
//                g2.setColor(Color.black);
//                g2.drawString(text, x--, y--);
//                g2.setColor(Color.white);
//                g2.drawString(text, x, y);

                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);
                g2.drawString("<", (int) (x+panel.tankSize*1.8-1), y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
                g2.drawString("<", (int) (x+panel.tankSize*1.8), y);
            }

            text = "Levels";
            x = centredX(text);
            y = panel.tankSize*7;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(menuCommand==1){
//                g2.drawImage(redMenuBar, (int) (x-panel.tankSize*1.5), (int) (y-panel.tankSize/1.6), panel.tankSize*4, panel.tankSize, null);
//
//                g2.setColor(Color.black);
//                g2.drawString(text, x--, y--);
//                g2.setColor(Color.white);
//                g2.drawString(text, x, y);

                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);
                g2.drawString("<", (int) (x+panel.tankSize*1.8-1), y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
                g2.drawString("<", (int) (x+panel.tankSize*1.8), y);
            }

            text = "Rules";
            x = centredX(text);
            y = panel.tankSize*8;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(menuCommand==2){
//                g2.drawImage(redMenuBar, (int) (x-panel.tankSize*1.5), (int) (y-panel.tankSize/1.6), panel.tankSize*4, panel.tankSize, null);
//
//                g2.setColor(Color.black);
//                g2.drawString(text, x--, y--);
//                g2.setColor(Color.white);
//                g2.drawString(text, x, y);

                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);
                g2.drawString("<", (int) (x+panel.tankSize*1.8-1), y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
                g2.drawString("<", (int) (x+panel.tankSize*1.8), y);
            }

            text = "Exit";
            x = centredX(text);
            y = panel.tankSize*9;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(menuCommand==3){
//                g2.drawImage(redMenuBar, (int) (x-panel.tankSize*1.5), (int) (y-panel.tankSize/1.6), panel.tankSize*4, panel.tankSize, null);
//
//                g2.setColor(Color.black);
//                g2.drawString(text, x--, y--);
//                g2.setColor(Color.white);
//                g2.drawString(text, x, y);

                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);
                g2.drawString("<", (int) (x+panel.tankSize*1.8-1), y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
                g2.drawString("<", (int) (x+panel.tankSize*1.8), y);
            }
        }else if(titleScreenMode==2){
            String gameName = "Levels";
            g2.setFont(retroGaming.deriveFont(35f));
            int x = centredX(gameName);
            int y = panel.tankSize*3;
            g2.setColor(Color.black);
            g2.drawString(gameName, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(gameName, x, y);

            String text;
            g2.setFont(retroGaming.deriveFont(25f));
            text = "Level 1";
            x = centredX(text);
            y = panel.tankSize*6;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(menuCommand==0){

                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);
                g2.drawString("<", (int) (x+panel.tankSize*1.8-1), y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
                g2.drawString("<", (int) (x+panel.tankSize*1.8), y);
            }

            text = "Level 2";
            x = centredX(text);
            y = panel.tankSize*7;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(menuCommand==1){

                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);
                g2.drawString("<", (int) (x+panel.tankSize*1.8-1), y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
                g2.drawString("<", (int) (x+panel.tankSize*1.8), y);
            }

            text = "Level 3";
            x = centredX(text);
            y = panel.tankSize*8;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(menuCommand==2){

                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);
                g2.drawString("<", (int) (x+panel.tankSize*1.8-1), y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
                g2.drawString("<", (int) (x+panel.tankSize*1.8), y);
            }

            text = "Back";
            x = centredX(text);
            y = panel.tankSize*9;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if(menuCommand==3){
                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);
                g2.drawString("<", (int) (x+panel.tankSize*1.8-1), y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
                g2.drawString("<", (int) (x+panel.tankSize*1.8), y);
            }
        }
    }
    private void gameOverScreen(Graphics2D g2) {
        g2.drawImage(background, 0, 0, panel.screenWidth, panel.screenHeight, null);
            if(titleScreenMode==1) {
                String gameName = "Танки: Місія спалення Москви";
                g2.setFont(retroGaming.deriveFont(35f));
                int x = centredX(gameName);
                int y = panel.tankSize * 3;
                g2.setColor(Color.black);
                g2.drawString(gameName, x--, y--);
                g2.setColor(Color.white);
                g2.drawString(gameName, x, y);

                String text;
                g2.setFont(retroGaming.deriveFont(35f));
                text = "Game Over";
                x = centredX(text);
                y = panel.tankSize * 4;
                g2.setColor(Color.black);
                g2.drawString(text, x--, y--);
                g2.setColor(Color.red);
                g2.drawString(text, x, y);
            }
    }
    public int centredX(String text){
        int textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int xText = panel.screenWidth / 2 - textLenght / 2;
        return xText;
    }

    private void startTitleScreenTimer() {
        Timer timer = new Timer(550, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fill >= panel.tankSize * 4) {
                    ((Timer) e.getSource()).stop();
                    titleScreenMode=1;
                } else {
                    fill += 1;
                    panel.repaint();
                }
            }
        });
        timer.start();
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
    public void playerEnergy() {
        int x, y;
        x = panel.tankSize;
        y = panel.tankSize / 2;
        g2.setColor(Color.YELLOW);
        g2.drawRect(x,y*3,300,panel.tankSize/2);
        if(panel.player.lives>0){
            for (int i = 1; i <= panel.player.energy; i++) {
                g2.fillRect(x, y * 3, 300 / panel.player.maxEnergy, panel.tankSize / 2);
                x+=300/panel.player.maxEnergy;
            }
        }
        g2.drawImage(energy, panel.tankSize/2, (int) (y*2.5), panel.tankSize, panel.tankSize, null);
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
