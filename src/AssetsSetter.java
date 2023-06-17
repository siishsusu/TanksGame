
public class AssetsSetter {
    GamePanel panel;
    public AssetsSetter(GamePanel panel){
        this.panel=panel;
    }
    public void setEnemies(){
        panel.enemies.add(new enemyTanks(panel, 1));
        panel.enemies.get(0).playerX=panel.tankSize*24;
        panel.enemies.get(0).playerY=panel.tankSize*17;


        panel.enemies.add(new enemyTanks(panel, 2));
        panel.enemies.get(1).playerX=panel.tankSize*25;
        panel.enemies.get(1).playerY=panel.tankSize*17;

        panel.enemies.add(new enemyTanks(panel, 2));
        panel.enemies.get(2).playerX=panel.tankSize*26;
        panel.enemies.get(2).playerY=panel.tankSize*17;

        panel.enemies.add(new UsualTurret(panel));
        panel.enemies.get(3).playerX=panel.tankSize*27;
        panel.enemies.get(3).playerY=panel.tankSize*17;
        panel.enemies.get(3).direction="up";
    }
}
