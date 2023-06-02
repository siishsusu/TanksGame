public class CollisionChecker {
    GamePanel panel;
    public CollisionChecker(GamePanel panel){
        this.panel=panel;
    }
    public void collideWithTile(Tanks tanks){
        int collideUpTank = (int) (tanks.playerY+tanks.solidCollision.getY());
        int collideDownTank = (int) (tanks.playerY+tanks.solidCollision.getY()+tanks.solidCollision.getHeight());
        int collideRightTank = (int) (tanks.playerX+tanks.solidCollision.getX()+tanks.solidCollision.getWidth());
        int collideLeftTank = (int) (tanks.playerX+tanks.solidCollision.getX());

        int collideUpRow = collideUpTank/panel.tankSize;
        int collideDownRow = collideDownTank/panel.tankSize;
        int collideLeftCol = collideLeftTank/panel.tankSize;
        int collideRightCol = collideRightTank/panel.tankSize;

        int tile1, tile2;

        switch (tanks.direction){
            case "up":
                collideUpRow=(collideUpTank-tanks.playerSpeed)/panel.tankSize;
                tile1 = panel.manager.mapTileNum[collideLeftCol][collideUpRow];
                tile2 = panel.manager.mapTileNum[collideRightCol][collideUpRow];
                if(panel.manager.tile[tile1].collide==true||panel.manager.tile[tile2].collide==true){
                    tanks.isCollided=true;
                }
                if(panel.manager.tile[tile1].brake==true||panel.manager.tile[tile2].brake==true){
                    tanks.isBreakable=true;
                    panel.manager.paintCell(collideDownRow, collideLeftCol, 0);
                }
                if(panel.manager.tile[tile1].livesDown==true||panel.manager.tile[tile2].livesDown==true){
                    if(panel.player.lives>0){
                        tanks.isLivesDown = true;
                        panel.player.lives--;
                        panel.ui.showMessage("Ви втратили 1 життя");
                        panel.player.setDefault();
                    }
                }
                break;
            case "down":
                collideDownRow=(collideDownTank+tanks.playerSpeed)/panel.tankSize;
                tile1 = panel.manager.mapTileNum[collideLeftCol][collideDownRow];
                tile2 = panel.manager.mapTileNum[collideRightCol][collideDownRow];
                if(panel.manager.tile[tile1].collide==true||panel.manager.tile[tile2].collide==true){
                    tanks.isCollided=true;
                }
                if(panel.manager.tile[tile1].brake==true||panel.manager.tile[tile2].brake==true){
                    tanks.isBreakable=true;
                    panel.manager.paintCell(collideDownRow, collideLeftCol, 0);
                }
                break;
            case "right":
                collideRightCol=(collideRightTank-tanks.playerSpeed)/panel.tankSize;
                tile1 = panel.manager.mapTileNum[collideRightCol][collideUpRow];
                tile2 = panel.manager.mapTileNum[collideRightCol][collideDownRow];
                if(panel.manager.tile[tile1].collide==true||panel.manager.tile[tile2].collide==true){
                    tanks.isCollided=true;
                }
                if(panel.manager.tile[tile1].brake==true||panel.manager.tile[tile2].brake==true){
                    tanks.isBreakable=true;
                    panel.manager.paintCell(collideDownRow, collideLeftCol, 0);
                }
                break;
            case "left":
                collideLeftCol=(collideLeftTank-tanks.playerSpeed)/panel.tankSize;
                tile1 = panel.manager.mapTileNum[collideLeftCol][collideUpRow];
                tile2 = panel.manager.mapTileNum[collideLeftCol][collideDownRow];
                if(panel.manager.tile[tile1].collide==true||panel.manager.tile[tile2].collide==true){
                    tanks.isCollided=true;
                }
                if(panel.manager.tile[tile1].brake==true||panel.manager.tile[tile2].brake==true){
                    tanks.isBreakable=true;
                    panel.manager.paintCell(collideDownRow, collideLeftCol, 0);
                }
                break;
        }
    }
}
