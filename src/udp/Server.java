package udp;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        // write your code here
        // OPEN AN UDP SOCKET
        int portNumber = 8081;
        String hostName = "localhost"; // 127.0.0.1
        DatagramSocket socket = new DatagramSocket(portNumber);

        // CREATE A DATAGRAM PACKET AND RECEIVE DATA FROM THE THE SOCKET
        byte[] recvBuffer = new byte[1024];

        while (socket.isBound()) {
            DatagramPacket receivedPacket = new DatagramPacket(recvBuffer, recvBuffer.length);

            // RECEIVE PACKET/MESSAGE FROM CLIENT
            System.out.println("Waiting for packet...");
            socket.receive(receivedPacket); // blocking method!
            String receivedString = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println("Received: " + receivedString);

            // SENDER ADDRESS, PORT AND MESSAGE
            InetAddress address = receivedPacket.getAddress();
            System.out.println("address = " + address);
            int port = receivedPacket.getPort();
            System.out.println("port = " + port);
            String separator = "-";
            System.out.println(separator.repeat(25));

            // SEND A PACKET/MESSAGE TO THE CLIENT
            String response;
            if (receivedString.equals("hit me")) {
                RandomAccessFile source = new RandomAccessFile("resources/quotes.txt", "r");
                source.length();
                source.seek((long) (Math.random() * source.length()));
                source.readLine();
                if (source.readLine() == null) {
                    response = "ups, please try again";
                } else {
                    response = source.readLine();
                }

            } else {
                response = "unsupported operation".toUpperCase();
            }

            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, port);
            socket.send(responsePacket);

        }

        // CLOSE THE SOCKET
        socket.close();

    }
}