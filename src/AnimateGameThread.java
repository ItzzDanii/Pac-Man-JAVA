import java.awt.Color;

public class AnimateGameThread extends Thread {
    MyPanel pan;
    int delay;
    private boolean animate = true;

    AnimateGameThread(MyPanel p, int d) {
        this.pan = p;
        this.delay = d;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(pan.game_logic.blinkyDead){
                    if(animate)
                        pan.game_graphic.ghosts_points = new Color(149, 213, 178);
                else pan.game_graphic.ghosts_points = new Color(82, 183, 136);
                }

                if (SingleTon.getInstance().score >= SingleTon.getInstance().max_score && SingleTon.getInstance().max_score != 0) {
                    if (animate)
                        pan.game_graphic.highscore = new Color(112, 224, 0);
                    else 
                        pan.game_graphic.highscore = new Color(56, 176, 0);
                }

                else if(SingleTon.getInstance().score <= SingleTon.getInstance().max_score)
                    pan.game_graphic.highscore = new Color(255,255,255);

                if (pan.game_logic.powered) {
                    if (animate)
                        pan.game_graphic.cell_fill_color = new Color(173, 40, 49);
                    else 
                        pan.game_graphic.cell_fill_color = new Color(128, 14, 19);
                } 
                else if (pan.game_logic.numeroPalle() < 50) {
                    if (animate)
                        pan.game_graphic.cell_fill_color = new Color(0, 78, 137);
                    else 
                        pan.game_graphic.cell_fill_color = new Color(26, 101, 158);
                } 
                else {
                    pan.game_graphic.cell_fill_color = new Color(0, 78, 137);
                }

                if (animate) {
                    pan.game_graphic.ready_color = new Color(251, 133, 0);
                } else {
                    pan.game_graphic.ready_color = new Color(255, 183, 3);
                }

                animate = !animate;
                pan.repaint();
                sleep(delay);
            } catch (Exception e) {}
        }
    }
}