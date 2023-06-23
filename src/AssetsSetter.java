import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AssetsSetter {
    GamePanel panel;
    Timer timer;
    public AssetsSetter(GamePanel panel){
        this.panel=panel;
    }
    public void setEnemies(){
        if(panel.level==1){
            panel.enemies.add(new enemyTanks(panel, 1));
            panel.enemies.get(0).playerX=panel.tankSize*24;
            panel.enemies.get(0).playerY=panel.tankSize*6;
            panel.enemies.get(0).projectiles.setLives(30);

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(1).playerX=panel.tankSize*25;
            panel.enemies.get(1).playerY=panel.tankSize*6;
            panel.enemies.get(1).projectiles.setLives(30);

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(2).playerX=panel.tankSize*26;
            panel.enemies.get(2).playerY=panel.tankSize*6;
            panel.enemies.get(2).setLives(5);
            panel.enemies.get(2).projectiles.setLives(30);

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(3).playerX=panel.tankSize*27;
            panel.enemies.get(3).playerY=panel.tankSize*6;
            panel.enemies.get(3).setLives(1);
            panel.enemies.get(3).projectiles.setLives(30);

            panel.enemies.add(new UsualTurret(panel));
            panel.enemies.get(4).playerX = panel.tankSize * 11;
            panel.enemies.get(4).playerY = panel.tankSize * 17;
            panel.enemies.get(4).projectiles.setLives(30);
            panel.enemies.get(4).direction = "right";

            panel.enemies.add(new UsualTurret(panel));
            panel.enemies.get(5).playerX = panel.tankSize * 11;
            panel.enemies.get(5).playerY = panel.tankSize * 18;
            panel.enemies.get(5).projectiles.setLives(30);
            panel.enemies.get(5).direction = "right";

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(6).playerX = panel.tankSize * 10;
            panel.enemies.get(6).playerY = panel.tankSize * 19;
            panel.enemies.get(6).projectiles.setLives(30);
            panel.enemies.get(6).direction = "right";
        }else if(panel.level==2){
            panel.enemies.add(new enemyTanks(panel, 1));
            panel.enemies.get(0).playerX=panel.tankSize*24;
            panel.enemies.get(0).playerY=panel.tankSize*6;
            panel.enemies.get(0).projectiles.setLives(60);

            panel.enemies.add(new enemyTanks(panel, 1));
            panel.enemies.get(1).playerX=panel.tankSize*25;
            panel.enemies.get(1).playerY=panel.tankSize*6;
            panel.enemies.get(1).projectiles.setLives(60);

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(2).playerX=panel.tankSize*26;
            panel.enemies.get(2).playerY=panel.tankSize*6;
            panel.enemies.get(2).setLives(5);
            panel.enemies.get(2).projectiles.setLives(60);

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(3).playerX=panel.tankSize*27;
            panel.enemies.get(3).playerY=panel.tankSize*6;
            panel.enemies.get(3).setLives(1);
            panel.enemies.get(3).projectiles.setLives(60);

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(4).playerX=panel.tankSize*23;
            panel.enemies.get(4).playerY=panel.tankSize*6;
            panel.enemies.get(4).projectiles.setLives(60);

            panel.enemies.add(new enemyTanks(panel, 1));
            panel.enemies.get(5).playerX = panel.tankSize * 33;
            panel.enemies.get(5).playerY = panel.tankSize * 40;
            panel.enemies.get(5).projectiles.setLives(60);

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(6).playerX = panel.tankSize * 33;
            panel.enemies.get(6).playerY = panel.tankSize * 41;
            panel.enemies.get(6).projectiles.setLives(60);

            panel.enemies.add(new enemyTanks(panel, 2));
            panel.enemies.get(7).playerX = panel.tankSize * 33;
            panel.enemies.get(7).playerY = panel.tankSize * 42;
            panel.enemies.get(7).projectiles.setLives(60);

            panel.enemies.add(new enemyTanks(panel, 1));
            panel.enemies.get(8).playerX = panel.tankSize * 33;
            panel.enemies.get(8).playerY = panel.tankSize * 43;
            panel.enemies.get(8).projectiles.setLives(60);
        }
        else if(panel.level==3){
            panel.enemies.add(new UsualTurret(panel));
            panel.enemies.get(0).playerX=panel.tankSize*20;
            panel.enemies.get(0).playerY=panel.tankSize*28;
            panel.enemies.get(0).direction="up";

            panel.enemies.add(new UsualTurret(panel));
            panel.enemies.get(1).playerX=panel.tankSize*22;
            panel.enemies.get(1).playerY=panel.tankSize*28;
            panel.enemies.get(1).direction="up";

            panel.enemies.add(new UsualTurret(panel));
            panel.enemies.get(2).playerX=panel.tankSize*24;
            panel.enemies.get(2).playerY=panel.tankSize*28;
            panel.enemies.get(2).direction="up";

            panel.enemies.add(new UsualTurret(panel));
            panel.enemies.get(3).playerX=panel.tankSize*26;
            panel.enemies.get(3).playerY=panel.tankSize*28;
            panel.enemies.get(3).direction="up";

            panel.enemies.add(new UsualTurret(panel));
            panel.enemies.get(4).playerX=panel.tankSize*27;
            panel.enemies.get(4).playerY=panel.tankSize*28;
            panel.enemies.get(4).direction="up";

            setEnemiesTimed(20,6);
        }
    }
    public void setObjects(){
            panel.objects.add(new KeyObj());
            panel.objects.get(0).worldX = panel.tankSize * 25;
            panel.objects.get(0).worldY = panel.tankSize * 15;

            panel.objects.add(new KeyObj());
            panel.objects.get(1).worldX = panel.tankSize * 26;
            panel.objects.get(1).worldY = panel.tankSize * 15;

            panel.objects.add(new GateObj());
            panel.objects.get(2).worldX = panel.tankSize * 23;
            panel.objects.get(2).worldY = panel.tankSize * 14;
            panel.objects.add(new GateObj());
            panel.objects.get(3).worldX = panel.tankSize * 24;
            panel.objects.get(3).worldY = panel.tankSize * 14;

            panel.objects.add(new MinaObj());
            panel.objects.get(4).worldX = panel.tankSize * 25;
            panel.objects.get(4).worldY = panel.tankSize * 17;

    }
    public void setEnemiesTimed(int x, int y) {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int tankCount = 0;
            @Override
            public void run() {
                if (tankCount < 20) {
                    panel.enemies.add(new enemyTanks(panel, 1));
                    panel.enemies.get(panel.enemies.size() - 1).playerX = panel.tankSize * x;
                    panel.enemies.get(panel.enemies.size() - 1).playerY = panel.tankSize * y;
                    Random random = new Random();
                    int livesAndSpeed = random.nextInt(5)+1;
                    panel.enemies.get(panel.enemies.size() - 1).setLives(livesAndSpeed);
                    panel.enemies.get(panel.enemies.size() - 1).enemySpeed=livesAndSpeed+3;
                    if(livesAndSpeed==1 || livesAndSpeed==2) panel.enemies.get(panel.enemies.size() - 1).direction="down";
                    else if(livesAndSpeed==3 || livesAndSpeed==4 || livesAndSpeed==5)panel.enemies.get(panel.enemies.size() - 1).direction="right";
                    tankCount++;
                    System.out.println(tankCount);
                } else {
                    timer.cancel();
                }
            }
        };

        long delay = 0;
        long period = 5000;
        timer.scheduleAtFixedRate(task, delay, period);
    }

}
