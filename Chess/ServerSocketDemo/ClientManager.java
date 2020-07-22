package Networking.SocketProgramming_Two;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientManager {

    static DataOutputStream[] dataOutputStreams = new DataOutputStream[100];
    static DataInputStream[] dataInputStreams = new DataInputStream[100];
    static ClientReceivers[] clientReceivers = new ClientReceivers[100];

    static void setAt(Socket socket, int at) throws IOException {
        dataInputStreams[at] = new DataInputStream(socket.getInputStream());
        dataOutputStreams[at] = new DataOutputStream(socket.getOutputStream());
        clientReceivers[at] = new ClientReceivers(dataInputStreams[at]);
        clientReceivers[at].t.start();
    }
    static void sendTo(String message, int to) throws IOException {
        dataOutputStreams[to-1].writeUTF(message);
    }
}
