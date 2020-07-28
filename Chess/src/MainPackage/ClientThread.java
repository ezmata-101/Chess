package MainPackage;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientThread implements Runnable{
    Thread t;
    DataInputStream dis;
    Stage stage;
    boolean signIn=false;
    Game game;

    public ClientThread(DataInputStream dis){
        this.dis=dis;
        t=new Thread(this);
    }
    public ClientThread(){}

    @Override
    public void run() {
        while(true){
            try {
                String msg=dis.readUTF();
                System.out.println("Message received: "+msg);
                handleMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleMessage(String message) throws IOException {
        String[] strings=message.split("/");
        if(strings[0].equals("signin")){
            if(strings[1].equals("successfull")){
            /*FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXMLS/SignIn.fxml"));
            Parent root=loader.load();
            SignInController signin=loader.getController();
            Stage stage=(Stage)signin.username.getScene().getWindow();*/

                Platform.runLater(
                        ()->{
                            stage.close();
//                            game = new Game();
//                            game.init();
                        }
                );
            }
            else if(strings[1].equals("user_already_exist")){
                Platform.runLater(
                        ()->{
                            Alert a = new Alert(Alert.AlertType.NONE);
                            a.setAlertType(Alert.AlertType.WARNING);
                            a.setContentText("This Username Already Exist.");
                            a.show();
                        }
                );
            }
            else if(strings[1].equals("unsuccessfull")){
                Platform.runLater(
                        ()->{
                            Alert a = new Alert(Alert.AlertType.NONE);
                            a.setAlertType(Alert.AlertType.WARNING);
                            a.setContentText("SignIn Unsuccessfull");
                            a.show();
                        }
                );
            }
        }

        else if(strings[0].equals("login")){
            if(strings[1].equals("successful")){
                Platform.runLater(
                        ()->{
                            stage.close();
//                            game = new Game();
//                            game.init();
                        }
                );
            }
            else if(strings[1].equals("invalid_pass")){
                Platform.runLater(
                        ()->{
                            Alert a = new Alert(Alert.AlertType.NONE);
                            a.setAlertType(Alert.AlertType.WARNING);
                            a.setContentText("Invalid Password.");
                            a.show();
                        }
                );
            }
            else if(strings[1].equals("unsuccessful")){
                Platform.runLater(
                        ()->{
                            Alert a = new Alert(Alert.AlertType.NONE);
                            a.setAlertType(Alert.AlertType.WARNING);
                            a.setContentText("Invalid Username or Password.");
                            a.show();
                        }
                );
            }
        }
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void setSignIn(boolean b){
        signIn=b;
    }
    public boolean getSignIn(){
        return signIn;
    }
}
