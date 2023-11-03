package RPC_Version2.Server;

import RPC_Version2.Services.ServiceProvider2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  This class is a thread pool implementation of RPC Server
 *  - a pool of threads is always waiting and executing incoming client side's query
 *  - higher efficiency
 */

public class RPCServerThreadPool2 implements RPCServerInterface2 {
    // interface (service) name -> service object
    private ServiceProvider2 serviceProvider;
    private final ThreadPoolExecutor threadPool;

    /**
     * Key Components of ThreadPoolExecutor
     * - Core Pool Size: The number of threads to keep in the pool, even if they are idle.
     * - Maximum Pool Size: The maximum number of threads allowed in the pool.
     * - Work Queue: A queue that holds tasks before they are executed.
     * - Thread Factory: A factory to create new threads.
     * - Handler: A handler for rejected tasks.
     */

    public RPCServerThreadPool2(ServiceProvider2 serviceProvider) {
        this.serviceProvider = serviceProvider;
        this.threadPool = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(), // corePoolSize
                1000,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
    }

    public RPCServerThreadPool2(ServiceProvider2 serviceProvider, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this.serviceProvider = serviceProvider;
        this.threadPool = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue);
    }

    @Override
    public void start(int port) {
        try {
            // create a server socket
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port 8080");
            // server socket listening
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Server: a connection has been established");
                // thread pool execution
                threadPool.execute(new WorkThread2(socket, serviceProvider));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server start failed");
        }
    }

    @Override
    public void end() {

    }
}
