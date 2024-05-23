package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.finalGrade;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.FinalGradeCreatorDto;

import java.util.Set;

public interface FinalGradeOfStudentService {
    ApiResponse createFinalGrades(Set<FinalGradeCreatorDto> dto);
}
