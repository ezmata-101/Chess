package MainPackage;

import javafx.scene.layout.Pane;

public class Checkmate {
    private ChessBoard board;
    private int color;
    private boolean isWhiteTurn;
    private boolean isBlackTurn;
    private int count=0;
    Checkmate(ChessBoard board,boolean isWhiteTurn,boolean isBlackTurn){
        this.board=board;
        this.isWhiteTurn=isWhiteTurn;
        this.isBlackTurn=isBlackTurn;
        if(isWhiteTurn==true){
            color=1;
        }
        else{
            color=0;
        }
    }
    public boolean checkingCheckMate(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                String[] strings = board.labels[i][j].getText().split(",");
                int indX = Integer.parseInt(strings[0]);
                int indY = Integer.parseInt(strings[1]);
                if(indX == -1 || indY == -1){
                    continue;
                }
                if(board.chessItems[indX][indY].getColor()==color){
                    //System.out.println("Check kortese "+board.chessItems[indX][indY].getColor() +" er jonno");
                    checkPossibleMoves(board.chessItems[indX][indY]);
                }
            }
        }
        System.out.println("Count: "+count);
        if(count>=1){
            count=0;
            return true;
        }
        else{
            count=0;
            return false;
        }
    }
    private void checkPossibleMoves(ChessItem ci) {
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
                    checkAndColorPossibleMove(ci, possibleX, possibleY);
                }
            }
        }
        if(type.equals("bishop") || type.equals("queen")){
            for(int i=1; ;i++){
                int possibleX = posX + i;
                int possibleY = posY + i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX + i;
                int possibleY = posY - i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX - i;
                int possibleY = posY + i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX - i;
                int possibleY = posY - i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY)) break;
            }
        }
        if(type.equals("rook") || type.equals("queen")){
            for(int i=1; ;i++){
                int possibleX = posX;
                int possibleY = posY + i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX;
                int possibleY = posY - i;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX + i;
                int possibleY = posY;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY)) break;
            }
            for(int i=1; ;i++){
                int possibleX = posX - i;
                int possibleY = posY;
                if(!checkAndColorPossibleMove(ci, possibleX, possibleY)) break;
            }
        }
        if(type.equals("king")){
            for(int i=-1; i<=1; i++){
                for(int j=-1; j<=1; j++){
                    int possibleX = posX + i;
                    int possibleY = posY + j;
                    checkAndColorPossibleMove(ci, possibleX, possibleY);
                }
            }
        }
        if(type.equals("pawn")){
            int pls = board.order == 1 ? 1 : -1;
            if(ci.getColor() == ChessItem.WHITE) pls*=-1;

            if(board.getChessItemFromPos(posX+pls, posY) == null) checkAndColorPossibleMove(ci, posX+pls, posY);
            if(board.getChessItemFromPos(posX+pls*2, posY) == null && (posX<2 || posX>5)) checkAndColorPossibleMove(ci, posX+pls*2, posY);
            if(board.getChessItemFromPos(posX+pls, posY+1) != null) checkAndColorPossibleMove(ci, posX+pls, posY+1);
            if(board.getChessItemFromPos(posX+pls, posY-1) != null) checkAndColorPossibleMove(ci, posX+pls, posY-1);

        }
    }

    private boolean checkAndColorPossibleMove(ChessItem ci, int toX, int toY){

        if(toX < 0 || toX >7 || toY<0 || toY > 7) return false;
        String[] ss = board.labels[toX][toY].getText().split(",");
        int inToX = Integer.parseInt(ss[0]);
        int inToY = Integer.parseInt(ss[1]);
        if(inToX == -1 || inToY == -1){
            return true;
        }
        else if(ci.getColor() != board.chessItems[inToX][inToY].getColor()){
            //System.out.println("Attack point e ase " +board.chessItems[inToX][inToY].getType());
            if(board.chessItems[inToX][inToY].type.getFileName().equals("king")){
                //System.out.println("Eikhane ashche");
                count++;
            }
            return false;
        }
        else return false;
    }
}
