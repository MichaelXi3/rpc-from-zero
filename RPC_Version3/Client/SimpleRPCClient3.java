package RPC_Version3.Client;

import RPC_Version3.Common.RPCRequest3;
import RPC_Version3.Common.RPCResponse3;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This is an implementation of BIO RPCClient side by Socket
 */

@AllArgsConstructor
public class SimpleRPCClient3 implements RPCClient3 {
    private String host;
    private int port;

    @Override
    public RPCResponse3 sendRequest(RPCRequest3 rpcRequest) {
        try {
            // set up a socket connection to a specific server by ip + port
            Socket socket = new Socket(host, port);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeObject(rpcRequest);
            oos.flush();

            RPCResponse3 response = (RPCResponse3) ois.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }
}
