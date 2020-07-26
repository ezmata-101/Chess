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

    public ServerThread(Socket s,MessageHandler handler){
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
                        dos.writeUTF("signin/successfull");
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
                        dos.writeUTF("login/successful");
                    }
                    else if(i==0){
                        dos.writeUTF("login/invalid_pass");
                    }
                    else if(i==2){
                        dos.writeUTF("login/unsuccessful");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Player Left!");

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
}
