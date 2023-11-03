package RPC_Version2.Client;

import RPC_Version2.Common.RPCRequest2;
import RPC_Version2.Common.RPCResponse2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *  This class encapsulates the details of client side socket connection
 *  @Return the response from server as RPC Response object
 */

public class ClientRequest2 {
    public static RPCResponse2 sendRequest(String host, int port, RPCRequest2 rpcRequest) {
        try {
            // Create Socket for establishing connection with server side
            // Socket specifies the IP address of server and the port it listening for connections
            Socket socket = new Socket(host, port);
            System.out.println("Client attempts to connect the server");

            // Obtain the I/O stream from socket to send and receive data
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Client side sends RPC Request to server side
            oos.writeObject(rpcRequest);
            oos.flush();

            // Client side receives result from user side
            RPCResponse2 rpcResponse = (RPCResponse2) ois.readObject();
            return rpcResponse;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client Failed");
            e.printStackTrace();
            return null;
        }
    }
}
