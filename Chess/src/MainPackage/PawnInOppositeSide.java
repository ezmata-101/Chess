package MainPackage;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.midi.Soundbank;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PawnInOppositeSide{

    private AnchorPane anchorPane;
    private ImageView image1,image2,image3,image4;
    private Label label1,label2,label3,label4,mainLabel;
    private VBox vBox1,vBox2,vBox3,vBox4;
    private HBox hBox1,hBox2;
    public static String SOURCE_PATH = "Resource\\";
    private int itemNo=0;
    private String file_path;
    private Image img;
    FXMLLoader fxmlLoader;
    PawnOppositeController controller;
    Stage primaryStage;
    private StackPane stackPane;
    public PawnInOppositeSide(int color){
        file_path=SOURCE_PATH;
        if(color==0){
            file_path+="Black\\";
        }
        else{
            file_path+="White\\";
        }
    }

    public void init() throws FileNotFoundException {
        anchorPane=new AnchorPane();
        anchorPane.setPrefHeight(244);
        anchorPane.setPrefWidth(424);
        mainLabel=new Label();
        mainLabel.setAlignment(Pos.CENTER);
        mainLabel.setPrefHeight(17);
        mainLabel.setPrefWidth(424);
        mainLabel.setText("Select a Piece");
        mainLabel.setStyle("-fx-font-weight: Bold Italic;"+"-fx-font-size: 14;");
        mainLabel.setLayoutX(-2);




        hBox1=new HBox();
        hBox1.setPrefHeight(100);
        hBox1.setPrefWidth(404);
        hBox1.setSpacing(10);
        hBox1.setLayoutY(28);
        anchorPane.setBottomAnchor(hBox1,114.0);
        anchorPane.setLeftAnchor(hBox1,10.0);
        anchorPane.setRightAnchor(hBox1,10.0);
        anchorPane.setTopAnchor(hBox1,30.0);
        hBox1.setPadding(new Insets(10,10,10,10));
        vBox1=new VBox();
        vBox1.setAlignment(Pos.TOP_CENTER);
        vBox1.setPrefHeight(82);
        vBox1.setPrefWidth(186);
        img=new Image(new FileInputStream(file_path+"queen.png"));
        image1=new ImageView();
        image1.setImage(img);
        image1.setFitHeight(60);
        image1.setFitWidth(120);
        image1.setPickOnBounds(true);
        image1.setPreserveRatio(true);
        image1.setOnMouseClicked(e->{
            setItemNo(2);
        });
        label1=new Label();
        label1.setText("Queen");
        label1.setStyle("-fx-font-size: 14;"+"-fx-font-weight: Bold Italic;");
        vBox1.getChildren().addAll(image1,label1);




        vBox2=new VBox();
        vBox2.setAlignment(Pos.TOP_CENTER);
        vBox2.setPrefHeight(82);
        vBox2.setPrefWidth(186);
        img=new Image(new FileInputStream(file_path+"bishop.png"));
        image2=new ImageView();
        image2.setImage(img);
        image2.setFitHeight(60);
        image2.setFitWidth(120);
        image2.setPickOnBounds(true);
        image2.setPreserveRatio(true);
        image2.setOnMouseClicked(e->{
            setItemNo(3);
        });
        label2=new Label();
        label2.setText("Bishop");
        label2.setStyle("-fx-font-size: 14;"+"-fx-font-weight: Bold Italic;");
        vBox2.getChildren().addAll(image2,label2);
        hBox1.getChildren().addAll(vBox1,vBox2);





        hBox2=new HBox();
        hBox2.setPrefHeight(100);
        hBox2.setPrefWidth(404);
        hBox2.setSpacing(10);
        hBox2.setLayoutX(10);
        hBox2.setLayoutY(138);
        hBox2.setPadding(new Insets(10,10,10,10));
        vBox3=new VBox();
        vBox3.setAlignment(Pos.TOP_CENTER);
        vBox3.setPrefHeight(80);
        vBox3.setPrefWidth(184);
        img=new Image(new FileInputStream(file_path+"rook.png"));
        image3=new ImageView();
        image3.setImage(img);
        image3.setFitHeight(60);
        image3.setFitWidth(120);
        image3.setPickOnBounds(true);
        image3.setPreserveRatio(true);
        image3.setOnMouseClicked(e->{
            setItemNo(1);
        });
        label3=new Label();
        label3.setText("Rook");
        label3.setStyle("-fx-font-size: 14;"+"-fx-font-weight: Bold Italic;");
        vBox3.getChildren().addAll(image3,label3);





        vBox4=new VBox();
        vBox4.setAlignment(Pos.TOP_CENTER);
        vBox4.setPrefHeight(80);
        vBox4.setPrefWidth(184);
        img=new Image(new FileInputStream(file_path+"knight.png"));
        image4=new ImageView();
        image4.setImage(img);
        image4.setFitHeight(60);
        image4.setFitWidth(120);
        image4.setPickOnBounds(true);
        image4.setPreserveRatio(true);
        image4.setOnMouseClicked(e->{
            setItemNo(4);
        });
        label4=new Label();
        label4.setText("Queen");
        label4.setStyle("-fx-font-size: 14;"+"-fx-font-weight: Bold Italic;");
        vBox4.getChildren().addAll(image4,label4);
        hBox2.getChildren().addAll(vBox3,vBox4);

        anchorPane.getChildren().addAll(mainLabel,hBox1,hBox2);
        anchorPane.setVisible(false);
        anchorPane.setDisable(true);

        try {

            stackPane = fxmlLoader.load();
            controller = fxmlLoader.getController();
            stackPane.getChildren().add(anchorPane);
            StackPane.setAlignment(anchorPane,Pos.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage=new Stage();
        primaryStage.setTitle("Changing Pawn");
        primaryStage.setScene(new Scene(stackPane));
        primaryStage.show();
        showPane();
    }
    public void setItemNo(int i){
        this.itemNo=i;
    }
    public int getItemNo(){
        return this.itemNo;
    }


    public void showPane(){
        System.out.println("Showing pawn changing pane");
        TranslateTransition tt = new TranslateTransition(Duration.millis(1500));
        tt.setFromX(-500);
        tt.setToX(30);
        tt.setFromY(100);
        tt.setToY(100);
        tt.setNode(anchorPane);
        tt.play();
        anchorPane.setDisable(false);
        anchorPane.setVisible(true);
        System.out.println("Showing done");
    }
    public void hidePane(){
        System.out.println("Removing pawn changing pane");
        TranslateTransition tt = new TranslateTransition(Duration.millis(1500));
        tt.setFromX(30);
        tt.setToX(500);
        tt.setFromY(100);
        tt.setToY(100);
        tt.setNode(anchorPane);
        tt.play();
        primaryStage.close();
    }
}
