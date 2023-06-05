import javax.swing.*;
import java.awt.*;

public class Bullet extends Projectiles{
    private int x, y;
    GamePanel panel;
    Image bulletImage;
    public Bullet(GamePanel panel){
        super(panel);
        this.panel=panel;
        alive=false;
        direction="right";
        playerSpeed = 7;
        maxLives = 100;
        lives = maxLives;
        getBullet();
    }
    public void getBullet(){
            up = new ImageIcon("imgs/bulletDown.png").getImage();
            down = new ImageIcon("imgs/bulletUp.png").getImage();
            right = new ImageIcon("imgs/bulletLeft.png").getImage();
            left = new ImageIcon("imgs/bulletRight.png").getImage();
    }
}
