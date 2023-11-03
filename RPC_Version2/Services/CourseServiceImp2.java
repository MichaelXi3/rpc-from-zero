package RPC_Version2.Services;

import RPC_Version2.Common.Course2;

import java.util.Random;

public class CourseServiceImp2 implements CourseService2 {
    @Override
    public Course2 getCourseById(Integer id) {
        System.out.println("Client requests Course with id: " + id);
        // Simulation of database query
        Random rand = new Random();
        Course2 course = new Course2("Computer Science (V2)", rand.nextInt(50), id, rand.nextBoolean());
        return course;
    }

    @Override
    public Integer insertCourse(Course2 course) {
        System.out.println("Inserted following course to DB: \n" + course);
        return 0;
    }
}
