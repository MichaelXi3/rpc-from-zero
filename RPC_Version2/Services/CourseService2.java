package RPC_Version2.Services;

import RPC_Version2.Common.Course2;

/**
 *  This class is visible to both client and server
 *  - Client calls functions defined in this interface
 *  - Server implements functions defined in this interface
 */

public interface CourseService2 {

    Course2 getCourseById(Integer id);

    Integer insertCourse(Course2 course);
}
