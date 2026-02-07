public class PacManThread extends Thread{
    MyPanel pan;
    private Logica logica;

    public PacManThread(MyPanel p){
        this.pan = p;
        this.logica = p.game_logic;
    }

    @Override
    public void run() {
        while (true) {
            logica.movePacman();
            pan.repaint();
            try {
                sleep(50);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
