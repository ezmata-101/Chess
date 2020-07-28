package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(5000);
        MessageHandler handler=new MessageHandler();
        while(true){
            Socket s=ss.accept();
//            ServerThread thread=new ServerThread(s,handler);
            handler.handleTemporarily(s);
//            thread.t.start();
        }
    }
}
