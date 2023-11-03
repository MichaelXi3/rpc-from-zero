package RPC_Version3.Services;

import RPC_Version3.Common.Course3;

import java.util.Random;

public class CourseServicesImp3 implements CourseServices3 {
    @Override
    public Course3 getCourseById(Integer id) {
        System.out.println("[CourseServices] Get Course by id: " + id);
        // Simulation of database query
        Random rand = new Random();
        Course3 course = new Course3("Computer Science (V3)", rand.nextInt(50), id, rand.nextBoolean());
        return course;
    }

    @Override
    public Integer insertCourse(Course3 course) {
        System.out.println("[CourseServices] Inserted following course to DB: \n" + course);
        return 0;
    }
}
