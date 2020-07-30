package MainPackage;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
//import org.omg.PortableInterceptor.INACTIVE;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class ClientManage implements Runnable{

    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    Stage stage;
    Thread t;
    Game game;
    String name;
    String address;

    ClientManage(String address){
        this.address=address;
    }

    public boolean start(){
        try{
            socket =  new Socket(address, 5000);
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
    private void handleMessage(String message) {
        String[] strings=message.split("/");
        switch (strings[0]) {
            case "signin":
                if (strings[1].equals("successfull")) {
                    name = strings[2];
                    initGame();
                } else {
                    if (strings[1].equals("user_already_exist")) createAlert("This UserName Already Used!");
                    else createAlert("SignIn Unsuccessful!");
                }
                break;

            case "login":
                if (strings[1].equals("successful")) {
                    name = strings[2];
                    initGame();
                } else {
                    if (strings[1].equals("invalid_pass")) createAlert("Invalid Password!");
                    if (strings[1].equals("User_Already_Online")) createAlert("You are Already Online!");
                    else createAlert("Invalid Username or Password!");
                }
                break;

            case "CREATE_GAME":
                if (strings[1].equals("SUCCESS")) {
//                game.setCreateGameCode(strings[2]);
                    Platform.runLater(() -> game.setCreateGameCode(strings[2]));
                }
                break;

            case "JOIN_GAME":
                if (strings[1].equals("SUCCESS")) {
                    Platform.runLater(() -> {
                        game.createNewGame(strings[2].equals("WHITE"), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]));
                    });
                } else {
                    Platform.runLater(() -> game.setJoinGameNotification(strings[1]));
                }
                break;
            case "GAME":
                if(strings[3].equals(name)) return;
                Platform.runLater(() -> {
                    try {
                        //game.selectItem(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
                        game.moveItem(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        }
    }

    private void initGame() {
        Platform.runLater(
            ()->{
                stage.close();
                game = new Game(this);
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
    GamePaneController gamePaneController;
    public void setGamePaneController() {
        this.gamePaneController = gamePaneController;
    }
    public String getName(){
        return name;
    }
}
