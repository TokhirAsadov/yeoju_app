package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.dowloadCounter;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface DownloadCounterService {
    ApiResponse getDownloadCountOfStudent(String teacherId, String educationYearId,String groupId,String subjectId, String studentId);
}
