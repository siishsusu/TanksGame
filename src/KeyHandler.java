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
        if(panel.gameState==panel.playState){
            if (code == KeyEvent.VK_UP) {
                up = true;
            } else if (code == KeyEvent.VK_DOWN) {
                down = true;
            } else if (code == KeyEvent.VK_LEFT) {
                left = true;
            } else if (code == KeyEvent.VK_RIGHT) {
                right = true;
            } else if (code == KeyEvent.VK_ENTER) {
                shoot = true;
            } else if (code == KeyEvent.VK_SPACE) {
                pause = true;
                if (panel.gameState == panel.playState) panel.gameState = panel.pauseState;
                else if (panel.gameState == panel.pauseState) panel.gameState = panel.playState;
            }
        }else if(panel.gameState==panel.titleState ){
            if (code == KeyEvent.VK_UP) {
                panel.ui.menuCommand--;
                if(panel.ui.menuCommand<0)panel.ui.menuCommand=0;
            } else if (code == KeyEvent.VK_DOWN) {
                panel.ui.menuCommand++;
                if(panel.ui.menuCommand>3)panel.ui.menuCommand=3;
            }else if (code == KeyEvent.VK_ENTER) {
                if(panel.ui.titleScreenMode==1){
                    if(panel.ui.menuCommand==0)panel.gameState=panel.playState; //play
                    else if(panel.ui.menuCommand==1){ //levels
                        panel.ui.titleScreenMode=2;
                        panel.ui.menuCommand=0;
                    }
                    else if(panel.ui.menuCommand==2); //rules
                    else if(panel.ui.menuCommand==3) System.exit(0); //exit
                }else if(panel.ui.titleScreenMode==2){
                    if(panel.ui.menuCommand==0){ //level 1
                        panel.level=1;
                        panel.gameState=panel.playState;
                    }
                    else if(panel.ui.menuCommand==1){ //level 2
                        panel.level=2;
                        panel.gameState=panel.playState;
                    }
                    else if(panel.ui.menuCommand==2){ //level 3
                        panel.level=3;
                        panel.gameState=panel.playState;
                    }
                    else if(panel.ui.menuCommand==3) {//back
                        panel.ui.titleScreenMode=1;
                        panel.ui.menuCommand=0;
                    }
                }
            }
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
