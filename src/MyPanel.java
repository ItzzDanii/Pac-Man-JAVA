import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

class MyPanel extends JPanel {

    //dimensioni finestra
    final int screenW = 900; //900x1000
    final int screenH = 1000;

    //icona finestra
    Image gameOver_screen = new ImageIcon("Images/gameOver.png").getImage();

    //sound fine partita
    Sound gameOver_sound;
    boolean isPlayed = false;

    //CUSTOM FONT
    private Font emulogicFont = null;

    Logica game_logic = new Logica(this, screenW, screenH);
    Grafica mappa = new Grafica(game_logic);
    
    IntroThread introThread;
    PacManThread pacmanThread;
    BlinkyThread blinkyThread;

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));

        addKeyListener(new MyKeyboardAdapter(this));

        //inizializza la mappa
        game_logic.initializeMap();

        //avvia il thread dell'intro
        introThread = new IntroThread(this, 5000); // 5 secondi di intro
        introThread.start();

        //carica font
        try {
            emulogicFont = Font.createFont(Font.TRUETYPE_FONT, 
                new File("fonts/Emulogic-zrEw.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(emulogicFont);
        } catch (FontFormatException | IOException e) {
            System.err.println("Errore nel caricamento del font Emulogic: " + e.getMessage());
            emulogicFont = new Font("Arial", Font.BOLD, 12);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(screenW, screenH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
            if (game_logic.gameOver) {
                SingleTon.getInstance().intro.stop();

                if(!isPlayed)
                {
                    gameOver_sound  = new Sound("Sounds/gameOver.wav");
                    gameOver_sound.play();
                    isPlayed= true;
                    setFocusable(false);
                }

                if(isPlayed && gameOver_sound.isFinished())
                    setFocusable(true);

            if (pacmanThread != null)
                pacmanThread.stopThread();

            if(blinkyThread != null)
                blinkyThread.stopThread();

            g.drawImage(gameOver_screen, 0, 0, null);

            Font sizeFont = emulogicFont.deriveFont(Font.BOLD, 20);
            g.setFont(sizeFont);
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER!",(screenW/2)-90, (screenH/2));
            
            String mess_score = "";

            if(SingleTon.getInstance().newRecord)
                mess_score = "New max score: "+Integer.toString(SingleTon.getInstance().loadMaxScore());
            else if(!SingleTon.getInstance().newRecord)
                mess_score = "Score: "+Integer.toString(SingleTon.getInstance().score);

            g.drawString(mess_score, (screenW/2)-90, (screenH/2)+30);

            return;
        }

        mappa.drawMap(g);

        if (game_logic.isReady) {
            mappa.startGame(g);
            setFocusable(false);
        }

        if (!introThread.isAlive()) {
            setFocusable(true);
            requestFocusInWindow();
        }

        if (game_logic.isLvlCompleted()) {
            game_logic.levelCompleted();
        }

        if (game_logic.blinkyDead) {

            Font sizeFont = emulogicFont.deriveFont(Font.BOLD, 12);
            g.setFont(sizeFont);
            g.setColor(Color.WHITE);
            g.drawString("200", (game_logic.blinkyX * SingleTon.getInstance().COLS + 50), (game_logic.blinkyY * SingleTon.getInstance().ROWS + 50));
            game_logic.pointsTimer--;

        }
    }

    public void rematch() {
        isPlayed = false;
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

        if (pacmanThread != null) pacmanThread.stopThread();
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
        
        pacmanThread = new PacManThread(this, 180);
        pacmanThread.start();
        blinkyThread.start();

        ((javax.swing.Timer)e.getSource()).stop();
    }).start();

    repaint();
    }

    public void startGame() {
        game_logic.isReady = false;
        if (pacmanThread == null) {
            pacmanThread = new PacManThread(this, 180);
            pacmanThread.start();
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
        if (pacmanThread != null) pacmanThread.stopThread();

        game_logic.resetPositions();
        game_logic.initializeMap();

        pacmanThread = new PacManThread(this, 120);
        blinkyThread = new BlinkyThread(this, game_logic.ghost_vel);

        pacmanThread.start();
        blinkyThread.start();

        repaint();
}

    public void close() {
        if (introThread != null) introThread.stopThread();
        if (pacmanThread != null) pacmanThread.stopThread();
        if(blinkyThread!= null) blinkyThread.stopThread();

        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) parentWindow.dispose();
    }
}