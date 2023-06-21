import javax.swing.*;

public class Projectiles extends Tanks{
    Tanks user;
    AudioStorage au = panel.audioStorage;
    public boolean playerDamaged = false;
    public int typeProjectiles; //
    public Projectiles(GamePanel panel){
        super(panel);
    }
    public void set (int playerX, int playerY, String direction, Tanks user, boolean alive){
        playerType=3;
        this.playerX=playerX;
        this.playerY=playerY;
        this.direction=direction;
        this.user=user;
        this.alive=alive;
        this.lives=maxLives;
    }

    public void update(){
        if(user == panel.player){
            int enemyIndex = panel.checker.checkTanks(this, panel.enemies);
            if(enemyIndex!=999){
                au.getTrack(7).sound();
                panel.player.damageEnemy(enemyIndex, attack);
                alive=false;
            }
        }else if(user != panel.player){
            boolean playerContacted = panel.checker.checkPlayer(this);
            if(panel.player.invincible==false && playerContacted == true){
                playerDamaged = damagePlayer(attack);
                alive=false;
            }
        }
        switch (direction){
            case "up": playerY -= playerSpeed; break;
            case "down": playerY += playerSpeed; break;
            case "right": playerX += playerSpeed; break;
            case "left": playerX -= playerSpeed; break;
        }
        lives--;
        if(lives<=0){
            alive=false;
        }
    }

    /**
     * @param attack сила нанесення удару на гравця
     * перевірка стану танку гравця
     */
    public boolean damagePlayer(int attack) {
        if(!panel.player.invincible){
            invincible=true;
            int damage = attack - panel.player.defense;
            if(damage < 0){
                damage=0;
            }
            if(!panel.player.impenetrable){
                panel.player.lives -= damage;
                panel.player.invincible = true;
                panel.player.reactEnemy();
            }else if(panel.player.impenetrable){
                panel.player.gotArmour--;
                panel.player.armourState();
            }

            if(panel.player.lives<=0){
                panel.player.dying = true;
                panel.gameState = panel.endState;
                panel.ui.gameOver = true;
                panel.ui.showMessage("Ви померли");
            }
            return true;
        }
        return false;
    }
    /**
     * Геттер для отримання зображення кулі
     */
    public void getBullet(){
        up = new ImageIcon("imgs/bulletDown.png").getImage();
        down = new ImageIcon("imgs/bulletUp.png").getImage();
        right = new ImageIcon("imgs/bulletLeft.png").getImage();
        left = new ImageIcon("imgs/bulletRight.png").getImage();
    }
    /**
     * Геттер для отримання зображення ракети
     */
    public void getRocket(){
        up = new ImageIcon("imgs/rocket-up.png").getImage();
        down = new ImageIcon("imgs/rocket-down.png").getImage();
        right = new ImageIcon("imgs/rocket-right.png").getImage();
        left = new ImageIcon("imgs/rocket-left.png").getImage();
    }
}
