import java.awt.*;

public class Projectiles extends Tanks{
    Tanks user;
    public Projectiles(GamePanel panel){
        super(panel);
    }
    public void set (int playerX, int playerY, String direction, Tanks user, boolean alive){
        this.playerX=playerX;
        this.playerY=playerY;
        this.direction=direction;
        this.user=user;
        this.alive=alive;
        this.lives=maxLives;
    }
    public void update(){
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
}
