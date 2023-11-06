package RPC_Version3.Serialize;

import RPC_Version3.Common.RPCMessage3;
import RPC_Version3.Common.RPCMessageConstants3;
import RPC_Version3.Serialize.Serializer.Serializer3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 *   custom protocol decoder
 *
 *   0              2                 4                     8
 *   +--------------+-----------------+---------------------+-------------------------------------------------
 *   | message type |  serialize type |     full length     |                 message body                   |
 *   +-----------------------+--------+---------------------+------------------------------------------------+
 *
 *   @see <a href="https://zhuanlan.zhihu.com/p/95621344">LengthFieldBasedFrameDecoder解码器</a>
 */

public class MyRPCDecoder3 extends LengthFieldBasedFrameDecoder {
    private Serializer3 serializer;

    // lengthFieldOffset: message type 2B, and serialize type is 2B, and then full length. so value is 4
    // lengthFieldLength: full length is 4B. so value is 4
    // lengthAdjustment: the length field has the correct value, thus no adjustment needed
    // initialBytesToStrip: no bytes are discarded, so 0
    public MyRPCDecoder3() {
        super(RPCMessageConstants3.MAX_FRAME_LENGTH, 4, 4, 0, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);

        try {
            RPCMessage3 rpcMessage = new RPCMessage3();

            // Step 1: get the message type
            short messageType = frame.readShort();
            rpcMessage.setMessageType(messageType);

            // Step 2: get the serialize type
            short serializerType = frame.readShort();
            this.serializer = Serializer3.getSerializationInstanceByCode(serializerType);
            rpcMessage.setSerializationType(serializerType);

            // Step 3: get the length of body and the message body itself
            int length = frame.readInt();
            byte[] bytes = new byte[frame.readableBytes()];
            frame.readBytes(bytes);

            // ‼️ BUG SOLVED: Message body is RPCMessage, not RPCResponse or RPCRequest!!!
            Object deserializedRPCMessage = serializer.deserialize(bytes, RPCMessage3.class);

            rpcMessage.setData(((RPCMessage3) deserializedRPCMessage).getData());

            return rpcMessage;
        } finally {
            frame.release();
        }
    }
}
