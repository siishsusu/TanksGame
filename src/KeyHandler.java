import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean up, down, left, right, shoot, pause;
    GamePanel panel;
    AudioStorage audioStorage;
    Audio a;
    Audio shootAudio;
    @Override
    public void keyTyped(KeyEvent e) {

    }
    public KeyHandler(GamePanel panel){
        this.panel=panel;
         audioStorage = panel.audioStorage;
         a= audioStorage.getTrack(10);
         shootAudio= audioStorage.getTrack(6);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(panel.gameState==panel.playState || panel.gameState==panel.pauseState|| panel.gameState==panel.marketState){
            if (code == KeyEvent.VK_UP) {
                a.play();
                a.timerPlay();
                up = true;
            } else if (code == KeyEvent.VK_DOWN) {
                a.play();
                a.timerPlay();
                down = true;
            } else if (code == KeyEvent.VK_LEFT) {
                a.play();
                a.timerPlay();
                left = true;
            } else if (code == KeyEvent.VK_RIGHT) {
                a.play();
                a.timerPlay();
                right = true;
            } else if (code == KeyEvent.VK_ENTER) {
                shoot = true;
                shootAudio.sound();
            } else if (code == KeyEvent.VK_SPACE) {
                pause = true;
                if (panel.gameState == panel.playState) {
                    panel.gameState = panel.pauseState;
                    panel.backMusic.stop();
                }
                else if (panel.gameState == panel.pauseState) {
                    panel.gameState = panel.playState;
                    panel.backMusic.playMusic();
                }
            }else if (code == KeyEvent.VK_M) {
                pause = false;
                    panel.gameState = panel.titleState;
                    panel.ui.titleScreenMode=1;
                    panel.backMusic.play();
            }
            else if (code == KeyEvent.VK_B) {
                if (panel.gameState == panel.playState) {
                    panel.gameState = panel.marketState;
                    panel.backMusic.stop();
                }
                else if (panel.gameState == panel.marketState) {
                    panel.gameState = panel.playState;
                    panel.backMusic.play();

                }
            }
        }else if(panel.gameState==panel.titleState || panel.gameState==panel.endState){
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
                    else if(panel.ui.menuCommand==1 && panel.openLevel2){ //level 2
                        panel.level=2;
                        panel.gameState=panel.playState;
                    }
                    else if(panel.ui.menuCommand==2 && panel.openLevel3){ //level 3
                        panel.level=3;
                        panel.gameState=panel.playState;
                    }
                    else if(panel.ui.menuCommand==3) {//back
                        panel.ui.titleScreenMode=1;
                        panel.ui.menuCommand=0;
                    }
                }else if(panel.ui.titleScreenMode==3){
                    if(panel.ui.menuCommand==0){ //reset game
                        panel.gameState=panel.playState;
                        panel.resetGame();
                    }
                    else if(panel.ui.menuCommand==1){ //exit game
                        System.exit(0);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(panel.gameState==panel.titleState ) {
            audioStorage.getTrack(1).sound();
        }
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_UP&&!(panel.gameState==panel.titleState)){
            up=false;
        }else if(code==KeyEvent.VK_DOWN&&!(panel.gameState==panel.titleState)){
            down=false;
        }else if(code==KeyEvent.VK_LEFT &&!(panel.gameState==panel.titleState)){
            left=false;
        }else if(code==KeyEvent.VK_RIGHT&&!(panel.gameState==panel.titleState)){
            right=false;
        }else if(code==KeyEvent.VK_ENTER&&!(panel.gameState==panel.titleState)){
            shoot=false;
        }else if(code==KeyEvent.VK_SPACE&&!(panel.gameState==panel.titleState)){
            pause=false;
        }
    }
}
