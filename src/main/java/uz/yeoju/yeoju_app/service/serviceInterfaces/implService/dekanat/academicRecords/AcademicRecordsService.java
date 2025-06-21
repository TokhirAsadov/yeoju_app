package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.academicRecords;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface AcademicRecordsService {
    ApiResponse saveRecords(MultipartHttpServletRequest request);
    ApiResponse getRecordsByUserId(String userId);
}
