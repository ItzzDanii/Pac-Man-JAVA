import javax.sound.sampled.*;
import java.io.File;

public class Sound {

    private Clip clip;

    public Sound(String path) {
        try {
            AudioInputStream originalStream =
                    AudioSystem.getAudioInputStream(new File(path));

            AudioFormat baseFormat = originalStream.getFormat();

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false // little-endian
            );

            AudioInputStream decodedStream =
                    AudioSystem.getAudioInputStream(decodedFormat, originalStream);

            clip = AudioSystem.getClip();
            clip.open(decodedStream);

        } catch (Exception e) {
            System.err.println("Errore caricamento audio: " + path);
            e.printStackTrace();
            clip = null;
        }
    }

    public void play() {
        if (clip == null) return;

        if (clip.isRunning())
            clip.stop();

        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        if (clip != null)
            clip.stop();
    }

    public boolean isFinished() {
        if (clip == null) return true;
        return !clip.isRunning() &&
               clip.getFramePosition() >= clip.getFrameLength();
    }
}
