import javax.swing.SwingUtilities;

import java.awt.Color;
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
        
        JFrame f = new JFrame("Pac Man");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250,500);

        MyPanel p = new MyPanel();
        p.setBackground(Color.BLACK);
        p.requestFocusInWindow();
        f.add(p);
        f.pack();
        f.setVisible(true);
        f.setIconImage(game_icon);
    }
}