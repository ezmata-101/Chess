package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {

    private Pane chessBoardPane = new Pane();
    private BorderPane mainPane = new BorderPane();
    private ChessBoard chessBoard = new ChessBoard();

    @Override
    public void start(Stage primaryStage) throws Exception{
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<51; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        chessBoardPane = chessBoard.createMainPane();
        mainPane.setCenter(chessBoardPane);
        if(list.get(25)%2==0){
            chessBoard.distributeItems(1,1);
        }
        else{
            chessBoard.distributeItems(1,2);
        }
        //chessBoard.distributeItems(1);
        //ChessItem chessItem = new ChessItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.BISHOP);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
        chessBoard.showFirstTurnStatus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
