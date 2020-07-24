package MainPackage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePaneController implements Initializable {
    @FXML BorderPane borderPane;
    @FXML MenuBar menuBar;
    @FXML Menu hideMenu, fileMenu;
    @FXML
    MenuItem closeMenuItem;
    public void onHideMenu(){}
    public void onCloseMenuItem(){
        System.out.println("Close");
        if(borderPane.getTop() != null){
            borderPane.setTop(null);
            borderPane.setBottom(menuBar);
        }else{
            borderPane.setBottom(null);
            borderPane.setTop(menuBar);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
