
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    GamePanel panel;
    AudioStorage au;
    public MouseHandler(GamePanel panel){
        this.panel=panel;
        au = panel.audioStorage;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //marketState
        if ((e.getX() > 330)&&(e.getX() < 505)&&(e.getY() > 575)&&(e.getY() < 630) && panel.gameState == panel.marketState){
            if(panel.player.coinsAll>=150){
                panel.armourCount++;
                panel.player.coinsAll -= 150;
                au.getTrack(11).sound();;
            }
            System.out.println(panel.armourCount);
        }

        if ((e.getX() > 650)&&(e.getX() < 825)&&(e.getY() > 575)&&(e.getY() < 630) && panel.gameState == panel.marketState){
            if(panel.player.coinsAll>=20){
                panel.freezerCount++;
                panel.player.coinsAll -= 20;
                au.getTrack(11).sound();
            }
            System.out.println(panel.freezerCount);
        }

        //inventoryState
        int itemX = panel.screenWidth/2-panel.tankSize*2-15;
        int itemY = (int) (panel.screenHeight/2-panel.tankSize*2+panel.tankSize/1.5);
        int xDiff = panel.tankSize+10;
        if ((e.getX() > itemX)&&(e.getX() < itemX+panel.tankSize)&&(e.getY() > itemY)&&(e.getY() < itemY+panel.tankSize) && panel.gameState == panel.inventoryState){
            if(panel.freezerCount>0){
                panel.freezerCount--;
                panel.player.gotFreezer++;
                panel.player.freezerState();
                System.out.println("panel.player.gotFreezer " + panel.player.gotFreezer);
            }
        }

        if ((e.getX() > itemX+xDiff)&&(e.getX() < itemX+xDiff+panel.tankSize)&&(e.getY() > itemY)&&(e.getY() < itemY+panel.tankSize) && panel.gameState == panel.inventoryState){
            if(panel.armourCount>0){
                panel.armourCount--;
                panel.player.gotArmour++;
                panel.player.armourState();
                System.out.println("panel.player.gotArmour " + panel.player.gotArmour);
            }
        }

        if ((e.getX() > itemX+xDiff*2)&&(e.getX() < itemX+xDiff*2+panel.tankSize)&&(e.getY() > itemY)&&(e.getY() < itemY+panel.tankSize) && panel.gameState == panel.inventoryState){
            System.out.println("button 3");
        }
        if ((e.getX() > itemX+xDiff*3)&&(e.getX() < itemX+xDiff*3+panel.tankSize)&&(e.getY() > itemY)&&(e.getY() < itemY+panel.tankSize) && panel.gameState == panel.inventoryState){
            System.out.println("button 4");
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getX() > 850)&&(e.getX() < 920)&&(e.getY() > 100)&&(e.getY() < 170) && panel.gameState == panel.pauseState){
            if(panel.ui.isSoundOn){
            panel.ui.isSoundOn = false;
            panel.audioStorage.nSound=0;
                panel.audioStorage.setAllTracksVolume(0);

            }else {
                panel.ui.isSoundOn = true;
                panel.audioStorage.setAllTracksVolume(1);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
