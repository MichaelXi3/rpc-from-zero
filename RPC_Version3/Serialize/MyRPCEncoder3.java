package RPC_Version3.Serialize;

import RPC_Version3.Common.RPCMessage3;
import RPC_Version3.Common.RPCRequest3;
import RPC_Version3.Common.RPCResponse3;
import RPC_Version3.Serialize.Serializer.Serializer3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;

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

@AllArgsConstructor
@NoArgsConstructor
public class MyRPCEncoder3 extends MessageToByteEncoder<RPCMessage3> {
    private Serializer3 serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RPCMessage3 rpcMessage, ByteBuf out) throws Exception {
        // step 1: get the message type to encode, short is 2 bytes
        out.writeShort(rpcMessage.getMessageType());

        // step 2: get the serialize type to encode, short is 2 bytes
        short serializationType = rpcMessage.getSerializationType();
        out.writeShort(serializationType);
        this.serializer = Serializer3.getSerializationInstanceByCode(serializationType);

        // step 3: get the byte[] and write its length and body into ByteBuf
        try {
            byte[] serializeResult = serializer.serialize(rpcMessage);
            out.writeInt(serializeResult.length);
            out.writeBytes(serializeResult);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Serialization failed", e);
        }
    }
}
