import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyboardAdapter implements KeyListener {

    private MyPanel pannello;
    private Logica logica;

    public MyKeyboardAdapter(MyPanel p) {
        this.pannello = p;
        logica = pannello.game_logic;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(!logica.isGameOver() && logica.canPlay()){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                logica.changeDir("sx");
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                logica.changeDir("dx");
                break;

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                logica.changeDir("su");
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                logica.changeDir("giu");
                break;

            default:
                break;
        }
}
    // schermata fine partita
        if (logica.isGameOver() && SingleTon.getInstance().pac_lifes<=0) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_P:
                    System.out.println("Nuova partita...");
                    pannello.rematch();
                    break;

                case KeyEvent.VK_ESCAPE:
                    System.out.println("Uscita...");
                    pannello.close();
                    break;

                default:
                    break;
            }
        }

        pannello.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
