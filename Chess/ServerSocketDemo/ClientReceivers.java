package Networking.SocketProgramming_Two;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientReceivers implements Runnable{
    Thread t;
    DataInputStream dataInputStream;

    ClientReceivers(DataInputStream dataInputStream){
        this.dataInputStream = dataInputStream;
        t = new Thread(this);
    }

    @Override
    public void run() {
        while(true){
            try {
                String string = dataInputStream.readUTF();
                System.out.println(string);
                String[] strings = string.split(",");
                try{
                    int receiver = Integer.parseInt(strings[0]);
                    ClientManager.sendTo(strings[1], receiver);
                }catch (NumberFormatException e){

                }
            } catch (IOException e) {
                System.out.println("Akash Eto Meghla...");
                e.printStackTrace();
                break;
            }
        }
    }
}
