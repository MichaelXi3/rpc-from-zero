package RPC_Version3.Client;

import RPC_Version3.Common.Course3;
import RPC_Version3.Common.Professor3;
import RPC_Version3.Services.CourseServices3;
import RPC_Version3.Services.ProfessorServices3;

import java.util.Random;

public class ClientMain3 {
    public static void main(String[] args) {
        // create a netty NIO RPC Client
        RPCClient3 NIORPCClient = new NettyRPCClient3("127.0.0.1", 8080);
        // create client proxy
        RPCClientProxy3 clientProxy = new RPCClientProxy3(NIORPCClient);

        // create interface proxies for this client
        CourseServices3 proxyCourseService = clientProxy.getProxy(CourseServices3.class);
        ProfessorServices3 proxyProfessorService = clientProxy.getProxy(ProfessorServices3.class);

        // call methods and test results
        // Method 1: get course by id = 5
        Course3 returnedCourse = proxyCourseService.getCourseById(5);
        System.out.println("Get course by id returned: \n" + returnedCourse);

        // Method 2: insert a course to DB
        Course3 newCourse = new Course3("new course (V3)", 555, 2333, true);
        Integer insertCourse = proxyCourseService.insertCourse(newCourse);
        System.out.println("Insert new course returned: \n" + insertCourse);

        // Method 3: get professor by id
        Professor3 returnedProfessor = proxyProfessorService.getProfessorById(10);
        System.out.println("Get professor by id returned: \n" + returnedProfessor);

        // Method 4: promote professor tenure status
        Professor3 professorToPromote = new Professor3("Michael3", 5, "Computer Science", false);
        professorToPromote = proxyProfessorService.promoteTenured(professorToPromote);
        System.out.println("Professor after promotion: \n" + professorToPromote);
    }
}
