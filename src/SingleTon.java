import java.awt.Image;

import javax.swing.ImageIcon;

public class SingleTon {
    private static SingleTon instance;

    private SingleTon(){
        this.score = 0;
        this.current_level = 1;
    }

    public static SingleTon getInstance() {
        if (instance == null) {
            instance = new SingleTon();
        }
        return instance;
    }

    //========================== IMMAGINI  PAC MAN ==========================
    public Image pac_left  = new ImageIcon("Images/PAC_MAN/pacman_left.png").getImage();
    public Image pac_right = new ImageIcon("Images/PAC_MAN/pacman_right.png").getImage();
    public Image pac_up    = new ImageIcon("Images/PAC_MAN/pacman_up.png").getImage();
    public Image pac_down  = new ImageIcon("Images/PAC_MAN/pacman_down.png").getImage();

    public Image pac_left_closed = new ImageIcon("Images/PAC_MAN/pacman_left_closed.png").getImage();
    public Image pac_right_closed = new ImageIcon("Images/PAC_MAN/pacman_right_closed.png").getImage();
    public Image pac_up_closed = new ImageIcon("Images/PAC_MAN/pacman_up_closed.png").getImage();
    public Image pac_down_closed = new ImageIcon("Images/PAC_MAN/pacman_down_closed.png").getImage();

    //========================== IMMAGINI  PINKY ==========================
    public Image pinky_left = new ImageIcon("Images/PINKY/pinky_left.png").getImage();
    public Image pinky_right = new ImageIcon("Images/PINKY/pinky_right.png").getImage();
    public Image pinky_up = new ImageIcon("Images/PINKY/pinky_up.png").getImage();
    public Image pinky_down = new ImageIcon("Images/PINKY/pinky_down.png").getImage();

    public Image pinky_left_2 = new ImageIcon("Images/PINKY/pinky_left2.png").getImage();
    public Image pinky_right_2 = new ImageIcon("Images/PINKY/pinky_right2.png").getImage();
    public Image pinky_up_2 = new ImageIcon("Images/PINKY/pinky_up2.png").getImage();
    public Image pinky_down_2 = new ImageIcon("Images/PINKY/pinky_down2.png").getImage();

    //========================== IMMAGINI  BLINKY ==========================
    public Image blinky_left  = new ImageIcon("Images/BLINKY/blinky_left.png").getImage();
    public Image blinky_right = new ImageIcon("Images/BLINKY/blinky_right.png").getImage();
    public Image blinky_up    = new ImageIcon("Images/BLINKY/blinky_up.png").getImage();
    public Image blinky_down  = new ImageIcon("Images/BLINKY/blinky_down.png").getImage();

    public Image blinky_left_2 = new ImageIcon("Images/BLINKY/blinky_left2.png").getImage();
    public Image blinky_right_2 = new ImageIcon("Images/BLINKY/blinky_right2.png").getImage();
    public Image blinky_up_2 = new ImageIcon("Images/BLINKY/blinky_up2.png").getImage();
    public Image blinky_down_2 = new ImageIcon("Images/BLINKY/blinky_down2.png").getImage();

    //========================== IMMAGINI  INKY ==========================
    public Image inky_left = new ImageIcon("Images/INKY/inky_left.png").getImage();
    public Image inky_right = new ImageIcon("Images/INKY/inky_right.png").getImage();
    public Image inky_up = new ImageIcon("Images/INKY/inky_up.png").getImage();
    public Image inky_down = new ImageIcon("Images/INKY/inky_down.png").getImage();

    public Image inky_left_2 = new ImageIcon("Images/INKY/inky_left_2.png").getImage();
    public Image inky_right_2 = new ImageIcon("Images/INKY/inky_right_2.png").getImage();
    public Image inky_up_2 = new ImageIcon("Images/INKY/inky_up_2.png").getImage();
    public Image inky_down_2 = new ImageIcon("Images/INKY/inky_down_2.png").getImage();

    //========================== IMMAGINI  CLYDE ==========================
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
    public int current_level = 1; //livello attuale (man mano difficile)
    public int score = 0; //score del player
       
    public final int ROWS = 31;
    public final int COLS = 28;
    public String[][] game_map = new String[ROWS][COLS];
}
