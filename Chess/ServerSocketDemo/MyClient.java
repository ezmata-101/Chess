package Networking.SocketProgramming_Two;

import java.io.*;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("192.168.0.102", 12333);
        System.out.println(s.getInetAddress());
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str2 = "";
        str2 = din.readUTF();
        System.out.println(str2);

        ClientReceiver clientReceiver = new ClientReceiver(din);
        ClientSender clientSender = new ClientSender(dout);
        clientReceiver.t.start();
        clientSender.t.start();

//        dout.close();
//        s.close();
    }
}