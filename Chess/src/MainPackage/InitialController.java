package MainPackage;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialController implements Initializable {
    @FXML
    AnchorPane anchorPane;
    @FXML
    Pane loginPane, signInPane;
    @FXML
    TextField signInName, loginName;
    @FXML
    PasswordField signInPass, loginPass;
    @FXML
    Text goToLoginText, gotoSignInText;
    @FXML
    Button signInButton, loginButton;

    ClientManage client;
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInPane.setVisible(false);
        signInPane.setDisable(true);
//        signInPane.setLayoutX(-signInPane.getWidth());
        signInName.setPromptText("User Name");
        loginName.setPromptText("User Name");
        loginPass.setPromptText("Password");
        signInPass.setPromptText("Password");

        gotoSignInText.setStyle("-fx-text-fill: crimson;"+
                "-fx-font-size: 16;"+
                "-fx-fill: crimson");
        goToLoginText.setStyle("-fx-text-fill: darkblue;"+
                "-fx-font-size: 16;"+
                "-fx-fill: darkblue");
    }

    public void onSignIn(ActionEvent actionEvent) {
        String name = signInName.getText();
        String pass = signInPass.getText();
        signInPass.clear();
        boolean flag = false;
        if(name.equals("")){
            signInName.clear();
            signInName.setPromptText("Invalid User Name");
            signInName.setStyle("-fx-text-fill: crimson;" +
                    "-fx-border-color: crimson");
            flag = true;
        }
        if(pass.equals("") || pass.length() < 8){
            signInPass.setPromptText("Minimum Length: 8");
            signInPass.setStyle("-fx-border-color: crimson;"+
                    "-fx-text-fill: crimson;");
            flag = true;
        }
        if(flag) return;
        client.sendToServer("signin/"+name+"/"+pass);
    }

    public void onLogin(ActionEvent actionEvent) {
        String name = loginName.getText();
        String pass = loginPass.getText();
        loginPass.clear();
        boolean flag = false;
        if(name.equals("")){
            loginName.clear();
            loginName.setPromptText("Enter a user name");
            loginName.setStyle("-fx-fill: crimson; "+
                    "-fx-text-fill: crimson");
            loginName.setStyle("-fx-border-color: crimson");
            flag = true;
        }
        if(pass.equals("") || pass.length() < 8){
            loginPass.setPromptText("Invalid Pass.");
            loginPass.setStyle("-fx-text-fill: crimson; "+"" +
                    "-fx-border-color: crimson");
            flag = true;
        }
        if(flag) return;
        client.sendToServer("login/"+name+"/"+pass);
    }

    public void gotoLogin(MouseEvent mouseEvent) {
        doAnimation(loginPane, signInPane, -1);
    }

    public void gotoSignIn(MouseEvent mouseEvent) {
        doAnimation(signInPane, loginPane, 1);
    }
    private void doAnimation(Pane p1, Pane p2, int p){
        signInName.clear();
        signInPass.clear();
        loginPass.clear();
        loginName.clear();

        int b = (int) p1.getWidth();
        TranslateTransition tt = new TranslateTransition();
        tt.setAutoReverse(false);
        tt.setFromX(-b * p);
        tt.setToX(0);
        tt.setDuration(Duration.millis(750));
        tt.setNode(p1);

        TranslateTransition tt2 = new TranslateTransition();
        tt2.setAutoReverse(false);
        tt2.setFromX(0);
        tt2.setToX(b * p);
        tt2.setDuration(Duration.millis(750));
        tt2.setNode(p2);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(tt, tt2);
        pt.play();

        p1.setVisible(true);
        p1.setDisable(false);
    }
    public void setClient(ClientManage client){
        this.client = client;
    }
}
