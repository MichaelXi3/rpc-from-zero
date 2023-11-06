package RPC_Version3.Client;

import RPC_Version3.Common.RPCMessage3;
import RPC_Version3.Common.RPCRequest3;
import RPC_Version3.Common.RPCResponse3;
import RPC_Version3.Common.RPCMessageConstants3;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class NettyRPCClient3 implements RPCClient3 {
    private final String host;
    private final int port;
    private static final Bootstrap bootStrap;
    private static final EventLoopGroup eventLoopGroup;

    public NettyRPCClient3(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        // netty client side initialization
        eventLoopGroup = new NioEventLoopGroup();
        bootStrap = new Bootstrap();
        bootStrap.group(eventLoopGroup)
                // async TCP client side
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer3());
        System.out.println("[NettyRPCClient] Netty Client Init Done");
    }

    @Override
    public RPCResponse3 sendRequest(RPCRequest3 rpcRequest) {
        try {
            // connect to the remote server side by ip + port
            ChannelFuture channelFuture = bootStrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("-------- Client Side connect to " + host + " , " + port + " --------");

            // instantiate RPCMessage to encapsulate RPC message transmission
            RPCMessage3 RPCMessage = RPCMessage3.builder()
                    .messageType(RPCMessageConstants3.MESSAGE_TYPE_RPC_REQUEST)
                    .serializationType(RPCMessageConstants3.SERIALIZATION_TYPE_KRYO) // 🌟 The place to specify the serialization method
                    .data(rpcRequest).build();

            // send the data through channel
            channel.writeAndFlush(RPCMessage);
            channel.closeFuture().sync();

            // get the RPCResponse returned from the server side in channel
            AttributeKey<RPCMessage3> key = AttributeKey.valueOf("RPCMessage");
            RPCMessage3 responseMsg = channel.attr(key).get();
            return (RPCResponse3) responseMsg.getData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
