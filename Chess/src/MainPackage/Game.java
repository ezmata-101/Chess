package MainPackage;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
    private BorderPane mainPane = new BorderPane();
    private ChessBoard chessBoard = new ChessBoard();
    FXMLLoader fxmlLoader;
    GamePaneController controller;

    /*Game(DatabaseUserManage ds){
        this.ds = ds;
    }*/
    Game(){}

    public void init(){
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLS/game_layout.fxml"));
            mainPane = fxmlLoader.load();
            controller = fxmlLoader.getController();
//            fxmlLoader.setController(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage=new Stage();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<51; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        chessBoardPane = chessBoard.createMainPane();
        mainPane.setCenter(chessBoardPane);
        if(list.get(25)%2==0){
            chessBoard.distributeItems(1,1);
        }
        else{
            chessBoard.distributeItems(1,2);
        }
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
        chessBoard.showFirstTurnStatus();
    }
}
