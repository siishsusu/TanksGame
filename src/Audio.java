import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    public String track;
    private Clip clip =null;
    private FloatControl volumeC=null;
    private double volume;
    private boolean plAudio=false;
    private long timerP=0;//початковий час звучання
    private long timerD;//к-сть часу звучвання
    private long timerF=0;//кінцевий час звучання
    public boolean isPlaying=false;

    public Audio(String track, double volume) {
        this.track = track;
        this.volume=volume;
    }
    public Audio(String track, double volume, long timerD) {
        this.track = track;
        this.volume=volume;
        this.timerD = timerD;
        this.plAudio=false;
    }
    public void sound() {
        File audioFile = new File(this.track);
        AudioInputStream tr = null;
        try {
            tr = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();
            clip.open(tr);
            volumeC = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume();
            clip.setFramePosition(0);
            clip.start();
        }
        catch (LineUnavailableException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void play() {
        isPlaying=true;
        File audioFile = new File(this.track);
        AudioInputStream tr = null;
        try {
            tr = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();
            clip.open(tr);
            volumeC = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume();
            if (!this.plAudio) {
                clip.setFramePosition(0);
                clip.start();
                this.plAudio = true;
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public void stop(){
        isPlaying=false;
        clip.stop();
        clip.close();
        plAudio=false;
    }
    public void setVolume() {
        if (volume < 0) volume = 0;
        if (volume > 1) volume = 1;
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        volumeC.setValue((max - min) * (float) volume + min);
    }

    public void loop(){
        if(this.plAudio){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void playMusic(){
            play();
            loop();
    }
    public void timerPlay(){
        if(timerP==0){
            timerP=System.currentTimeMillis();
            timerF=timerP+timerD;
        }
        if(timerF<System.currentTimeMillis()){
            this.stop();
            timerP=0;
        }
    }
}
