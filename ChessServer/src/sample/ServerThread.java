package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable{
    Socket s;
    Thread t;
    DatabaseUserManage ds;
    MessageHandler handler;
    DataInputStream dis;
    DataOutputStream dos;
    private Client client;//Not used may be...
    int indexId;
    boolean isOnline;

    public ServerThread(Socket s,MessageHandler handler){
        isOnline = false;
        this.s=s;
        t=new Thread(this);
        this.handler=handler;
        try {
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to produce dis,dos in Server.");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                String msg=dis.readUTF();
                System.out.println("Message is: "+msg);
                String[] strings=msg.split("/");
                if(strings[0].equals("signin")){
                    int i=handler.handleSignIn(msg);
                    if(i==1){
                        dos.writeUTF("signin/successfull/"+strings[1]);
                        handler.addClient(strings[1], this);
                    }
                    else if(i==0){
                        dos.writeUTF("signin/unsuccessfull");
                    }
                    else if(i==2){
                        dos.writeUTF("signin/user_already_exist");
                    }
                }
                else if(strings[0].equals("login")){
                    int i=handler.handleLogIn(msg);
                    if(i==1){
                        if(handler.isOnline(strings[1])){
                            dos.writeUTF("signin/User_Already_Online");
                        }
                        else{
                            dos.writeUTF("login/successful/"+strings[1]);
                            handler.addClient(strings[1], this);
                        }
                    }
                    else if(i==0){
                        dos.writeUTF("login/invalid_pass");
                    }
                    else if(i==2){
                        dos.writeUTF("login/unsuccessful");
                    }
                }
                else if(strings[0].equals("CREATE_NEW_GAME")){
                    handler.createNewGame(client, strings[1]);
                }
                else if(strings[0].equals("JOIN_GAME")){
                    handler.joinGame(client, strings[1]);
                }
                else if(strings[0].equals("GAME")){
                    client.gameMovement(msg);
                }else if(strings[0].equals("guest_login")){
                    handler.addClient("GUEST", this);
                }else if(strings[0].equals("turnMove")){
                    client.gameMovement(msg);
                }
            }
        } catch (IOException e) {
            System.out.println("Player Left!");
            handler.leaveGame(client);
            if(client != null){
                client.setOnline(false);
                client.setInGame(false);
            }
            //e.printStackTrace();
        }
        finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void gameControl(String message){
        handler.handleWinLose(message);
    }
    public void updateRequest(String name){
        handler.handleTotalMatch(name);
    }

    public void sendToClient(String message) {
        try {
            dos.writeUTF(message);
            System.out.println("Sent: "+message);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void start() {
        isOnline = true;
        t.start();
    }
}
