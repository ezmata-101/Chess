package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private Pane chessBoardPane = new Pane();
    private BorderPane mainPane = new BorderPane();
    private ChessBoard chessBoard = new ChessBoard();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        chessBoardPane = chessBoard.createMainPane();
        mainPane.setCenter(chessBoardPane);
        chessBoard.distributeItems(1);
        //ChessItem chessItem = new ChessItem(ChessItem.BLACK, ChessItem.CHESS_ITEM.BISHOP);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
