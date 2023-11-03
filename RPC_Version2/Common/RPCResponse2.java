package RPC_Version2.Common;

import java.io.Serializable;

/**
 *  This class is an abstraction of RPC Response
 *  - Server sends an RPC Response Object to Client
 *  - Server needs to send:
 *      Result object,
 */

public class RPCResponse2 implements Serializable {
    private Object data;
    private int code;
    private String message;

    public RPCResponse2(Object data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public static RPCResponse2 success(Object data) {
        return new RPCResponse2(data, 200, "RPCResponse Success");
    }

    public static RPCResponse2 fail() {
        return new RPCResponse2(null, 500, "RPCResponse Fail");
    }
}

