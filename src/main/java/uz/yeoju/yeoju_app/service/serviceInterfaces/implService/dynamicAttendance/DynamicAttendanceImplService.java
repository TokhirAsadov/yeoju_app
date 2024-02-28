package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dynamicAttendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.DynamicAttendance;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.DynamicAttendanceDto;
import uz.yeoju.yeoju_app.payload.MultiDynamicAttendance2Dto;
import uz.yeoju.yeoju_app.payload.MultiDynamicAttendanceDto;
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
        if (dto.id==null) {
            Boolean exists = repository.existsByYearAndWeekAndWeekdayAndSectionAndCreatedByAndStudentIdAndRoom(dto.getYear(), dto.getWeek(), dto.getWeekday(), dto.getSection(), user.getId(), dto.getStudentId(), dto.room);
            if (!exists) {
                Optional<User> optionalUser = userRepository.findById(dto.getStudentId());
                if (optionalUser.isPresent()) {
                    repository.save(new DynamicAttendance(
                            dto.getYear(), dto.getWeek(), dto.getWeekday(), dto.getSection(), dto.getIsCome(), dto.room, optionalUser.get()
                    ));
                    return new ApiResponse(true, "Attendance was created successful!.");
                } else {
                    return new ApiResponse(false, "Not found student by id: " + dto.getStudentId());
                }
            } else {
                return new ApiResponse(false, "Attendance already exists");
            }
        }
        else {
            Optional<DynamicAttendance> optionalDynamicAttendance = repository.findById(dto.id);
            if (optionalDynamicAttendance.isPresent()) {
                Optional<User> optionalUser = userRepository.findById(dto.getStudentId());
                if (optionalUser.isPresent()) {
                    DynamicAttendance dynamicAttendance = optionalDynamicAttendance.get();
                    dynamicAttendance.setYear(dto.getYear());
                    dynamicAttendance.setWeek(dto.getWeek());
                    dynamicAttendance.setWeekday(dto.getWeekday());
                    dynamicAttendance.setSection(dto.getSection());
                    dynamicAttendance.setIsCome(dto.getIsCome());
                    dynamicAttendance.setRoom(dto.getRoom());
                    dynamicAttendance.setStudent(optionalUser.get());
                    repository.save(dynamicAttendance);
                    return new ApiResponse(true, "Attendance was updated successful!.");
                } else {
                    return new ApiResponse(false, "Not found student by id: " + dto.getStudentId());
                }
            }
            else {
                return new ApiResponse(false, "Attendance not found by id: "+ dto.id);
            }
        }

    }

    @Override
    @Transactional
    public ApiResponse createMultiDynamicAttendance(User user, MultiDynamicAttendanceDto dto) {
        dto.getStudentsIds().forEach(id ->{
            Boolean exists = repository.existsByYearAndWeekAndWeekdayAndSectionAndCreatedByAndStudentIdAndRoom(dto.getYear(), dto.getWeek(), dto.getWeekday(), dto.getSection(), user.getId(),id, dto.room);
            if (!exists) {
                Optional<User> optionalUser = userRepository.findById(id);
                if (optionalUser.isPresent()){
                    repository.save(new DynamicAttendance(
                            dto.getYear(), dto.getWeek(), dto.getWeekday(), dto.getSection(), dto.getIsCome(), dto.room, optionalUser.get()
                    ));
                }
                else {
                    throw new UserNotFoundException("Not found student by id: " + id);
                }
            }
            else {
                throw new UserNotFoundException("Attendance already exists");
            }
        });

        return new ApiResponse(true,"Attendances were created successful!.");
    }

    @Override
    public ApiResponse createMultiDynamicAttendance2(User user, MultiDynamicAttendance2Dto dto) {
        return null;
    }
}
