package MainPackage;

public class Pawn {
    private int posX,posY;
    private int[][] itemcolor=null;
    private int[][] itemtype=null;
    int color,toX,toY;
    int positionOfPawn;
    int positionToGo;
    int multiply;
    Pawn(int x, int y, int[][] itemcolor,int[][] itemtype,int color,int i,int j){
        this.posX=x;
        this.posY=y;
        this.itemcolor=itemcolor;
        this.itemtype=itemtype;
        this.color=color;
        this.toX=i;
        this.toY=j;
    }
    public boolean movePawn(){
        if(color == 0){
            multiply=1;
        }
        else if(color == 1){
            multiply=-1;
        }
        positionOfPawn= (posX*8)+posY+1;
        positionToGo=(toX*8)+toY+1;
        System.out.println(posX +" "+posY +" "+color +" "+toX +" "+toY+" "+positionOfPawn +" "+positionToGo);
        int getdifference=positionToGo-positionOfPawn;
        int coordinates;
        if(Math.abs(getdifference)==8){
            coordinates=positionOfPawn+(8*multiply);
            if(checkforeight(coordinates,positionToGo)){
                return true;
            }
        }
        else if(Math.abs(getdifference)==16){
            if((color==0 && posX==1) || (color==1 && posX==6)){
                coordinates=positionOfPawn+(8*multiply);
                int x=(coordinates-1)/8;
                int y=(coordinates-1)%8;
                System.out.println("x: "+x +" y: "+y +" itemtype: "+itemtype[x][y]);
                if(itemtype[x][y]!=-1){
                    System.out.println("Majh e ekta ase..so jaite parbe na");
                    return false;
                }
                else {
                    System.out.println("Majh e keo silo na ..tai eikhane ashche");
                    coordinates=coordinates+(8*multiply);
                    if(checkforeight(coordinates,positionToGo)){
                        return true;
                    }
                }
            }
        }
        else if(Math.abs(getdifference)==7){
            coordinates=positionOfPawn+(7*multiply);
            if(coordinates==positionToGo){
                if((color==0 && posY==0) || (color==1 && posY==7)){
                    return false;
                }
                if(itemtype[toX][toY]==-1){
                    return false;
                }
                else if(itemcolor[toX][toY]!=color){
                    return true;
                }
                else {
                    return false;
                }
            }

        }
        else if(Math.abs(getdifference)==9){
            coordinates=positionOfPawn+(9*multiply);
            if(coordinates==positionToGo){
                if((color==0 && posY==7) || (color==1 && posY==0)){
                    return false;
                }
                if(itemtype[toX][toY]==-1){
                    return false;
                }
                else if(itemcolor[toX][toY]!=color){
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
    private boolean checkforeight(int coordinates,int positionToGo){
        if(coordinates==positionToGo){
            if(itemtype[toX][toY]==-1){
                return true;
            }
            /*else if(itemcolor[toX][toY]!=color){
                return false;
            }*/
            else {
                return false;
            }
        }
        return false;
    }
}
