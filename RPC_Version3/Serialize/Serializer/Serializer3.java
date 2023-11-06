package RPC_Version3.Serialize.Serializer;

public interface Serializer3 {

    /**
     * Serialization
     * @param obj Input is an object
     * @return Output is a byte array that based on the object
     */
    byte[] serialize(Object obj);

    /**
     * De-Serialization
     * @param bytes Input byte array
     * @param clazz Target Class
     * @param <T> Class Type
     * @return Deserialized Object
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

    /**
     * Get the int value that represents serialization type
     * @return Integer value that maps to serialization type
     *
     * (1) 0 <-> kryo serialization
     */
    short getSerializationType();

    static Serializer3 getSerializationInstanceByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer3();
            default:
                return null;
        }
    }
}
