import javax.swing.*;
import java.awt.*;

public class Bullet extends Projectiles{
    private int x, y;
    GamePanel panel;
    public Bullet(GamePanel panel){
        super(panel);
        alive=false;
        direction="right";
        playerSpeed = 7;
        maxLives = 100;
        lives = maxLives;
        attack=1;

        solidCollision.x = 0;
        solidCollision.y = 0;
        solidCollision.width = 55;
        solidCollision.height = 55;
        solidCollisionSetupX = solidCollision.x;
        solidCollisionSetupY = solidCollision.y;

        getBullet();
    }
    public void getBullet(){
            up = new ImageIcon("imgs/bulletDown.png").getImage();
            down = new ImageIcon("imgs/bulletUp.png").getImage();
            right = new ImageIcon("imgs/bulletLeft.png").getImage();
            left = new ImageIcon("imgs/bulletRight.png").getImage();
    }
}
