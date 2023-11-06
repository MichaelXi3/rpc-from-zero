package RPC_Version3.Common;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Data
public class RPCRequest3 implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] methodParameters;
    // Array of class objects with unknown type
    private Class<?>[] parameterTypes;
}
