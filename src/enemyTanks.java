import javax.swing.*;
import java.util.Random;

public class enemyTanks extends Tanks{
    public enemyTanks(GamePanel panel) {
        super(panel);
        playerType = 2;

        playerSpeed=2;
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
    public void getEnemyImage(){
        up = new ImageIcon("imgs/enemyUp.png").getImage();
        down = new ImageIcon("imgs/enemyDown.png").getImage();
        right = new ImageIcon("imgs/enemyRight.png").getImage();
        left = new ImageIcon("imgs/enemyLeft.png").getImage();
    }
    public void setAction(){
        if(panel.level==2) {
            //зміни для 2 рівня
        }else if(panel.level==3) {
            //зміни для 3 рівня
        }
        if(alive){
            actions++;
            if(actions==120){
                Random random = new Random();
                int rand = random.nextInt(100)+1;

                if(rand<=25) direction = "up";
                else if(rand>25 && rand<=50) direction = "down";
                else if(rand>50 && rand<=75) direction = "right";
                else if(rand>75 && rand<=100) direction = "left";

                actions=0;
            }
        }else{

        }
        shootEnemy++;
        if(shootEnemy==150){
            projectiles.set(playerX,playerY,direction,this,true);
            panel.projectilesList.add(projectiles);

            shootEnemy=0;
        }
    }
}
