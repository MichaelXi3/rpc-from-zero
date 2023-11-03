package RPC_Version2.Server;

import RPC_Version2.Services.ServiceProvider2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 *  This class is a simple implementation of RPC Server
 *  - a work thread is created ONLY after the new socket connection is established
 */

public class RPCServerSimple2 implements RPCServerInterface2 {
    // interface (service) name -> service object
    private ServiceProvider2 serviceProvider;

    public RPCServerSimple2(ServiceProvider2 serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            // create a server socket
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port 8080");
            // server socket listening
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Server: a connection has been established");
                new Thread(new WorkThread2(socket, serviceProvider));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server start failed");
        }
    }

    @Override
    public void end() {

    }
}
