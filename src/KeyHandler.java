import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean up, down, left, right;
    @Override
    public void keyTyped(KeyEvent e) {

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
        }
    }
}
