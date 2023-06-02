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

        solidCollision = new Rectangle(0,0,55,55);

        setDefault();
        maxLives=3;
        lives=maxLives;
        panel.gameState=panel.playState;
    }
    public void setDefault (){
        playerX = panel.tankSize*23;
        playerY = panel.tankSize*21;
        playerSpeed=4;
        direction="left";
    }
    public void update() {
        if(handler.up==true||handler.down==true||handler.right==true||handler.left==true){
            isCollided=false; isLivesDown=false;
            panel.checker.collideWithTile(this);
            if (handler.up) {
                Image image = new ImageIcon("imgs/yellowUp.png").getImage();
                tankImage = image;
                direction="up";
            } else if (handler.down) {
                Image image = new ImageIcon("imgs/yellowDown.png").getImage();
                tankImage = image;
                direction="down";
            } else if (handler.right) {
                Image image = new ImageIcon("imgs/yellowRight.png").getImage();
                tankImage = image;
                direction="right";
            } else if (handler.left) {
                Image image = new ImageIcon("imgs/yellowLeft.png").getImage();
                tankImage = image;
                direction="left";
            }
            if(isCollided==false && lives>0){
                switch (direction){
                    case "up": playerY -= playerSpeed; break;
                    case "down": playerY += playerSpeed; break;
                    case "right": playerX += playerSpeed; break;
                    case "left": playerX -= playerSpeed; break;
                }
            }if(lives==0){
                panel.ui.gameOver=true;
            }
        }
    }
    public void draw(Graphics2D g2d){
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
        panel.manager.draw(g2d);
        int imageSize = panel.tankSize;
        g2d.drawImage(tankImage, screenX, screenY, imageSize, imageSize, null);
        if (handler.shoot) {
            Bullet bullet = new Bullet(playerX,playerY);
            bullet.getBullet(direction, g2d);
        }
    }
}
