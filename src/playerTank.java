import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class playerTank extends Tanks{
    GamePanel panel;
    KeyHandler handler;
    int screenX, screenY;

    Image tankImage = new ImageIcon("imgs/yellowRight.png").getImage();
    public playerTank(GamePanel panel, KeyHandler handler){
        super(panel);
        this.panel=panel;
        this.handler=handler;

        screenX = panel.screenWidth/2-panel.tankSize/2;
        screenY = panel.screenHeight/2-panel.tankSize/2;

        solidCollision = new Rectangle(0,0,55,55);

        setDefault();
        maxLives=3;
        lives=maxLives;
        maxEnergy=5;
        energy=maxEnergy;
        panel.gameState=panel.playState;
    }
    public void setDefault (){
        playerX = panel.tankSize*23;
        playerY = panel.tankSize*21;
        playerSpeed=4;
        direction="right";
        projectiles = new Bullet(panel);
        getPlayerImage();
    }
    public void getPlayerImage(){
        up = new ImageIcon("imgs/yellowUp.png").getImage();
        down = new ImageIcon("imgs/yellowDown.png").getImage();
        right = new ImageIcon("imgs/yellowRight.png").getImage();
        left = new ImageIcon("imgs/yellowLeft.png").getImage();
    }
    public void update() {
        if(handler.up==true||handler.down==true||handler.right==true||handler.left==true){
            isCollided=false; isLivesDown=false;
            panel.checker.collideWithTile(this);
            if (handler.up) {
                tankImage = up;
                direction="up";
            } else if (handler.down) {
                tankImage = down;
                direction="down";
            } else if (handler.right) {
                tankImage = right;
                direction="right";
            } else if (handler.left) {
                tankImage = left;
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
        if(handler.shoot==true && !projectiles.alive){
            projectiles.set(playerX,playerY,direction,this,true);
            panel.projectilesList.add(projectiles);
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
