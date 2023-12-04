package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeOfStudentByTeacher;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.module.CreateGradeOfStudentByTeacher;


public interface GradeOfStudentByTeacherService {
    ApiResponse getGradesOfStudent(String teacherId,String studentId,String educationYearId, String subjectId);
    ApiResponseTwoObj getAvgGradesOfStudent(String teacherId, String studentId, String educationYearId, String subjectId,String groupId);
    ApiResponse getAllMiddleGradesOfGroup(String educationYearId, String groupId);
    ApiResponse createGrade(User user, CreateGradeOfStudentByTeacher dto);
    ApiResponse updateGrade(User user, CreateGradeOfStudentByTeacher dto);


    ApiResponse delete(User user, String id);
}
