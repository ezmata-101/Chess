package MainPackage;

public class Queen {
    private int posX,posY;
    private int[][] itemcolor=null;
    private int[][] itemtype=null;
    int color,toX,toY;
    int positionOfQueen;
    int positionToGo;
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
            System.out.println("Bishop kaj korse");
            return true;
        }
        else if(rook.moveRook()){
            System.out.println("Bishop kaj kore nai but rook kaj korse");
            return true;
        }
        else{
            System.out.println("Bishop rook konotai kaj kore nai");
            return false;
        }
    }
}
