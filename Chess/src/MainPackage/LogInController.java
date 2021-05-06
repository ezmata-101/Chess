package MainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Text createText;
    ClientManage client;

    public void LogInButtonPushed(ActionEvent event) throws IOException {
        String name = username.getText();
        String pass = password.getText();
        password.clear();
        boolean flag = false;
        if(name.equals("")){
            username.clear();
            username.setPromptText("Enter a user name");
            username.setStyle("-fx-fill: brown; "+
                    "-fx-text-fill: brown");
            username.setStyle("-fx-border-color: brown");
            flag = true;
        }
        if(pass.equals("") || pass.length() < 8){
            password.setPromptText("Invalid Pass.");
            password.setStyle("-fx-text-fill: brown; "+"" +
                    "-fx-border-color: brown");
            flag = true;
        }
        if(flag) return;

        String msg="login/"+name+"/"+pass;
        client.dos.writeUTF(msg);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ds.open();
        username.setText("");
        username.setPromptText("User Name");
        password.setText("");
        password.setPromptText("Password");
        createText.setStyle("-fx-background-color: darkblue;"+
                "-fx-font-size: 15;"+
                "-fx-fill: darkblue;"+
                "-fx-alignment: center");

    }
    public void setClient(ClientManage client){
        this.client=client;
    }

    public void moveToSignUp(MouseEvent mouseEvent) {
        System.out.println("Move to Sign Up!");
    }

    public void loginAsGuest(ActionEvent actionEvent) throws IOException {
        client.dos.writeUTF("guest_login");
    }
}