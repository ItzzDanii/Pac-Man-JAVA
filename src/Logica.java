import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

public class Logica{
//========================== FINESTRA ==========================
    public int screenW,screenH;
    public MyPanel pan;
    boolean introPlayed = false;
    
    //========================== GLOBAL ==========================
    boolean firstTime = true; //prima partita?
    boolean gameOver = false; //game over?

    boolean level_completed = false; //livello completato?

    int pointsX, pointsY; //posizione della scritta dello score dato fantasma mangiato
    int pointsTimer; //durata della scritta
    
    PowerUpThread pUpThread; //thread che gestisce il potenziamento di pacman
    int powerSession; //numero di thread di potenziamento

    int ghost_vel; //velocità fantasmi

    //========================== MURI ==========================
    int cell_width,cell_heigth; //dimensioni della cella
    int cellX = 0,cellY = 0; //posizione della cella

     //========================== PALLINA DA MANGIARE ==========================
    int ball_diam; //diametro della pallina
    int ballX = 0,ballY = 0; //posizione della pallina

     //========================== PAC MAN ==========================
    int pac_manX,pac_manY; //posizione di pacman
    boolean mouthOpen = true; //animazione di pacman
    int vel = 1; //velocità di pacman
    String dir; //direzione di pacman
    boolean isReady = true; //intro? (dopo aver caricato la mappa)
    boolean powered = false; //pacman potenziato?
    boolean pacmanDead = false; //pac man morto?

    //========================== PINKY - FANSTAMA ROSA ==========================
    int pinkyX,pinkyY; //posizione di pinky
    int pinkyDirection; //direzione di pinky
    public boolean pinkyAnimate = true; //per animare pinky
    boolean pinkyDead = false; //pinky morto??
    int pinkyMoveCounter = 0;

     //========================== BLINKY - FANSTAMA ROSSO ==========================
    int blinkyX,blinkyY; //posizione di blinky
    int blinkyDirection; //direzione di blinky
    public boolean blinkyAnimate = true; //animazione di blinky
    boolean blinkyDead = false; //blinky morto??

    int blinkyMoveCounter = 0;

    //========================== INKY - FANSTAMA AZZURRO ==========================
    int inkyX,inkyY;

     //========================== CLYDE - FANSTAMA ARANCIONE ==========================
    int clydeX,clydeY;

    public Logica(MyPanel panel,int screenW,int screenH){
        this.pan = panel;
        this.screenW = screenW;
        this.screenH = screenH;
    
        this.isReady = true;
        this.gameOver = false;
        this.level_completed = false;

        this.cell_width = (screenW/SingleTon.getInstance().COLS);
        this.cell_heigth = (screenH/SingleTon.getInstance().ROWS);

        this.ball_diam = cell_width/4;

        this.pac_manX = 13;
        this.pac_manY = 23;
        this.dir = "dx";
        
        this.blinkyX = 14;
        this.blinkyY = 11;
        this.blinkyDirection = 0;

        this.ghost_vel = 200;
    }

    // === GETTER ===
    public int getScreenH(){return this.screenH;}
    public int getScreenW(){return this.screenW;}
    public MyPanel getPanel(){return this.pan;}
    public int getPacX() {return this.pac_manX;}
    public int getPacY() {return this.pac_manY;}
    public int distXFinestra() {return screenW - (this.cell_width*SingleTon.getInstance().COLS);}
    public int distYFinestra() {return screenH - (this.cell_heigth*SingleTon.getInstance().ROWS);}
    public boolean isFirstGame(){return this.firstTime;}
    public boolean isLvlCompleted(){return this.level_completed;}
    public boolean isGameOver(){return this.gameOver;}

}