public class PacManThread extends Thread {
    MyPanel pannello;
    int delay;
    boolean running = true;
    private Logica logica;

    public PacManThread(MyPanel pannello, int d) {
        this.pannello = pannello;
        this.delay = d;
        logica = pannello.game_logic;
    }

    @Override
    public void run() {
        while (running) {
            if (!logica.isReady && !logica.isGameOver()) {
                logica.movePacman();
                pannello.repaint();      
            }
            try {
                sleep(delay);
            } catch (Exception e) {
                running = false;
            }
        }
    }

    public void stopThread() {
        running = false;
        this.interrupt();
    }
}