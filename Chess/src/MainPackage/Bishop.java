package MainPackage;

import java.lang.Math;



/**ei class ta kalke tmk bujhiya dibo...eita jhamela er ektu...tmi na bujhle boilo ..bujhaiya dibo**/
public class Bishop {
    private int posX,posY;
    private int[][] itemcolor=null;
    private int[][] itemtype=null;
    int color,toX,toY;
    int positionOfBishop;
    int positionToGo;
    Bishop(int x, int y, int[][] itemcolor,int[][] itemtype,int color,int i,int j){
        this.posX=x;
        this.posY=y;
        this.itemcolor=itemcolor;
        this.itemtype=itemtype;
        this.color=color;
        this.toX=i;
        this.toY=j;
    }
    public boolean moveBishop(){
        positionOfBishop= (posX*8)+posY+1;
        positionToGo=(toX*8)+toY+1;
        int getdifference=positionToGo-positionOfBishop;
        if(positionToGo<positionOfBishop) {
            if (Math.abs(getdifference) % 9 == 0) {
                int coordinates;
                if(posY==0){
                    return false;
                }
                coordinates = positionOfBishop - 9;
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
                    coordinates=coordinates-9;
                }
                return false;
            }

            else if (Math.abs(getdifference) % 7 == 0) {
                int coordinates;
                if(posY==7){
                    return false;
                }
                coordinates = positionOfBishop - 7;
                while (coordinates >= 1 && coordinates <= 64) {
                    if(coordinates>positionToGo) {
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
                    coordinates=coordinates-7;
                }
                return false;
            }
        }
        else{
            if (Math.abs(getdifference) % 9 == 0) {
                int coordinates;
                if(posY==7){
                    return false;
                }
                coordinates = positionOfBishop + 9;
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
                    coordinates=coordinates+9;
                }
                return false;
            }

            else if (Math.abs(getdifference) % 7 == 0) {
                int coordinates;
                if(posY==0){
                    return false;
                }
                coordinates = positionOfBishop + 7;
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
                    coordinates=coordinates+7;
                }
                return false;
            }
        }
        return false;
    }
}
