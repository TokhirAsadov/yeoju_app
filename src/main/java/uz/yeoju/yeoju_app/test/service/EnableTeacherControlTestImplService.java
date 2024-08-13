package uz.yeoju.yeoju_app.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.teacher.Teacher;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.TeacherRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.test.payload.CreatorEnableTeacherControlTestDto;
import uz.yeoju.yeoju_app.test.repository.EnableTeacherControlTestRepository;


@Service
@RequiredArgsConstructor
public class EnableTeacherControlTestImplService implements EnableTeacherControlTestService{
    private final EnableTeacherControlTestRepository repository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;

    @Override
    public ApiResponse findAllEnableTeacherByKafedraId(String kafkaId) {
        return new ApiResponse(true,"All teachers' list",repository.getEnableTeachersByKafedraId(kafkaId));
    }

    @Override
    public ApiResponse createAndUpdateEnableTeacher(CreatorEnableTeacherControlTestDto dto) {
        if (dto.id==null || dto.id.isEmpty()){
            return save(dto);
        }


        return null;
    }

    private ApiResponse save(CreatorEnableTeacherControlTestDto dto) {
        return null;
    }

}
