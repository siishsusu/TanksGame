import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Turrets {
    public int playerX, playerY, playerSpeed, shootEnemy = 0, spriteNum = 1, actions,
            solidCollisionSetupX, solidCollisionSetupY, invincibleCounter = 0, attack;
    public Rectangle solidCollision = new Rectangle(), attackArea =  new Rectangle();
    public boolean isCollided = false, isBreakable = false, isLivesDown = false, alive = true, invincible = false;
    protected int defense;
    protected boolean dying = false;
    String direction = "left";
    public int lives, maxLives;
    public Projectiles projectiles;
    public Image turretImg;
    GamePanel panel;
        public Turrets(GamePanel panel) {
//            panel.playerType = 2;
            maxLives=1;
            lives=maxLives;
            projectiles = new Bullet(panel);
            alive=true;
            solidCollision.x = 5;
            solidCollision.y = 5;
            solidCollision.width = 50;
            solidCollision.height = 50;
            solidCollisionSetupX = solidCollision.x;
            solidCollisionSetupY = solidCollision.y;
            turretImg = new ImageIcon("imgs/enemyUp.png").getImage();
        }

    public void draw(Graphics2D g2){
        Image image = null;
        int screenX = playerX-panel.player.playerX + panel.player.screenX;
        int screenY = playerY-panel.player.playerY + panel.player.screenY;

        if(playerX+panel.tankSize>panel.player.playerX-panel.player.screenX &&
                playerX-panel.tankSize<panel.player.playerX+panel.player.screenX &&
                playerY+panel.tankSize>panel.player.playerY-panel.player.screenY &&
                playerY-panel.tankSize<panel.player.playerY+panel.player.screenY){

                 image=turretImg;


//            if(panel.playerType==2){
//                g2.setColor(Color.black);
//                int fill = lives/panel.tankSize*100;
//                g2.drawRect(screenX-1, screenY-16, panel.tankSize+2, 12);
//                g2.setColor(Color.green);
//                g2.fillRect(screenX, screenY-15, panel.tankSize, 10);
//            }
            if(dying){
                dying(g2);
            }
            g2.drawImage(image, screenX, screenY, panel.tankSize, panel.tankSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    int dyingCounter = 0;
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
        public void setAction(){
            if(alive){
                actions++;
                if(actions==120){
                    Random random = new Random();
                    int rand = random.nextInt(100)+1;

                    if(rand<=25) direction = "up";
                    else if(rand>25 && rand<=50) direction = "down";
                    else if(rand>50 && rand<=75) direction = "right";
                    else if(rand>75 && rand<=100) direction = "left";

                    actions=0;
                }
            }else{

            }

//            shootEnemy++;
//            if(shootEnemy==150){
//                projectiles.set(playerX,playerY,direction,this,true);
//                panel.projectilesList.add(projectiles);
//
//                shootEnemy=0;
//            }
        }
    }


