import javax.swing.SwingUtilities;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class SwingPaintDemo1 {
    
    // icona finestra di gioco
    static Image game_icon = new ImageIcon("img/game_icon.png").getImage();

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        
        JFrame f = new JFrame("Pac Man"); // titolo finestra
        f.setSize(250,500); // dimensioni finestra
        MyPanel p = new MyPanel();
        p.setBackground(p.game_graphic.color_bg); // sfondo finestras
        f.add(p);
        f.setResizable(false); // finestra non ridimensionabile
        f.pack();
        f.setVisible(true);
        p.requestFocusInWindow();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setIconImage(game_icon);
        
    }
}
