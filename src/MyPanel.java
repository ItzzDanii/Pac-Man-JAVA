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

    PacManThread pacThread = new PacManThread(this,50);
    ThreadBlinky blinkyThread = new ThreadBlinky(this, 150);
    IntroThread introThread = new IntroThread(this, 5000);

    public MyPanel() {
        addKeyListener(new MyKeyboardAdapter(this));
        setBorder(BorderFactory.createLineBorder(Color.black)); 
        game_logic.initializeMap();
        SingleTon.getInstance().initImages();
        pacThread.start();
        blinkyThread.start();
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

    public void startGame() {
        game_logic.isReady = false;
        if (pacThread == null) {
            pacThread = new PacManThread(this, 180);
            pacThread.start();
        }

        if(blinkyThread==null){
            blinkyThread = new ThreadBlinky(this, game_logic.ghost_vel);
            blinkyThread.start();
        }
    }
    
    // chiude finestra
    public void close(){
        if (introThread != null) introThread.stopThread();
        if (pacThread != null) pacThread.stopThread();
        if(blinkyThread!= null) blinkyThread.stopThread();

        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) parentWindow.dispose();
    }
}
