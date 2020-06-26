package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TestPane extends Application {


    private Pane mainPane = new Pane();
    private ChessBoard chessBoard = new ChessBoard();


    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane pane = new Pane();
        ChessItem ci1 = new ChessItem(ChessItem.WHITE, ChessItem.CHESS_ITEM.PAWN);
        pane.getChildren().add(ci1.getImageView());
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
