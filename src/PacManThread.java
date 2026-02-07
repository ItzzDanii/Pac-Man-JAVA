public class PacManThread extends Thread{
    MyPanel pan;
    int delay;
    private Logica logica;
    boolean running = true;

    public PacManThread(MyPanel p,int ms){
        this.pan = p;
        this.delay = ms;
        this.logica = p.game_logic;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            if(logica.isReady && !logica.isGameOver()){
                logica.movePacman();
                pan.repaint();
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
