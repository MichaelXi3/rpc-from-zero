package RPC_Version3.Client;

import RPC_Version3.Serialize.MyRPCDecoder3;
import RPC_Version3.Serialize.MyRPCEncoder3;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyClientInitializer3 extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyRPCDecoder3());
        pipeline.addLast(new MyRPCEncoder3());
        pipeline.addLast(new NettyClientHandler3());
    }

    /**
     * 👇 the pipeline implementation before MyEncoder and MyDecoder that uses Java Serialization
     */
    /* protected void initChannel(SocketChannel ch) throws Exception {
        // get the channel pipeline of newly created channel
        ChannelPipeline pipeline = ch.pipeline();
        System.out.println("[NettyClientInitializer] Client Side Channel is Visited");

        // ** configure the channel pipeline **
        // note: we're using netty's encoder, decoder, and length field
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
        pipeline.addLast(new NettyClientHandler3());

        // outbound 2: frame prepender
        pipeline.addLast(new LengthFieldPrepender(4));

        // outbound 1: object encoder
        pipeline.addLast(new ObjectEncoder());
    } */
}
