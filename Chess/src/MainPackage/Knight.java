package MainPackage;

import javax.crypto.spec.PSource;

public class Knight{
    private int[] movableCoordinates= {-17,-15,-10,-6,6,10,15,17};//Kono ekta Knight er position theke joto dike jaoa jay.Eita dekhay dibo ne
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
        positionOfKnight= (posX*8)+posY+1;//Knight ekhn jeikhane ase
        positionToGo=(toX*8)+toY+1;//Jeikhane jaite hbe
        System.out.println(posX +" "+posY +" "+color +" "+toX +" "+toY+" "+positionOfKnight +" "+positionToGo);
        int coordinates;
        for(int x:movableCoordinates){
            coordinates=positionOfKnight+x;
            System.out.println("jaite chaitese :"+ coordinates +" jaite hbe: "+ " "+ positionToGo);
            if(coordinates<1 || coordinates>64){ //Coordinate valid naki check kortesi
                //System.out.println("Doing continue "+ " "+ coordinates);
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
