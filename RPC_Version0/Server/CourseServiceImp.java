package RPC_Version0.Server;

import RPC_Version0.Common.Course;
import RPC_Version0.Services.CourseService;

import java.util.Random;

/**
 *  This class belongs to server
 *  - Server implements the function calls it has based on the interface
 */

public class CourseServiceImp implements CourseService {

    @Override
    public Course getCourseById(Integer id) {
        System.out.println("Client requests Course with id: " + id);
        // Simulation of database query
        Random rand = new Random();
        Course course = new Course("English Literature", rand.nextInt(50), rand.nextInt(), rand.nextBoolean());
        return course;
    }
}
