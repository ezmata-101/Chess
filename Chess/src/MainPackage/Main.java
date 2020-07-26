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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLS/initial_layout.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/FXMLS/main.fxml"));
        Parent root = loader.load();
        InitialController controller = loader.getController();
        ClientManage client = new ClientManage();
        if(client.start()){
            System.out.println("Client Manager Started Successfully");
        }else {
            System.out.println("Client Failed to Connect. Contact Server.");
            return;
        }
        controller.setClient(client);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        client.setStage(primaryStage);
        //Game game = new Game(new DatabaseUserManage());
        /*Game game=new Game();
        game.init();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
