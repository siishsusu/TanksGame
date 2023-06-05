import java.awt.*;

public class Tanks {
    public int playerX, playerY, playerSpeed, spriteCounter = 0, spriteNum = 1;
    public Rectangle solidCollision;
    public boolean isCollided = false, isBreakable = false, isLivesDown = false, alive = true;
    String direction = "left";
    public int lives, maxLives, energy, maxEnergy;
    public Projectiles projectiles;
    public Image up, down, left, right;
    GamePanel panel;

    public Tanks(GamePanel panel) {
        this.panel=panel;
    }
    public void update() {
        isCollided=false;
        panel.checker.collideWithTile(this);
        if(isCollided==false){
            switch (direction){
                case "up": playerY -= playerSpeed; break;
                case "down": playerY += playerSpeed; break;
                case "right": playerX += playerSpeed; break;
                case "left": playerX -= playerSpeed; break;
            }
        }
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
            g2.drawImage(image, screenX, screenY, panel.tankSize, panel.tankSize, null);
        }
    }
}
