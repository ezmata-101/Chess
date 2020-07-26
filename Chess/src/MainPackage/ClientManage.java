package MainPackage;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientManage implements Runnable{

    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    Stage stage;
    Thread t;

    ClientManage(){}

    public boolean start(){
        try{
            socket =  new Socket("localhost", 5000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
//            ClientThread thread=new ClientThread(dis);
//            thread.setStage(stage);
//            thread.t.start();
            t = new Thread(this, "Client Manage");
            t.start();
            return true;
        }catch (Exception e){
            System.out.println("Failed to connect to the Server!");
            e.printStackTrace();
            return false;
        }
    }

    private boolean stop() {
        try{
            dos.close();
            dis.close();
            socket.close();
        }catch (Exception e){
            System.out.println("Failed to Close Client Thread!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }

    public boolean sendToServer(String message){
        try {
            dos.writeUTF(message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                String msg=dis.readUTF();
                System.out.println("Message received: "+msg);
                handleMessage(msg);
            } catch (IOException e) {
                System.out.println("Server Closed!");
                break;
            }
        }
    }
    private void handleMessage(String message) throws IOException {
        String[] strings=message.split("/");
        if(strings[0].equals("signin")){
            if(strings[1].equals("successfull")){
                initGame();
            }
            else{
                if(strings[1].equals("user_already_exist"))createAlert("This UserName Already Exists!");
                else createAlert("SignIn Unsuccessful!");
            }
        }

        else if(strings[0].equals("login")){
            if(strings[1].equals("successful")){
                initGame();
            }
            else {
                if(strings[1].equals("invalid_pass"))createAlert("Invalid Password!");
                else createAlert("Invalid Username or Password!");
            }
        }
    }

    private void initGame() {
        Platform.runLater(
                ()->{
                    stage.close();
                    Game game = new Game();
                    game.init();
                }
        );
    }
    private void createAlert(String message){
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(message);
            a.show();
        });
    }
}
