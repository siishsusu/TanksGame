import sun.security.krb5.internal.PAData;

public class AssetsSetter {
    GamePanel panel;
    public AssetsSetter(GamePanel panel){
        this.panel=panel;
    }
    public void setEnemies(){
        panel.enemies[0]=new enemyTanks(panel);
        panel.enemies[0].playerX=panel.tankSize*24;
        panel.enemies[0].playerY=panel.tankSize*18;
    }
}
