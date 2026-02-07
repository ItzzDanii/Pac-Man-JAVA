import java.awt.Color;

public class AnimateGameThread extends Thread{
    MyPanel pan;
    int delay;
    private boolean animate = true;

    AnimateGameThread(MyPanel p,int d){
        this.pan = p;
        this.delay = d;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(animate){
                    pan.game_graphic.ready_color= new Color(251, 133, 0);
                }
                else {
                    pan.game_graphic.ready_color= new Color(255, 183, 3);
                }
                animate = !animate;

                pan.repaint();

                sleep(delay);
            } catch (Exception e) {}
        }
    }
}
