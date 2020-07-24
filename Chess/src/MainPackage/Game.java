package MainPackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private Pane chessBoardPane = new Pane();
    private BorderPane mainPane = new BorderPane();
    private ChessBoard chessBoard = new ChessBoard();
    DatabaseUserManage ds;
    FXMLLoader fxmlLoader;
    GamePaneController controller;

    Game(DatabaseUserManage ds){
        this.ds = ds;
    }

    public void init(){
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("game_layout.fxml"));
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
