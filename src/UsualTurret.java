import javax.swing.*;
import java.util.Random;

public class UsualTurret extends Tanks {
    public UsualTurret(GamePanel panel) {
        super(panel);
        playerType = 2;

        playerSpeed = 0;
        setLives(1);
        projectiles = new Bullet(panel);
        alive = true;

        solidCollision.x = 5;
        solidCollision.y = 5;
        solidCollision.width = 50;
        solidCollision.height = 50;
        solidCollisionSetupX = solidCollision.x;
        solidCollisionSetupY = solidCollision.y;

        getEnemyImage();
    }

    public void getEnemyImage() {
        up = new ImageIcon("imgs/enemyUp.png").getImage();
        down = new ImageIcon("imgs/enemyDown.png").getImage();
        right = new ImageIcon("imgs/enemyRight.png").getImage();
        left = new ImageIcon("imgs/enemyLeft.png").getImage();
    }

    public void setAction() {
        if (panel.level == 2) {
            //зміни для 2 рівня
        } else if (panel.level == 3) {
            //зміни для 3 рівня
        }


        Random random = new Random();
        int rand = random.nextInt(160);
        shootEnemy = rand;
        if (shootEnemy == 150 || shootEnemy==34) {
            projectiles.set(playerX, playerY, direction, this, true);
            panel.projectilesList.add(projectiles);

            shootEnemy = 0;
        }
    }
}
