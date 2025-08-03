package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.planOfSubject;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.module.CreatePlanOfStudent;
import uz.yeoju.yeoju_app.payload.moduleV2.CreatePlanOfStudentV2;

public interface PlanOfSubjectV2Service {
     ApiResponseTwoObj getPlansForTeacherSciences(String teacherId, String educationId, String subjectId, Integer groupLevel);
     ApiResponseTwoObj getAllPlansForTeacherSciences(String teacherId, String educationId);

     ApiResponse createPlan(User user, CreatePlanOfStudentV2 dto);

     ApiResponse getExistPlans(String teacherId, String educationId, String subjectId, Integer groupLevel);


     ApiResponse getTeacherWIthSubjectForPlan(String id,String educationYearId);

     ApiResponse getPlansByKafedraId(String id, String kafedraId);

    ApiResponse getAllDataOfPlanById(String planId);
    ApiResponse getTeacherSubjects(String teacherId, String educationYearId);
    ApiResponse getCourseDetailsByCourseId(String planId);
    ApiResponse getPlansBySubjectId(String lessonId, String educationYearId);

    ApiResponse getCourseDetailsByCourseIdV2(String courseId);
}
