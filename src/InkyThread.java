public class InkyThread extends Thread {
    private final MyPanel pannello;
    private final int delay;
    private boolean running = true;
    private Logica logica;

    public InkyThread(MyPanel pannello, int delay) {
        this.pannello = pannello;
        this.delay = delay;
        this.logica = pannello.game_logic;
    }

    @Override
    public void run() {
        while (running) {
            if (!logica.isReady && !logica.isGameOver()) {
                logica.moveInky(false);
                logica.checkInkyCollision();
                pannello.repaint();
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stopThread() {
        running = false;
        this.interrupt();
    }
}
