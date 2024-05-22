package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.module.Vedimost;
import uz.yeoju.yeoju_app.entity.module.VedimostCondition;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
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
        boolean existsTeacher = userRepository.existsById(dto.teacherId);
        if (existsTeacher) {
            boolean existsLesson = lessonRepository.existsById(dto.lessonId);
            if (existsLesson) {
                boolean existsEducationYear = educationYearRepository.existsById(dto.educationYearId);
                if (existsEducationYear) {
                    dto.groupsIds.forEach(groupId -> {
                        groupRepository.findById(groupId).ifPresent(group -> {
                            Vedimost vedimost = new Vedimost();
                            vedimost.setLevel(dto.level);
                            vedimost.setDeadline(dto.deadline);
//                            vedimost.setTimeClose(dto.timeClose);
                            vedimost.setCondition(VedimostCondition.valueOf(dto.condition));
                            vedimost.setTeacher(userRepository.findById(dto.teacherId).orElse(null));
                            vedimost.setLesson(lessonRepository.findById(dto.lessonId).orElse(null));
                            vedimost.setGroup(group);
                            vedimost.setEducationYear(educationYearRepository.findById(dto.educationYearId).orElse(null));
                            vedimostRepository.save(vedimost);
                        });
                    });
                }
                else {
                    throw new UserNotFoundException("Education year was not found by id " +dto.educationYearId);
                }
            }
            else {
                throw new UserNotFoundException("Lesson was not found by id " +dto.lessonId);
            }
        }
        else {
            throw new UserNotFoundException("Teacher was not found by id " +dto.teacherId);
        }
        return new ApiResponse(true, "Vedimost created successfully");
    }

    @Override
    public ApiResponse getVedimostByKafedra(String kafedraId,String educationYearId) {
        return new ApiResponse(true,"All vedimosts",vedimostRepository.getVedimostOfKafedra(kafedraId,educationYearId));
    }

    @Override
    public ApiResponse getVedimostByTeacherId(String teacherId, String educationYearId) {
        return null;
    }
}
