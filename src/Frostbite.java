import javax.swing.*;

public class Frostbite extends Projectiles{
    private int x, y;
    GamePanel panel;
    public Frostbite(GamePanel panel){
        super(panel);

        typeProjectiles=2;

        alive=false;
        direction="right";
        playerSpeed = 7;
        maxLives = 100;
        lives = maxLives;
        attack=2;

        solidCollision.x = 0;
        solidCollision.y = 0;
        solidCollision.width = 55;
        solidCollision.height = 55;
        solidCollisionSetupX = solidCollision.x;
        solidCollisionSetupY = solidCollision.y;

        getFrostbite();
    }

    /**
     * Геттер для отримання зображення заморожувального снаряду
     */
    public void getFrostbite(){
        up = new ImageIcon("imgs/frostbiteUp.png").getImage();
        down = new ImageIcon("imgs/frostbiteDown.png").getImage();
        right = new ImageIcon("imgs/frostbiteRight.png").getImage();
        left = new ImageIcon("imgs/frostbiteLeft.png").getImage();
    }
}
