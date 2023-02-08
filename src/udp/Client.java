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

        // CREATE A DATAGRAM PACKET AND SEND IT FROM THE SOCKET
        byte[] message = "Hello World this is another message".getBytes();

        //reads a string from console
        String input = "";
        while (!input.equals("exit")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a message to send to the server: ");

            // RECEIVE USER INPUT TO SEND MESSAGE
            input = br.readLine();
            if (input.equals("exit")) break;
            message = input.getBytes();

            // SEND A PACKET/MESSAGE TO THE SERVER
            DatagramPacket sendPacket = new DatagramPacket(message, message.length, InetAddress.getByName(hostName), portNumber);
            socket.send(sendPacket);
            System.out.println("Message sent to the server: " + input);

            // RECEIVE A PACKET/MESSAGE FROM THE SERVER
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket);
            String receivedString = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(receivedString);
        }
    }
}