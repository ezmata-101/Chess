package MainPackage;

import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class ChessBoard {
    private Pane mainPane = new Pane();
    private StackPane[][] stackPanes = new StackPane[8][8];
    private Pane[][] panes = new Pane[8][8];
    public Label[][] labels = new Label[8][8];
    private VBox vBox= new VBox();
    private HBox[] hBoxes = new HBox[8];
    public ChessItem[][] chessItems = new ChessItem[2][16];
    private ImageView[][] images = new ImageView[8][8];
    public int lastX =-1, lastY=-1, lastIndexRow, lastIndexCol;
    private boolean isSelected = false;
    private boolean lastMove = false;
    public int[][] itemcolor=new int[8][8];//Eita hoilo prottekta position e jodi element thake taile ki color er element tar jonno
    public int[][] itemtype=new int[8][8];//Eita hoilo oi element ta ki bishop naki Knight naki king na queen eigula bujhar jonno
    //Upoer 2 tar jonnoi jodi kono position e kisu na thake taile default value -1 pore set kora hoise
    private ChessItem lastMovedItem = null;
    public int order;
    private int turn;
    Alert a = new Alert(Alert.AlertType.NONE);
    private boolean isBlackTurn=false;
    private boolean isWhiteTurn=false;
    private boolean isWhiteKingChecked=false;
    private boolean isBlackKingChecked=false;
    public boolean isWhiteKingMoved=false;
    public boolean isBlackKingMoved=false;
    public boolean isWhiteRookMoved1=false;
    public boolean isWhiteRookMoved2=false;
    public boolean isBlackRookMoved1=false;
    public boolean isBlackRookMoved2=false;
    public static final boolean OFFLINE_PRACTICE = true;
    public static final boolean ONLINE_MATCH = false;
    public boolean mode;
    private Controller controller;
    private boolean isRotated = false;

    ClientManage client;
    private boolean PLAYER_COLOR;
    private static final boolean WHITE = true;
    private static final boolean BLACK = false;
    private boolean notOk = true;
    private boolean itemSelected = false;


    public ChessBoard(){}
    ChessBoard(ClientManage client){
        this.client = client;
    }

    public Pane createMainPane() {
        mainPane = new Pane();
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

                stackPanes[i][j].setOnMouseClicked(e -> {
                    onStackPaneSelected(finalI, finalJ, true);
                });
                hBoxes[i].getChildren().add(stackPanes[i][j]);
            }
            vBox.getChildren().add(hBoxes[i]);
        }
        mainPane.getChildren().add(vBox);
        return mainPane;
    }

    public void showFirstTurnStatus(){
        if(turn==1){
            isWhiteTurn=true;
            isBlackTurn=false;
            a.setAlertType(AlertType.INFORMATION);
            a.setContentText("White's turn first");
            a.show();
        }
        else if(turn==2){
            isWhiteTurn=false;
            isBlackTurn=true;
            a.setAlertType(AlertType.INFORMATION);
            a.setContentText("Black's turn first");
            a.show();
        }
    }
    //Ei function tar last e ami extra ekta variable add korsi jeita hoilo itemtype,eita tmr code e chilo na.
    public void distributeItems(int order,int turn){
        this.order = order;
        this.turn=turn;
        if(turn == 1) PLAYER_COLOR = WHITE;
        else PLAYER_COLOR = BLACK;
        for(int i=0; i<8; i++){
        initiateNewItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.PAWN, 1, 8+i, 6, i,6);
        initiateNewItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.PAWN, 0, 8+i, 1, i,6);}
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

    public void clearPane(){

    }
    private void removeItem(int row, int col){
        panes[row][col].getChildren().clear();
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
        chessItems[indexRow][indexCol] = new ChessItem(color, type, indexRow, indexCol);
        placeItem(chessItems[indexRow][indexCol], posRow, posCol, indexRow, indexCol,itemtype);
    }

    //Inefficient we know :(
    private void clearPossibleMoveColors(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                removeColor(i, j);
            }
        }
    }

    private void showInvalidMove(){
        a.setAlertType(Alert.AlertType.WARNING);
        a.setContentText("You are not allowed to make this move");
        a.show();
    }

    public void onStackPaneSelected(int i, int j, boolean offlineMove){
        clearPossibleMoveColors();
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
            int x=item.getPosX(); //Item tar x,y,coordinate store korlam
            int y=item.getPosY();
    //        System.out.println(x+ " "+ y +" " +item.color+ " "+ i + " "+ j);

            //Jodi element ta Knight hoy taile eita run hbe

            if(isValidMove(item, x, y, i, j)){
                if(item.isType("pawn")){
                    if((item.color==0 && i==7) || (item.color==1 && i==0)){
                        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/FXMLS/PawnInOpposite.fxml"));
                        Parent root= null;
                        try {
                            root = fxmlLoader.load();
                        } catch (IOException e) {
                            System.out.println("Can't load pawn's fxml.");
                            e.printStackTrace();
                        }
                        PawnOppositeController pawnOppositeController=fxmlLoader.getController();
                        pawnOppositeController.setEverything(this, i, j, item.color);
                        Stage stage=new Stage();
                        stage.setTitle("Chess");
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                    else{
                        moveItem(lastX,lastY,i,j,0, true);
                    }
                }
                else moveItem(lastX, lastY, i, j, 0, true);
            }else if(item.isType("king") && checkAndDoACastle(x, y, i, j, true)){
                a.setAlertType(AlertType.CONFIRMATION);
                a.setContentText("Castling Done");
                a.show();
            }else showInvalidMove();
            isSelected = false;
            return;
        }
        else if(!isSelected && !labels[i][j].getText().equals("-1,-1")) {
            //isSelected = true;
            String[] strings = labels[i][j].getText().split(",");
            lastIndexRow = Integer.parseInt(strings[0]);
            lastIndexCol = Integer.parseInt(strings[1]);

            if(isWhiteTurn==true && isBlackTurn==false){
                if(chessItems[lastIndexRow][lastIndexCol].color==1){
                    isSelected=true;
                }
                else{
                    a.setAlertType(Alert.AlertType.WARNING);
                    a.setContentText("It's White Player's turn");
                    a.show();
                    isSelected=false;
                }
                isWhiteTurn=true;
                isBlackTurn=false;
            }
            else if(isBlackTurn==true && isWhiteTurn==false){
                if(chessItems[lastIndexRow][lastIndexCol].color==0){
                    isSelected=true;
                }
                else{
                    a.setAlertType(Alert.AlertType.WARNING);
                    a.setContentText("It's Black Player's turn");
                    a.show();
                    isSelected=false;
                }
                isWhiteTurn=false;
                isBlackTurn=true;
            }

            if(lastMovedItem != null)colorPossibleMoves(lastMovedItem, false);
            if(isSelected){
                colorPossibleMoves(chessItems[lastIndexRow][lastIndexCol], true);
            }
            //colorPossibleMoves(chessItems[lastIndexRow][lastIndexCol], true);
            //System.out.println("Selected!");
        }
        //panes[i][j].setStyle("-fx-background-color: #BACA45");
        if(isSelected){
            colorPane(i, j, COLOR.SELECTED_SQUARE);
        }
        //colorPane(i, j, COLOR.SELECTED_SQUARE);
        lastX = i;
        lastY = j;
        //System.out.println(i+","+j);
    }
    private boolean isValidMove(ChessItem it, int fromX, int fromY, int toX, int toY){
        if(it.isType("knight")){
            return new Knight(fromX, fromY, this, it.color, toX, toY).moveKnight();
        }else if(it.isType("bishop")){
            return new Bishop(fromX, fromY, itemcolor, itemtype, it.color, toX, toY).moveBishop();
        }else if(it.isType("rook")){
            return new Rook(fromX, fromY, itemcolor, itemtype, it.color, toX, toY).moveRook();
        }else if(it.isType("queen")){
            return new Queen(fromX, fromY, itemcolor, itemtype, it.color, toX, toY).moveQueen();
        }else if(it.isType("pawn")){
            return new Pawn(fromX, fromY, itemcolor, itemtype, it.color, toX, toY).movePawn();
        }else if(it.isType("king")){
            return new King(fromX, fromY, itemcolor, itemtype, it.color, toX, toY).moveKing();
        }else return false;
    }
    private boolean checkAndDoACastle(int fromX, int fromY, int toX, int toY, boolean doCastle) {
        if(Math.abs(fromY - toY) !=2 || fromX != toX){
            System.out.println("Returning false for distance!");
            return false;
        }

        ChessItem king = getChessItemFromPos(fromX, fromY);
        if(king.getColor() == ChessItem.WHITE && isWhiteKingChecked) return false;
        if(king.getColor() == ChessItem.BLACK && isBlackKingChecked) return false;
//        System.out.println("Check passed");
        ChessItem rook;
        int pls = -1;
        int rookIndX, rookIndY;
        if(toY > fromY){
            rook = getChessItemFromPos(fromX, 7);
            pls = 1;
        }
        else rook = getChessItemFromPos(fromX, 0);

        String s;
        if(toY > fromY) s =  labels[fromX][7].getText();
        else s = labels[fromX][0].getText();
        if(s.equals("-1,-1"))return false;
        String[] ss = s.split(",");
        rookIndX = Integer.parseInt(ss[0]);
        rookIndY = Integer.parseInt(ss[1]);
        rook = chessItems[rookIndX][rookIndY];
//        System.out.println("Null Rock passed");
        if(rook.isEverMoved()) return false;
//        System.out.println("Is ever Moved passed");

        for(int i=fromY+pls; i<7&&i>0; i+=pls){
            if(getChessItemFromPos(toX, i) != null){
//                System.out.format("Returning for : %d %d\n", toX, i);
                return false;
            }
        }

        if(!doCastle) return true;
//        System.out.println("Everything passed");

        moveItem(fromX, fromY, toX, toY,0, true);

        lastIndexRow = rookIndX;
        lastIndexCol = rookIndY;
        lastX = rook.getPosX();
        lastY = rook.getPosY();
        if(pls>0){
            moveItem(fromX, 7, fromX, 5,0, true);
        }
        else{
            moveItem(fromX, 0, fromX, 3,0, true);
        }
        rotateTurn(king.getColor() == ChessItem.WHITE, true);
//        if(king.getColor() == ChessItem.WHITE){
//            isWhiteTurn = false;
//            isBlackTurn = true;
//        } else{
//            isWhiteTurn = true;
//            isBlackTurn = false;
//        }

        return true;
    }

    public void rotateTurn(boolean whiteTurn, boolean sendToServer) {
        isWhiteTurn = !whiteTurn;
        isBlackTurn = whiteTurn;
        System.out.println("isWhiteTurn: "+whiteTurn);
        if(mode == ONLINE_MATCH && sendToServer)sendToMessage("turnMove/"+whiteTurn);
    }

    private void colorPossibleMoves(ChessItem ci, boolean colorOrDiscolor) {
        if(colorOrDiscolor) itemSelected = true;
        if(!colorOrDiscolor)notOk = true;
        int posX = ci.getPosX();
        int posY = ci.getPosY();
        String type = ci.getType().getItemType();
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
        if(type.equals("king")){
            for(int i=-1; i<=1; i++){
                for(int j=-1; j<=1; j++){
                    int possibleX = posX + i;
                    int possibleY = posY + j;
                    checkAndColorPossibleMove(ci, possibleX, possibleY, colorOrDiscolor);
                }
            }
        }
        if(type.equals("pawn")){
            int pls = order == 1 ? 1 : -1;
            if(ci.getColor() == ChessItem.WHITE) pls*=-1;

            if(getChessItemFromPos(posX+pls, posY) == null){
                checkAndColorPossibleMove(ci, posX+pls, posY, colorOrDiscolor);
                if(getChessItemFromPos(posX+pls*2, posY) == null && (posX<2 || posX>5)) checkAndColorPossibleMove(ci, posX+pls*2, posY, colorOrDiscolor);
            }
            if(getChessItemFromPos(posX+pls, posY+1) != null) checkAndColorPossibleMove(ci, posX+pls, posY+1, colorOrDiscolor);
            if(getChessItemFromPos(posX+pls, posY-1) != null) checkAndColorPossibleMove(ci, posX+pls, posY-1, colorOrDiscolor);

        }
        notOk = false;
        lastMovedItem = ci;
    }

    public ChessItem getChessItemFromPos(int x, int y){
        if(x<0 || x>7 || y<0 || y>7)return null;
        String[] ss = labels[x][y].getText().split(",");
        int indX = Integer.parseInt(ss[0]);
        int indY = Integer.parseInt(ss[1]);
        if(indX == -1 || indY == -1) return null;
        return chessItems[indX][indY];
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

    public void createMovement(int fromX, int fromY, int toX, int toY, int x, boolean b) {
        String s = labels[fromX][fromY].getText();
        String[] ss = s.split(",");
        //ChessItem ci = getChessItemFromPos(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
        lastIndexRow = Integer.parseInt(ss[0]);
        lastIndexCol = Integer.parseInt(ss[1]);
        lastX = fromX;
        lastY = fromY;
        moveItem(fromX, fromY, toX, toY, x, false);
    }

    public void moveItem(int fromX, int fromY, int toX, int toY,int x, boolean offlineMove) {
//        System.out.println(fromX + " " + fromY + " " + toX + " " + toY + " " + x + " " + offlineMove);
//        printBoard();

        if(mode == ONLINE_MATCH && offlineMove){
            int ciColor = getChessItemFromPos(fromX, fromY).color;
            boolean flag = (PLAYER_COLOR == WHITE && ciColor == ChessItem.BLACK)
                    || (PLAYER_COLOR == BLACK && ciColor == ChessItem.WHITE);
            if(flag){
                clearPossibleMoveColors();
                return;
            }
            sendToMessage("GAME/"+fromX+"/"+fromY+"/"+toX+"/"+toY+"/"+x);
        }
        panes[fromX][fromY].getChildren().clear();
        labels[fromX][fromY].setText("-1,-1");
        chessItems[lastIndexRow][lastIndexCol].setEverMoved(true);
        if(x==0){
            deleteItemFrom(toX, toY);
            placeItem(chessItems[lastIndexRow][lastIndexCol], toX, toY, lastIndexRow, lastIndexCol,itemtype[fromX][fromY]);
//            client.sendToServer("GAME/"+fromX+"/"+fromY+"/"+toX+"/"+toY+"/"+x);
        }
        else{
            ChessItem item = null;
            ChessItem ci = getChessItemFromPos(toX, toY);
            int rowIndex = ci.getRowIndex();
            int colIndex = ci.getColumnIndex();
            switch (x){
                case 1:
                    item = new ChessItem(chessItems[lastIndexRow][lastIndexCol].color,ChessItem.CHESS_ITEM.ROOK);
                    break;
                case 2:
                    item = new ChessItem(chessItems[lastIndexRow][lastIndexCol].color,ChessItem.CHESS_ITEM.QUEEN);
                    break;
                case 3:
                    item = new ChessItem(chessItems[lastIndexRow][lastIndexCol].color,ChessItem.CHESS_ITEM.BISHOP);
                    break;
                case 4:
                    item = new ChessItem(chessItems[lastIndexRow][lastIndexCol].color,ChessItem.CHESS_ITEM.KNIGHT);
                    break;
            }
            if(item == null){
                System.out.println("SOMETHING WRONG!");
                return;
            }
            chessItems[rowIndex][colIndex]=item;
            placeItem(item, toX, toY, rowIndex, colIndex,itemtype[fromX][fromY]);
            if(isRotated)item.getImageView().setRotate(180);
        }
//        else if(x==1){
//            ChessItem item=new ChessItem(chessItems[lastIndexRow][lastIndexCol].color,ChessItem.CHESS_ITEM.ROOK);
//            chessItems[lastIndexRow][lastIndexCol]=item;
//            placeItem(item, toX, toY, lastIndexRow, lastIndexCol,itemtype[fromX][fromY]);
//        }
//        else if(x==2){
//            ChessItem item=new ChessItem(chessItems[lastIndexRow][lastIndexCol].color,ChessItem.CHESS_ITEM.QUEEN);
//            chessItems[lastIndexRow][lastIndexCol]=item;
//            placeItem(item, toX, toY, lastIndexRow, lastIndexCol,itemtype[fromX][fromY]);
//        }
//        else if(x==3){
//            ChessItem item=new ChessItem(chessItems[lastIndexRow][lastIndexCol].color,ChessItem.CHESS_ITEM.BISHOP);
//            chessItems[lastIndexRow][lastIndexCol]=item;
//            placeItem(item, toX, toY, lastIndexRow, lastIndexCol,itemtype[fromX][fromY]);
//        }
//        else if(x==4){
//            ChessItem item=new ChessItem(chessItems[lastIndexRow][lastIndexCol].color,ChessItem.CHESS_ITEM.KNIGHT);
//            chessItems[lastIndexRow][lastIndexCol]=item;
//            placeItem(item, toX, toY, lastIndexRow, lastIndexCol,itemtype[fromX][fromY]);
//        }
        itemcolor[fromX][fromY]=-1;//Jeheto ei jayga ta khali hoia jabe tai value -1 korlam
        itemtype[fromX][fromY]=-1;//Same kahini eitar jonno o
        //panes[toX][toY].setStyle("-fx-background-color: #F6F782");
        colorPane(toX, toY, COLOR.LAST_MOVED);
        lastMove = true;
        lastX = toX;
        lastY = toY;

//        Checkmate checkmate1=new Checkmate(this,!isWhiteTurn,!isBlackTurn);
//        if(checkmate1.checkingCheckMate()){
//            if(isBlackKingChecked){
//                a.setAlertType(AlertType.INFORMATION);
//                //a.setContentText("Black KIng checkmate");
//                a.setContentText("White King Player Wins");
//                a.show();
//                isBlackKingChecked=false;
//                //sendToMessage("Checkmate/Black");
//            }
//            else if(isWhiteKingChecked){
//                a.setAlertType(AlertType.INFORMATION);
//                //a.setContentText("White king checkmate");
//                a.setContentText("Black King Player Wins");
//                a.show();
//                isWhiteKingChecked=false;
//                //sendToMessage("Checkmate/White");
//            }
//        }
//        Checkmate checkmate2=new Checkmate(this,isWhiteTurn,isBlackTurn);
//        if(checkmate2.checkingCheckMate()){
//            if(isWhiteTurn){
//                isBlackKingChecked=true;
//                isWhiteKingChecked=false;
//                a.setAlertType(Alert.AlertType.WARNING);
//                a.setContentText("Black KIng check");
//                a.show();
//            }
//            else if(isBlackTurn){
//                isWhiteKingChecked=true;
//                isBlackKingChecked=false;
//                a.setAlertType(Alert.AlertType.WARNING);
//                a.setContentText("White KIng check");
//                a.show();
//            }
//        }



//        if(isWhiteTurn){
//            isWhiteTurn=false;
//            isBlackTurn=true;
//        }
//        else if(isBlackTurn){
//            isBlackTurn=false;
//            isWhiteTurn=true;
//        }
        isWhiteTurn = !isWhiteTurn;
        isBlackTurn = !isBlackTurn;
        if(mode == OFFLINE_PRACTICE) doARotation(750);

//        System.out.println("Eikhane chilo: "+tempLastItem);
//        if(tempLastItem != null) for(int i=0; i<2; i++){
//            for(int j=0; j<16; j++){
//                if(tempLastItem == chessItems[i][j]){
//                    System.out.println("Ekhono ache!" + i+ ", "+j);
//                }
//            }
//        }
        printBoard();
    }

    private void deleteItemFrom(int toX, int toY) {
        ChessItem ci = getChessItemFromPos(toX, toY);
        if(ci != null){
            System.out.println("Deleted: "+ci);
//            chessItems[ci.getRowIndex()][ci.getColumnIndex()] = null;
        }
    }

    public void setPlayerColor(boolean color) {
        PLAYER_COLOR = color;
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

    public void setMode(boolean mode) {
        this.mode = mode;
    }
    private void colorPane(int i, int j, COLOR color){
        //panes[i][j].setStyle("-fx-border-color: black");
        panes[i][j].setStyle(color.setColor());
    }

    private void removeColor(int i, int j){
        if((i+j)%2 == 0) panes[i][j].setStyle(COLOR.WHITE_SQUARE.setColor());
        else panes[i][j].setStyle(COLOR.BLACK_SQUARE.setColor());
    }

    public void doARotation(int timeInMillis) {
        isRotated = !isRotated;
        doRotate(mainPane, timeInMillis);
        System.out.println("mainPane rotate kortesi");
        for(int i=0; i<2; i++){
            for(int j=0; j<16; j++){
                if(chessItems[i][j] != null)doRotate(chessItems[i][j].getImageView(), timeInMillis);
            }
        }
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

                System.out.print(chessItems[indX][indY].getType().getItemType().charAt(0)+" ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------\n\n");
        printChessItems();
    }
    void printChessItems(){
        for(int i=0; i<2; i++){
            for(int j=0; j<16; j++){
                System.out.println(chessItems[i][j]);
            }
            System.out.println();
        }
    }
    void sendToMessage(String message){
        if(client == null) return;
        try{
            client.sendToServer(message);
        }catch (Exception e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }
    private void doRotate(Node node, int time){
        RotateTransition rt = new RotateTransition();
        rt.setAxis(Rotate.Z_AXIS);
        //System.out.println(isRotated);
        if(isRotated)rt.setByAngle(-180);
        else rt.setByAngle(180);
        //rt.setByAngle(180);
        rt.setCycleCount(1);
        rt.setDuration(Duration.millis(time));
        rt.setAutoReverse(false);
        rt.setNode(node);
        rt.play();
    }
    /*public void showPane(AnchorPane anchorPane){
        System.out.println("Showing pawn changing pane");
        TranslateTransition tt = new TranslateTransition(Duration.millis(1500));
        tt.setFromX(-500);
        tt.setToX(30);
        tt.setFromY(100);
        tt.setToY(100);
        tt.setNode(anchorPane);
        tt.play();
        anchorPane.setDisable(false);
        anchorPane.setVisible(true);
        System.out.println("Showing done");
    }
    public void hidePane(AnchorPane anchorPane){
        System.out.println("Removing pawn changing pane");
        TranslateTransition tt = new TranslateTransition(Duration.millis(1500));
        tt.setFromX(30);
        tt.setToX(500);
        tt.setFromY(100);
        tt.setToY(100);
        tt.setNode(anchorPane);
        tt.play();
    }*/
    //    public void lastRowMoveItem(int fromX, int fromY, int toX, int toY) {
//        panes[fromX][fromY].getChildren().clear();
//        labels[fromX][fromY].setText("-1,-1");
//        placeItem(chessItems[lastIndexRow][lastIndexCol], toX, toY, lastIndexRow, lastIndexCol,itemtype[fromX][fromY]);
//        chessItems[lastIndexRow][lastIndexCol].setEverMoved(true);
//        itemcolor[fromX][fromY]=-1;//Jeheto ei jayga ta khali hoia jabe tai value -1 korlam
//        itemtype[fromX][fromY]=-1;//Same kahini eitar jonno o
//        //panes[toX][toY].setStyle("-fx-background-color: #F6F782");
//        colorPane(toX, toY, COLOR.LAST_MOVED);
//        lastMove = true;
//        lastX = toX;
//        lastY = toY;
//        Checkmate checkmate1=new Checkmate(this,!isWhiteTurn,!isBlackTurn);
//
//        if(checkmate1.checkingCheckMate()){
//            if(isBlackKingChecked){
//                a.setAlertType(Alert.AlertType.WARNING);
//                a.setContentText("Black KIng checkmate");
//                a.show();
//                isBlackKingChecked=false;
//            }
//            else if(isWhiteKingChecked){
//                a.setAlertType(Alert.AlertType.WARNING);
//                a.setContentText("White king checkmate");
//                a.show();
//                isWhiteKingChecked=false;
//            }
//        }
//
//
//        Checkmate checkmate2=new Checkmate(this,isWhiteTurn,isBlackTurn);
//        if(checkmate2.checkingCheckMate()){
//            if(isWhiteTurn){
//                isBlackKingChecked=true;
//                isWhiteKingChecked=false;
//                a.setAlertType(Alert.AlertType.WARNING);
//                a.setContentText("Black KIng check");
//                a.show();
//            }
//            else if(isBlackTurn){
//                isWhiteKingChecked=true;
//                isBlackKingChecked=false;
//                a.setAlertType(Alert.AlertType.WARNING);
//                a.setContentText("White KIng check");
//                a.show();
//            }
//        }
//        if(isWhiteTurn){
//            isWhiteTurn=false;
//            isBlackTurn=true;
//        }
//        else if(isBlackTurn){
//            isBlackTurn=false;
//            isWhiteTurn=true;
//        }
//
//        if(mode == OFFLINE_PRACTICE) doARotation(750);
//
//        //printBoard();
//    }

}
