import java.awt.*;

public class Tanks {
    public int playerX, playerY, playerSpeed, enemySpeed, shootEnemy = 0, spriteNum = 1, actions,
            solidCollisionSetupX, solidCollisionSetupY, invincibleCounter = 0, attack, playerType, coinsAll, coins;;
    public Rectangle solidCollision = new Rectangle(), attackArea =  new Rectangle();
    public boolean isCollided = false, isBreakable = false, isLivesDown = false, alive = true, invincible = false;
    protected int defense;
    protected boolean dying = false;
    String direction = "left";
    public int lives, maxLives, energy, maxEnergy;
    public Projectiles projectiles;
    public Image up, down, left, right;
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
        if(isCollided==false){
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
            if(dying){
                dying(g2);
            }
            g2.drawImage(image, screenX, screenY, panel.tankSize, panel.tankSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    int dyingCounter = 0;

    /**
     * @param g2
     * Анімація вмирання танка (моргання)
     */
    private void dying(Graphics2D g2) {
        dyingCounter ++;
        if(dyingCounter<=5){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }else if(dyingCounter>5 && dyingCounter<=10){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }else if(dyingCounter>10 && dyingCounter<=15){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }else if(dyingCounter>15 && dyingCounter<=20){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }else if(dyingCounter>20 && dyingCounter<=25){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }else if(dyingCounter>25 && dyingCounter<=30){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter==30){
            dying=false;
            alive=false;
        }
    }

    protected void reactEnemy() {
    }
}
