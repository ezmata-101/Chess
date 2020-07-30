package MainPackage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class PawnOppositeController implements Initializable {
    private int itemNo=0;

    @FXML
    ImageView img1,img2,img3,img4;
    ChessBoard board;
    int tox,toY;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /*public int getItemNo(){
        return itemNo;
    }
    public void setItemNo(int i){
        this.itemNo=i;
    }*/
    public void onImg1Clicked(MouseEvent event){
        //setItemNo(1);
        Stage stage=(Stage)this.img1.getScene().getWindow();
        stage.close();
        System.out.println("Item no set to 1");
        Platform.runLater(()->{
                board.moveItem(board.lastX,board.lastY,tox,toY,1);
        });
    }
    public void onImg2Clicked(MouseEvent event){
        //setItemNo(3);
        Stage stage=(Stage)this.img1.getScene().getWindow();
        stage.close();
        System.out.println("Item no set to 3");
        Platform.runLater(()->{
            board.moveItem(board.lastX,board.lastY,tox,toY,3);
        });
    }
    public void onImg3Clicked(MouseEvent event){
        //setItemNo(2);
        Stage stage=(Stage)this.img1.getScene().getWindow();
        stage.close();
        System.out.println("Item no set to 2");
        Platform.runLater(()->{
            board.moveItem(board.lastX,board.lastY,tox,toY,2);
        });
    }
    public void onImg4Clicked(MouseEvent event){
        //setItemNo(4);
        Stage stage=(Stage)this.img1.getScene().getWindow();
        stage.close();
        System.out.println("Item no set to 4");
        Platform.runLater(()->{
            board.moveItem(board.lastX,board.lastY,tox,toY,4);
        });
    }
    public void setChessBoard(ChessBoard board){
        this.board=board;
    }
    public void settoX(int i){
        this.tox=i;
    }
    public void setToY(int i){
        this.toY=i;
    }
    public void createImageOfPieces(int color) throws FileNotFoundException {
        String path="Resourse\\";
        if(color==0){
            path+="Black\\";
        }
        else{
            path+="White\\";
        }
        Image img;
        img=new Image(new FileInputStream(path+"rook.png"));
        img1.setImage(img);
        img=new Image(new FileInputStream(path+"bishop.png"));
        img2.setImage(img);
        img=new Image(new FileInputStream(path+"queen.png"));
        img3.setImage(img);
        img=new Image(new FileInputStream(path+"knight.png"));
        img4.setImage(img);
    }
}
