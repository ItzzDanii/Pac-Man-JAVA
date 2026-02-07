import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class MyPanel extends JPanel {

    //dimensioni finestra
    final int screenW = 900; //900x1000
    final int screenH = 1000;

    Logica game_logic = new Logica(this, screenW, screenH);
    Grafica game_graphic = new Grafica(this, screenW, screenH, game_logic);

    PacManThread pacThread = new PacManThread(this);

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black)); 
        game_logic.initializeMap();
        SingleTon.getInstance().initImages();
        pacThread.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(screenW,screenH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);   
        game_graphic.drawMap(g); 
    }  

    // fa ripartire tutto (dopo game over)
    public void rematch(){

    }

    // chiude finestra
    public void close(){

    }
}
