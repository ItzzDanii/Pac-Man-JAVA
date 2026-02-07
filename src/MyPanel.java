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
    BlinkyThread blinkyThread = new BlinkyThread(this, 150);
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
        game_logic.gameOver = false;
        game_logic.firstTime = true;
        game_logic.introPlayed = false;

        game_logic.initializeMap();
        game_logic.resetPositions();

        game_logic.resetScore();
        game_logic.updateHighScore();
        game_logic.setLifes(3);

        game_logic.powered = false;
        game_logic.isReady = true;
        game_logic.level_completed = false;

        new javax.swing.Timer(5000, e -> {
            game_logic.isReady = false;

            if (pacThread != null) pacThread.stopThread();
            if (blinkyThread != null) blinkyThread.stopThread();

            switch (SingleTon.getInstance().current_level) {
                case 1:
                    blinkyThread = new BlinkyThread(this, 160);
                    break;

                case 2:
                    blinkyThread = new BlinkyThread(this, 200);
                    break;
            
                default:
                    break;
            }
            
            pacThread = new PacManThread(this, 180);
            pacThread.start();
            blinkyThread.start();
        ((javax.swing.Timer)e.getSource()).stop();
        }).start();

        repaint();
    }

    public void startGame() {
        game_logic.isReady = false;
        if (pacThread == null) {
            pacThread = new PacManThread(this, 180);
            pacThread.start();
        }

        if(blinkyThread==null){
            blinkyThread = new BlinkyThread(this, game_logic.ghost_vel);
            blinkyThread.start();
        }
    }
    
    public void levelUp() {
        game_logic.ghost_vel -= 50;
        if (game_logic.ghost_vel < 50) game_logic.ghost_vel = 50;

        if (blinkyThread != null) blinkyThread.stopThread();
        if (pacThread != null) pacThread.stopThread();

        game_logic.resetPositions();
        game_logic.initializeMap();

        pacThread = new PacManThread(this, 120);
        blinkyThread = new BlinkyThread(this, game_logic.ghost_vel);

        pacThread.start();
        blinkyThread.start();

        repaint();
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
