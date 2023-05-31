import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable{
    public final int orTankSize = 23, scale = 3, tankSize = orTankSize*scale;
    final int maxScreenCol = 16, maxScreenRow = 13;
    final int maxWorldCol = 50, maxWorldRow = 50;
    final int screenWidth = tankSize*maxScreenCol, screenHeight = tankSize*maxScreenRow;
    final int worldWidth = tankSize*maxWorldCol, worldHeight = tankSize*maxWorldRow;
    public final int titleState = 0, playState = 1, pauseState = 2, endState = 3, shopState = 4;
    final int screenX = screenWidth/2-tankSize/2, screenY = screenHeight/2-tankSize/2;
    public static int gameState;
    Thread gameThread;
    KeyHandler handler = new KeyHandler();
    int FPS = 60;
    Image tankImage = new ImageIcon("imgs/yellowRight.png").getImage();
    TileManager manager = new TileManager(this);

    //початкова позиція гравця
    int playerX=tankSize*23;
    int playerY=tankSize*21;
    int playerSpeed=3;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(handler);
        this.setFocusable(true);
    }
    public void gameSetup(){
        gameState=titleState;
    }
    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval =1000000000/FPS;
        double nextDrawTime = System.nanoTime()+drawInterval;
        while (gameThread!=null){

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime-System.nanoTime();
                remainingTime=remainingTime/1000000;
                if(remainingTime<0) remainingTime=0;
                Thread.sleep((long) remainingTime);
                nextDrawTime+=drawInterval;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void update() {
        if (handler.up) {
            playerY -= playerSpeed;
            Image image = new ImageIcon("imgs/yellowUp.png").getImage();
            tankImage = image;
        } else if (handler.down) {
            playerY += playerSpeed;
            Image image = new ImageIcon("imgs/yellowDown.png").getImage();
            tankImage = image;
        } else if (handler.right) {
            playerX += playerSpeed;
            Image image = new ImageIcon("imgs/yellowRight.png").getImage();
            tankImage = image;
        } else if (handler.left) {
            playerX -= playerSpeed;
            Image image = new ImageIcon("imgs/yellowLeft.png").getImage();
            tankImage = image;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        manager.draw(g2d);
        int imageX = playerX;
        int imageY = playerY;
        int imageSize = tankSize;
        g2d.drawImage(tankImage, screenX, screenY, imageSize, imageSize, null);
    }

}
