import javax.swing.*;
import java.awt.*;

public class Bullet extends Projectiles{
    private int x, y;
    GamePanel panel;
    public Bullet(GamePanel panel){
        super(panel);

        typeProjectiles=1;

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

        if(user==panel.player) getBullet();
        else getRocket();
    }
}
