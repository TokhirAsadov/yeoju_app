package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.module.FinalGradeOfStudentRepository;
import uz.yeoju.yeoju_app.repository.module.VedimostRepository;

@Service
@RequiredArgsConstructor
public class VedimostImplService implements VedimostService{
    private final VedimostRepository vedimostRepository;
    private final FinalGradeOfStudentRepository finalGradeOfStudentRepository;

    @Override
    public ApiResponse findAllVedimosts() {
        return null;
    }
}
