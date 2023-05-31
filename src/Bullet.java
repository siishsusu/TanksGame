import javax.swing.*;
import java.awt.*;

public class Bullet {
    private int x, y;
    Image bullet;
    GamePanel panel;
    public Bullet(int x, int y){
        this.x=x;
        this.y=y;
    }
    public void getBullet(String direction, Graphics2D g2){
            if(direction.equals("up")) bullet = new ImageIcon("imgs/bulletDown.png").getImage();
            else if(direction.equals("down")) bullet = new ImageIcon("imgs/bulletUp.png").getImage();
            else if(direction.equals("right")) bullet = new ImageIcon("imgs/bulletLeft.png").getImage();
            else if(direction.equals("left")) bullet = new ImageIcon("imgs/bulletRight.png").getImage();

            g2.drawImage(bullet, x, y, 150, 150, null);
    }
}
