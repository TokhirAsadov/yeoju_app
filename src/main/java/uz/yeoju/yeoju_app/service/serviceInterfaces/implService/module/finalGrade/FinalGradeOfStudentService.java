package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.finalGrade;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.FinalGradeCreatorDto;


public interface FinalGradeOfStudentService {
    ApiResponse createFinalGrades(FinalGradeCreatorDto dto);
    ApiResponse getGradesWithVedimostByVedimostId(String vedimostId);
}
