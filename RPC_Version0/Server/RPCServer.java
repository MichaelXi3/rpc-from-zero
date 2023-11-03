package RPC_Version0.Server;

import RPC_Version0.Common.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  This class is RPC Server
 *  - Server listening to the client side socket using BIO
 *  - Once request is received, server will call corresponding method and return the result to client
 */

public class RPCServer {
    public static void main(String[] args) {
        RPC_Version0.Server.CourseServiceImp courseServices = new RPC_Version0.Server.CourseServiceImp();
        try {
            // Server side begins to listen on port 8080
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server is listening on port 8080");

            // Server side waits for connections
            while (true) {
                // Once connection is established, accept method returns a socket object to represent the connection to the client
                Socket socket = serverSocket.accept();
                System.out.println("Server: a connection has been established");
                // Open a thread to handle the new request
                new Thread(() -> {
                    try {
                        // Establish input and output streams for communication
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                        // Get the id argument passed from client
                        Integer id = ois.readInt();
                        Course course = courseServices.getCourseById(id);

                        // Send the result of query back to client
                        oos.writeObject(course);
                        oos.flush();
                    } catch (IOException e) {
                        System.out.println("Server IO Error");
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Server Failed");
            e.printStackTrace();
        }
    }
}
