package MainPackage;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientNetwork {
    ClientNetwork(){}
    private DatagramSocket ds;
    String address;

    public boolean init(){
        try {
            ds = new DatagramSocket();
            ds.setBroadcast(true);
            byte[] sendData = "REQUEST".getBytes();
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 5000);
                ds.send(sendPacket);
                System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            } catch (Exception e) {
                return false;
            }
            /*Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }
                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                        ds.send(sendPacket);
                    } catch (Exception e) {

                    }
                    System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                }
            }*/
            //System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            ds.receive(receivePacket);
            System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
            String message = new String(receivePacket.getData()).trim();
            if (message.equals("RESPONSE")) {
                this.address=receivePacket.getAddress().getHostAddress();
                return true;
            }
            ds.close();
        } catch (IOException ex) {
            return false;
        }
        return false;
    }
}
