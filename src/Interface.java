import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Interface {
    GamePanel panel;
    AudioStorage audioStorage;
    public boolean messageOn = false, gameOver = false, loading = false;
    public String message = "";
    public int counter = 0, fill=0, titleScreenMode = 0, menuCommand = 0;
    public double playTime;
    public int endState; // if 1 - win, if 2 - lose
    Font retroGaming, vampireWars;
    DecimalFormat format = new DecimalFormat("#0.00");
    File titleFont = new File("font/Vampire Wars.ttf"), mainFont = new File("font/Retro Gaming.ttf");
    Image heartFull = new ImageIcon("imgs/heart_full.png").getImage(), heartHalf = new ImageIcon("imgs/heart_half.png").getImage(),
            heartBlank = new ImageIcon("imgs/heart_blank.png").getImage(), energy = new ImageIcon("imgs/energy.png").getImage(),
            background = new ImageIcon("imgs/background menu.jpg").getImage(), coin = new ImageIcon("imgs/coin.png").getImage(),
            hangar = new ImageIcon("imgs/hangar menu.jpg").getImage(), key = new ImageIcon("imgs/key.png").getImage(),
            freezerImage = new ImageIcon("imgs/freezer.png").getImage(), armourImage = new ImageIcon("imgs/armory.png").getImage(),
            soundOnImage = new ImageIcon("imgs/zvuk-off.png").getImage(),
            soundOffImage = new ImageIcon("imgs/zvuk-on.png").getImage(),
            soundImage ;

    boolean isSoundOn=true;

    private Graphics2D g2;

    public Interface(GamePanel panel) {
        this.panel = panel;
        audioStorage = panel.audioStorage;
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
            //TIMER
//            playTime += (double) 1 / 60;
//            g2.drawString("Timer: " + format.format(playTime), (int) (panel.tankSize * 12.5), 65);
            g2.drawString("x" + panel.player.coins, (int) (panel.tankSize * 14), 75);
            g2.drawImage(coin, (int) (panel.tankSize * 13.2), 30, panel.tankSize, panel.tankSize, null);

            g2.drawString("x" + panel.player.hasKey, (int) (panel.tankSize * 14), 70+75);
            g2.drawImage(key, (int) (panel.tankSize * 13.2), 100, panel.tankSize, panel.tankSize, null);

            if (panel.player.gotFreezer>0 && panel.player.gotArmour==0){
                g2.drawString("x" + panel.player.gotFreezer, (int) (panel.tankSize * 14), 140 + 75);
                g2.drawImage(freezerImage, (int) (panel.tankSize * 13.2), 170, panel.tankSize, panel.tankSize, null);
            }else if (panel.player.gotFreezer==0 && panel.player.gotArmour>0){
                g2.drawString("x" + panel.player.gotArmour, (int) (panel.tankSize * 14), 140 + 75);
                g2.drawImage(armourImage, (int) (panel.tankSize * 13.2), 170, panel.tankSize, panel.tankSize, null);
            }else if (panel.player.gotFreezer>0 && panel.player.gotArmour>0){
                g2.drawString("x" + panel.player.gotFreezer, (int) (panel.tankSize * 14), 140 + 75);
                g2.drawImage(freezerImage, (int) (panel.tankSize * 13.2), 170, panel.tankSize, panel.tankSize, null);

                g2.drawString("x" + panel.player.gotArmour, (int) (panel.tankSize * 14), 210 + 75);
                g2.drawImage(armourImage, (int) (panel.tankSize * 13.2), 240, panel.tankSize, panel.tankSize, null);
            }

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
            titleScreenMode=3;
            gameOverScreen(g2);
            if(!panel.audioStorage.getTrack(13).isPlaying)
                panel.gameOverSound.play();
        } else if (panel.gameState == panel.pauseState) {
            pauseScreen();
        }
        else if (panel.gameState == panel.marketState) {
            marketScreen();
        }else if (panel.gameState == panel.inventoryState) {
            inventoryScreen();
        }
        else if (panel.gameState == panel.rulesState) {
            rulesScreen();
        }
    }

    /**
     * @param g2
     * Відмальовування екрану титрів
     */
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

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
            }
            String emoji = "";
            if(panel.openLevel2==true)text = "Level 2";
            else {
                text = "Level 2";
                emoji = "\uD83D\uDD12";
            }
            x = centredX(text);
            y = panel.tankSize*7;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            Font font = new Font("Segoe UI Emoji", Font.PLAIN, 16);
            g2.setFont(font);
            g2.drawString(emoji, x+120, y-5);
            g2.setFont(retroGaming.deriveFont(25f));
            if(menuCommand==1){
                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
            }
            if(panel.openLevel3==true)text = "Level 3";
            else {
                text = "Level 3";
                emoji = "\uD83D\uDD12";
            }
            x = centredX(text);
            y = panel.tankSize*8;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            //g2.setFont(retroGaming.deriveFont(16f));
            font = new Font("Segoe UI Emoji", Font.PLAIN, 16);
            g2.setFont(font);
            g2.drawString(emoji, x+120, y-5);
            g2.setFont(retroGaming.deriveFont(25f));
            if(menuCommand==2){
                g2.setColor(Color.black);
                g2.drawString(">", x-panel.tankSize-1, y--);

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
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

                g2.setColor(Color.white);
                g2.drawString(">", x-panel.tankSize, y);
            }
        }
    }

    /**
     * @param g2
     * Відмальовування екрану закінчення гри
     */
    private void gameOverScreen(Graphics2D g2) {
        if(titleScreenMode==3) {

            panel.enemies.clear();
            float opacity = 0.5f; // 50% opacity
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
            Composite previousComposite = g2.getComposite();
            g2.setComposite(alphaComposite);
            g2.drawImage(background, 0, 0, panel.screenWidth, panel.screenHeight, null);
            g2.setComposite(previousComposite);
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
            if(endState==1){
                text = "Game Over!!! You win!!!";
            }else{
                text = "Game Over! You lose!";
            }
            x = centredX(text);
            y = panel.tankSize * 4;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.red);
            g2.drawString(text, x, y);

            text = "Reset game";
            x = centredX(text);
            y = panel.tankSize * 6;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if (menuCommand == 0) {
                g2.setColor(Color.black);
                g2.drawString(">", x - panel.tankSize - 1, y--);

                g2.setColor(Color.white);
                g2.drawString(">", x - panel.tankSize, y);
            }
            text = "Exit game";
            x = centredX(text);
            y = panel.tankSize * 7;
            g2.setColor(Color.black);
            g2.drawString(text, x--, y--);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if (menuCommand == 1) {
                g2.setColor(Color.black);
                g2.drawString(">", x - panel.tankSize - 1, y--);

                g2.setColor(Color.white);
                g2.drawString(">", x - panel.tankSize, y);
            }
        }
    }

    /**
     * @param text
     * @return позицію по Х при якій поданий в метод текст буде розміщуватися по центру екрану
     */
    public int centredX(String text){
        int textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int xText = panel.screenWidth / 2 - textLenght / 2;
        return xText;
    }

    /**
     * Використовується для екрану титрів (завантаження гри)
     */
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

    /**
     * @param text
     * Передача тексту для відображення на екрані
     */
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    /**
     * Відмальовування екрану паузи та характеристики персонажа
     */
    public void pauseScreen() {
        String pauseText = "PAUSED";
        g2.setColor(Color.WHITE);
        g2.setFont(vampireWars.deriveFont(80f));
        int textLength = (int) g2.getFontMetrics().getStringBounds(pauseText, g2).getWidth();
        int xText = panel.screenWidth / 2 - textLength / 2;
        int yText = panel.screenHeight / 2;
        g2.drawString(pauseText, xText, yText);

        int frameX = panel.tankSize*2, frameY = panel.tankSize, frameWidth = panel.tankSize*12, frameHeight = panel.tankSize*9;
        float opacity = 0.5f; // 50% opacity
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        Composite previousComposite = g2.getComposite();
        g2.setComposite(alphaComposite);
        g2.setColor(Color.black);
        g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 35, 35);
        g2.setComposite(previousComposite);

        String text = "Player's tank character status";
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = centredX(text);
        yText = (int) (panel.tankSize*1.5);
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xText, yText);

        text = "Level: " + panel.level;
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = frameX+panel.tankSize/2;
        yText = (int) (panel.tankSize*2.5);
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xText, yText);

        text = "Lives: " + panel.player.lives + "/" + panel.player.maxLives;
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = frameX+panel.tankSize/2;
        yText = (int) (panel.tankSize*3.5);
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xText, yText);

        text = "Energy: " + panel.player.energy + "/" + panel.player.maxEnergy;
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = frameX+panel.tankSize/2;
        yText = (int) (panel.tankSize*4.5);
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xText, yText);

        text = "Speed: " + panel.player.playerSpeed;
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = frameX+panel.tankSize/2;
        yText = (int) (panel.tankSize*5.5);
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xText, yText);

        text = "Coins: " + panel.player.coinsAll;
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = frameX+panel.tankSize/2;
        yText = (int) (panel.tankSize*6.5);
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xText, yText);

        text = "Enemies left: " + panel.enemies.size();
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = frameX+panel.tankSize/2;
        yText = (int) (panel.tankSize*7.5);
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xText, yText);

        text = "if you want to go back to the menu press M";
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = centredX(text);
        yText = (int) (panel.tankSize*9.5);
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.red);
        g2.drawString(text, xText, yText);
         if(isSoundOn) {
             g2.drawImage(soundOnImage, 850, 100, 70, 70, null);
         }
         else {
             g2.drawImage(soundOffImage, 850, 100, 70, 70, null);
         }
        }


    public void inventoryScreen() {
        int xText, yText;

        int frameX = panel.screenWidth/2-panel.tankSize*2-20, frameY = panel.screenHeight/2-panel.tankSize*2, frameWidth = panel.tankSize*4+40, frameHeight = panel.tankSize*2;
        float opacity = 0.5f; // 50% opacity
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        Composite previousComposite = g2.getComposite();
        g2.setComposite(alphaComposite);
        g2.setColor(Color.black);
        g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 35, 35);
        g2.setComposite(previousComposite);

        String text = "Inventory";
        g2.setColor(Color.black);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = centredX(text);
        yText = frameY+panel.tankSize/2;
        g2.drawString(text, xText-1, yText-1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xText, yText);

        int xOneItem = frameX+5; int yOneItem = (int) (frameY+panel.tankSize/1.5);
        int xDiff = panel.tankSize+10; int yItem = yOneItem;

        opacity = 0.5f; // 50% opacity
        alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2.setComposite(alphaComposite);
        g2.setColor(Color.black);
        g2.fillRoundRect(xOneItem, yOneItem, panel.tankSize, panel.tankSize, 35, 35);
        g2.setComposite(previousComposite);
        g2.drawImage(freezerImage, xOneItem, yOneItem, panel.tankSize, panel.tankSize, null);
        text = String.valueOf(panel.freezerCount);
        g2.setColor(Color.WHITE);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = xOneItem + panel.tankSize/2 - g2.getFontMetrics().stringWidth(text)/2;
        yText = yOneItem + panel.tankSize/2 + g2.getFontMetrics().getHeight()/2-5;
        g2.drawString(text, xText, yText);

        opacity = 0.5f; // 50% opacity
        alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2.setComposite(alphaComposite);
        g2.setColor(Color.black);
        g2.fillRoundRect(xOneItem+xDiff, yOneItem, panel.tankSize, panel.tankSize, 35, 35);
        g2.setComposite(previousComposite);
        g2.drawImage(armourImage, xOneItem+xDiff, yOneItem, panel.tankSize, panel.tankSize, null);
        text = String.valueOf(panel.armourCount);
        g2.setColor(Color.WHITE);
        g2.setFont(retroGaming.deriveFont(30f));
        xText = xOneItem + xDiff + panel.tankSize/2 - g2.getFontMetrics().stringWidth(text)/2;
        yText = yOneItem + panel.tankSize/2 + g2.getFontMetrics().getHeight()/2 - 5;
        g2.drawString(text, xText, yText);

        opacity = 0.5f; // 50% opacity
        alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2.setComposite(alphaComposite);
        g2.setColor(Color.black);
        g2.fillRoundRect(xOneItem+xDiff*2, yOneItem, panel.tankSize, panel.tankSize, 35, 35);
        g2.setComposite(previousComposite);

        opacity = 0.5f; // 50% opacity
        alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2.setComposite(alphaComposite);
        g2.setColor(Color.black);
        g2.fillRoundRect(xOneItem+xDiff*3, yOneItem, panel.tankSize, panel.tankSize, 35, 35);
        g2.setComposite(previousComposite);

    }
    public void rulesScreen() {
        Image rules = new ImageIcon("imgs/rules game.png").getImage();

        g2.drawImage(rules, 0, 0, panel.screenWidth, panel.screenHeight, null);
    }


    /**
     * Відмальовування енергії гравця
     */
    public void playerEnergy() {
        int x, y;
        x = panel.tankSize;
        y = panel.tankSize / 2;
        g2.setColor(Color.YELLOW);
        g2.drawRect(x,y*3,300,panel.tankSize/2);
        if(panel.player.lives>0){
            if(panel.level==3){
                for (int i = 1; i <= panel.player.energy; i++) {
                    float opacity = 0.4f; // 50% opacity
                    AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
                    Composite previousComposite = g2.getComposite();
                    g2.setComposite(alphaComposite);
                    g2.fillRect(x, y * 3, 300 / panel.player.maxEnergy, panel.tankSize / 2);
                    g2.setComposite(previousComposite);
                    x += 300 / panel.player.maxEnergy;
                }
            }
        }
        g2.drawImage(energy, panel.tankSize/2, (int) (y*2.5), panel.tankSize, panel.tankSize, null);
    }

    /**
     * Відмальовування життів гравця
     */
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
            for (int i = 0; i < panel.player.lives; i++) {
                g2.drawImage(heartFull, x, y, panel.tankSize, panel.tankSize, null);
                x += panel.tankSize;
                if(x>=panel.screenWidth/2){
                    x = panel.tankSize / 2;
                    y += panel.tankSize;
                }
            }
    }

    private void marketScreen() {
        String marketName = "Market";

        int frameX = panel.tankSize * 2, frameY = panel.tankSize, frameWidth = panel.tankSize * 12, frameHeight = panel.tankSize * 9;
        float opacity = 0.5f; // 50% opacity
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        Composite previousComposite = g2.getComposite();
        g2.setComposite(alphaComposite);
        g2.setColor(Color.black);
        g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 35, 35);
        g2.setComposite(previousComposite);

        g2.drawImage(hangar, 0, 0, panel.screenWidth, panel.screenHeight, null);
        g2.setFont(vampireWars.deriveFont(80f));
        int textLength = (int) g2.getFontMetrics().getStringBounds(marketName, g2).getWidth();
        int xText = panel.screenWidth / 2 - textLength / 2;
        int yText = panel.screenHeight / 2;
        g2.setColor(Color.WHITE);
        g2.drawString(marketName, xText, yText);
        Image marketImage = new ImageIcon("imgs/market.png").getImage();
        g2.drawImage(marketImage, 0, 0, panel.tankSize*16, panel.tankSize*11, null);

        g2.setFont(retroGaming.deriveFont(30f));
        g2.setColor(Color.white);
        if(panel.player.coinsAll<20)g2.setColor(Color.red);
        g2.drawString("x" + panel.player.coinsAll, (int) (panel.tankSize * 14), 75);
        g2.drawImage(coin, (int) (panel.tankSize * 13.2), 30, panel.tankSize, panel.tankSize, null);
    }
}
