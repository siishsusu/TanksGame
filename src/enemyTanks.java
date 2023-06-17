import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class enemyTanks extends Tanks{
    int type;
    public enemyTanks(GamePanel panel, int type) {
        super(panel);
        playerType = 2;
        this.type= type;
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



    public void getEnemyImage() {
        switch (type){
        case 1:{
            up = new ImageIcon("imgs/enemy-tank-up-1.png").getImage();
            down = new ImageIcon("imgs/enemy-tank-down-1.png").getImage();
            right = new ImageIcon("imgs/enemy-tank-right-1.png").getImage();
            left = new ImageIcon("imgs/enemy-tank-left-1.png").getImage();
            break;
        }
            case 2:{
                up = new ImageIcon("imgs/enemy-tank-up.png").getImage();
                down = new ImageIcon("imgs/enemy-tank-down.png").getImage();
                right = new ImageIcon("imgs/enemy-tank-right.png").getImage();
                left = new ImageIcon("imgs/enemy-tank-left.png").getImage();
                break;
            }
     }
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
