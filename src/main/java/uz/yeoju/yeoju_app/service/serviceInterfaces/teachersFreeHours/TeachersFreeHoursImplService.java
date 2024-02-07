package uz.yeoju.yeoju_app.service.serviceInterfaces.teachersFreeHours;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.teacher.TeachersFreeHours;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.teacher.TeachersFreeHoursDto;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.TeachersFreeHoursRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeachersFreeHoursImplService implements TeachersFreeHoursService{

    private final TeachersFreeHoursRepository repository;
    private final EducationYearRepository educationYearRepository;
    private final GroupRepository groupRepository;
//    private final L educationYearRepository;

    @Override
    public ApiResponse getAllHoursByTeacherId(String teacherId) {
        List<TeachersFreeHours> freeHours = repository.findAllByCreatedByOrderByCreatedAt(teacherId);
        return new ApiResponse(true,"Teacher's free hours",freeHours);
    }

    @Override
    public ApiResponse checkerThatExistsTeachersFreeHours(String educationYearId,String teacherId) {
        Boolean bool = repository.existsByEducationYearIdAndCreatedBy(educationYearId, teacherId);
        if (bool){
            return new ApiResponse(true,"All things have been excellent!.");
        }
        else {
            List<TeachersFreeHours> hoursList = repository.findAllByCreatedByOrderByCreatedAt(teacherId);
            repository.deleteAll(hoursList);
            return new ApiResponse(false,"You must to create your free hours for retakes..");
        }
    }

    @Override
    public ApiResponse createNewHour(User user, TeachersFreeHoursDto dto) {
        Optional<EducationYear> optionalEducationYear = educationYearRepository.findById(dto.getEducationYearId());
        if (optionalEducationYear.isPresent()) {
            repository.save(new TeachersFreeHours(
                    optionalEducationYear.get(),
                    dto.day,
                    dto.schedule
            ));
            return new ApiResponse(true,"free-hour was created successfully");
        }
        else {
            return new ApiResponse(false,"education year not found by id: "+dto.getEducationYearId());
        }
    }

    @Override
    public ApiResponse deleteFreeHours(User user, String id) {
        Optional<TeachersFreeHours> optional = repository.findByIdAndCreatedBy(id, user.getId());
        if (optional.isPresent()) {
            repository.deleteById(id);
            return new ApiResponse(true, "Teacher's free hour was deleted successfully");
        }
        else {
            return new ApiResponse(false,"Not Found teacher's free hour by id: "+id);
        }
    }

    @Override
    public ApiResponse allFreeHoursForGroup(String educationYearId, String groupId) {
        return new ApiResponse(true,"free hours",repository.getFreeHoursWithSubject(groupId, educationYearId));
    }
}
