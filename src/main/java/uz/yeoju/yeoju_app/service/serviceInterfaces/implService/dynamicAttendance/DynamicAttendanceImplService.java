package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dynamicAttendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.DynamicAttendance;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.DynamicAttendanceDto;
import uz.yeoju.yeoju_app.repository.DynamicAttendanceRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DynamicAttendanceImplService implements DynamicAttendanceService {
    private final DynamicAttendanceRepository repository;
    private final UserRepository userRepository;
    @Override
    public ApiResponse createDynamicAttendance(User user, DynamicAttendanceDto dto) {
        Boolean exists = repository.existsByYearAndWeekAndWeekdayAndSectionAndCreatedByAndStudentId(dto.getYear(), dto.getWeek(), dto.getWeekday(), dto.getSection(), user.getId(),dto.getStudentId());
        if (!exists) {
            Optional<User> optionalUser = userRepository.findById(dto.getStudentId());
            if (optionalUser.isPresent()){
                repository.save(new DynamicAttendance(
                        dto.getYear(), dto.getWeek(), dto.getWeekday(), dto.getSection(), dto.getIsCome(), optionalUser.get()
                ));
                return new ApiResponse(true,"Attendance was created successful!.");
            }
            else {
                return new ApiResponse(false,"Not found student by id: " + dto.getStudentId());
            }
        }
        else {
            return new ApiResponse(false,"Attendance already exists");
        }

    }
}
