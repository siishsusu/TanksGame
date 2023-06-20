import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public Image image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public void draw(Graphics2D g2 , GamePanel panel){
        int screenX = worldX - panel.player.playerX + panel.player.screenX;
        int screenY = worldY - panel.player.playerY + panel.player.screenY;

        if (worldX + panel.tankSize > panel.player.playerX - panel.player.screenX &&
                worldX - panel.tankSize < panel.player.playerX + panel.player.screenX &&
                worldY + panel.tankSize > panel.player.playerY - panel.player.screenY &&
                worldY - panel.tankSize < panel.player.playerY + panel.player.screenY) {
            g2.drawImage(image, screenX, screenY, panel.tankSize, panel.tankSize, null);
        }
    }
}
