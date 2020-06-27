package MainPackage;

public class Rook {
    private int posX,posY;
    private int[][] itemcolor=null;
    private int[][] itemtype=null;
    int color,toX,toY;
    int positionOfRook;
    int positionToGo;
    Rook(int x, int y, int[][] itemcolor,int[][] itemtype,int color,int i,int j){
        this.posX=x;
        this.posY=y;
        this.itemcolor=itemcolor;
        this.itemtype=itemtype;
        this.color=color;
        this.toX=i;
        this.toY=j;
    }
    public boolean moveRook(){
        positionOfRook= (posX*8)+posY+1;
        positionToGo=(toX*8)+toY+1;
        System.out.println(posX +" "+posY +" "+color +" "+toX +" "+toY+" "+positionOfRook +" "+positionToGo);
        int getdifference=positionToGo-positionOfRook;
        if(positionToGo<positionOfRook) {
            if (Math.abs(getdifference) % 8 == 0) {
                int coordinates;
                coordinates = positionOfRook - 8;
                while (coordinates >= 1 && coordinates <= 64) {
                    int x=(coordinates-1)/8;
                    int y=(coordinates-1)%8;
                    if(coordinates>positionToGo) {
                        if(itemtype[x][y]!=-1) {
                            return false;
                        }
                    }
                    else if(coordinates==positionToGo) {
                        if(itemtype[x][y]==-1) {
                            return true;
                        }
                        else if(itemtype[x][y]!=-1){
                            if(itemcolor[x][y]!=color){
                                return true;
                            }
                            else{
                                return false;
                            }
                        }
                        else {
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                    coordinates=coordinates-8;
                }
                return false;
            }
            else if(posX==toX){
                if(Math.abs(getdifference)==1){
                    if(itemcolor[toX][toY]!=color){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                for(int i=posY-1;posY>toY;posY--){
                    if(itemtype[posX][i]!=-1){
                        return false;
                    }
                }
                if(itemtype[toX][toY]==-1){
                    return true;
                }
                else if(itemcolor[toX][toY]!=color){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            if (Math.abs(getdifference) % 8 == 0) {
                int coordinates;
                coordinates = positionOfRook + 8;
                while (coordinates >= 1 && coordinates <= 64) {
                    if(coordinates<positionToGo) {
                        int x=(coordinates-1)/8;
                        int y=(coordinates-1)%8;
                        if(itemtype[x][y]!=-1) {
                            return false;
                        }
                    }
                    else if(coordinates==positionToGo) {
                        int x=(coordinates-1)/8;
                        int y=(coordinates-1)%8;
                        if(itemtype[x][y]==-1) {
                            return true;
                        }
                        else if(itemtype[x][y]!=-1){
                            if(itemcolor[x][y]!=color){
                                return true;
                            }
                            else{
                                return false;
                            }
                        }
                        else {
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                    coordinates=coordinates+8;
                }
                return false;
            }
            else if(posX==toX){
                if(Math.abs(getdifference)==1){
                    if(itemcolor[toX][toY]!=color){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                for(int i=posY+1;posY<toY;posY++){
                    if(itemtype[posX][i]!=-1){
                        return false;
                    }
                }
                if(itemtype[toX][toY]==-1){
                    return true;
                }
                else if(itemcolor[toX][toY]!=color){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }
}
