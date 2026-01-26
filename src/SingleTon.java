import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class SingleTon {
    private static SingleTon instance;

    private SingleTon(){
        this.score = 0;
        this.current_level = 1;
        this.pac_lifes = 3;
        this.file_max_score = "max_score.txt";

        //Chiesto a chat come caricare un custom font
        try {
        customFont = Font.createFont(Font.TRUETYPE_FONT, 
            new File("Font/Emulogic-zrEw.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            System.err.println("Errore nel caricamento del font Emulogic: " + e.getMessage());
            customFont = new Font("Arial", Font.BOLD, 12); //Fallback
    }
    }

    public static SingleTon getInstance() {
        if (instance == null) {
            instance = new SingleTon();
        }
        return instance;
    }

    private void checkMaxScore() {
        if(score > max_score) {
            max_score = score;
            newRecord = true;
            saveMaxScore(max_score);
        } else {
            newRecord = false;
        }
    }    
    //! Generato con ChatGPT per gestione file !
    private void saveMaxScore(int maxScore) {
        try {
            FileWriter writer = new FileWriter(file_max_score);
            writer.write(Integer.toString(maxScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //! Generato con ChatGPT per gestione file !
    private int loadMaxScore() {
    try {
        File file = new File(file_max_score);

        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("0");
            }
            return 0;
        }

        Scanner sc = new Scanner(file);
        int max = sc.nextInt();
        sc.close();
        return max;

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}

    //========================== IMMAGINI  PAC MAN ==========================
    public Image pac_man_CurrentImage; //imagien attuale
    public Image pac_left  = new ImageIcon("Images/PAC_MAN/pacman_left.png").getImage();
    public Image pac_right = new ImageIcon("Images/PAC_MAN/pacman_right.png").getImage();
    public Image pac_up    = new ImageIcon("Images/PAC_MAN/pacman_up.png").getImage();
    public Image pac_down  = new ImageIcon("Images/PAC_MAN/pacman_down.png").getImage();

    public Image pac_left_closed = new ImageIcon("Images/PAC_MAN/pacman_left_closed.png").getImage();
    public Image pac_right_closed = new ImageIcon("Images/PAC_MAN/pacman_right_closed.png").getImage();
    public Image pac_up_closed = new ImageIcon("Images/PAC_MAN/pacman_up_closed.png").getImage();
    public Image pac_down_closed = new ImageIcon("Images/PAC_MAN/pacman_down_closed.png").getImage();

    //========================== IMMAGINI  PINKY ==========================
    public Image pinky_CurrentImage; //immagine attuale di pinky
    public Image pinky_left = new ImageIcon("Images/PINKY/pinky_left.png").getImage();
    public Image pinky_right = new ImageIcon("Images/PINKY/pinky_right.png").getImage();
    public Image pinky_up = new ImageIcon("Images/PINKY/pinky_up.png").getImage();
    public Image pinky_down = new ImageIcon("Images/PINKY/pinky_down.png").getImage();

    public Image pinky_left_2 = new ImageIcon("Images/PINKY/pinky_left2.png").getImage();
    public Image pinky_right_2 = new ImageIcon("Images/PINKY/pinky_right2.png").getImage();
    public Image pinky_up_2 = new ImageIcon("Images/PINKY/pinky_up2.png").getImage();
    public Image pinky_down_2 = new ImageIcon("Images/PINKY/pinky_down2.png").getImage();

    //========================== IMMAGINI  BLINKY ==========================
    public Image blinky_CurrentImage; //immagine attuale di blinky
    public Image blinky_left  = new ImageIcon("Images/BLINKY/blinky_left.png").getImage();
    public Image blinky_right = new ImageIcon("Images/BLINKY/blinky_right.png").getImage();
    public Image blinky_up    = new ImageIcon("Images/BLINKY/blinky_up.png").getImage();
    public Image blinky_down  = new ImageIcon("Images/BLINKY/blinky_down.png").getImage();

    public Image blinky_left_2 = new ImageIcon("Images/BLINKY/blinky_left2.png").getImage();
    public Image blinky_right_2 = new ImageIcon("Images/BLINKY/blinky_right2.png").getImage();
    public Image blinky_up_2 = new ImageIcon("Images/BLINKY/blinky_up2.png").getImage();
    public Image blinky_down_2 = new ImageIcon("Images/BLINKY/blinky_down2.png").getImage();

    //========================== IMMAGINI  INKY ==========================
    public Image inky_CurrentImage; //immagine attuale di inky
    public Image inky_left = new ImageIcon("Images/INKY/inky_left.png").getImage();
    public Image inky_right = new ImageIcon("Images/INKY/inky_right.png").getImage();
    public Image inky_up = new ImageIcon("Images/INKY/inky_up.png").getImage();
    public Image inky_down = new ImageIcon("Images/INKY/inky_down.png").getImage();

    public Image inky_left_2 = new ImageIcon("Images/INKY/inky_left_2.png").getImage();
    public Image inky_right_2 = new ImageIcon("Images/INKY/inky_right_2.png").getImage();
    public Image inky_up_2 = new ImageIcon("Images/INKY/inky_up_2.png").getImage();
    public Image inky_down_2 = new ImageIcon("Images/INKY/inky_down_2.png").getImage();

    //========================== IMMAGINI  CLYDE ==========================
    public Image clyde_CurrentImage; //immagine attuale di clyde
    public Image clyde_left = new ImageIcon("Images/CLYDE/clyde_left.png").getImage();
    public Image clyde_right = new ImageIcon("Images/CLYDE/clyde_right.png").getImage();
    public Image clyde_up = new ImageIcon("Images/CLYDE/clyde_up.png").getImage();
    public Image clyde_down = new ImageIcon("Images/CLYDE/clyde_down.png").getImage();

    public Image clyde_left_2 = new ImageIcon("Images/CLYDE/clyde_left_2.png").getImage();
    public Image clyde_right_2 = new ImageIcon("Images/CLYDE/clyde_right_2.png").getImage();
    public Image clyde_up_2 = new ImageIcon("Images/CLYDE/clyde_up_2.png").getImage();
    public Image clyde_down_2 = new ImageIcon("Images/CLYDE/clyde_down_2.png").getImage();
    
    //=================== FANTASMA VULNERABILE ================================
    public Image vul_ghost_blue = new ImageIcon("Images/GHOST_VUL/ghost_vulnerable_blue_1.png").getImage();
    public Image vul_ghost_blue_2 = new ImageIcon("Images/GHOST_VUL/ghost_vulnerable_blue_2.png").getImage();
    public Image vul_ghost_red = new ImageIcon("Images/GHOST_VUL/ghost_vulnerable_red_1.png").getImage();
    public Image vul_ghost_red_2 = new ImageIcon("Images/GHOST_VUL/ghost_vulnerable_red_1.png").getImage();

    //=================== FANTASMA MORTO ================================
    public Image dead_left = new ImageIcon("Images/GHOST_DEAD/dead_left.png").getImage();
    public Image dead_up = new ImageIcon("Images/GHOST_DEAD/dead_up.png").getImage();
    public Image dead_right = new ImageIcon("Images/GHOST_DEAD/dead_right.png").getImage();
    public Image dead_down = new ImageIcon("Images/GHOST_DEAD/dead_down.png").getImage();

    //========================== SUONI ==========================
    public Sound eat_sound = new Sound("SOUNDS/munch.wav");
    public Sound intro = new Sound("SOUNDS/intro.wav");
    public Sound eat_ghost = new Sound("SOUNDS/eatghost.wav");
    public Sound powerSound = new Sound("SOUNDS/powerup.wav");

    //======================= VARIABILI DI GIOCO==========================
    public int current_level; //livello attuale (man mano difficile)
    public int score; //score del player
    public int max_score; //high score
    public boolean newRecord = false; //new record?
       
    public final int ROWS = 31;
    public final int COLS = 28;
    public String[][] game_map = new String[ROWS][COLS];

    public Font customFont = null;

    public int pac_lifes; //vite

    private String file_max_score = "max_score.txt";
}
