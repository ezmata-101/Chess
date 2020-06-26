package MainPackage;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


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
    private int[][] itemcolor=new int[8][8];
    private int[][] itemtype=new int[8][8];


    public ChessBoard(){
    }


    public Pane createMainPane() {
        for(int i=0; i<8; i++){
            hBoxes[i] = new HBox();
            for(int j=0; j<8; j++){
                int finalI = i;
                int finalJ = j;


                int x=(i*8)+j+1;

                itemcolor[i][j]=-1;
                itemtype[i][j]=-1;
                labels[i][j] = new Label("-1,-1");
                labels[i][j].setStyle("-fx-font-size: 8px");
                images[i][j] = new ImageView();
                panes[i][j] = new Pane();
                panes[i][j].setPrefHeight(60);
                panes[i][j].setPrefWidth(60);
                if((i+j)%2==0)panes[i][j].setStyle("-fx-background-color: #EEEED2");
                else panes[i][j].setStyle("-fx-background-color: #769655");
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
        labels[row][col].setText(indexX+","+indexY);
        panes[row][col].getChildren().clear();
        panes[row][col].getChildren().add(item.getImageView());
        item.setPosition(row, col);
        itemcolor[row][col]=item.color;
        itemtype[row][col]=type;
    }
    public void initiateNewItem(int color, ChessItem.CHESS_ITEM type, int indexRow, int indexCol, int posRow, int posCol,int itemtype){
        chessItems[indexRow][indexCol] = new ChessItem(color, type);
        placeItem(chessItems[indexRow][indexCol], posRow, posCol, indexRow, indexCol,itemtype);
    }

    private void onStackPaneSelected(int i, int j){
        if((lastX != -1 && lastY != -1) || (lastX == i && lastY == j) || lastMove){
            if((lastX + lastY) % 2 == 0)panes[lastX][lastY].setStyle("-fx-background-color: #EEEED2");
            else panes[lastX][lastY].setStyle("-fx-background-color: #769655");
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
            ChessItem item=chessItems[lastIndexRow][lastIndexCol];
            int x=item.getPosX();
            int y=item.getPosY();
            System.out.println(x+ " "+ y +" " +item.color+ " "+ i + " "+ j);
            if(item.type.getFileName()=="knight"){
                Knight knight=new Knight(x,y,itemcolor,itemtype,item.color,i,j);
                if(knight.moveKnight()){
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
            System.out.println("Selected!");
        }
        panes[i][j].setStyle("-fx-background-color: #BACA45");
        //System.out.println(i+","+j);
        lastX = i;
        lastY = j;
    }

    public void moveItem(int fromX, int fromY, int toX, int toY) {
        panes[fromX][fromY].getChildren().clear();
        //boardposition[(fromX*8)+fromY+1]=false;
        itemcolor[fromX][fromY]=-1;
        itemtype[fromX][fromY]=-1;
        placeItem(chessItems[lastIndexRow][lastIndexCol], toX, toY, lastIndexRow, lastIndexCol,itemtype[fromX][fromY]);
        panes[toX][toY].setStyle("-fx-background-color: #F6F782");
        lastMove = true;
        lastX = toX;
        lastY = toY;
    }
}
