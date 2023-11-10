# RPC

# RPC Architecture

RPC (Remote Procedure Call) is a communication mechanism that allows processes or function calls to be executed in different address spaces (usually on different computers on a network). The main goal of RPC is to make remote service calls as simple and transparent as possible, akin to local method calls.

Generally, an RPC framework should not only provide service discovery capabilities but also features like load balancing and fault tolerance.

![1.png](https://s2.loli.net/2023/11/11/6rauHgpLWVm9NKY.png)


**Core Components of RPC**

1. **Client** (Server Consumer Side Client): The side that calls remote services in a local manner.
2. **Client Stub**: A proxy class responsible for passing the methods, classes, and method parameters you call to the server side.
3. **Network Transmission**: Between the server and client, using methods such as Socket or Netty.
4. **Server Stub**: Not a proxy class. It receives method execution requests from the client, directs them to the specified methods, and then returns the results to the client.
5. **Server** (Service Provider Side Server/Provider): The side that provides remote methods.


![2.png](https://s2.loli.net/2023/11/11/UbeVDRgJZ8EpOWG.png)


# RPC Implementation

## Version 0

![MyRPC_V0.png](https://s2.loli.net/2023/11/11/G3m54hiU97ASqTE.png)

> **Key Points**
>
> Socket Programming

### Components

1. Client
    - Establishes a Socket connection, can only transmit id to the server, and receives the corresponding Object (Query Result)
2. Server
    - BIO listens on the Socket, if data is received, it calls the service to execute tasks and return results
3. Service Interface
    - Called by the Client, a list of services provided by the Server (currently a single service)
4. Others
    - The Class of the transmitted Object (Serializable)

### Implemented Features

1. The Client can only pass an ID to the Server, then the Server uses this ID to call a method and return a value
2. The returned Object can only be a Course Object, nothing else

## Next Steps for Improvement

1. Abstract the Client: The Client should be able to request any method, passing className, methodName, parameters
2. Abstract the Response: The returned value should be able to be any type of value returned by any method
3. Dynamic Proxy Client: Simplify the logic on the Client side, letting the proxy handle it


## Version 1

![MyRPC_V1.png](https://s2.loli.net/2023/11/11/sfQRkrXOJ3y18qe.png)


> **Key Points**
>
> Dynamic Proxy
> Reflection

### Components

1. Client
- The Client itself
- Client Dynamic Proxy

2. Server

    - Single Server: Receives RPC Request Object and returns RPC Response Object, BIO listens on Socket

3. Service Interface

    - Called by the Client, services provided by the Server (currently only a single service)

4. Others

    - RPC Request Class

    - RPC Response Class

    - The Class of the transmitted Object corresponding to the Course Class

### Implemented Features

- The request sent by the Client to the Server is abstracted into a ClientRequest Object
- The response sent by the Server to the Client is abstracted into a ClientResponse Object
- The Client's Dynamic Proxy abstracts the RPC logic, enhancing the capabilities of the proxy class

### Next Steps for Improvement

1. Decouple the Server Side logic
2. How to handle the situation where the Server Side provides multiple services
3. How to increase the Server Side's request processing capability through multithreading


## Version 2

![V2.png](https://s2.loli.net/2023/11/11/vK75WFdGTzQrqgj.png)

> **Key Points**
>
> - Decoupling
> - Thread pool

### Components

1. Client
    - The Client itself
    - Client Dynamic Proxy
2. Server
    - Server Interface to abstract the capabilities of the Server
        - Non-thread pool implementation of Server: SimpleRPCServer implements the Server Interface
        - Threadpool implementation of Server: ThreadPoolRPCServer implements Server Interface
    - Decoupling 1: Separating each WorkThread's implementation from the Server-side code
3. Service Interface → Provides Multiple Services
    - Services called by the Client and provided by the Server
    - Abstracted into a Service Provider Class to provide multiple services: interface name → service object
4. Others
    - RPC Request Class
    - RPC Response Class
    - Classes corresponding to the transmitted Objects

### Implemented Features

1. Server-Side Feature Decoupling
    - The RPC Server can be implemented in various ways after abstraction, such as through a thread pool or single-threaded each time
    - Decouple the work thread that handles connections from the Client side after the Server side listens
2. The Server side can provide a variety of services, i.e., requests and returns for various interfaces and methods
    - Maintained by the ServiceProvider: service (interface) name ↔ service object map

### Next Steps for Improvement

1. Socket I/O is BIO, which has lower network transmission performance
2. Java's built-in Serialization has poor performance and low efficiency and needs to be replaced


## Version 3

![V3.png](https://s2.loli.net/2023/11/11/DpNGxsKSgeCwPmM.png)

> **Key Points**
>
> - Netty high-performance network framework: BIO → NIO

### Components

1. Client
    - Client Interface to abstract the capabilities of the RPC Client
        - Socket BIO implementation of Client: `SimpleRPCClient`
        - Netty implementation of Client: `NettyRPCClient`
            - Initializer → Client Side Pipeline
            - Handler → Part of the Pipeline, used to handle inbound RPCMessage
    - Client Dynamic Proxy
    - Client Main
2. Server
    - Server Interface to abstract the capabilities of the Server
        - Netty implementation of Server: `NettyRPCServer`
            - Initializer → Server Side Pipeline
            - Handler → Part of the Pipeline, used to handle inbound RPCMessage
    - Server Main
3. Services → Provides Multiple Services
    - Service Provider Class to provide multiple services: interface name → service object
    - Interfaces and implementations of multiple services called by the Client and provided by the Server
    - Classes corresponding to the transmitted Objects
4. Common
    - RPC Request Class
    - RPC Response Class
    - RPC Message Constants → A place where commonly used constants are stored
    - RPC Message Class → messageType, serializationType, data (response or request)
        - RPC transmission uniformly uses the RPCMessage Object
5. Serialize
    - Serializer
        - Serializer Interface to abstract the capabilities of the serializer
        - KryoSerializer Implementation
    - `MyEncoder` → Encode using a customized defined protocol
    - `MyDecoder` → Decode based on a customized defined protocol

### Implemented Features

1. Client-Side functionality abstracted into an Interface
2. Client-Side and Server-Side **Netty** NIO implementation
3. The transmission process uses `kryo` for Serialization and Deserialization for better performance, while also ensuring the possibility of adding more serialization methods in the future, just need to implement the Serializer interface. At the same time, the Client side can decide which serialization method to use based on the `serializationType` of the RPCMessage
4. Customized transmission message format, including the **length** of the message body, to solve the problem of packet sticking and splitting

### Next Steps for Improvement

1. Clients must know the corresponding service's IP and port number, and if the service goes down or changes address, the corresponding service can no longer be found. At the same time, scalability is very limited.
2. Add heartbeat mechanism