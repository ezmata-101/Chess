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
    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//        primaryStage.setTitle("Chess");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

        Game game = new Game(new DatabaseUserManage());
        game.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
