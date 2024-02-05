package uz.yeoju.yeoju_app.service.serviceInterfaces.teachersFreeHours;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.TeachersFreeHoursRepository;

@Service
@RequiredArgsConstructor
public class TeachersFreeHoursImplService implements TeachersFreeHoursService{

    private final TeachersFreeHoursRepository repository;

    @Override
    public ApiResponse getAllHoursByTeacherId(String teacherId) {
        return null;
    }
}
