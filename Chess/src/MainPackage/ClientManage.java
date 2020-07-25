package MainPackage;

import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientManage{

    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    Stage stage;

    ClientManage(){}

    public boolean start(){
        try{
            socket =  new Socket("localhost", 5000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            ClientThread thread=new ClientThread(dis);
            thread.setStage(stage);
            thread.t.start();
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
}
