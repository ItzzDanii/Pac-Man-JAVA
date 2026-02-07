import java.awt.Color;
import java.awt.Font;
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
            //spazio dal bordo finestra per le scritte
            int marginTop = 75;
            int marginLeft = 50;
            int marginRight = 25;
            int marginBottom = 50;

            int availableWidth = screenW - marginLeft - marginRight;
            int availableHeight = screenH - marginTop - marginBottom;
            
            // font delle scritte
            Font sizeFont = SingleTon.getInstance().customFont.deriveFont(Font.BOLD, (float)(logic.cell_heigth-10));

            // scritta nome player: "1UP"
            g.setFont(sizeFont);
            g.setColor(Color.WHITE);
            g.drawString("1UP",availableWidth/4,(marginTop-45));

            // scritta score player
            g.drawString(Integer.toString(SingleTon.getInstance().score), (availableWidth/5)+40,(marginTop-10));

            // scritta high score e aggiorna sempre high score
            logic.updateHighScore();
            g.drawString("HIGH SCORE", availableWidth/2, (marginTop-45));
            g.drawString(Integer.toString(SingleTon.getInstance().max_score),availableWidth/2 + 50, (marginTop-15));

            int cellW = availableWidth / SingleTon.getInstance().COLS;
            int cellH = availableHeight / SingleTon.getInstance().ROWS;
            String cell_value = "";

           for (int i = 0; i < SingleTon.getInstance().ROWS; i++) {
                for (int j = 0; j < SingleTon.getInstance().COLS; j++) {
                    int x = marginLeft + j * cellW;
                    int y = marginTop + i * cellH;

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
                        if(logic.blinkyDead){
                            switch (logic.blinkyDirection) {
                                case 0:
                                    SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_left;
                                    break;

                                case 1:
                                    SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_up;
                                    break;

                                case 2:
                                    SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_right;
                                    break;

                                case 3:
                                    SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_down;
                                    break;
                            
                                default:
                                    break;
                            }
                        }
                        else if(logic.powered){
                           if (logic.blinkyAnimate)
                                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().vul_ghost_blue;
                            else
                                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().vul_ghost_blue_2;
                        }
                        else{
                            switch (logic.blinkyDirection) {
                                case 0: 
                                    if(logic.blinkyAnimate)
                                        SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_left ;
                                    else SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_left_2;
                                break;

                                case 1: 
                                if(logic.blinkyAnimate)
                                    SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_up ;
                                else SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_up_2;
                                break;

                                case 2: 
                                if(logic.blinkyAnimate)
                                    SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_right ;
                                else SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_right_2;
                                break;

                                case 3: 
                                if(logic.blinkyAnimate)
                                    SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_down ;
                                else SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_down_2;
                                break;
                        }
                        
                        g.drawImage(SingleTon.getInstance().blinky_CurrentImage,x,y,cellW,cellH,null);
                    }
                }
            }
        }
    }
}
}
