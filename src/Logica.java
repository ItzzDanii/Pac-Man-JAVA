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

    // === SETTER ===
    public void setPacX(int x) {this.pac_manX = x;}
    public void setPacY(int y) {this.pac_manY = y;}
    public void setLifes(int num){SingleTon.getInstance().pac_lifes = num;}
    public void resetScore(){SingleTon.getInstance().score = 0;}
    public void updateHighScore(){SingleTon.getInstance().checkMaxScore();}
    
    // === METODI DI GIOCO ===
    public void placeWall(int col,int row,int larghezza,int altezza)
    {
        int startcol = col;
        for(int j=0;j<altezza;j++)
        {  
            for(int i = 0;i<larghezza;i++)
            {
                SingleTon.getInstance().game_map[row][col] = "WALL";
                col++;
            }
            col=startcol;
            row++;
        }

    }
    public void emptyCell(int col, int row, int lung, int alt) {
    for (int i = 0; i < alt; i++) {
        for (int j = 0; j < lung; j++) {
            SingleTon.getInstance().game_map[row + i][col + j] = "EMPTY";
        }
    }
}
    public void initializeMap() {
        if(isFirstGame() || isLvlCompleted()) //solo se prima parita o livello completato (dopo reset)
        {
            updateHighScore();
            //riempo tutto di palline
            for(int i=0;i<SingleTon.getInstance().ROWS;i++)
            {
                for(int j=0;j<SingleTon.getInstance().COLS;j++)
                {
                    SingleTon.getInstance().game_map[i][j] = "BALL";
                }
            }

        //contorno solo muro
       for(int rows = 0; rows < SingleTon.getInstance().ROWS; rows++) {
        for(int columns = 0; columns < SingleTon.getInstance().COLS; columns++) {

            if(rows==0 || rows == SingleTon.getInstance().ROWS-1)
                SingleTon.getInstance().game_map[rows][columns] = "WALL";

            if(columns==0 || columns==SingleTon.getInstance().COLS-1)
                SingleTon.getInstance().game_map[rows][columns] = "WALL";
        }
    }

    //altri elementi da aggiungere usando metodo placewall (lungo ma comodo):
    //muri
        placeWall(2, 2, 4, 3);

        placeWall(7, 2, 5, 3);

        placeWall(13, 1, 2, 4);

        placeWall(16, 2, 5, 3);

        placeWall(22, 2, 4, 3);

        placeWall(2, 6, 4, 2);

        placeWall(7, 6, 2, 8);
        placeWall(9, 9, 3, 2);

        placeWall(10, 6, 8, 2);
        placeWall(13, 8, 2, 3);

        placeWall(16, 9, 3, 2);
        placeWall(19, 6, 2, 8);

        placeWall(22, 6, 4, 2);

        placeWall(1, 9, 5, 5);

        placeWall(1, 15, 5, 5);

        placeWall(7, 15, 2, 5);

        placeWall(2, 21, 4, 2);
        placeWall(4, 23, 2, 3);

        placeWall(1, 24, 2, 2);

        placeWall(2, 27, 10, 2);
        placeWall(7, 24, 2, 3);

        placeWall(7, 21, 5, 2);

        placeWall(10, 24, 8, 2);
        placeWall(13, 26, 2, 3);

        placeWall(16, 27, 10, 2);
        placeWall(19, 24, 2, 3);

        placeWall(25, 24, 2, 2);

        placeWall(22, 21, 4, 2);
        placeWall(22, 23, 2, 3);

        placeWall(16, 21, 5, 2);

        placeWall(10, 18, 8, 2);
        placeWall(13, 20, 2, 3);

        placeWall(19, 15, 2, 5);

        placeWall(22, 9, 5, 5);

        placeWall(22, 15, 5, 5);

        placeWall(10, 12, 3, 1);
        placeWall(10, 12, 1, 4);
        placeWall(10, 16, 8, 1);
        placeWall(17, 12, 1, 4);
        placeWall(15, 12, 3, 1);

        //celle vuote
        emptyCell(3, 3,2,1);
        emptyCell(8,3,3,1);
        emptyCell(17, 3, 3, 1);
        emptyCell(23,3,2,1);

        emptyCell(0,10,5,3);
        emptyCell(0,16,5,3);

        emptyCell(23,10,5,3);
        emptyCell(23,16,5,3);

        emptyCell(0, 14, 6, 1);
        emptyCell(22, 14, 6, 1);

        emptyCell(12, 9, 1, 3);
        emptyCell(15, 9, 1, 3);

        emptyCell(13, 11, 2, 5);
        emptyCell(9, 11, 10, 1);
        emptyCell(9, 11, 1, 9);
        emptyCell(9, 17, 10, 1);

        emptyCell(18, 11, 1, 9);

        emptyCell(7, 14, 2, 1);
        emptyCell(19, 14, 2, 1);

        emptyCell(11, 13, 6, 3);

        //porte della base dei fantasmi
        SingleTon.getInstance().game_map[12][13] = "DOOR1";
        SingleTon.getInstance().game_map[12][14] = "DOOR2";

        //potenziamenti di pacman
        SingleTon.getInstance().game_map[3][1] = "POWER_UP";
        SingleTon.getInstance().game_map[3][26] = "POWER_UP";
        SingleTon.getInstance().game_map[23][1] = "POWER_UP";
        SingleTon.getInstance().game_map[23][26] = "POWER_UP";

        //teletrasporti per pacman
        SingleTon.getInstance().game_map[14][0] = "TELEPORT";
        SingleTon.getInstance().game_map[14][27] = "TELEPORT_2";

        //scritta ready per intro del gioco
        SingleTon.getInstance().game_map[17][11] = "R";
        SingleTon.getInstance().game_map[17][12] = "E";
        SingleTon.getInstance().game_map[17][13] = "A";
        SingleTon.getInstance().game_map[17][14] = "D";
        SingleTon.getInstance().game_map[17][15] = "Y";
        SingleTon.getInstance().game_map[17][16] = "!";
    }
    firstTime = false;
}
    
    // === METODI DI PACMAN ===
    public boolean canPlay() {
            return !this.isReady;
    }
    public boolean canMove(String dir) {
    //collisioni con muri e bordi finestra
    switch (dir) {
        case "sx":
            if ((pac_manX - vel) < 0) return false; // se fuori dalla finestra non può muoversi
            String cellSx = SingleTon.getInstance().game_map[pac_manY][pac_manX - vel];
            return !cellSx.equals("WALL") && !cellSx.equals("DOOR1") && !cellSx.equals("DOOR2"); // se è un muro non può muoversi
        
            case "dx":
            if ((pac_manX + vel) >= SingleTon.getInstance().COLS) return false;
            String cellDx = SingleTon.getInstance().game_map[pac_manY][pac_manX + vel];
            return !cellDx.equals("WALL") && !cellDx.equals("DOOR1") && !cellDx.equals("DOOR2");
        
            case "su":
            if ((pac_manY - vel) < 0) return false;
            String cellSu = SingleTon.getInstance().game_map[pac_manY - vel][pac_manX];
            return !cellSu.equals("WALL") && !cellSu.equals("DOOR1") && !cellSu.equals("DOOR2");
        
            case "giu":
            if ((pac_manY + vel) >= SingleTon.getInstance().ROWS) return false;
            String cellGiu = SingleTon.getInstance().game_map[pac_manY + vel][pac_manX];

            if (cellGiu.equals("WALL") || cellGiu.equals("DOOR1") || cellGiu.equals("DOOR2")) {
                return false;
            }
            return true;
            }
            return false;
}
    
    public void changeDir(String nuovaDir) {
         if (!canMove(nuovaDir)) return; //se non si può muovere
         else dir = nuovaDir;
    }
    
    public void eatBall() {
    //cella in cui c'è attuamlente pacman
    String cell = SingleTon.getInstance().game_map[pac_manY][pac_manX];
    
    if(cell.equals("BALL")){
        SingleTon.getInstance().eat_sound.play();
        emptyCell(pac_manX,pac_manY,1,1);
        SingleTon.getInstance().score+=10;
    }
    
    if(cell.equals("POWER_UP")) {
        pUpThread =  new PowerUpThread(pan, 8500);
        pUpThread.start(); //avvia thread di potenziamento
        SingleTon.getInstance().score += 50;
        emptyCell(pac_manX, pac_manY, 1, 1);
    }
}
    
    public void movePacman() {
    if (!canMove(dir)) return; // se non si può muovere
    if(isGameOver()|| isLvlCompleted()) return; // se gameover o livello completato
    if(SingleTon.getInstance().pac_lifes<=0){ //se muore
        gameOver = true;
        level_completed = false;
        return;
    }
    
    switch (dir) {
        case "sx":
            pac_manX-=vel;
            // animazione della bocca
            if (mouthOpen)
                SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_left;
            else
                SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_left_closed;
            break;

        case "dx":
            pac_manX+=vel;
            if (mouthOpen)
                SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_right;
            else
                SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_right_closed;
            break;

        case "su":
            pac_manY-=vel;
            if (mouthOpen)
                SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_up;
            else
                SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_up_closed;
            break;

        case "giu":
            pac_manY+=vel;
            if (mouthOpen)
                SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_down;
            else
                SingleTon.getInstance().pac_man_CurrentImage = SingleTon.getInstance().pac_down_closed;
            break;
    }

    // alterno per animazione bocca
    mouthOpen = !mouthOpen;
    
    if(pac_manX == 0 && pac_manY == 14)
        pac_manX = 26;

    if(pac_manX == 27 && pac_manY == 14)
        pac_manX = 1;

        eatBall();
    }

    // === METODI DI BLINKY ===
    public void animateBlinky() {
        if(powered && powerSession > 1000) // in ms - 1000ms ovvero che all'inizio del potenziamento di pac man, diventa bianco-blu
        {
            if(blinkyAnimate)
                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().vul_ghost_blue;
            else SingleTon.getInstance().blinky_CurrentImage =  SingleTon.getInstance().vul_ghost_blue_2;
        }
        else if(powered && powerSession < 7000) // se manca poco al termine del potenziamento di pac man, diventa bianco-rosso
        {
            if(blinkyAnimate)
                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().vul_ghost_red;
            else SingleTon.getInstance().blinky_CurrentImage =  SingleTon.getInstance().vul_ghost_red_2;
        }
        else if(blinkyDead)
        {
            switch (blinkyDirection) {
                 case 0: //LEFT
                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_left;
                break;

            case 1: //UP
                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_up;
                break;

            case 2: //RIGHT
                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_right;
                break;

            case 3: //DOWN
                SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().dead_down;
                break;
            }
        }
        else {
            switch (blinkyDirection) {
                case 0: //LEFT
                    if(blinkyAnimate)
                        SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_left;
                    else SingleTon.getInstance().blinky_CurrentImage =  SingleTon.getInstance().blinky_left_2;
                    break;

                case 1: //UP
                    if(blinkyAnimate)
                        SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_up;
                    else SingleTon.getInstance().blinky_CurrentImage =  SingleTon.getInstance().blinky_up_2;
                    break;

                case 2: //RIGHT
                    if(blinkyAnimate)
                        SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_right;
                    else SingleTon.getInstance().blinky_CurrentImage =  SingleTon.getInstance().blinky_right_2;
                    break;

                case 3: //DOWN
                    if(blinkyAnimate)
                        SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_down;
                    else SingleTon.getInstance().blinky_CurrentImage =  SingleTon.getInstance().blinky_down_2;
                    break;
            }

            //alterno per animazione
            blinkyAnimate = !blinkyAnimate; 
        }
    }

    //! Generato con ChatGPT algoritmo del fantasma !
    public void moveBlinkyBFS(int targetX, int targetY) {
    class Node { int x, y; Node parent; Node(int x,int y,Node p){this.x=x;this.y=y;parent=p;} }

    boolean[][] visited = new boolean[SingleTon.getInstance().ROWS][SingleTon.getInstance().COLS];
    Queue<Node> queue = new LinkedList<>();
    queue.add(new Node(blinkyX, blinkyY, null));
    visited[blinkyY][blinkyX] = true;

    Node endNode = null;

    while(!queue.isEmpty()){
        Node current = queue.poll();
        if(current.x == targetX && current.y == targetY){ endNode = current; break; }

        String[] directions = {"su","giu","sx","dx"};
        for(String dir : directions){
            int nx = current.x;
            int ny = current.y;

            switch(dir){
                case "su": 
                    ny--; 
                    break;

                case "giu": 
                    ny++; 
                    break;

                case "sx": 
                    nx--; 
                    break;

                case "dx": 
                    nx++;
                    break;
            }

            if(nx >=0 && nx < SingleTon.getInstance().COLS && ny >=0 && ny < SingleTon.getInstance().ROWS && !visited[ny][nx]){
                String cell = SingleTon.getInstance().game_map[ny][nx];
                if(!cell.equals("WALL") && !cell.equals("DOOR1") && !cell.equals("DOOR2")){
                    visited[ny][nx] = true;
                    queue.add(new Node(nx, ny, current));
                }
            }
        }
    }

    if(endNode != null){
        //Risaliamo il percorso fino alla prima mossa
        Node moveNode = endNode;
        while(moveNode.parent != null && moveNode.parent.parent != null){
            moveNode = moveNode.parent;
        }
        //Esegui il movimento
        if(moveNode.x > blinkyX){ blinkyX++; blinkyDirection = 2; }
        if(moveNode.x < blinkyX){ blinkyX--; blinkyDirection = 0; }
        if(moveNode.y > blinkyY){ blinkyY++; blinkyDirection = 3; }
        if(moveNode.y < blinkyY){ blinkyY--; blinkyDirection = 1; }

        animateBlinky();
    }
}
    
    //! Generato con ChatGPT algoritmo del fantasma !
    public void moveBlinky(boolean scatterMode) {
        float currentSpeed; //1 = normale, 2 = più lento (quando si potenzia pacman)

        if (blinkyDead) {
            currentSpeed = 1; //velocità normale quando morto (torna alla base)
        } else {
            if(powered) currentSpeed = 2;
            else currentSpeed = 1; //2 = lento quando powered, 1 = normale
        }

        blinkyMoveCounter++;
        if (blinkyMoveCounter < currentSpeed) return;
        blinkyMoveCounter = 0;

        int targetX, targetY;

        if (blinkyDead) {
            targetX = 13; targetY = 11;
        } else if (powered) {
            //Fuga da Pac-Man
            int maxDist = -1;
            int farX = blinkyX;
            int farY = blinkyY;
            for (int y = 0; y < SingleTon.getInstance().ROWS; y++) {
                for (int x = 0; x < SingleTon.getInstance().COLS; x++) {
                    String cell = SingleTon.getInstance().game_map[y][x];
                    if (!cell.equals("WALL") && !cell.equals("DOOR1") && !cell.equals("DOOR2")) {
                        int dist = Math.abs(x - pac_manX) + Math.abs(y - pac_manY);
                        if (dist > maxDist) { maxDist = dist; farX = x; farY = y; }
                    }
                }
            }
            targetX = farX;
            targetY = farY;
        } else if (scatterMode) {
            targetX = SingleTon.getInstance().COLS - 1; targetY = 0;
        } else {
            targetX = pac_manX; targetY = pac_manY;
        }

        moveBlinkyBFS(targetX, targetY);

        //Se Blinky è morto e ha raggiunto la base, resuscita
        if (blinkyDead && blinkyX == 13 && blinkyY == 11) {
            blinkyDead = false;
            powered = false; //opzionale, così non scappa subito

        //Ripristina immagine normale in base alla direzione
        switch (blinkyDirection) {
            case 0: SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_left; break;
            case 1: SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_up; break;
            case 2: SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_right; break;
            case 3: SingleTon.getInstance().blinky_CurrentImage = SingleTon.getInstance().blinky_down; break;
        }
    }
}
}