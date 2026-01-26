// ! Generato con ChatGPT per gestione Sound !

import javax.sound.sampled.*;
import java.io.File;

public class Sound {

    private Clip clip;

    public Sound(String path) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null) return;

        if (clip.isRunning())
            clip.stop();   // evita sovrapposizioni

        clip.setFramePosition(0); // riparte da inizio
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
    
    public boolean isFinished() {
        if (clip == null) return true;
        return !clip.isRunning() && clip.getFramePosition() >= clip.getFrameLength();
    }
}
