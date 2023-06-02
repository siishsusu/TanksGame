import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean up, down, left, right, shoot, pause;
    GamePanel panel;
    @Override
    public void keyTyped(KeyEvent e) {

    }
    public KeyHandler(GamePanel panel){
        this.panel=panel;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_UP){
            up=true;
        }else if(code==KeyEvent.VK_DOWN){
            down=true;
        }else if(code==KeyEvent.VK_LEFT){
            left=true;
        }else if(code==KeyEvent.VK_RIGHT){
            right=true;
        }else if(code==KeyEvent.VK_ENTER){
            shoot=true;
        }else if(code==KeyEvent.VK_SPACE){
            pause=true;
            if(panel.gameState==panel.playState) panel.gameState=panel.pauseState;
            else if (panel.gameState==panel.pauseState) panel.gameState=panel.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_UP){
            up=false;
        }else if(code==KeyEvent.VK_DOWN){
            down=false;
        }else if(code==KeyEvent.VK_LEFT){
            left=false;
        }else if(code==KeyEvent.VK_RIGHT){
            right=false;
        }else if(code==KeyEvent.VK_ENTER){
            shoot=false;
        }else if(code==KeyEvent.VK_SPACE){
            pause=false;
        }
    }
}
