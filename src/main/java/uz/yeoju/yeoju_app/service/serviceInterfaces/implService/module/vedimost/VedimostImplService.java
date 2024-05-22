package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.VedimostCreaterDto;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.module.FinalGradeOfStudentRepository;
import uz.yeoju.yeoju_app.repository.module.VedimostRepository;

@Service
@RequiredArgsConstructor
public class VedimostImplService implements VedimostService{
    private final VedimostRepository vedimostRepository;
    private final FinalGradeOfStudentRepository finalGradeOfStudentRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final EducationYearRepository educationYearRepository;
    private final GroupRepository groupRepository;

    @Override
    public ApiResponse findAllVedimosts() {
        return null;
    }

    @Override
    public ApiResponse createVedimost(VedimostCreaterDto dto) {
        return null;
    }
}
