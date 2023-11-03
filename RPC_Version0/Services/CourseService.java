package RPC_Version0.Services;

import RPC_Version0.Common.Course;

/**
 *  This class is visible to both client and server
 *  - Client calls functions defined in this interface
 *  - Server implments functions defined in this interface
 */

public interface CourseService {
    Course getCourseById(Integer id);
}
