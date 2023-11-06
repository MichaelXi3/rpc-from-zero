package RPC_Version3.Serialize.Serializer;

import RPC_Version3.Common.RPCMessage3;
import RPC_Version3.Common.RPCRequest3;
import RPC_Version3.Common.RPCResponse3;
import RPC_Version3.Services.Course3;
import RPC_Version3.Services.Professor3;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Kryo is a serialization library for Java that requires you to register
 * the classes you are going to serialize and deserialize.
 */

public class KryoSerializer3 implements Serializer3 {

    // kryo is not thread safe, thus using threadlocal
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        // Register all possible classes -> Better Performance
        kryo.register(RPCMessage3.class);
        kryo.register(RPCRequest3.class);
        kryo.register(RPCResponse3.class);
        kryo.register(Object[].class);
        kryo.register(Class.class);
        kryo.register(Class[].class);
        kryo.register(Course3.class);
        kryo.register(Professor3.class);
        // Or, disable the registration requirement by:
        // kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream)) {
            // get kryo serializer
            Kryo kryo = kryoThreadLocal.get();
            // serialize object from obj to output
            kryo.writeObject(output, obj);
            kryoThreadLocal.remove();
            // return object as byte array
            return output.toBytes();
        } catch (IOException e) {
            System.out.println("Kryo serialize error");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream)) {
            // get kryo serializer
            Kryo kryo = kryoThreadLocal.get();
            // read input and deserialize to object
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        } catch (IOException e) {
            System.out.println("Kryo deserialize error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Let 0 represents the kryo serialization type
     */
    @Override
    public short getSerializationType() {
        return 0;
    }
}
