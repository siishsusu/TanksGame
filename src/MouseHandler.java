
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    GamePanel panel;
    AudioStorage audioStorage;
    public MouseHandler(GamePanel panel){
        this.panel=panel;
        audioStorage = panel.audioStorage;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if ((e.getX() > 330)&&(e.getX() < 505)&&(e.getY() > 575)&&(e.getY() < 630) && panel.gameState == panel.marketState){
            System.out.println("Button");
        }

        if ((e.getX() > 650)&&(e.getX() < 825)&&(e.getY() > 575)&&(e.getY() < 630) && panel.gameState == panel.marketState){
            System.out.println("Button2");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
