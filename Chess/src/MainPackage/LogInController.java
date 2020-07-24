package MainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField password;
    private Pane chessBoardPane = new Pane();
    private BorderPane mainPane = new BorderPane();
    private ChessBoard chessBoard = new ChessBoard();
    DatabaseUserManage ds=new DatabaseUserManage();
    Alert a = new Alert(Alert.AlertType.NONE);
    Game game;

    public void LogInButtonPushed(ActionEvent event) throws IOException {
        if(ds.getUserByName(username.getText())){
            if(ds.AccountValidityCheck(username.getText(),password.getText())){
                Stage stage=(Stage)this.username.getScene().getWindow();
                stage.close();
                game = new Game(ds);
                game.init();
            }
            else{
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Invalid Password");
                a.show();
            }
        }
        else{
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Invalid Username or Password");
            a.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ds.open();
        username.setText("");
        password.setText("");
    }
}