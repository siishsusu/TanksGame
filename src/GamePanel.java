import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    AudioStorage audioStorage= new AudioStorage();
    public final int orTankSize = 23, scale = 3, tankSize = orTankSize*scale;
    final int maxScreenCol = 16, maxScreenRow = 11;
    final int maxWorldCol = 50, maxWorldRow = 50;
    final int screenWidth = tankSize*maxScreenCol, screenHeight = tankSize*maxScreenRow;
    public ArrayList<Tanks> projectilesList = new ArrayList<>();
    public ArrayList<Tanks> tanksList = new ArrayList<>();
    public final int titleState = 0, pauseState = 1, endState = 2, playState = 3;
    public int level, playerType;
    public static int gameState;
    Thread gameThread;
    KeyHandler handler = new KeyHandler(this);
    playerTank player = new playerTank(this, handler);
    CollisionChecker checker = new CollisionChecker(this);
    Tanks enemies[] = new Tanks[5];
    Interface ui = new Interface(this);
    AssetsSetter setter = new AssetsSetter(this);
    int FPS = 60;
    Audio a;
    TileManager manager = new TileManager(this);

    public GamePanel(){
        a = audioStorage.getTrack(0);
        a.sound();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(handler);
        this.setFocusable(true);
        setupGame();
    }
    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }
    public  void  setupGame(){
        setter.setEnemies();
        gameState=titleState;
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
        if(gameState==playState){
            player.update();
            for(int i = 0; i<enemies.length; i++){
                if(enemies[i]!=null){
                    if(enemies[i].alive==true && enemies[i].dying==false)enemies[i].update();
                    else if (enemies[i].alive==false)enemies[i]=null;
                }
            }
            for (int i = 0; i<projectilesList.size(); i++){
                if(projectilesList.get(i)!=null){
                    if(projectilesList.get(i).alive == true){
                        projectilesList.get(i).update();
                    }if(projectilesList.get(i).alive == false){
                        projectilesList.remove(i);
                    }
                }
            }
        }
        else if (gameState==pauseState);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(gameState==titleState){
            ui.draw(g2d);
        }else{
            player.draw(g2d);
            tanksList.add(player);

            for(int i = 0; i<enemies.length; i++){
                if(enemies[i]!=null){
                    tanksList.add(enemies[i]);
                }
            }
            for(int i = 0; i<projectilesList.size(); i++){
                if(projectilesList.get(i)!=null){
                    tanksList.add(projectilesList.get(i));
                }
            }
            for(int i = 0; i<tanksList.size(); i++){
                tanksList.get(i).draw(g2d);
            }
            tanksList.clear();
            ui.draw(g2d);

        }

    }

}
