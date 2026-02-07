public class IntroThread extends Thread {
    MyPanel pannello;
    int delay;
    boolean running = true;

    public IntroThread(MyPanel pannello, int d) {
        this.pannello = pannello;
        this.delay = d;
    }

    @Override
    public void run() {
        if(running)
            pannello.setFocusable(false);
        try {
            sleep(delay);
        } catch (Exception e) {
            running = false;
        }
        
        running = false;
        pannello.setFocusable(true);
        pannello.startGame();
        pannello.repaint();
    }

    public void stopThread() {
        running = false;
        this.interrupt();
    }
}