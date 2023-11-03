package RPC_Version2.Common;

import java.io.Serializable;

/**
 *  This class is an abstraction of RPC Request
 *  - Client use send RPC Request Object to Server for communication
 *  - Client needs to send:
 *      Service Interface name,
 *      Method name,
 *      Method Parameters,
 *      Parameters Type
 */

public class RPCRequest2 implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] methodParameters;
    // Array of class objects with unknown type
    private Class<?>[] parameterTypes;

    public RPCRequest2(String interfaceName, String methodName, Object[] methodParameters, Class<?>[] parameterTypes) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.methodParameters = methodParameters;
        this.parameterTypes = parameterTypes;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String getInterfaceName() {
        return this.interfaceName;
    }

    public Object[] getMethodParameters() {
        return this.methodParameters;
    }

    public Class<?>[] getParameterTypes() {
        return  this.parameterTypes;
    }
}
