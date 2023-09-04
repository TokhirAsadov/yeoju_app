package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.groupConnect;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;

public interface GroupConnectSubjectService {
    ApiResponseTwoObj getGroupsAndStatisticsByGroupName(String studentId,String groupName,String educationYearId);

    ApiResponse getSubjectsOfTeacherByEducationId(String teacherId, String educationId);
    ApiResponse getGroupsOfTeacherByEducationId(String teacherId, String educationId,String subjectId);
    ApiResponse getGroupsOfTeacherByEducationId(String teacherId, String educationId,String subjectId,String eduType);
    ApiResponse getStatisticsOfGroupForTeacher(String educationId,String groupId,String subjectId);
}
