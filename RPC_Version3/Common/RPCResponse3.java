package RPC_Version3.Common;

import RPC_Version2.Common.RPCResponse2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *  This class is an abstraction of RPC Response
 *  - Server sends an RPC Response Object to Client
 *  - Server needs to send:
 *      Result object
 */

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class RPCResponse3 implements Serializable {
    private Object data;
    private int code;
    private String message;

    public static RPCResponse3 success(Object data) {
        return new RPCResponse3(data, 200, "RPCResponse Success");
    }

    public static RPCResponse3 fail() {
        return new RPCResponse3(null, 500, "RPCResponse Fail");
    }
}
