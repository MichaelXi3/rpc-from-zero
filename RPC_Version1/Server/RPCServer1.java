package RPC_Version1.Server;

import RPC_Version1.Common.Course1;
import RPC_Version1.Common.RPCRequest1;
import RPC_Version1.Common.RPCResponse1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  This class is RPC Server
 *  - Server listening to the client side socket using BIO
 *  - Once request is received, server will call corresponding method and return the result to client
 */

public class RPCServer1 {
    public static void main(String[] args) {
        CourseServiceImp1 courseServices = new CourseServiceImp1();
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

                        // Get the RPCRequest object from client
                        RPCRequest1 rpcRequest = (RPCRequest1) ois.readObject(); // contains method name, para, paraTypes, interface name

                        // Use reflection to call the requested method - implementation is only available at server side
                        Method method = courseServices.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                        Object result = method.invoke(courseServices, rpcRequest.getMethodParameters());

                        // Send the result of query back to client
                        oos.writeObject(RPCResponse1.success(result));
                        oos.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        System.out.println("Server Thread Error");
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Server Launch Failed");
            e.printStackTrace();
        }
    }
}
