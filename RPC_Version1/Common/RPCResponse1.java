package RPC_Version1.Common;

import java.io.Serializable;

/**
 *  This class is an abstraction of RPC Response
 *  - Server sends an RPC Response Object to Client
 *  - Server needs to send:
 *      Result object,
 *
 */

public class RPCResponse1 implements Serializable {
    private Object data;
    private int code;
    private String message;

    public RPCResponse1(Object data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public static RPCResponse1 success(Object data) {
        return new RPCResponse1(data, 200, "RPCResponse Success");
    }

    public static RPCResponse1 fail() {
        return new RPCResponse1(null, 500, "RPCResponse Fail");
    }
}
