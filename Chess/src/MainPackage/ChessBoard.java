package MainPackage;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Random;


public class ChessBoard {
    private Pane mainPane = new Pane();
    private StackPane[][] stackPanes = new StackPane[8][8];
    private Pane[][] panes = new Pane[8][8];
    private Label[][] labels = new Label[8][8];
    private VBox vBox= new VBox();
    private HBox[] hBoxes = new HBox[8];
    private ChessItem[][] chessItems = new ChessItem[2][16];
    private ImageView[][] images = new ImageView[8][8];
    private int lastX =-1, lastY=-1, lastIndexRow, lastIndexCol;
    private boolean isSelected = false;
    private boolean lastMove = false;
    public int[][] itemcolor=new int[8][8];//Eita hoilo prottekta position e jodi element thake taile ki color er element tar jonno
    public int[][] itemtype=new int[8][8];//Eita hoilo oi element ta ki bishop naki Knight naki king na queen eigula bujhar jonno
    //Upoer 2 tar jonnoi jodi kono position e kisu na thake taile default value -1 pore set kora hoise
    private ChessItem lastMovedItem = null;

    public ChessBoard(){}

    public Pane createMainPane() {
        for(int i=0; i<8; i++){
            hBoxes[i] = new HBox();
            for(int j=0; j<8; j++){
                int finalI = i;
                int finalJ = j;

                itemcolor[i][j]=-1;//Eije default value
                itemtype[i][j]=-1;//Eitar o default value

                labels[i][j] = new Label("-1,-1");
                labels[i][j].setStyle("-fx-font-size: 8px");
                images[i][j] = new ImageView();
                panes[i][j] = new Pane();
                panes[i][j].setPrefHeight(60);
                panes[i][j].setPrefWidth(60);
                removeColor(i, j);
                panes[i][j].getChildren().add(images[i][j]);

                stackPanes[i][j] = new StackPane();
                stackPanes[i][j].setPrefWidth(60);
                stackPanes[i][j].setPrefHeight(60);
                stackPanes[i][j].getChildren().addAll(labels[i][j],panes[i][j]);

                stackPanes[i][j].setOnMouseClicked(e -> onStackPaneSelected(finalI, finalJ));
                hBoxes[i].getChildren().add(stackPanes[i][j]);
            }
            vBox.getChildren().add(hBoxes[i]);
        }
        mainPane.getChildren().add(vBox);
        return mainPane;
    }


    //Ei function tar last e ami extra ekta variable add korsi jeita hoilo itemtype,eita tmr code e chilo na.
    public void distributeItems(int order){
        for(int i=0; i<8; i++){
            initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.PAWN, 0, 8+i, 6, i,6);
            initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.PAWN, 1, 8+i, 1, i,6);
        }
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.ROOK, 0, 0, 0, 0,3);
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.ROOK, 0, 1, 0, 7,3);
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.KNIGHT, 0, 2, 0, 1,5);
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.KNIGHT, 0, 3, 0, 6,5);
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.BISHOP, 0, 4, 0, 2,4);
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.BISHOP, 0, 5, 0, 5,4);
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.QUEEN, 0, 6, 0, 3,2);
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.KING, 0, 7, 0, 4,1);

        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.ROOK, 1, 0, 7, 0,3);
        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.ROOK, 1, 1, 7, 7,3);
        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.KNIGHT, 1, 2, 7, 1,5);
        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.KNIGHT, 1, 3, 7, 6,5);
        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.BISHOP, 1, 4, 7, 2,4);
        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.BISHOP, 1, 5, 7, 5,4);
        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.QUEEN, 1, 6, 7, 3,2);
        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.KING, 1, 7, 7, 4,1);
    }
    private void placeItem(ChessItem item, int row, int col, int indexX, int indexY,int type){
        panes[row][col].getChildren().clear();
        labels[row][col].setText(indexX+","+indexY);
        panes[row][col].getChildren().add(item.getImageView());
        item.setPosition(row, col);
        itemcolor[row][col]=item.color;//Color set kortesi
        itemtype[row][col]=type;//Items er type set kortesi
        //System.out.println("Type after replace: "+itemtype[row][col]);
    }

    public void initiateNewItem(int color, ChessItem.CHESS_ITEM type, int indexRow, int indexCol, int posRow, int posCol,int itemtype){
        chessItems[indexRow][indexCol] = new ChessItem(color, type);
        placeItem(chessItems[indexRow][indexCol], posRow, posCol, indexRow, indexCol,itemtype);
    }

    private void onStackPaneSelected(int i, int j){
        printBoard();
        if((lastX != -1 && lastY != -1) || (lastX == i && lastY == j) || lastMove){
            if(lastMovedItem != null) colorPossibleMoves(lastMovedItem, false);
            removeColor(lastX, lastY);
            lastMove = false;
        }
        if(lastX == i && lastY == j){
            isSelected = false;
            lastY = -1;
            lastX = -1;
            return;
        }
        if(isSelected){
            //moveItem(lastX, lastY, i, j);
            ChessItem item=chessItems[lastIndexRow][lastIndexCol];//Jei element ta selected hoilo oita item e rakhlam
            int x=item.getPosX();//Itar x,y,coordinate store korlam
            int y=item.getPosY();
    //        System.out.println(x+ " "+ y +" " +item.color+ " "+ i + " "+ j);

            //Jodi element ta Knight hoy taile eita run hbe
            if(item.type.getFileName()=="knight"){
                Knight knight=new Knight(x,y,this,item.color,i,j);
                if(knight.moveKnight()){
                    moveItem(lastX,lastY,i,j);
                }
            }

            //R jodi bishop hoy taile eita
            if(item.type.getFileName()=="bishop"){
                Bishop bishop=new Bishop(x,y,itemcolor,itemtype,item.color,i,j);
                if(bishop.moveBishop()){
                    moveItem(lastX,lastY,i,j);
                }
            }

            /**For rook**/
            if(item.type.getFileName()=="rook"){
                Rook rook=new Rook(x,y,itemcolor,itemtype,item.color,i,j);
                if(rook.moveRook()){
                    moveItem(lastX,lastY,i,j);
                }
            }
            /**For queen**/
            if(item.type.getFileName()=="queen"){
                Queen queen=new Queen(x,y,itemcolor,itemtype,item.color,i,j);
                if(queen.moveQueen()){
                    moveItem(lastX,lastY,i,j);
                }
            }
            /**For pawn**/
            if(item.type.getFileName()=="pawn"){
                Pawn pawn=new Pawn(x,y,itemcolor,itemtype,item.color,i,j);
                if(pawn.movePawn()){
                    moveItem(lastX,lastY,i,j);
                }
            }
            /**For king**/
            if(item.type.getFileName()=="king"){
                King king=new King(x,y,itemcolor,itemtype,item.color,i,j);
                if(king.moveKing()){
                    moveItem(lastX,lastY,i,j);
                }
            }
            isSelected = false;
            return;
        }
        else if(!isSelected && !labels[i][j].getText().equals("-1,-1")) {
            isSelected = true;
            String[] strings = labels[i][j].getText().split(",");
            lastIndexRow = Integer.parseInt(strings[0]);
            lastIndexCol = Integer.parseInt(strings[1]);

            if(lastMovedItem != null)colorPossibleMoves(lastMovedItem, false);
            colorPossibleMoves(chessItems[lastIndexRow][lastIndexCol], true);
            //System.out.println("Selected!");
        }
        //panes[i][j].setStyle("-fx-background-color: #BACA45");
        colorPane(i, j, COLOR.SELECTED_SQUARE);
        //System.out.println(i+","+j);
        lastX = i;
        lastY = j;
    }

    private void colorPossibleMoves(ChessItem ci, boolean colorOrDiscolor) {
        int posX = ci.getPosX();
        int posY = ci.getPosY();
        String type = ci.getType().getFileName();
        if(type.equals("knight")){
            int ara1[] = {1,1,2,2,-1,-1,-2,-2};
            int ara2[] = {2, -2, 1, -1};
            for(int i=0; i<2; i++){
                for(int j=0; j<4; j++){
                    int possibleX = posX + ara1[j + i*4];
                    int possibleY = posY + ara2[j];
                    checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor);
                }
            }
        }

        if(type.equals("bishop") || type.equals("queen")){
            for(int i=1; ;i++){
                int possibleX = posX + i;
                int possibleY = posY + i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX + i;
                int possibleY = posY - i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX - i;
                int possibleY = posY + i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX - i;
                int possibleY = posY - i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor)) break;
            }
        }

        if(type.equals("rook") || type.equals("queen")){
            for(int i=1; ;i++){
                int possibleX = posX;
                int possibleY = posY + i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX;
                int possibleY = posY - i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX + i;
                int possibleY = posY;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX - i;
                int possibleY = posY;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor)) break;
            }
        }

        lastMovedItem = ci;
    }

    private boolean checkAndColorPossibleMove(ChessItem ci, int toX, int toY, boolean color){

        if(toX < 0 || toX >7 || toY<0 || toY > 7) return false;
        if(!color){
            removeColor(toX,toY);
            return true;
        }
        String[] ss = labels[toX][toY].getText().split(",");
        int inToX = Integer.parseInt(ss[0]);
        int inToY = Integer.parseInt(ss[1]);
        if(inToX == -1 || inToY == -1){
            colorPane(toX, toY, COLOR.POSSIBLE_NORMAL);
            return true;
        }
        else if(ci.getColor() != chessItems[inToX][inToY].getColor()){
            colorPane(toX, toY, COLOR.POSSIBLE_ATTACK);
            return false;
        }
        else return false;
    }

    enum COLOR{
        WHITE_SQUARE("EEEED2"),
        BLACK_SQUARE("769655"),
        SELECTED_SQUARE("BACA45"),
        LAST_MOVED("F6F782"),
        POSSIBLE_NORMAL("3ce815"),
        POSSIBLE_ATTACK("e85f15");


        private String code;
        COLOR(String code) {
            this.code = code;
        }
        public String setColor(){
            return "-fx-background-color: #"+code;
        }
    }

    private void colorPane(int i, int j, COLOR color){
        panes[i][j].setStyle(color.setColor());
    }
    private void removeColor(int i, int j){
        if((i+j)%2 == 0) panes[i][j].setStyle(COLOR.WHITE_SQUARE.setColor());
        else panes[i][j].setStyle(COLOR.BLACK_SQUARE.setColor());
    }

    public void moveItem(int fromX, int fromY, int toX, int toY) {
        panes[fromX][fromY].getChildren().clear();
        labels[fromX][fromY].setText("-1,-1");
        placeItem(chessItems[lastIndexRow][lastIndexCol], toX, toY, lastIndexRow, lastIndexCol,itemtype[fromX][fromY]);
        itemcolor[fromX][fromY]=-1;//Jeheto ei jayga ta khali hoia jabe tai value -1 korlam
        itemtype[fromX][fromY]=-1;//Same kahini eitar jonno o
        //panes[toX][toY].setStyle("-fx-background-color: #F6F782");
        colorPane(toX, toY, COLOR.LAST_MOVED);
        lastMove = true;
        lastX = toX;
        lastY = toY;
        printBoard();
    }

    public void printBoard(){

        System.out.println("\n\n_______________________________");
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                String[] strings = labels[i][j].getText().split(",");
                int indX = Integer.parseInt(strings[0]);
                int indY = Integer.parseInt(strings[1]);
                if(indX == -1 || indY == -1){
                    System.out.print("--- ");
                    continue;
                }

                if(chessItems[indX][indY].getColor() == ChessItem.WHITE) System.out.print("W-");
                else System.out.print("B-");

                System.out.print(chessItems[indX][indY].getType().getFileName().charAt(0)+" ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------\n\n");
    }
}
