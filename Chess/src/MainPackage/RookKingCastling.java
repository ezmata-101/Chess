package MainPackage;

public class RookKingCastling {
    private ChessBoard board;
    private int color;
    private boolean isWhiteTurn;
    private boolean isBlackTurn;
    RookKingCastling(ChessBoard board,boolean isWhiteTurn,boolean isBlackTurn){
        this.board=board;
        this.isWhiteTurn=isWhiteTurn;
        this.isBlackTurn=isBlackTurn;
        if(isWhiteTurn==true){
            color=1;
        }
        else if(isBlackTurn==true){
            color=0;
        }
    }
    public boolean rightCheckCastling(){
        if(color==1 && board.isWhiteKingMoved==true){
            return false;
        }
        else if(color==0 && board.isBlackKingMoved==true){
            return false;
        }
        if(color==0 && board.isBlackRookMoved2==true){
            return false;
        }
        else if(color==1 && board.isWhiteRookMoved2==true){
            return false;
        }
        return false;/**Emnei disi**/
    }
}
