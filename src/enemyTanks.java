import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class enemyTanks extends Tanks{

    public enemyTanks(GamePanel panel, int type) {
        super(panel);
        playerType = 2;
        this.type= type;
        enemySpeed = 2;
        playerSpeed=enemySpeed;
        setLives(3);
        projectiles = new Bullet(panel);
        alive=true;

        solidCollision.x = 5;
        solidCollision.y = 5;
        solidCollision.width = 50;
        solidCollision.height = 50;
        solidCollisionSetupX = solidCollision.x;
        solidCollisionSetupY = solidCollision.y;

        if(!isFrozen)getEnemyImage();
    }
    public void setAction(){
        if(panel.level==2) {
            //зміни для 2 рівня
        }else if(panel.level==3) {
            //зміни для 3 рівня
        }
        if(alive && !isFrozen){
                actions++;
                if (actions == 120) {
                    Random random = new Random();
                    int rand = random.nextInt(100) + 1;

                    if (rand <= 25) direction = "up";
                    else if (rand > 25 && rand <= 50) direction = "down";
                    else if (rand > 50 && rand <= 75) direction = "right";
                    else if (rand > 75 && rand <= 100) direction = "left";

                    actions = 0;
                }
                shootEnemy++;
                if (shootEnemy == 150 && !isFrozen) {
                    projectiles.set(playerX, playerY, direction, this, true);
                    panel.projectilesList.add(projectiles);

                    shootEnemy = 0;
                }
        }else{
            playerSpeed=enemySpeed;
        }
    }
    public void pickUpObj(int index) {
        if (index != 999) {
            String objName = panel.objects.get(index).name;
            switch (objName) {
                case "Key":
                    hasKey++;
                    panel.objects.set(index, null);
                    break;

                case "Door":
                    if (hasKey > 0) {
                        panel.objects.set(index, null);
                        hasKey--;
                    }
                    System.out.println(hasKey);
                    break;
                case "Mina":
                    lives-=1;
                    playerX+=panel.tankSize*2;
                    isBurning=true;
                    break;
            }
        }
    }
}
