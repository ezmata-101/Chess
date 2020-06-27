package MainPackage;

public class Queen {
    private int posX,posY;
    private int[][] itemcolor=null;
    private int[][] itemtype=null;
    int color,toX,toY;
    Queen(int x, int y, int[][] itemcolor,int[][] itemtype,int color,int i,int j){
        this.posX=x;
        this.posY=y;
        this.itemcolor=itemcolor;
        this.itemtype=itemtype;
        this.color=color;
        this.toX=i;
        this.toY=j;
    }
    public boolean moveQueen(){
        Bishop bishop=new Bishop(posX,posY,itemcolor,itemtype,color,toX,toY);
        Rook rook=new Rook(posX,posY,itemcolor,itemtype,color,toX,toY);
        if(bishop.moveBishop()){
            return true;
        }
        else if(rook.moveRook()){
            return true;
        }
        else{
            return false;
        }
    }
}
