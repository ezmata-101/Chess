package MainPackage;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePaneController implements Initializable {
    @FXML
    AnchorPane anchorPane;
    @FXML
    MenuBar menuBar;
    @FXML
    Menu fileMenu;
    @FXML
    BorderPane borderPane;
    @FXML MenuItem menuMenuItem;

    ClientManage client;
    Game game;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //borderPane = new BorderPane();
    }
    public BorderPane getBorderPane(){
        return borderPane;
    }
    public void setClient(){
        this.client = client;
        client.setGamePaneController();
    }
    public void sendMessage(String message){
        if(client == null) return;
        client.sendToServer(message);
    }
    public void onMenuItem(){
        if(game == null){
            System.out.println("Returning...");
            return;
        }
        game.showMenu();
    }
    public void onCloseMenuItem(ActionEvent actionEvent) {
        if(game == null){
            System.out.println("Returning...");
            return;
        }
        game.hideMenu();
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void setBorderPane(BorderPane mainPane) {
        borderPane = mainPane;
    }
}
