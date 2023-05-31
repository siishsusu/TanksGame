import javax.swing.*;
import java.awt.*;

public class playerTank extends Tanks{
    GamePanel panel;
    KeyHandler handler;
    int screenX, screenY;
    Image tankImage = new ImageIcon("imgs/yellowRight.png").getImage();
    public playerTank(GamePanel panel, KeyHandler handler){
        this.panel=panel;
        this.handler=handler;

        screenX = panel.screenWidth/2-panel.tankSize/2;
        screenY = panel.screenHeight/2-panel.tankSize/2;

        setDefault();
    }
    public void setDefault (){
        playerX = panel.tankSize*23;
        playerY = panel.tankSize*21;
        playerSpeed=3;
    }
    public void update() {
        if (handler.up) {
            playerY -= playerSpeed;
            Image image = new ImageIcon("imgs/yellowUp.png").getImage();
            tankImage = image;
        } else if (handler.down) {
            playerY += playerSpeed;
            Image image = new ImageIcon("imgs/yellowDown.png").getImage();
            tankImage = image;
        } else if (handler.right) {
            playerX += playerSpeed;
            Image image = new ImageIcon("imgs/yellowRight.png").getImage();
            tankImage = image;
        } else if (handler.left) {
            playerX -= playerSpeed;
            Image image = new ImageIcon("imgs/yellowLeft.png").getImage();
            tankImage = image;
        }
    }
    public void draw(Graphics2D g2d){
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
        panel.manager.draw(g2d);
        int imageSize = panel.tankSize;
        g2d.drawImage(tankImage, screenX, screenY, imageSize, imageSize, null);
    }
}
