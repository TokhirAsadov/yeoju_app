package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.VedimostCreaterDto;
import uz.yeoju.yeoju_app.payload.module.VedimostUpdaterDto;

public interface VedimostService {
    ApiResponse findAllVedimosts();
    ApiResponse createVedimost(VedimostCreaterDto dto);

    ApiResponse getVedimostByKafedra(String kafedraId,String educationYearId);

    ApiResponse getVedimostByLessonId(String type,String dekanatOrKafedraId,String lessonId, String educationYearId);
    ApiResponse getVedimostByTeacherId(String type,String dekanatOrKafedraId,String teacherId, String educationYearId);
    ApiResponse getVedimostByTeacherIdAndLessonId(String type,String dekanatOrKafedraId,String teacherId, String educationYearId,String lessonId);

    ApiResponse getAllVedimostByKafedra(String kafedraId);

    ApiResponse getAllVedimostByTeacherId(String teacherId);

    ApiResponse updateVedimost(VedimostUpdaterDto dto);

    ApiResponse deleteVedimostById(String id);

    ApiResponse getAllVedimostByGroupId(String groupId);

    ApiResponse getVedimostByGroupId(String type,String dekanatOrKafedraId,String groupId, String educationYearId);
    ApiResponse getVedimostByFacultyId(String type,String dekanatOrKafedraId,String facultyId, String educationYearId);

    ApiResponse getVedimostForBeingDone(String teacherId, String lessonId, String groupId, String educationYearId);
    ApiResponse getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(String dekanatOrKafedraId,String teacherId, String lessonId, String facultyId, String educationYearId,String type);

    ApiResponse getVedimostByTeacherIdAndFacultyId(String type,String dekanatOrKafedraId,String teacherId, String facultyId, String educationYearId);

    ApiResponse getVedimostByLessonIdAndFacultyId(String type,String dekanatOrKafedraId,String educationYearId, String lessonId, String facultyId);

    ApiResponse getVedimostByLessonIdAndGroupId(String type,String dekanatOrKafedraId,String educationYearId, String lessonId, String groupId);

    ApiResponse getVedimostByTeacherIdAndGroupId(String type,String dekanatOrKafedraId,String teacherId, String educationYearId, String groupId);

    ApiResponse getVedimostByEducationYearId(String type,String dekanatOrKafedraId,String educationYearId);

    ApiResponse getLast50Vedimost(String type,String dekanatOrKafedraId);

    ApiResponse getVedimostByLevel(String type,String dekanatOrKafedraId,Integer level, String educationYearId);

    ApiResponse getVedimostByLessonIdAndLevel(String type,String dekanatOrKafedraId,String educationYearId, String lessonId, Integer level);

    ApiResponse getVedimostByFacultyIdAndLevel(String type,String dekanatOrKafedraId,String educationYearId, String facultyId, Integer level);

    ApiResponse getVedimostByTeacherIdAndLevel(String type, String dekanatOrKafedraId,String teacherId, Integer level, String educationYearId);

    ApiResponse getDataAboutVedimostByDekanat(String dekanatId, String educationYearId,String condition);

    ApiResponse getDataAboutVedimostByKafedra(String kafedraId, String educationYearId,String condition);

    ApiResponse getDataAboutVedimostForMonitoring(String educationYearId, String condition);
}
