
public class AssetsSetter {
    GamePanel panel;
    public AssetsSetter(GamePanel panel){
        this.panel=panel;
    }
    public void setEnemies(){
        panel.enemies.add(new enemyTanks(panel));
        panel.enemies.get(0).playerX=panel.tankSize*24;
        panel.enemies.get(0).playerY=panel.tankSize*17;
    }
}
