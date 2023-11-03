package RPC_Version1.Services;

import RPC_Version1.Common.Course1;

/**
 *  This class is visible to both client and server
 *  - Client calls functions defined in this interface
 *  - Server implements functions defined in this interface
 */

public interface CourseService1 {

    Course1 getCourseById(Integer id);

    Integer insertCourse(Course1 course);
}
