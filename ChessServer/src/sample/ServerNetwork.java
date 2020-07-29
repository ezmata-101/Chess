package sample;

import java.io.IOException;
import java.net.*;

public class ServerNetwork implements Runnable{
    Thread t;
    DatagramSocket socket;
    private boolean isClientConnected;
    ServerNetwork(){
        t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(5000);
            socket.setBroadcast(true);
            while (true) {
                System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
                System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));
                String message = new String(packet.getData()).trim();
                if (message.equals("REQUEST")) {
                    byte[] sendData = "RESPONSE".getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                    socket.send(sendPacket);
                    System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
                }
            }
        } catch (IOException e) {

        }
    }
}
