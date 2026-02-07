// ! PARZIALMENTE generato con ChatGPT per gestione di più potenziamenti in contemporanea/parallelo !
// Problema: partiva un potenziamento ma se ne partiva un altro quando c'era già uno in esecuzione, il timer non ripartiva e quindi 
// fermava tutti i potenziamenti

public class PowerUpThread extends Thread {
    private MyPanel pan;
    private int duration; // ms
    public int session;
    private boolean running = true;
    private Logica logica;

    public PowerUpThread(MyPanel pan, int duration) {
        this.pan = pan;
        this.duration = duration;
        logica = pan.game_logic;

        session = ++logica.powerSession;
    }

    @Override
    public void run() {

        if (running) {
                if (!running) return;
                logica.powered = true;
                SingleTon.getInstance().powerSound.play();
                SingleTon.getInstance().pac_vel -= 10;
                pan.repaint();
        }

        try {
            sleep(duration);
        } catch (InterruptedException e) {
            running = false;
            return;
        }

        if (running && session == logica.powerSession) {
                if (!running) return;
                logica.powered = false;
                SingleTon.getInstance().pac_vel += 10;
                SingleTon.getInstance().powerSound.stop();
                pan.repaint();
        }
    }

    public void stopThread() {
        running = false;
        this.interrupt();
    }
}
