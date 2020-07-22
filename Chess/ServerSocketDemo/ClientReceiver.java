package Networking.SocketProgramming_Two;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientReceiver implements Runnable{
    Thread t;
    DataInputStream dataInputStream;
    ClientReceiver(DataInputStream dis) {
        dataInputStream = dis;
        t = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println("clientReceiverStarted...");
        while(true){
            try {
                String string = dataInputStream.readUTF();
                System.out.println(string);
                if(string.equals("close")){
                    dataInputStream.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
