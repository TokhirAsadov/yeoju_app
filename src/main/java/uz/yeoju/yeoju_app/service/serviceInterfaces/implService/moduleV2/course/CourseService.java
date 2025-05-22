package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.course;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseCreator;

public interface CourseService {
    void createCourse(CourseCreator creator);
    boolean deleteCourse(String courseId);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String id);
    ApiResponse findByPlanId(String id);

    ApiResponse getCourseByIdV1(String id);
    ApiResponse getCourseByIdV2(String id,String userId);

    ApiResponse findByStudentIdAndEducationYearId(String studentId, String educationYearId);
}
