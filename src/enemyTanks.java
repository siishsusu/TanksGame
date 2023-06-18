import javax.swing.*;
import java.util.Random;

public class enemyTanks extends Tanks{
    public enemyTanks(GamePanel panel) {
        super(panel);
        playerType = 2;

        enemySpeed=2;
        playerSpeed=enemySpeed;
        setLives(1);
        projectiles = new Bullet(panel);
        alive=true;

        solidCollision.x = 5;
        solidCollision.y = 5;
        solidCollision.width = 50;
        solidCollision.height = 50;
        solidCollisionSetupX = solidCollision.x;
        solidCollisionSetupY = solidCollision.y;

        getEnemyImage();
    }

    /**
     *Налаштовує зображення противника
     */
    public void getEnemyImage(){
        up = new ImageIcon("imgs/enemyUp.png").getImage();
        down = new ImageIcon("imgs/enemyDown.png").getImage();
        right = new ImageIcon("imgs/enemyRight.png").getImage();
        left = new ImageIcon("imgs/enemyLeft.png").getImage();
    }

    /**
     * Налаштовує дії противника
     */
    public void setAction(){
        if(panel.level==1){
            //зміни для 1 рівня
            if(alive){
                if(isCollided==true){
                    if(direction == "up") direction = "right";
                    else if(direction == "right") direction = "down";
                    else if(direction == "down") direction = "left";
                    else if(direction == "left") direction = "up";
                }
            }
            shootEnemy++;
            if(shootEnemy==150){
                projectiles.set(playerX,playerY,direction,this,true);
                panel.projectilesList.add(projectiles);
                shootEnemy=0;
            }
        }
        else if(panel.level==2) {
            //зміни для 2 рівня
            if(alive){
                shootEnemy++;
                if(shootEnemy==150){
                    projectiles.set(playerX,playerY,direction,this,true);
                    panel.projectilesList.add(projectiles);
                    if(projectiles.playerDamaged){
                        projectiles.set(playerX,playerY,direction,this,true);
                        panel.projectilesList.add(projectiles);
                        playerSpeed=0;
                    }else if(isCollided==true){
                        if(direction == "up") direction = "right";
                        else if(direction == "right") direction = "down";
                        else if(direction == "down") direction = "left";
                        else if(direction == "left") direction = "up";
                    }
                    playerSpeed=enemySpeed;
                    shootEnemy=0;
                }
//                actions++;
//                if (actions == 120) {
//                    Random random = new Random();
//                    int rand = random.nextInt(100) + 1;
//
//                    if (rand <= 25) direction = "up";
//                    else if (rand > 25 && rand <= 50) direction = "down";
//                    else if (rand > 50 && rand <= 75) direction = "right";
//                    else if (rand > 75 && rand <= 100) direction = "left";
//
//                    actions = 0;
//                }
            }
        }else if(panel.level==3) {
            //зміни для 3 рівня
        }

    }
}
