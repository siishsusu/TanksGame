import java.util.ArrayList;

public class AudioStorage {
    ArrayList<Audio> audioList = new ArrayList();

    AudioStorage() {
        audioList.add(new Audio("audioFiles\\ix27ve-seen-scary-things-151751.wav", 0.7));
        audioList.add(new Audio("audioFiles\\knopka-komnatnogo-pereklyuchatelya1.wav", 0.8));
        audioList.add(new Audio("audioFiles\\knopka-menu-vybir.wav", 1));
        audioList.add(new Audio("audioFiles\\knopka-v-prostranstve-blizkii-multyashnyii-rezkii.wav", 1));
        audioList.add(new Audio("audioFiles\\korotkiy-metallicheskiy-zvuk-perezaryada.wav", 0.3));
        audioList.add(new Audio("audioFiles\\korotkiy-zvuk-perezaryada-vintovki.wav", 0.3));
        audioList.add(new Audio("audioFiles\\pojar-odinochnyii-korotkii-shumnyii-udar.wav", 0.9, 1600));
        audioList.add(new Audio("audioFiles\\tyajelyiy-tupoy-udar.wav", 0.3));
        audioList.add(new Audio("audioFiles\\tyanut-chto-to-po-trave-26082.wav", 0.3));
        audioList.add(new Audio("audioFiles\\udar-priglushennyiy-reshitelnyiy.wav", 0.3));
        audioList.add(new Audio("audioFiles\\zvuk-dvygyna.wav", 0.6, 470));
        audioList.add(new Audio("audioFiles\\zvuk pokypky.wav", 0.8));

    }
    public Audio getTrack(int nOfTrack){
        return audioList.get(nOfTrack);
    }

}
