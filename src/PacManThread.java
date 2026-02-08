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
                if(logica.numeroPalle() < 200 && !logica.inkyReleased){
                    logica.inkyReleased=true;
                    logica.inkyY -= 3;
                }
                if(logica.numeroPalle() < 190 && !logica.pinkyReleased){
                    logica.pinkyReleased=true;
                    logica.pinkyY -= 3;
                }
                if(logica.numeroPalle() < 180 && !logica.clydeReleased){
                    logica.clydeReleased=true;
                    logica.clydeY -= 3;
                }
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