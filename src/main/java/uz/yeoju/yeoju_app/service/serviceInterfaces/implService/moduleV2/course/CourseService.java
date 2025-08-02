package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.course;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseUpdator;

public interface CourseService {
    void createCourse(CourseCreator creator);
    ApiResponse updateCourse(CourseUpdator updator);
    boolean deleteCourse(String courseId);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String id);
    ApiResponse findByPlanId(String id);

    ApiResponseTwoObj getCourseByIdV1(String id);
    ApiResponse getCourseByIdV2(String id,String userId);

    ApiResponse findByStudentIdAndEducationYearId(String studentId, String educationYearId);
    ApiResponse getCourseProgressForGroup(String groupId, String courseId);
    ApiResponse getCourseProgressForGroupV2(String groupId, String courseId);

    ApiResponse getGroups(String facultyId, String courseId);
}
