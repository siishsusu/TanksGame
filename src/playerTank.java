import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class playerTank extends Tanks {
    GamePanel panel;
    KeyHandler handler;
    int screenX, screenY;
    boolean impenetrable;
    int gotFreezer, gotArmour;
    Image tankImage = new ImageIcon("imgs/tank-right.png").getImage();
    AudioStorage audioStorage;
    public playerTank(GamePanel panel, KeyHandler handler) {
        super(panel);
        this.panel = panel;
        audioStorage= panel.audioStorage;
        this.handler = handler;

        playerType = 1;

        screenX = panel.screenWidth / 2 - panel.tankSize / 2;
        screenY = panel.screenHeight / 2 - panel.tankSize / 2;

        solidCollision.x = 0;
        solidCollision.y = 0;
        solidCollision.width = 55;
        solidCollision.height = 55;
        solidCollisionSetupX = solidCollision.x;
        solidCollisionSetupY = solidCollision.y;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefault();

        panel.gameState = panel.playState;
    }

    /**
     * Налаштовує дефолт гравця
     */
    public void setDefault (){
        setDefaultPosition();
        playerSpeed=4;
        coinsAll=999999;
        coins=0;
        coinsAll+=coins;
        setDefaultResources();
        if(bonus==0) projectiles = new Bullet(panel);
        else if(bonus==1) projectiles = new Frostbite(panel);
        getPlayerImage();
    }

    /**
     * Налаштовує дефолтні ресурси гравця
     */
    public void setDefaultResources() {
        setLives(3);
        setEnergy(5);
        invincible = false;
    }

    /**
     * Налаштовує дефолтну позицію гравця
     */
    public void setDefaultPosition() {
        playerX = panel.tankSize * 23;
        playerY = panel.tankSize * 21;
        direction = "right";
    }

    /**
     * Налаштовує зображення гравця
     */
    public void getPlayerImage() {
        up = new ImageIcon("imgs/tank-up.png").getImage();
        down = new ImageIcon("imgs/tank-down.png").getImage();
        right = new ImageIcon("imgs/tank-right.png").getImage();
        left = new ImageIcon("imgs/tank-left.png").getImage();
    }
    public void getPlayerImageFrozen() {
        up = new ImageIcon("imgs/tank-up-freeze.png").getImage();
        down = new ImageIcon("imgs/tank-down-freeze.png").getImage();
        right = new ImageIcon("imgs/tank-right-freeze.png").getImage();
        left = new ImageIcon("imgs/tank-left-freeze.png").getImage();
    }
    public void freezerState(){
        panel.player.projectiles = new Frostbite(panel);
        panel.player.bonus=1;
        panel.player.getPlayerImageFrozen();
    }
    public void armourState(){
        panel.player.projectiles = new Bullet(panel);
        panel.player.projectiles.getBullet();
        panel.player.bonus=2;
        panel.player.impenetrable=true;
        panel.player.getPlayerImage();
        if(gotArmour==0){
            panel.player.bonus=0;
            panel.player.impenetrable=false;
            panel.player.getPlayerImage();
        }
    }

    /**
     * Логіка стрільби
     */
    public void attack() {
        int worldX = playerX;
        int worldY = playerY;
        int worldWidth = solidCollision.width;
        int worldHeight = solidCollision.height;

        switch (direction) {
            case "up":
                playerY -= attackArea.height;
                break;
            case "down":
                playerY += attackArea.height;
                break;
            case "left":
                playerX -= attackArea.width;
                break;
            case "right":
                playerX += attackArea.width;
                break;
        }
        solidCollision.width = attackArea.width;
        solidCollision.height = attackArea.height;

        int enemyIndex = panel.checker.checkTanks(this, panel.enemies);
        damageEnemy(enemyIndex, attack);
        playerX = worldX;
        playerY = worldY;
        solidCollision.width = worldWidth;
        solidCollision.height = worldHeight;
    }

    public void update() {
        if (handler.up == true || handler.down == true || handler.right == true || handler.left == true) {
            isCollided = false;
            isLivesDown = false;
            panel.checker.collideWithTile(this);
            panel.checker.checkTanks(this, panel.enemies);
            indexOfObj = panel.checker.checkObject(this, true);
            pickUpObj(indexOfObj);
            if (handler.up) {
                tankImage = up;
                direction = "up";
            } else if (handler.down) {
                tankImage = down;
                direction = "down";
            } else if (handler.right) {
                tankImage = right;
                direction = "right";
            } else if (handler.left) {
                tankImage = left;
                direction = "left";
            }
            if (isCollided == false && lives > 0) {
                switch (direction) {
                    case "up":
                        playerY -= playerSpeed;
                        break;
                    case "down":
                        playerY += playerSpeed;
                        break;
                    case "right":
                        playerX += playerSpeed;
                        break;
                    case "left":
                        playerX -= playerSpeed;
                        break;
                }
            }
            if (lives == 0) {
                panel.ui.gameOver = true;
            }
        }
        if (handler.shoot == true && !projectiles.alive) {

            attack();
            audioStorage.getTrack(6).sound();
            projectiles.set(playerX, playerY, direction, this, true);
            panel.projectilesList.add(projectiles);
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (lives <= 0 || panel.enemies.size() <= 0) {
            if(panel.level==1){
                if (lives <= 0) {
                    panel.gameState = panel.endState;
                    panel.ui.gameOver = true;
                    panel.ui.endState = 2;
                    panel.level=1;
                    panel.manager.loadMap(new File("map2d.txt"));
                    panel.objects.clear();
                    panel.setupGame();
                } else if (panel.enemies.size() <= 0) {
                    panel.level=2;
                    panel.openLevel2=true;
                    panel.manager.loadMap(new File("test.txt"));
                    setDefaultPosition();
                    panel.player.hasKey=0;
                    panel.objects.clear();
                    panel.setupGame();
                    panel.ui.showMessage("Ви перейшли на 2 рівень");
                }
            }else if (panel.level==2){
                if (lives <= 0) {
                    panel.gameState = panel.endState;
                    panel.ui.gameOver = true;
                    panel.ui.endState = 2;
                    panel.level=2;
                } else if (panel.enemies.size() <= 0) {
                    panel.level=3;
                    panel.openLevel3=true;
                    panel.manager.loadMap(new File("boss-map.txt"));
                    setDefaultPosition();
                    panel.player.hasKey=0;
                    panel.objects.clear();
                    panel.setupGame();
                    panel.ui.showMessage("Ви перейшли на 3 рівень");
                }
            }
            else if(panel.level==3){
                panel.gameState = panel.endState;
                panel.ui.gameOver = true;
                panel.level=3;
                if (lives <= 0) {
                    panel.ui.endState = 2;
                } else if (panel.enemies.size() <= 0) {
                    panel.ui.endState = 1;
                }
            }
        }
    }

    /**
     * Перевіряє чи потрапив снаряд у ворога
     *
     * @param enemyIndex чи потрапила куля в ворога
     * @param attack     сила удару
     */
    public boolean damageEnemy(int enemyIndex, int attack) {

        if(enemyIndex!=999){
            System.out.println("hit");
            if(!panel.enemies.get(enemyIndex).invincible){
                invincible=true;
                if(projectiles.typeProjectiles==1){
                    int damage = attack - panel.enemies.get(enemyIndex).defense;
                    if (damage < 0) {
                        damage = 0;
                    }
                    panel.enemies.get(enemyIndex).lives -= damage;
                    panel.enemies.get(enemyIndex).invincible = false;
                    panel.enemies.get(enemyIndex).reactEnemy();

                    if (panel.enemies.get(enemyIndex).lives <= 0) {
                        panel.enemies.get(enemyIndex).dying = true;
                        coins += panel.enemies.get(enemyIndex).maxLives * 10;
                        coinsAll+=coins;

                        if (panel.ui.gameOver == false) {
                            panel.ui.showMessage("Танк противника усунено");
                        }

                        return true;
                    }
                }else if(bonus==1 ){
                    //заморожувальний снаряд
                    if(gotFreezer>0){
                        unFroze(enemyIndex);
                        int damage = attack - panel.enemies.get(enemyIndex).defense;
                        if (damage < 0) {
                            damage = 0;
                        }
                        panel.enemies.get(enemyIndex).lives -= damage;
                        panel.enemies.get(enemyIndex).invincible = false;
                        panel.enemies.get(enemyIndex).reactEnemy();

                        if (panel.enemies.get(enemyIndex).lives <= 0) {
                            panel.enemies.get(enemyIndex).dying = true;
                            coins += panel.enemies.get(enemyIndex).maxLives * 10;

                            if (panel.ui.gameOver == false) {
                                panel.ui.showMessage("Танк противника усунено");
                            }

                            return true;
                        }

                        if(gotFreezer==0){
                            panel.player.projectiles = new Bullet(panel);
                            panel.player.projectiles.getBullet();
                            panel.player.bonus=0;
                            panel.player.getPlayerImage();
                        }gotFreezer--;
                    }
                }else if(bonus==2){

                }
            }
        }else{
            System.out.println("miss");
            if(bonus==1){
                if(gotFreezer==0){
                    panel.player.projectiles = new Bullet(panel);
                    panel.player.projectiles.getBullet();
                    panel.player.bonus=0;
                    panel.player.getPlayerImage();
                }
//                gotFreezer--;
            }
        }
        return false;
    }
    private void unFroze(int enemyIndex) {
        panel.enemies.get(enemyIndex).playerSpeed=0;
        panel.enemies.get(enemyIndex).isFrozen=true;
        panel.enemies.get(enemyIndex).getFrozen();
        java.util.Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                panel.enemies.get(enemyIndex).isFrozen = false;
                panel.enemies.get(enemyIndex).getEnemyImage();
                panel.enemies.get(enemyIndex).playerSpeed=panel.enemies.get(enemyIndex).enemySpeed;
                panel.enemies.get(enemyIndex).setAction();
            }
        };
        long delay = 10000;
        timer.schedule(task, delay);
    }

    public void pickUpObj(int index) {
        if (index != 999) {
            String objName = panel.objects.get(index).name;
            switch (objName) {
                case "Key":
                    hasKey++;
                    panel.objects.set(index, null);
                    panel.objects.remove(index);
                    break;

                case "Door":
                    if (hasKey > 0) {
                        panel.objects.set(index, null);
                        panel.objects.remove(index);
                        hasKey--;
                    }
                    System.out.println(hasKey);
                    break;
                case "Mina":
                    panel.gameOverSound.sound();
                    if (gotArmour == 0) {
                        panel.player.lives--;
                        playerX += panel.tankSize * 2;
                        isBurning = true;
                    } else {
                        playerX += panel.tankSize * 2;
                        panel.player.gotArmour--;
                        panel.player.armourState();
                    }
                    break;
                case "Chest":
                    panel.objects.set(index, null);
                    panel.objects.remove(index);
                    panel.coins.sound();
                    panel.player.coins+=50;
                    break;

            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight());
        panel.manager.draw(g2d);
        int imageSize = panel.tankSize;
        g2d.drawImage(tankImage, screenX, screenY, imageSize, imageSize, null);
        if(isBurning){
            Image icon = new ImageIcon("imgs/fire.gif").getImage();
            g2d.drawImage(icon, screenX, screenY, panel.tankSize, panel.tankSize, null);
            burning(g2d);
        }
        if(panel.player.impenetrable){
            float opacity = 0.2f; // 50% opacity
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
            Composite previousComposite = g2d.getComposite();
            g2d.setComposite(alphaComposite);
            g2d.setColor(Color.green);
            g2d.fillOval(screenX, screenY, panel.tankSize, panel.tankSize);
            g2d.setComposite(previousComposite);
        }
    }
}
