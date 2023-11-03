package RPC_Version3.Server;

import RPC_Version3.Common.RPCRequest3;
import RPC_Version3.Common.RPCResponse3;
import RPC_Version3.Services.ServiceProvider3;
import io.netty.channel.*;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * NettyRPCServerHandler is a channel handler that processes all RPCRequest typed inbound messages
 */

@AllArgsConstructor
public class NettyRPCServerHandler3 extends SimpleChannelInboundHandler<RPCRequest3> {
    private ServiceProvider3 serviceProvider;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCRequest3 rpcRequest) throws Exception {
        System.out.println("-------- Netty Server Received following Request --------------");
        System.out.println(rpcRequest);
        System.out.println("---------------------------------------------------------------");

        RPCResponse3 response = getResponse(rpcRequest);
        // ‼️ BUG FIXED
        // Past: channelHandlerContext.writeAndFlush(response); -> not sending anything to the client
        // FIX:  channelHandlerContext.channel().writeAndFlush(response);

        /**
         * 这个调用是用来将 response 对象写入到网络 Socket 并立即刷新。writeAndFlush 是一个组合操作，它首先将数据写入到 Channel 的内部缓冲区，然后刷新缓冲区，确保数据被发送出去。
         * channelHandlerContext.channel() 获取到与当前 ChannelHandlerContext 关联的 Channel 实例，然后对这个 Channel 执行写操作。
         * 这是异步的操作。当调用 writeAndFlush 时，Netty 并不会立即将数据发送出去，而是返回一个 ChannelFuture 实例，这个实例代表了即将执行的 I/O 操作。
         */
        ChannelFuture f = channelHandlerContext.channel().writeAndFlush(response);
        /**
         * 这个调用是在 ChannelFuture 上添加了一个监听器，当 ChannelFuture 表示的操作完成时（无论是成功还是失败），都会通知这个监听器。
         * ChannelFutureListener.CLOSE 是一个预定义的监听器，它的作用是在 ChannelFuture 完成时关闭这个 Channel。
         * 这意味着，一旦响应 response 被写入并发送到客户端，这个网络连接就会被关闭。
         */
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        cause.printStackTrace();
        channelHandlerContext.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server Channel is active");
        super.channelActive(ctx);
    }

    private RPCResponse3 getResponse(RPCRequest3 request) {
        // get service interface name
        String interfaceName = request.getInterfaceName();
        // get corresponding interface object
        Object serviceObject = serviceProvider.getServiceByName(interfaceName);
        // use reflection to call the method
        String methodName = request.getMethodName();
        try {
            Method method = serviceObject.getClass().getMethod(methodName, request.getParameterTypes());
            Object invoke = method.invoke(serviceObject, request.getMethodParameters());
            RPCResponse3 response = RPCResponse3.success(invoke);
            return response;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return RPCResponse3.fail();
        }
    }
}
