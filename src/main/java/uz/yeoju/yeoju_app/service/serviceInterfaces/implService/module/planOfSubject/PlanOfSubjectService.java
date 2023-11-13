package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.planOfSubject;

import io.swagger.annotations.Api;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.module.CreatePlanOfStudent;

public interface PlanOfSubjectService {
     ApiResponseTwoObj getPlansForTeacherSciences(String teacherId, String educationId, String subjectId, Integer groupLevel);
     ApiResponseTwoObj getAllPlansForTeacherSciences(String teacherId, String educationId);

     ApiResponse createPlan(User user, CreatePlanOfStudent dto);
     ApiResponse createPlanByKafedraMudiri(User user, CreatePlanOfStudent dto);
     ApiResponse getExistPlans(String teacherId, String educationId, String subjectId, Integer groupLevel);

    ApiResponse updatePlan(User user, CreatePlanOfStudent dto);

     ApiResponse getTeacherWIthSubjectForPlan(String id,String educationYearId);
}
