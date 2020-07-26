package MainPackage;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class Game {
    private Pane chessBoardPane = new Pane();
    private AnchorPane anchorPane;
    private BorderPane mainPane;
    private ChessBoard chessBoard;
    FXMLLoader fxmlLoader;
    GamePaneController controller;
    Stage primaryStage;
    ClientManage client;

    /*Game(DatabaseUserManage ds){
        this.ds = ds;
    }*/
    Game(){
        chessBoard = new ChessBoard();
    }
    Game(ClientManage client){
        this.client = client;
        chessBoard = new ChessBoard(client);
    }

    public void init(){
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLS/game_layout.fxml"));
            anchorPane = fxmlLoader.load();
            controller = fxmlLoader.getController();
            mainPane = new BorderPane();
//            fxmlLoader.setController(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage=new Stage();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<51; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        chessBoardPane = chessBoard.createMainPane();
        mainPane.setCenter(chessBoardPane);
        controller.setBorderPane(mainPane);
        boolean x = false;
        if(list.get(25)%2==0){
            chessBoard.distributeItems(1,1);
        }
        else{
            x = true;
            chessBoard.distributeItems(1,2);
        }
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
        chessBoard.showFirstTurnStatus();
        if(x) chessBoard.doARotation();
    }
}
