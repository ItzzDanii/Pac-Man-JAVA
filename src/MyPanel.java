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
    Image gameOver_screen = new ImageIcon("img/gameOver.png").getImage();

    //sound fine partita
    Sound gameOver_sound;
    boolean isLosePlayed = false;

    //CUSTOM FONT
    private Font emulogicFont = null;

    Logica game_logic = new Logica(this, screenW, screenH);
    Grafica game_graphic = new Grafica(game_logic);
    
    IntroThread introThread;
    PacManThread pacmanThread;
    BlinkyThread blinkyThread;
    AnimateGameThread animateThread = new AnimateGameThread(this,500);

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));

        addKeyListener(new MyKeyboardAdapter(this));

        //inizializza la mappa
        game_logic.initializeMap();

        if(!game_logic.isGameOver()){
        //avvia il thread dell'intro
        introThread = new IntroThread(this, 5000); // 5 secondi di intro
        introThread.start();
        
        }

        //carica font
        try {
            emulogicFont = Font.createFont(Font.TRUETYPE_FONT, 
                new File("font/Emulogic-zrEw.ttf"));
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
            if (game_logic.isGameOver()) {
                SingleTon.getInstance().intro.stop();

                if(!isLosePlayed)
                {
                    gameOver_sound  = new Sound("sounds/gameOver.wav");
                    gameOver_sound.play();
                    isLosePlayed= true;
                }
            
            setFocusable(true);
            requestFocusInWindow();
            
            g.drawImage(gameOver_screen, 0, 0, null);

            Font sizeFont = emulogicFont.deriveFont(Font.BOLD, 20);
            g.setFont(sizeFont);
            g.setColor(Color.RED);
            g.drawString("GAME OVER!",(screenW/2)-90, (screenH/2));
            
            String mess_score = "";

            if(SingleTon.getInstance().score >= SingleTon.getInstance().max_score){
                mess_score = "New max score: "+Integer.toString(SingleTon.getInstance().loadMaxScore());
                g.setColor(game_graphic.highscore);
            }
                
            else if(!SingleTon.getInstance().newRecord){
                g.setColor(game_graphic.UPscore);
                mess_score = "Score: "+Integer.toString(SingleTon.getInstance().score);
            }

            g.drawString(mess_score, (screenW/2)-90, (screenH/2)+30);

            return;
        }

        game_graphic.drawMap(g);

        if (game_logic.isReady || game_logic.level_completed && !game_logic.isGameOver()) {
            game_graphic.startGame(g);
            setFocusable(false);
        }

        if (!introThread.isAlive()) {
            setFocusable(true);
            requestFocusInWindow();
        }

        Font sizeFont = emulogicFont.deriveFont(Font.BOLD, 12);
        g.setFont(sizeFont);
        g.setColor(game_graphic.ghosts_points);

        if (game_logic.blinkyDead && game_logic.pointsTimer > 0) {
            g.drawString("200", (game_logic.blinkyX * game_logic.cell_width + 10), (game_logic.blinkyY * game_logic.cell_heigth+ 10));
            game_logic.pointsTimer--;
        }

        if (game_logic.inkyDead && game_logic.pointsTimer > 0) {
            g.drawString("200", (game_logic.inkyX * game_logic.cell_width + 10), (game_logic.inkyY * game_logic.cell_heigth + 10));
            game_logic.pointsTimer--;
        }

        if (game_logic.pinkyDead && game_logic.pointsTimer > 0) {
            g.drawString("200", (game_logic.pinkyX * game_logic.cell_width + 10), (game_logic.pinkyY * game_logic.cell_heigth + 10));
            game_logic.pointsTimer--;
        }

        if (game_logic.clydeDead && game_logic.pointsTimer > 0) {
            g.drawString("200", (game_logic.clydeX * game_logic.cell_width + 10), (game_logic.clydeY * game_logic.cell_heigth + 10));
            game_logic.pointsTimer--;
        }
    }

    public void rematch() {

        gameOver_sound.stop();
        game_logic.gameOver = false;
        game_logic.firstTime = true;
        game_logic.introPlayed = false;

        SingleTon.getInstance().pac_vel = 170; 
        SingleTon.getInstance().ghost_vel = SingleTon.getInstance().pac_vel + 10;

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

            blinkyThread = new BlinkyThread(this, SingleTon.getInstance().ghost_vel);
            pacmanThread = new PacManThread(this, SingleTon.getInstance().pac_vel);
            
            pacmanThread.start();
            blinkyThread.start();

            ((javax.swing.Timer)e.getSource()).stop();
        }).start();

    repaint();
    }

    public void startGame() {
        if(game_logic.isGameOver())
            return;

        SingleTon.getInstance().pac_vel = 170; 
        SingleTon.getInstance().ghost_vel = SingleTon.getInstance().pac_vel + 10;

        game_logic.isReady = false;
        if (pacmanThread == null) {
            pacmanThread = new PacManThread(this, SingleTon.getInstance().pac_vel);
            pacmanThread.start();
        }

        if(blinkyThread==null){
            blinkyThread = new BlinkyThread(this,SingleTon.getInstance().ghost_vel);
            blinkyThread.start();
        }
    }

    public void levelUp() {
        if(game_logic.isGameOver())
            return;

        if (blinkyThread != null) blinkyThread.stopThread();
        if (pacmanThread != null) pacmanThread.stopThread();
        game_logic.initializeMap();

        pacmanThread = new PacManThread(this, SingleTon.getInstance().pac_vel - (SingleTon.getInstance().current_level * 2));
        blinkyThread = new BlinkyThread(this, SingleTon.getInstance().ghost_vel - (SingleTon.getInstance().current_level * 2));

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