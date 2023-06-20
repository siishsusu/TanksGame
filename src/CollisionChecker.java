import java.util.ArrayList;

public class CollisionChecker {
    GamePanel panel;
    public CollisionChecker(GamePanel panel){
        this.panel=panel;
    }

    /**
     * @param tanks
     * Перевірка колізії з текстурами мапи
     */
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

    /**
     * @param tank
     * @param target
     * @return 999, якщо танк противника не задіто й індекс танку, якщо він уражений
     */
    public int checkTanks(Tanks tank, ArrayList<Tanks> target) {
        int index = 999;
        for(int i = 0; i<target.size(); i++){
            if(target.get(i) !=null){
                tank.solidCollision.x = tank.playerX + tank.solidCollision.x;
                tank.solidCollision.y = tank.playerY + tank.solidCollision.y;

                target.get(i).solidCollision.x = target.get(i).playerX + target.get(i).solidCollision.x;
                target.get(i).solidCollision.y = target.get(i).playerY + target.get(i).solidCollision.y;

                switch (tank.direction){
                    case "up":
                        tank.solidCollision.y -= tank.playerSpeed; break;
                    case "down":
                        tank.solidCollision.y += tank.playerSpeed; break;
                    case "right":
                        tank.solidCollision.x += tank.playerSpeed; break;
                    case "left":
                        tank.solidCollision.x -= tank.playerSpeed; break;
                }
                if(tank.solidCollision.intersects(target.get(i).solidCollision)){
                    if(target.get(i) !=tank){
                        tank.isCollided=true;
                        index=i;
                    }
                }
                tank.solidCollision.x=tank.solidCollisionSetupX;
                tank.solidCollision.y=tank.solidCollisionSetupY;

                target.get(i).solidCollision.x= target.get(i).solidCollisionSetupX;
                target.get(i).solidCollision.y= target.get(i).solidCollisionSetupY;
            }
        }
        return index;
    }

    /**
     * @param tank
     * @return false, якщо танк гравця не уражено і true, якщо уражено
     */
    public boolean checkPlayer(Tanks tank) {
        boolean contactPlayer = false;
            tank.solidCollision.x = tank.playerX + tank.solidCollision.x;
            tank.solidCollision.y = tank.playerY + tank.solidCollision.y;

            panel.player.solidCollision.x = panel.player.playerX + panel.player.solidCollision.x;
            panel.player.solidCollision.y = panel.player.playerY + panel.player.solidCollision.y;

            switch (tank.direction){
                case "up":
                    tank.solidCollision.y -= tank.playerSpeed; break;
                case "down":
                    tank.solidCollision.y += tank.playerSpeed; break;
                case "right":
                    tank.solidCollision.x += tank.playerSpeed; break;
                case "left":
                    tank.solidCollision.x -= tank.playerSpeed; break;
            }
            if(tank.solidCollision.intersects(panel.player.solidCollision)){
                tank.isCollided=true;
                contactPlayer=true;
            }
            tank.solidCollision.x=tank.solidCollisionSetupX;
            tank.solidCollision.y=tank.solidCollisionSetupY;

            panel.player.solidCollision.x=panel.player.solidCollisionSetupX;
            panel.player.solidCollision.y=panel.player.solidCollisionSetupY;
            return contactPlayer;
    }
    public int checkObject(Tanks tank, boolean player){
     int index = 999;
     for (int i=0; i< panel.objects.size(); i++){
         if(panel.objects.get(i)!=null) {
             tank.solidCollision.x = tank.playerX + tank.solidCollision.x;
             tank.solidCollision.y = tank.playerY + tank.solidCollision.y;

             panel.objects.get(i).solidArea.x = panel.objects.get(i).worldX + panel.objects.get(i).solidArea.x;
             panel.objects.get(i).solidArea.y = panel.objects.get(i).worldY + panel.objects.get(i).solidArea.y;

             switch (tank.direction){
                 case "up":
                     tank.solidCollision.y -= tank.playerSpeed;
                     if(tank.solidCollision.intersects(panel.objects.get(i).solidArea)){
                         if (panel.objects.get(i).collision ==true){
                             tank.isCollided= true;
                         }
                         if(player==true){
                             index = i;
                         }

                     }
                     break;

                 case "down":
                     tank.solidCollision.y += tank.playerSpeed;
                     if(tank.solidCollision.intersects(panel.objects.get(i).solidArea)){
                         if (panel.objects.get(i).collision ==true){
                             tank.isCollided= true;
                         }
                         if(player==true){
                             index = i;
                         }
                     }
                     break;
                 case "right":
                     tank.solidCollision.x += tank.playerSpeed;
                     if(tank.solidCollision.intersects(panel.objects.get(i).solidArea)){
                         if (panel.objects.get(i).collision ==true){
                             tank.isCollided= true;
                         }
                         if(player==true){
                             index = i;
                         }
                     }
                     break;
                 case "left":
                     tank.solidCollision.x -= tank.playerSpeed;
                     if(tank.solidCollision.intersects(panel.objects.get(i).solidArea)){
                         if (panel.objects.get(i).collision ==true){
                             tank.isCollided= true;
                         }
                         if(player==true){
                             index = i;
                         }
                     }
                     break;
                }

         tank.solidCollision.x = tank.solidCollisionSetupX;
         tank.solidCollision.y = tank.solidCollisionSetupY;
         panel.objects.get(i).solidArea.x = panel.objects.get(i).solidAreaDefaultX;
         panel.objects.get(i).solidArea.y = panel.objects.get(i).solidAreaDefaultY;
         }
     }

     return index;

    }
}
