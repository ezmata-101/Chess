package Networking.SocketProgramming_Two;

import javax.xml.crypto.Data;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientSender implements Runnable{
    Thread t;
    DataOutputStream dataOutputStream;
    Scanner sc = new Scanner(System.in);
    int clientID;
    ClientSender(DataOutputStream dataOutputStream){
        this.dataOutputStream = dataOutputStream;
        t = new Thread(this);
    }

    @Override
    public void run() {
        while(true){
            String string;
            string = sc.nextLine();
            try {
                dataOutputStream.writeUTF(string);
                if(string.equals("close")){
                    dataOutputStream.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
