import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    public Audio coins;
    AudioStorage audioStorage= new AudioStorage();
    public final int orTankSize = 23, scale = 3, tankSize = orTankSize*scale;
    final int maxScreenCol = 16, maxScreenRow = 11;
    final int maxWorldCol = 50, maxWorldRow = 50;
    final int screenWidth = tankSize*maxScreenCol, screenHeight = tankSize*maxScreenRow;
    public ArrayList<Tanks> projectilesList = new ArrayList<>();
    public ArrayList<Tanks> tanksList = new ArrayList<>();
    public final int titleState = 0, pauseState = 1, endState = 2, playState = 3, marketState=4, inventoryState=5, rulesState=6;
    public int level = 1;
    public boolean openLevel2 = false, openLevel3 = false;
    public static int gameState;
    Thread gameThread;
    KeyHandler handler = new KeyHandler(this);
    MouseHandler mouseHandler = new MouseHandler(this);
    int freezerCount=0, armourCount =0;
    playerTank player = new playerTank(this, handler);
    CollisionChecker checker = new CollisionChecker(this);
    public ArrayList<Tanks> enemies = new ArrayList<>();
    Interface ui = new Interface(this);
    AssetsSetter setter = new AssetsSetter(this);
    int FPS = 60;
    Audio backMusic, gameOverSound;
    TileManager manager = new TileManager(this);
    public ArrayList<SuperObject> objects= new ArrayList <>();

    public GamePanel(){
        backMusic = audioStorage.getTrack(0);
        backMusic.play();
        coins = audioStorage.getTrack(14);
        gameOverSound= audioStorage.getTrack(13);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(handler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
        setupGame();
        gameState=titleState;
    }
    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }
    public  void  setupGame(){
        setter.setEnemies();
        setter.setObjects();
    }
    public void resetGame(){
        player.setDefault();
        setter.setEnemies();
        setter.setObjects();
        player.projectiles.getBullet();
        gameOverSound.isPlaying=false;

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
            for(int i = 0; i<enemies.size(); i++){
                if(enemies.get(i) !=null){
                    if(enemies.get(i).alive==true && enemies.get(i).dying==false) enemies.get(i).update();
                    else if (enemies.get(i).alive==false && enemies.get(i).dying==false) {
                        enemies.remove(i);
                        System.out.println(enemies.size());
                    }
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

            for(int i = 0; i<enemies.size(); i++){
                if(enemies.get(i) !=null){
                    tanksList.add(enemies.get(i));
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
            for(int i = 0; i<objects.size(); i++){
                if(objects.get(i)!=null){
                    objects.get(i).draw(g2d, this);
                }
            }
            tanksList.clear();
            ui.draw(g2d);
        }

    }

}
