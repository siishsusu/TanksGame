import javax.swing.*;
import java.awt.*;

public class playerTank extends Tanks{
    GamePanel panel;
    KeyHandler handler;
    int screenX, screenY;
    Image tankImage = new ImageIcon("imgs/tank-right.png").getImage();
    public playerTank(GamePanel panel, KeyHandler handler){
        super(panel);
        this.panel=panel;
        this.handler=handler;

        screenX = panel.screenWidth/2-panel.tankSize/2;
        screenY = panel.screenHeight/2-panel.tankSize/2;

        solidCollision.x = 0;
        solidCollision.y = 0;
        solidCollision.width = 55;
        solidCollision.height = 55;
        solidCollisionSetupX = solidCollision.x;
        solidCollisionSetupY = solidCollision.y;

        attackArea.width=36;
        attackArea.height=36;

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
        up = new ImageIcon("imgs/tank-up.png").getImage();
        down = new ImageIcon("imgs/tank-down.png").getImage();
        right = new ImageIcon("imgs/tank-right.png").getImage();
        left = new ImageIcon("imgs/tank-left.png").getImage();
    }
    public void attack(){
        int worldX = playerX;
        int worldY = playerY;
        int worldWidth = solidCollision.width;
        int worldHeight = solidCollision.height;

        switch (direction){
            case "up": playerY -= attackArea.height; break;
            case "down": playerY += attackArea.height; break;
            case "left": playerX -= attackArea.width; break;
            case "right": playerX += attackArea.width; break;
        }
        solidCollision.width=attackArea.width;
        solidCollision.height=attackArea.height;

        int enemyIndex = panel.checker.checkTanks(this, panel.enemies);
        damageEnemy(enemyIndex, attack);
        playerX = worldX;
        playerY=worldY;
        solidCollision.width=worldWidth;
        solidCollision.height=worldHeight;
    }
    public void update() {
        if(handler.up==true||handler.down==true||handler.right==true||handler.left==true){
                isCollided=false; isLivesDown=false;
                panel.checker.collideWithTile(this);

                int enemyIndex = panel.checker.checkTanks(this, panel.enemies);

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
                attack();
                projectiles.set(playerX,playerY,direction,this,true);
                panel.projectilesList.add(projectiles);
            }
            if(invincible){
                invincibleCounter++;
                if(invincibleCounter>60){
                    invincible=false;
                    invincibleCounter=0;
                }
            }
    }

    public void damageEnemy(int enemyIndex, int attack) {

        if(enemyIndex!=999){
            if(!panel.enemies[enemyIndex].invincible){
                invincible=true;
                int damage = attack - panel.enemies[enemyIndex].defense;
                if(damage < 0){
                    damage=0;
                }
                panel.enemies[enemyIndex].lives -= damage;
                panel.enemies[enemyIndex].invincible = true;
                panel.enemies[enemyIndex].reactEnemy();

                if(panel.enemies[enemyIndex].lives<=0){
                    panel.enemies[enemyIndex].dying = true;
                    panel.ui.gameOver=true;
                    panel.ui.showMessage("Ви вбили когось я хз");
                }
            }
        }else{
            System.out.println("miss");
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
