package RPC_Version3.Server;

import RPC_Version3.Services.ServiceProvider3;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyRPCServer3 implements RPCServer3 {
    private ServiceProvider3 serviceProvider;

    @Override
    public void start(int port) {
        // bossGroup -> mainReactor: used for process new connections
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // workerGroup -> subReactor: used for process existing connections
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);

        try {
            // netty server initialization class
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)                        // 1. set up thread groups
                    .channel(NioServerSocketChannel.class)                       // 2. set up channel type: async TCP server side
                    .childHandler(new NettyServerInitializer3(serviceProvider)); // 3. set up child handler

            // bind server to a specific port
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("-------- Netty Server Launched, Listening on port " + port + " --------");
            // block the server socket thread util channel close, make sure the server is keep running
            Channel serverSocket = channelFuture.channel();
            serverSocket.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}
