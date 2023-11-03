package RPC_Version3.Client;

import RPC_Version3.Common.RPCResponse3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * NettyClientHandler class handles the inbound RPCResponse from the server side
 */

public class NettyClientHandler3 extends SimpleChannelInboundHandler<RPCResponse3> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse3 response) throws Exception {
        // after received the response from the server, set an attribute key name for it
        // the key is an identifier or tag in the channel
        // System.out.println("[NettyClientHandler3] Received Response: " + response);
        AttributeKey<RPCResponse3> key = AttributeKey.valueOf("RPCResponse");
        channelHandlerContext.channel().attr(key).set(response);
        channelHandlerContext.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        cause.printStackTrace();
        channelHandlerContext.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client Channel is active");
        super.channelActive(ctx);
    }
}
