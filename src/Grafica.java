import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Grafica {
    public MyPanel pan;
    public int screenW,screenH;
    Logica logic;
    
    // Colore degli elementi/HUD personalizzabili
    Color UPscore = new Color(255,255,255,255); // hud
    Color highscore = new Color(255,255,255,255);
    Color ready_color = new Color(247, 184, 1); // ready text
    Color ghosts_points = new Color(255,255,255);
    Color color_bg = new Color(0,0,0);
    Color cell_fill_color = new Color(0, 78, 137); //colori della cella
    Color ball_fill_color = new Color(241, 135, 1),ball_border_color = new Color(243, 91, 4); //colori della pallina

    //========================== METODI ==========================
    public Grafica(Logica logica) {
        this.logic = logica;
        this.screenW = logic.getScreenW();
        this.screenH = logic.getScreenH();
        this.pan = logic.getPanel();

        SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_right;

        SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_left;
    }

    public void drawMap(Graphics g) {
        logic.controlloClear(); //controlla se livello completato

        if(logic.isGameOver() || logic.isLvlCompleted()) return;
        else{
            //spazio dal bordo finestra per le scritte
            int marginTop = 75;
            int marginLeft = 50;
            int marginRight = 25;
            int marginBottom = 50;

            int availableWidth = screenW - marginLeft - marginRight;
            int availableHeight = screenH - marginTop - marginBottom;

            //dimensioni delle celle
            int blockW = availableWidth / SingleTon.getInstance().COLS;
            int blockH = availableHeight / SingleTon.getInstance().ROWS;

            //custom font - scritta nome player: "1UP"
            Font sizeFont = SingleTon.getInstance().customFont.deriveFont(Font.BOLD, (float)(logic.cell_heigth-10));
            g.setFont(sizeFont);
            g.setColor(UPscore);
            g.drawString("1UP",availableWidth/4,(marginTop-45));

            //scritta score player
            String mess_score = Integer.toString(SingleTon.getInstance().score);
            
            g.drawString(mess_score,(availableWidth/5)+40,(marginTop-10));

            //scritta high score
            logic.updateHighScore();
            g.setColor(highscore);
            g.drawString("HIGH SCORE", availableWidth/2, (marginTop-45));
            g.drawString(Integer.toString(SingleTon.getInstance().max_score),availableWidth/2 + 50, (marginTop-15));

            //Disegna le vite
            for(int l=0;l<SingleTon.getInstance().pac_lifes;l++)
            {
                int x = 50 + l * (blockW + 5);
                int y = (SingleTon.getInstance().ROWS * blockH) + (marginBottom + 35);
                g.drawImage(SingleTon.getInstance().pac_right, x, y,blockW,blockH, null);
            }

            for (int i = 0; i < SingleTon.getInstance().ROWS; i++) {
                for (int j = 0; j < SingleTon.getInstance().COLS; j++) {
                    int x = marginLeft + j * blockW;
                    int y = marginTop + i * blockH;

                    switch(SingleTon.getInstance().game_map[i][j]) {
                        case "WALL":
                            g.setColor(cell_fill_color);
                            g.fillRect(x, y, blockW, blockH);
                            break;

                        case "BALL":
                            int ballX = x + blockW / 3;
                            int ballY = y + blockH / 3;
                            g.setColor(ball_border_color);
                            g.drawOval(ballX, ballY, logic.ball_diam, logic.ball_diam);
                            g.setColor(ball_fill_color);
                            g.fillOval(ballX, ballY, logic.ball_diam, logic.ball_diam);
                            break;

                        case "EMPTY":
                            g.setColor(color_bg);
                            g.fillRect(x, y, blockW, blockH);
                            break;

                        case "DOOR1":
                            g.setColor(new Color(235, 168, 209, 255));
                            g.fillRect(x, y+(blockH/2)-5, blockW, blockH / 3);
                            break;

                        case "DOOR2":
                            g.setColor(new Color(235, 168, 209, 255));
                            g.fillRect(x, y+(blockH/2)-5, blockW, blockH / 3);
                            break;

                        case "POWER_UP":
                            int puX = x + blockW / 4;
                            int puY = y + blockH / 4;
                            g.setColor(ball_border_color);
                            g.drawOval(puX, puY, logic.ball_diam*2, logic.ball_diam*2);
                            g.setColor(ball_fill_color);
                            g.fillOval(puX, puY, logic.ball_diam*2, logic.ball_diam*2);
                            break;
                    }

                    //disegna pacman
                    if (i == logic.pac_manY && j == logic.pac_manX) {
                        g.drawImage(SingleTon.getInstance().pac_man_CurrentImage, x, y, blockW, blockH, null);
                    }

                    //disegna inky
                    if(i==logic.inkyY && j == logic.inkyX){
                        logic.animateInky();

                        //disegna inky
                        g.drawImage(SingleTon.getInstance().inky_CurrentImage, x, y,blockW,blockH, null);
                    }
                
                    //disegna blinky
                    if(i==logic.blinkyY && j == logic.blinkyX){
                        //se muore,occhi
                        if (logic.blinkyDead) {
                            switch (logic.blinkyDirection) {
                                case 0: SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_left; break;
                                case 1: SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_up; break;
                                case 2: SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_right; break;
                                case 3: SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_down; break;
                            }
                        }
                        else if (logic.powered) {
                            //altrimenti se debole,blu
                            if (logic.blinkyAnimate)
                                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().vul_ghost_blue;
                            else
                                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().vul_ghost_blue_2;
                        }
                        else {
                            //altrimenti normale
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
                        }
                        //disegna blinky
                        g.drawImage(SingleTon.getInstance().blinky_CurrentImage, x, y,blockW,blockH, null);
                    }
                
                    //disegna clyde
                    if(i==logic.clydeY && j == logic.clydeX){
                        logic.animateClyde();

                        //disegna pinky
                        g.drawImage(SingleTon.getInstance().clyde_CurrentImage, x, y,blockW,blockH, null);
                    }
                
                    //disegna pinky
                    if(i==logic.pinkyY && j == logic.pinkyX){
                        logic.animatePinky();

                        //disegna pinky
                        g.drawImage(SingleTon.getInstance().pinky_CurrentImage, x, y,blockW,blockH, null);
                    }
                
                }
            }
    }
}

    public void startGame(Graphics g) {
    if(logic.isReady || logic.level_completed){
        this.pan.setFocusable(false);
        int cellX = 11 * logic.cell_width;
        int cellY = 18 * logic.cell_heigth;

        SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_right;
        logic.pacManDir = "dx";

        SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_left;
        logic.blinkyDirection = 0;

        if (!logic.introPlayed) {
            SingleTon.getInstance().intro.play();
            logic.introPlayed = true;
        }

        g.setColor(ready_color); 

        Font sizeFont = SingleTon.getInstance().customFont.deriveFont(Font.BOLD, (float)(logic.cell_heigth-10));
        g.setFont(sizeFont); 
        
        g.drawString("READY!", cellX+40 ,cellY-1);
    }
    if(!logic.isReady)
        this.pan.setFocusable(true);
}
}