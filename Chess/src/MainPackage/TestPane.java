package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TestPane extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane pane = new AnchorPane();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLS/initial_layout.fxml"));
            pane = loader.load();
        }catch (Exception e){
            System.out.println("Failed to Load!");
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
