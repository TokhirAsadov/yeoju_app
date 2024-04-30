package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeOfStudentByTeacher;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.module.CreateGradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.payload.module.CreateMultipleGradeOfStudentByTeacher;

import java.util.Set;


public interface GradeOfStudentByTeacherService {
    ApiResponse getGradesOfStudent(String teacherId,String studentId,String educationYearId, String subjectId);
    ApiResponseTwoObj getAvgGradesOfStudent(String teacherId, String studentId, String educationYearId, String subjectId,String groupId);
    ApiResponse getAllMiddleGradesOfGroup(String educationYearId, String groupId);
    ApiResponse createGrade(User user, CreateGradeOfStudentByTeacher dto);
    ApiResponse updateGrade(User user, CreateGradeOfStudentByTeacher dto);
    ApiResponse retakeGrade(User user, CreateGradeOfStudentByTeacher dto);


    ApiResponse delete(User user, String id);

    ApiResponse multipleUpdate(User user, Set<CreateMultipleGradeOfStudentByTeacher> dto);

    ApiResponse getRetakesOfStudent(String failGradeId);
}
