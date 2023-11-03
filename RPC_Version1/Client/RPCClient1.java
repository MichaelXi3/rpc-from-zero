package RPC_Version1.Client;

import RPC_Version1.Common.Course1;
import RPC_Version1.Services.CourseService1;

import java.util.Random;

/**
 *  This class is RPC Client
 *  - Client uses proxy to encapsulate the rpc call's details and expands the object capabilities
 */

public class RPCClient1 {
    public static void main(String[] args) {
        ClientProxy1 clientProxy = new ClientProxy1("127.0.0.1", 8080);
        // Create a dynamic proxy that adheres to the specified interface
        CourseService1 proxy = clientProxy.getProxy(CourseService1.class);

        // Use proxy to call the remote functions and get results
        // Method 1: get course by id
        Course1 returnedCourse = proxy.getCourseById(new Random().nextInt(10));
        System.out.println("Get course by id returned: \n" + returnedCourse);
        // Method 2: insert a course to DB
        Course1 newCourse = new Course1("new course", 520, 1314, true);
        Integer insertCourse = proxy.insertCourse(newCourse);
        System.out.println("Insert new course returned: " + insertCourse);
    }
}
