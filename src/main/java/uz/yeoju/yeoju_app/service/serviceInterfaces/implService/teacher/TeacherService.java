package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.teacher;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.teacher.TeacherSaveDto;

public interface TeacherService {
    ApiResponse findAll();
    ApiResponse save(TeacherSaveDto dto);

    ApiResponse changeTeacherPosition(String userId, Long positionId);

    ApiResponse saveTeacherFromExcel(MultipartHttpServletRequest request);

    ApiResponse getDataForTeacherDocumentPDF(User user,String educationYearId,String subjectId,String groupId);

    ApiResponse getDataOfLeaders(String teacherId, String groupName);
}
