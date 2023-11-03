package RPC_Version0.Client;

import RPC_Version0.Common.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 *  This class is RPC Client
 *  - Client calls functions defined in this interface
 *  - Version 0 implements passing id ONLY (parameter)
 *  - Usually, RPC should pass the class name, method name, and method parameters
 */

public class RPCClient {
    public static void main(String[] args) {
        try {
            // Create Socket for establishing connection with server side
            // Socket specifies the IP address of server and the port it listening for connections
            Socket socket = new Socket("127.0.0.1", 8080);
            System.out.println("Client attempts to connect the server");

            // Obtain the I/O stream from socket to send and receive data (Object in Version 0)
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Client side sends id to server side
            oos.writeInt(new Random().nextInt());
            oos.flush();

            // Client side receives result from user side
            Course course = (Course) ois.readObject();
            System.out.println(course);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client Failed");
            e.printStackTrace();
        }
    }
}
