package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeForAttendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.module.GradeForAttendance;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.GradeForAttendanceDto;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.module.GradeForAttendanceRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GradeForAttendanceImplService implements GradeForAttendanceService{
    private final GradeForAttendanceRepository repository;
    private final EducationYearRepository educationYearRepository;
    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;


    @Override
    @Transactional
    public ApiResponse createGradeForAttendance(User user, GradeForAttendanceDto dto) {
        Optional<EducationYear> optionalEducationYear = educationYearRepository.findById(dto.educationYearId);
        if (optionalEducationYear.isPresent()) {
            EducationYear educationYear = optionalEducationYear.get();
            Optional<Lesson> optionalLesson = lessonRepository.findById(dto.lessonId);
            if (optionalLesson.isPresent()) {
                Lesson lesson = optionalLesson.get();
                List<Group> groupList = groupRepository.findAllById(dto.getGroupsIds());
                Set<Group> groups = new HashSet<>();
                groupList.forEach(group -> {
                    Boolean bool = repository.existTeachersLesson(user.getId(), group.getId(), dto.educationYearId, dto.lessonId);
                    if (!bool) {
                        throw new UserNotFoundException("Error. You do not have lesson for " + group.getName() + ". Please check one more your request!. Take care:)");
                    }
                    Boolean existGradeForAttendance = repository.existGradeForAttendance(group.getId(), dto.educationYearId, dto.lessonId);
                    if (!existGradeForAttendance) {
                        groups.add(group);
                    }
                });
                repository.save(new GradeForAttendance(dto.getGrade(),dto.getCredit(),educationYear,lesson,groups));
                return new ApiResponse(true,"Grade for attendance of group was created successfully!.");
            }
            else {
                return new ApiResponse(false,"Not found lesson by id: "+dto.lessonId);
            }
        }
        else {
            return new ApiResponse(false,"Not found education year by id: "+dto.educationYearId);
        }
    }
}
