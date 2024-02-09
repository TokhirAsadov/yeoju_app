package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeForAttendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.GradeForAttendanceDto;
import uz.yeoju.yeoju_app.repository.module.GradeForAttendanceRepository;

@Service
@RequiredArgsConstructor
public class GradeForAttendanceImplService implements GradeForAttendanceService{
    private final GradeForAttendanceRepository repository;


    @Override
    public ApiResponse createGradeForAttendance(User user, GradeForAttendanceDto dto) {
        return null;
    }
}
