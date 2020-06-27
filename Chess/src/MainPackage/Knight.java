package MainPackage;

public class Knight{
    private int[] movableCoordinates= {-17,-15,-10,-6,6,10,15,17};//Kono ekta Knight er position theke joto dike jaoa jay.Eita dekhay dibo ne
    private int posX,posY;
    private int[][] itemcolor=null;
    private int[][] itemtype=null;
    int color,toX,toY;
    int positionOfKnight;
    int positionToGo;
    Knight(int x, int y, ChessBoard board,int color,int i,int j){
        this.posX=x;
        this.posY=y;
        this.itemcolor=board.itemcolor;
        this.itemtype=board.itemtype;
        this.color=color;
        this.toX=i;
        this.toY=j;
    }
    public boolean moveKnight(){
        positionOfKnight= (posX*8)+posY+1;//Knight ekhn jeikhane ase
        positionToGo=(toX*8)+toY+1;//Jeikhane jaite hbe
        int coordinates;
        for(int x:movableCoordinates){
            if(posY==0 && (x==-17 || x==-10 || x==6 || x==15)){
                continue;
            }
            else if(posY==1 && (x==-10 || x==6)){
                continue;
            }
            else if(posY==6 && (x==-6 || x==10)){
                continue;
            }
            else if(posY==7 && (x==-15 || x==-6 || x==10 || x==17)){
                continue;
            }
            coordinates=positionOfKnight+x;
            if(coordinates<1 || coordinates>64){ //Coordinate valid naki check kortesi
                continue;
            }
            else if(coordinates==positionToGo){
                if(itemtype[toX][toY]==-1){ //jodi kisu na thake jeikhane jaite hbe oikhan etaile move korbo
                    return true;
                }
                else if(itemtype[toX][toY]!=-1){ //jodi kisu thake but oita onno color er hoy taileo move korbo
                    if(itemcolor[toX][toY]!=color){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
