package MainPackage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button signUp;
    @FXML private Button logIn;

    public void signUpButtonPushed(ActionEvent event) throws IOException {
        Stage stage=(Stage)this.signUp.getScene().getWindow();
        stage.close();
        ClientManage client=new ClientManage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/SignIn.fxml"));
        Parent root=loader.load();
        SignInController signin=loader.getController();
        signin.setClient(client);
        Stage primaryStage=new Stage();
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root));
        client.setStage(primaryStage);
        if(client.start()){
            primaryStage.show();
            System.out.println("New Client in Process.");
        }
        else {
            System.out.println("Failed to create a client");
        }
    }

    public void logInButtonPushed(ActionEvent event) throws IOException {
        Stage stage=(Stage)this.logIn.getScene().getWindow();
        stage.close();
        ClientManage client=new ClientManage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/LogIn.fxml"));
        Parent root=loader.load();
        LogInController login=loader.getController();
        login.setClient(client);
        Stage primaryStage=new Stage();
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root));
        client.setStage(primaryStage);
        if(client.start()){
            System.out.println("Previous Client in Process.");
            primaryStage.show();
        }
        else {
            System.out.println("Failed to connect to a previous client");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
