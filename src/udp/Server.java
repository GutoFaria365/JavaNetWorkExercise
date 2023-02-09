package udp;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Server {
    public static void main(String[] args) throws IOException {
        // write your code here
        // OPEN AN UDP SOCKET
       final int portNumber = 8081;
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
            System.out.println("-".repeat(25));

            // SEND A PACKET/MESSAGE TO THE CLIENT
            String response;
            response = getResponse(receivedString);
            //response = getResponseAlpha(receivedString);

            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, port);
            socket.send(responsePacket);

        }

        // CLOSE THE SOCKET
        socket.close();

    }

    private static String getResponse(String receivedString) {
        String response;
        if (receivedString.equals("HIT ME")) {
            response = getRandomLine(new File("").getAbsolutePath() + "/resources/quotes.txt");
        } else {
            response = "UNSUPPORTED OPERATION";
        }
        return response;
    }

    private static String getResponseAlpha(String receivedString) throws IOException {
        String response;
        if (receivedString.equals("HIT ME")) {
            RandomAccessFile source = new RandomAccessFile("resources/quotes.txt", "r");
            source.length();
            source.seek((long) (Math.random() * source.length()));
            source.readLine();
            response = source.readLine();
        } else {
            response = "unsupported operation".toUpperCase();
        }
        if (response == null){
            response = "something went wrong, please try again";
        }
        return response;
    }

    private static String getRandomLine(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lines.get(new Random().nextInt(lines.size()));
    }
}