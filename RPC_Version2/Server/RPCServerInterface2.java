package RPC_Version2.Server;

/**
 *  This class is a RPC Server interface that defines the MUST methods for a Server
 */

public interface RPCServerInterface2 {
    // start listening on specified port
    public void start(int port);

    // end the running server
    public void end();
}
