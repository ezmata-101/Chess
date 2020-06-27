package MainPackage;

public class King {
    private int[] movableCoordinates= {-9,-8,-7,-1,1,7,8,9};
    private int posX,posY;
    private int[][] itemcolor=null;
    private int[][] itemtype=null;
    int color,toX,toY;
    int positionOfKing;
    int positionToGo;
    King(int x, int y, int[][] itemcolor,int[][] itemtype,int color,int i,int j){
        this.posX=x;
        this.posY=y;
        this.itemcolor=itemcolor;
        this.itemtype=itemtype;
        this.color=color;
        this.toX=i;
        this.toY=j;
    }
    public boolean moveKing(){
        positionOfKing= (posX*8)+posY+1;//Knight ekhn jeikhane ase
        positionToGo=(toX*8)+toY+1;//Jeikhane jaite hbe
        int coordinates;
        for(int x:movableCoordinates){
            if(posY==0 && (x==-9 || x==-1 ||x==7)){
                continue;
            }
            else if(posY==7 && (x==-7 || x==1 || x==9)){
                continue;
            }
            coordinates=positionOfKing+x;
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
