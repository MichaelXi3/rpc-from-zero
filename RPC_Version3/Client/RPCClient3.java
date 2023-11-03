package RPC_Version3.Client;

import RPC_Version3.Common.RPCRequest3;
import RPC_Version3.Common.RPCResponse3;

import java.io.IOException;

/**
 * This class is an interface for the RPC client side
 * - client side need to send RPCRequest and receive a RPCResponse
 * - different implementation can send request in different mechanisms
 */

public interface RPCClient3 {
    RPCResponse3 sendRequest(RPCRequest3 rpcRequest) throws IOException;
}
