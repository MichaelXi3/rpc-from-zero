package RPC_Version2.Client;

import RPC_Version2.Client.ClientProxy2;
import RPC_Version2.Common.Course2;
import RPC_Version2.Common.Professor2;
import RPC_Version2.Services.CourseService2;
import RPC_Version2.Services.ProfessorService2;

import java.util.Random;

/**
 *  This class is RPC Client
 *  - Client uses proxy to encapsulate the rpc call's details and expands the object capabilities
 */

public class RPCClient2 {
    public static void main(String[] args) {
        ClientProxy2 clientProxy = new ClientProxy2("127.0.0.1", 8080);
        // Create a dynamic proxy that adheres to the specified interface
        CourseService2 proxyCourseService = clientProxy.getProxy(CourseService2.class);
        ProfessorService2 proxyProfessorService = clientProxy.getProxy(ProfessorService2.class);

        // Use proxy to call the remote functions and get results
        // Method 1: get course by id
        Course2 returnedCourse = proxyCourseService.getCourseById(new Random().nextInt(10));
        System.out.println("Get course by id returned: \n" + returnedCourse);

        // Method 2: insert a course to DB
        Course2 newCourse = new Course2("new course (V2)", 520, 1314, true);
        Integer insertCourse = proxyCourseService.insertCourse(newCourse);
        System.out.println("Insert new course returned: \n" + insertCourse);

        // Method 3: get professor by id
        Professor2 returnedProfessor = proxyProfessorService.getProfessorById(new Random().nextInt(10));
        System.out.println("Get professor by id returned: \n" + returnedProfessor);

        // Method 4: promote professor tenure status
        Professor2 professorToPromote = new Professor2("Michael", 5, "Computer Science", false);
        professorToPromote = proxyProfessorService.promoteTenured(professorToPromote);
        System.out.println("Professor after promotion: \n" + professorToPromote);
    }
}

