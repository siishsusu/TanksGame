import javax.swing.*;
import java.awt.*;

public class Tanks {
    public int playerX, playerY, playerSpeed, enemySpeed, shootEnemy = 0, spriteNum = 1, actions,
            solidCollisionSetupX, solidCollisionSetupY, invincibleCounter = 0, attack, playerType, coinsAll, coins;;
    public Rectangle solidCollision = new Rectangle(), attackArea =  new Rectangle();
    public boolean isCollided = false, isBreakable = false, isLivesDown = false, alive = true, invincible = false, isFrozen, isBurning;
    int bonus; //0 - нічого, 1 - заморожування, 2 - рікошет
    protected int defense;
    int type;
    protected boolean dying = false;
    String direction = "left";
    public int lives, maxLives, energy, maxEnergy;
    public Projectiles projectiles;
    public Image up, down, left, right;
    int indexOfObj = 999;
    int hasKey = 0;
    GamePanel panel;
    public Tanks(GamePanel panel) {
        this.panel=panel;
    }
    public void setAction(){}
    public void update() {
        setAction();
        isCollided=false;
        panel.checker.collideWithTile(this);

        panel.checker.checkTanks(this, panel.enemies);
        boolean contact = panel.checker.checkPlayer(this);
        //ДОПИСАТИ ПРО КУЛІ
        if(contact && playerType==2){
            if(!panel.player.invincible){
                panel.player.invincible=true;
//                panel.player.lives--;
            }
        }
        if(isCollided==false && isFrozen==false){
            switch (direction){
                case "up": playerY -= playerSpeed; break;
                case "down": playerY += playerSpeed; break;
                case "right": playerX += playerSpeed; break;
                case "left": playerX -= playerSpeed; break;
            }
        }
    }

    /**
     * @param number кількість життів
     * сеттер для задання кількості життів танків
     */
    public void setLives(int number){
        maxLives=number;
        lives=maxLives;
    }
    public void getFrozen() {
        up = new ImageIcon("imgs/ice.png").getImage();
        down = up;
        right = up;
        left = up;
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

    /**
     * @param number кількість енергії
     * сеттер для задання кількості енергії танків
     */
    public void setEnergy(int number){
        maxEnergy=number;
        energy=maxEnergy;
    }
    public void draw(Graphics2D g2){
        Image image = null;
        int screenX = playerX-panel.player.playerX + panel.player.screenX;
        int screenY = playerY-panel.player.playerY + panel.player.screenY;

        if(playerX+panel.tankSize>panel.player.playerX-panel.player.screenX &&
                playerX-panel.tankSize<panel.player.playerX+panel.player.screenX &&
                playerY+panel.tankSize>panel.player.playerY-panel.player.screenY &&
                playerY-panel.tankSize<panel.player.playerY+panel.player.screenY){
            switch (direction){
                case "up": image=up; break;
                case "down": image=down; break;
                case "right": image=right; break;
                case "left": image=left; break;
            }
            if(playerType==2){
                int x=screenX;
                double oneScale = panel.tankSize/maxLives;
                double value = oneScale*lives;
                g2.setColor(Color.black);
                g2.drawRect(screenX-1, screenY-16, panel.tankSize+2, 12);

                g2.setColor(Color.green);
                g2.fillRect(screenX, screenY-15, (int) value, 10);
            }

            g2.drawImage(image, screenX, screenY, panel.tankSize, panel.tankSize, null);
            if(dying){
                Image icon = new ImageIcon("imgs/explosion.gif").getImage();
                g2.drawImage(icon, screenX, screenY, panel.tankSize, panel.tankSize, null);
                dying(g2);
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    int dyingCounter = 0;

    /**
     * @param g2
     * Анімація вмирання танка (моргання)
     */
    void dying(Graphics2D g2) {
        dyingCounter ++;
        if(dyingCounter==30){
            dying=false;
            alive=false;
            isBurning=false;
            dyingCounter=0;
        }
    }
    void burning(Graphics2D g2) {
        dyingCounter ++;
        if(dyingCounter==350){
            isBurning=false;
            dyingCounter=0;
        }
    }

    protected void reactEnemy() {
    }
}
