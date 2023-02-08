package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        final int portNumber = 8081;
        String hostName = "localhost"; // 127.0.0.1
        DatagramSocket socket = new DatagramSocket();

        while (true) {
            System.out.println("Enter a message to send to the server: [Press \"HIT ME\" to continue or \"exit\" to quit]");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            if (input.equals("exit")) break;
            byte[] message = input.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(message, message.length, InetAddress.getByName(hostName), portNumber);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket);
            String receivedString = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(receivedString);
        }
        socket.close();
    }
}