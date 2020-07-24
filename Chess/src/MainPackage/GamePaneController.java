package MainPackage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePaneController implements Initializable {
    @FXML BorderPane borderPane;
    @FXML
    Menu hideMenu;
    public void onHideMenu(){
        System.out.println("Print!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
