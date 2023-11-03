package RPC_Version1.Server;

import RPC_Version1.Common.Course1;
import RPC_Version1.Services.CourseService1;

import java.util.Random;

/**
 *  This class belongs to server
 *  - Server implements the function calls it has based on the interface
 */

public class CourseServiceImp1 implements CourseService1 {

    @Override
    public Course1 getCourseById(Integer id) {
        System.out.println("Client requests Course with id: " + id);
        // Simulation of database query
        Random rand = new Random();
        Course1 course = new Course1("English Literature", rand.nextInt(50), id, rand.nextBoolean());
        return course;
    }

    @Override
    public Integer insertCourse(Course1 course) {
        System.out.println("Inserted following course to DB: \n" + course);
        return 520;
    }
}
