package RPC_Version2.Client;

import RPC_Version2.Common.RPCRequest2;
import RPC_Version2.Common.RPCResponse2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  This class is a dynamic proxy for client
 *  - dynamic proxy is used for handling various interfaces at runtime
 */

public class ClientProxy2 implements InvocationHandler {
    private String ipAddress;
    private int portNumber;

    public ClientProxy2(String ip, int port) {
        this.ipAddress = ip;
        this.portNumber = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // For every invocation through proxy, a rpc request is created and a socket request is sent to server
        RPCRequest2 rpcRequest = new RPCRequest2(
                method.getDeclaringClass().getName(), // interface name
                method.getName(),                     // method name
                args,                                 // method arguments
                method.getParameterTypes());          // method arguments types
        // RPC response is received through the socket communication
        RPCResponse2 response = ClientRequest2.sendRequest(ipAddress, portNumber, rpcRequest);
        return response.getData();
    }

    /**
     * Used to create a proxy instance of an interface
     * - classLoader: load the class
     * - interface classes: interfaces implemented by the proxied class
     * - invocation handler object: for customized proxy invocation logic
     *      - in the invoke method, we need to class the class methods that implement the interface
     */

    <T> T getProxy(Class<T> proxyClass) {
        Object o = Proxy.newProxyInstance(
                proxyClass.getClassLoader(),  // interface ClassLoader,
                new Class<?>[]{proxyClass},   // Class<?>[] interfaces,
                this                       // invocation handler
        );
        return (T) o;
    }
}