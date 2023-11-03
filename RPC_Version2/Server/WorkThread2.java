package RPC_Version2.Server;

import RPC_Version2.Common.RPCRequest2;
import RPC_Version2.Common.RPCResponse2;
import RPC_Version2.Services.ServiceProvider2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class WorkThread2 implements Runnable {
    // current socket connection between client and server
    private Socket socket;
    // key is interface (service) name, value is the serviceImp object
    private ServiceProvider2 serviceProvide;

    public WorkThread2(Socket socket, ServiceProvider2 serviceProvide) {
        this.socket = socket;
        this.serviceProvide = serviceProvide;
    }

    @Override
    public void run() {
        try {
            // Establish input and output streams for communication
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Get the RPCRequest object from client
            RPCRequest2 rpcRequest = (RPCRequest2) ois.readObject(); // contains method name, para, paraTypes, interface name

            // Use reflection to call the requested method - implementation is only available at server side
            RPCResponse2 rpcResponse = getResponse(rpcRequest);

            // Send the result of query back to client
            oos.writeObject(rpcResponse);
            oos.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Server Thread Error");
            e.printStackTrace();
        }
    }

    private RPCResponse2 getResponse(RPCRequest2 request) {
        // client side is looking for executing methods specified in interfaceName within RPCRequest
        String interfaceName = request.getInterfaceName();
        // server side receives the request and locate the corresponding class object
        Object service = serviceProvide.getService(interfaceName);

        // use reflection to call the service method and get return value
        try {
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
            Object result = method.invoke(service, request.getMethodParameters());
            return RPCResponse2.success(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("Failed to call method at server side");
            return RPCResponse2.fail();
        }
    }
}
