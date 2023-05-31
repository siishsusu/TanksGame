import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable{
    public final int orTankSize = 23, scale = 3, tankSize = orTankSize*scale;
    final int maxScreenCol = 16, maxScreenRow = 13;
    final int maxWorldCol = 50, maxWorldRow = 50;
    final int screenWidth = tankSize*maxScreenCol, screenHeight = tankSize*maxScreenRow;
    final int worldWidth = tankSize*maxWorldCol, worldHeight = tankSize*maxWorldRow;
    public final int titleState = 0, level1State = 1, level2State = 2, level3State = 3,
            pauseState = 4, endState = 5, shopState = 6;
    public static int gameState;
    Thread gameThread;
    KeyHandler handler = new KeyHandler();
    playerTank player = new playerTank(this, handler);
    int FPS = 60;
    TileManager manager = new TileManager(this);

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
            player.update();
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        player.draw(g2d);
    }

}
