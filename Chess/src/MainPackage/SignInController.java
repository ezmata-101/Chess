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

public class SignInController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Label usernameAlert;
    @FXML private Label passwordAlert;
    private Pane chessBoardPane = new Pane();
    private BorderPane mainPane = new BorderPane();
    private ChessBoard chessBoard = new ChessBoard();
    DatabaseUserManage ds=new DatabaseUserManage();
    Alert a = new Alert(Alert.AlertType.NONE);

    public void signInButtonPushed(ActionEvent event) throws IOException {
        if(username.getText().length()==0){
            usernameAlert.setText("Invalid Username.");
            if(password.getText().length()>=8){
                passwordAlert.setText("");
            }
        }
        else if(password.getText().length()<8){
            if(username.getText().length()!=0){
                usernameAlert.setText("");
            }
            passwordAlert.setText("Your password must be 8 characters long.");
        }
        else if(username.getText().length()!=0){
            if(ds.getUserByName(username.getText())){
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("This Username Already Exist.");
                a.show();
            }
            else{
                if(username.getText().length()!=0){
                    usernameAlert.setText("");
                }
                if(password.getText().length()>=8){
                    passwordAlert.setText("");
                }
                if(ds.AddUserToUserInfoTable(username.getText(),password.getText())){
                    Stage stage=(Stage)this.username.getScene().getWindow();
                    stage.close();
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    for (int i=1; i<51; i++) {
                        list.add(i);
                    }
                    Collections.shuffle(list);
                    Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
                    Stage primaryStage=new Stage();
                    chessBoardPane = chessBoard.createMainPane();
                    mainPane.setCenter(chessBoardPane);
                    if(list.get(25)%2==0){
                        chessBoard.distributeItems(1,1);
                    }
                    else{
                        chessBoard.distributeItems(1,2);
                    }
                    primaryStage.setTitle("Chess");
                    primaryStage.setScene(new Scene(mainPane));
                    primaryStage.show();
                    chessBoard.showFirstTurnStatus();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ds.open();
        username.setText("");
        password.setText("");
    }
}
