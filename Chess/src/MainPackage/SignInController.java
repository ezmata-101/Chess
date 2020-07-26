package MainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML public TextField username;
    @FXML private PasswordField password;
    @FXML private Label gotoLogin;
    /*private Pane chessBoardPane = new Pane();
    private BorderPane mainPane = new BorderPane();
    private ChessBoard chessBoard = new ChessBoard();*/
    //DatabaseUserManage ds=new DatabaseUserManage();
    Game game;
    ClientManage client;

    public void signInButtonPushed(ActionEvent event) throws IOException {

        String name = username.getText();
        String pass = password.getText();
        password.clear();
        boolean flag = false;
        if(name.equals("")){
            username.clear();
            username.setPromptText("Invalid User Name");
            username.setStyle("-fx-fill: brown; "+
                    "-fx-text-fill: brown");
            username.setStyle("-fx-border-color: brown");
            flag = true;
        }
        if(pass.equals("") || pass.length() < 8){
            password.setPromptText("Invalid Pass. Min len: 8");
            password.setStyle("-fx-text-fill: brown; "+"" +
                    "-fx-border-color: brown");
            flag = true;
        }
        if(flag) return;

        //String msg="signin/"+username.getText()+"/"+password.getText()+"/ ";
        String msg = "signin/"+name+"/"+pass;
        client.dos.writeUTF(msg);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText("");
        password.setText("");
    }
    public void setClient(ClientManage client){
        this.client=client;
    }

    public void goToLogIn(MouseEvent mouseEvent) {

    }
}
