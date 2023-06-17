public class Projectiles extends Tanks{
    Tanks user;
    AudioStorage au = panel.audioStorage;
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
                damagePlayer(attack);
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

    private void damagePlayer(int attack) {
        if(!panel.player.invincible){
            invincible=true;
            int damage = attack - panel.player.defense;
            if(damage < 0){
                damage=0;
            }
            panel.player.lives -= damage;
            panel.player.invincible = true;
            panel.player.reactEnemy();

            if(panel.player.lives<=0){
                panel.player.dying = true;
                panel.gameState = panel.endState;
                panel.ui.gameOver = true;
                panel.ui.showMessage("Ви померли");
            }
        }
    }
}
