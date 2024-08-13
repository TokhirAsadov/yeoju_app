package uz.yeoju.yeoju_app.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.test.repository.EnableTeacherControlTestRepository;

@Service
@RequiredArgsConstructor
public class EnableTeacherControlTestImplService implements EnableTeacherControlTestService{
    private final EnableTeacherControlTestRepository repository;

    @Override
    public ApiResponse findAllEnableTeacherByKafedraId(String kafkaId) {
        return new ApiResponse(true,"All teachers' list",repository.getEnableTeachersByKafedraId(kafkaId));
    }
}
