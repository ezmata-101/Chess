package Networking.SocketProgramming_Two;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    DataOutputStream[] dataOutputStreams = new DataOutputStream[100];


    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(12333);
        System.out.println("Server Listing on Port: 12333");
        System.out.println(ss.getInetAddress());
        int totalClients = 0;

        while (true) {
            Socket s = ss.accept();
            System.out.println(s.getRemoteSocketAddress()+" "+s.getLocalSocketAddress());
            ClientManager.setAt(s, totalClients);
            totalClients++;

            System.out.println("New Client Added");
            System.out.println(s.getRemoteSocketAddress());
            DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
            dataOutputStream.writeUTF("Client/" + totalClients);

//            System.out.println(s);
//            InputStream in = s.getInputStream();
//            OutputStream out = s.getOutputStream();
//            System.out.println(in);
//            System.out.println(out);
//            DataInputStream din = new DataInputStream(in);
//            DataOutputStream dout = new DataOutputStream(out);
//            System.out.println(din);
//            System.out.println(dout);

//            dout.writeUTF("Server Pathaise, Kemne pathaise janina...");
//            String string = din.readUTF();
//            System.out.println(string);
        }

    }
}
