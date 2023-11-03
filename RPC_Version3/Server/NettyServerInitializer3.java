package RPC_Version3.Server;

import RPC_Version3.Services.ServiceProvider3;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;

/**
 * NettyServerInitializer is used to configure the ChannelPipeline of newly registered Channels.
 * - ChannelPipeline is a chain of Netty processors used to process or intercept inbound events and outbound operations for channels
 */

@AllArgsConstructor
public class NettyServerInitializer3 extends ChannelInitializer<SocketChannel> {
    private ServiceProvider3 serviceProvider;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // get the channel pipeline of newly created channel
        ChannelPipeline pipeline = ch.pipeline();
        System.out.println("[NettyServerInitializer] Server Side Channel is Visited");
        // ** configure the channel pipeline **
        // inbound 1: frame decoder
            // LengthFieldBasedFrameDecoder： A decoder in Netty for solving sticky and unwrapped packets for length-based protocols.
            // It determines the boundaries of a message based on the length field in the message.
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

        // inbound 2: object decoder
        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
            @Override
            public Class<?> resolve(String className) throws ClassNotFoundException {
                return Class.forName(className);
            }
        }));

        // inbound 3: object handler
        pipeline.addLast(new NettyRPCServerHandler3(serviceProvider));

        // outbound 2: frame prepender
        pipeline.addLast(new LengthFieldPrepender(4));

        // outbound 1: object encoder
        pipeline.addLast(new ObjectEncoder());

        // pipeline.addLast("logger", new LoggingHandler(LogLevel.INFO));
    }
}



