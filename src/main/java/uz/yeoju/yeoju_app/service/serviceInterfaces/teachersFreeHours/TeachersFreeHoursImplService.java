package uz.yeoju.yeoju_app.service.serviceInterfaces.teachersFreeHours;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.teacher.TeachersFreeHours;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.teacher.TeachersFreeHoursDto;
import uz.yeoju.yeoju_app.repository.TeachersFreeHoursRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeachersFreeHoursImplService implements TeachersFreeHoursService{

    private final TeachersFreeHoursRepository repository;
    private final EducationYearRepository educationYearRepository;

    @Override
    public ApiResponse getAllHoursByTeacherId(String teacherId) {
        return null;
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
}
