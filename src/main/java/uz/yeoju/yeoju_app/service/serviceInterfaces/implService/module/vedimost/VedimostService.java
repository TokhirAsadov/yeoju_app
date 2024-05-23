package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.VedimostCreaterDto;
import uz.yeoju.yeoju_app.payload.module.VedimostUpdaterDto;

public interface VedimostService {
    ApiResponse findAllVedimosts();
    ApiResponse createVedimost(VedimostCreaterDto dto);

    ApiResponse getVedimostByKafedra(String kafedraId,String educationYearId);

    ApiResponse getVedimostByTeacherId(String teacherId, String educationYearId);

    ApiResponse getAllVedimostByKafedra(String kafedraId);

    ApiResponse getAllVedimostByTeacherId(String teacherId);

    ApiResponse updateVedimost(VedimostUpdaterDto dto);

    ApiResponse deleteVedimostById(String id);

    ApiResponse getAllVedimostByGroupId(String groupId);

    ApiResponse getVedimostByGroupId(String groupId, String educationYearId);

    ApiResponse checkVedimostExistsForTeacher(String teacherId, String lessonId, String groupId, String educationYearId);
}
