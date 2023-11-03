package RPC_Version3.Services;

import RPC_Version3.Common.Course3;

public interface CourseServices3 {

    Course3 getCourseById(Integer id);

    Integer insertCourse(Course3 course);
}
