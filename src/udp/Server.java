package udp;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        final int portNumber = 8081;
        DatagramSocket socket = new DatagramSocket(portNumber);

        byte[] receiveBuffer = new byte[1024];

        DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

        while (socket.isBound()) {
            System.out.println("Waiting for packet...");
            socket.receive(receivedPacket); // blocking method!
            String receivedString = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println("Received: " + receivedString);

            InetAddress address = receivedPacket.getAddress();
            System.out.println("address = " + address);
            int port = receivedPacket.getPort();
            System.out.println("port = " + port);
            System.out.println(".".repeat(25));

            String response;
            if (receivedString.equals("HIT ME")) {
                response = getRandomLine(new File("").getAbsolutePath() + "/resources/quotes.txt");
            } else {
                response = "UNSUPPORTED OPERATION";
            }
            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, port);
            socket.send(responsePacket);
            /*
            if (receivedString.equals("hit me")) {
                RandomAccessFile source = new RandomAccessFile("resources/quotes.txt", "r");
                source.length();
                source.seek((long) (Math.random() * source.length()));
                response = source.readLine();
            } else {
                response = "unsupported operation".toUpperCase());
            }
             */
        }
        socket.close();
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