package RPC_Version3.Client;

import RPC_Version3.Common.RPCRequest3;
import RPC_Version3.Common.RPCResponse3;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class RPCClientProxy3 implements InvocationHandler {
    private RPCClient3 client;

    // JDK dynamic proxy: for every invocation, the method will call this invokes function and get additional functionalities
    // i.e. user side call remote function without many considerations. However, the proxy helps user to first encapsulate
    // the request into a RPCRequest object, then the proxy send this RPCRequest object to the server side using  certain type
    // of RPCClient implementation, then the RPCResponse object is returned from the server side
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // analyze client request
        String interfaceName = method.getDeclaringClass().getName();
        String methodName = method.getName();

        // create RPC Request
        RPCRequest3 request = RPCRequest3.builder()
                .interfaceName(interfaceName)
                .methodName(methodName)
                .methodParameters(args)
                .parameterTypes(method.getParameterTypes())
                .build();

        System.out.println("#########################################################################");
        System.out.println("[Client Proxy] Invoking method: " + methodName + " ; " + "Interface: " + interfaceName);
        RPCResponse3 response = client.sendRequest(request);
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