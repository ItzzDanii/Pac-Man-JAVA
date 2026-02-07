import java.awt.Color;
import java.awt.Graphics;

public class Grafica {
    MyPanel pan;
    int screenW,screenH;
    Logica logic;

    Color cell_fill_color = new Color(53, 48, 148,255),cell_border_color = new Color(18, 16, 67,255); //colori della cella
    Color ball_fill_color = new Color(240, 114, 37,255),ball_border_color = new Color(44, 22, 12,255); //colori della pallina


    public Grafica(MyPanel p,int sW,int sH,Logica l){
        this.pan = p;
        this.screenW = sW;
        this.screenH = sH;
        this.logic = l;
    }

    public void drawMap(Graphics g){
        if(!logic.isGameOver() || !logic.isLvlCompleted()){
            int cellW = screenW / SingleTon.getInstance().COLS;
            int cellH = screenH / SingleTon.getInstance().ROWS;
            String cell_value = "";

           for (int i = 0; i < SingleTon.getInstance().ROWS; i++) {
                for (int j = 0; j < SingleTon.getInstance().COLS; j++) {
                    int x = j * cellW;
                    int y = i * cellH;

                    cell_value = SingleTon.getInstance().game_map[i][j];

                    switch (cell_value) {
                        case "EMPTY":
                            g.setColor(Color.BLACK);
                            g.fillRect(x, y, cellW, cellH);
                            break;

                        case "WALL":
                            g.setColor(cell_border_color);
                            g.drawRect(x, y, cellW, cellH);
                            g.setColor(cell_fill_color);
                            g.fillRect(x, y, cellW, cellH);
                            break;
                
                        case "BALL":
                            int ballX = x + cellW / 3;
                            int ballY = y + cellH / 3;
                            g.setColor(ball_border_color);
                            g.drawOval(ballX, ballY, logic.ball_diam, logic.ball_diam);
                            g.setColor(ball_fill_color);
                            g.fillOval(ballX, ballY, logic.ball_diam, logic.ball_diam);
                            break;

                        case "POWER_UP":
                        int puX = x + cellW / 4;
                        int puY = y + cellH / 4;
                        g.setColor(ball_border_color);
                        g.drawOval(puX, puY, logic.ball_diam*2, logic.ball_diam*2);
                        g.setColor(ball_fill_color);
                        g.fillOval(puX, puY, logic.ball_diam*2, logic.ball_diam*2);
                        break;

                        case "DOOR1":
                            g.setColor(new Color(235, 168, 209, 255));
                            g.fillRect(x, y+(cellH/2)-5, cellW, cellH / 3);
                            break;

                        case "DOOR2":
                            g.setColor(new Color(235, 168, 209, 255));
                            g.fillRect(x, y+(cellH/2)-5, cellW, cellH / 3);
                            break;

                        default:
                            break;
                    }
                    
                    //disegna pacman
                    if (i == logic.pac_manY && j == logic.pac_manX) {
                        g.drawImage(SingleTon.getInstance().pac_man_CurrentImage, x, y, cellW, cellH, null);
                    }

                    if(i == logic.blinkyX && j == logic.blinkyY){
                        g.drawImage(SingleTon.getInstance().blinky_CurrentImage,x,y,cellW,cellH,null);
                    }
                }
            }
        }
    }
}
