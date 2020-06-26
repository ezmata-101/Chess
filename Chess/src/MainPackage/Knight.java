package MainPackage;

import javax.crypto.spec.PSource;

public class Knight{
    private int[] movableCoordinates= {-17,-15,-10,-6,6,10,15,17};
    private int posX,posY;
    private int[][] itemcolor=null;
    private int[][] itemtype=null;
    int color,toX,toY;
    int positionOfKnight;
    int positionToGo;
    Knight(int x, int y, int[][] itemcolor,int[][] itemtype,int color,int i,int j){
        this.posX=x;
        this.posY=y;
        this.itemcolor=itemcolor;
        this.itemtype=itemtype;
        this.color=color;
        this.toX=i;
        this.toY=j;
    }
    public boolean moveKnight(){
        positionOfKnight= (posX*8)+posY+1;
        positionToGo=(toX*8)+toY+1;
        System.out.println(posX +" "+posY +" "+color +" "+toX +" "+toY+" "+positionOfKnight +" "+positionToGo);
        int coordinates;
        for(int x:movableCoordinates){
            coordinates=positionOfKnight+x;
            System.out.println("jaite chaitese :"+ coordinates +" jaite hbe: "+ " "+ positionToGo);
            if(coordinates<1 || coordinates>64){
                //System.out.println("Doing continue "+ " "+ coordinates);
                continue;
            }
            else if(coordinates==positionToGo){
                if(itemtype[toX][toY]==-1){
                    return true;
                }
                else if(itemtype[toX][toY]!=-1){
                    if(itemcolor[toX][toY]!=color){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
