package RPC_Version3.Common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RPCMessage3 {
    /**
     * RPC Message Type: Request or Response
     */
    private short messageType;

    /**
     * Serialization Type: Kryo -> 0, and more...
     */
    private short serializationType;

    /**
     * Request Body
     */
    private Object data;
}
