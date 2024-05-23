package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.finalGrade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.FinalGradeCreatorDto;
import uz.yeoju.yeoju_app.repository.module.FinalGradeOfStudentRepository;
import uz.yeoju.yeoju_app.repository.module.VedimostRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FinalGradeOfStudentImplService implements FinalGradeOfStudentService {
    private final FinalGradeOfStudentRepository finalGradeOfStudentRepository;
    private final VedimostRepository vedimostRepository;

    @Override
    public ApiResponse createFinalGrades(Set<FinalGradeCreatorDto> dto) {
        return null;
    }
}
