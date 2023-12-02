package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notificationOuter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.entity.dekanat.NotificationOuter;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.NotificationOuterCreateDto;
import uz.yeoju.yeoju_app.repository.DekanatRepository;
import uz.yeoju.yeoju_app.repository.FacultyRepository;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.NotificationOuterRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationOuterImplService implements NotificationOuterService{
    private final NotificationOuterRepository notificationRepository;
    private final DekanatRepository dekanatRepository;
    private final EducationYearRepository educationYearRepository;
    private final FacultyRepository facultyRepository;
    private final GroupRepository groupRepository;

    @Override
    public ApiResponse findAllNotifications() {
        return new ApiResponse(true,"all notifications", notificationRepository.findAll());
    }

    @Override
    public ApiResponse getStudentNotifications(String studentId) {
        return new ApiResponse(true,"student notifications",notificationRepository.getStudentNotificationOuters(studentId));
    }

    @Override
    public ApiResponse createAndUpdate(NotificationOuterCreateDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(NotificationOuterCreateDto dto){
        Optional<NotificationOuter> outerOptional = notificationRepository.findById(dto.getId());
        if (outerOptional.isPresent()){
            Optional<Dekanat> optionalDekanat = dekanatRepository.findById(dto.getDekanatId());
            if (optionalDekanat.isPresent()){
                Optional<EducationYear> optionalEducationYear = educationYearRepository.findById(dto.getEducationId());
                if (optionalEducationYear.isPresent()){
                    Dekanat dekanat = optionalDekanat.get();
                    EducationYear educationYear = optionalEducationYear.get();
                    List<Faculty> faculties = facultyRepository.findAllById(dto.facultiesId);
                    List<Group> groups = groupRepository.findAllById(dto.getGroupsId());

                    NotificationOuter notificationOuter = outerOptional.get();
                    notificationOuter.setDekanat(dekanat);
                    notificationOuter.setEducationYear(educationYear);
                    notificationOuter.setFaculties(new HashSet<>(faculties));
                    notificationOuter.setGroups(new HashSet<>(groups));
                    notificationOuter.setCourse(dto.getCourse());
                    notificationOuter.setFromDate(dto.getFromDate());
                    notificationOuter.setToDate(dto.getToDate());
                    notificationRepository.save(notificationOuter);
                    return new ApiResponse(true,"notification was updated successful");
                }
                else {
                    return new ApiResponse(false,"education year was not found by id: " + dto.getEducationId());
                }
            }
            else {
                return new ApiResponse(false,"dekanat was not found by id: " + dto.getDekanatId());
            }
        }
        else {
            return new ApiResponse(false,"notification was not found by id: " + dto.getId());
        }

    }

    public ApiResponse save(NotificationOuterCreateDto dto){
        Optional<Dekanat> optionalDekanat = dekanatRepository.findById(dto.getDekanatId());
        if (optionalDekanat.isPresent()){
            Optional<EducationYear> optionalEducationYear = educationYearRepository.findById(dto.getEducationId());
            if (optionalEducationYear.isPresent()){
                Dekanat dekanat = optionalDekanat.get();
                EducationYear educationYear = optionalEducationYear.get();
                List<Faculty> faculties = facultyRepository.findAllById(dto.facultiesId);
                List<Group> groups = groupRepository.findAllById(dto.getGroupsId());

                NotificationOuter notificationOuter = new NotificationOuter();
                notificationOuter.setDekanat(dekanat);
                notificationOuter.setEducationYear(educationYear);
                notificationOuter.setFaculties(new HashSet<>(faculties));
                notificationOuter.setGroups(new HashSet<>(groups));
                notificationOuter.setCourse(dto.getCourse());
                notificationOuter.setFromDate(dto.getFromDate());
                notificationOuter.setToDate(dto.getToDate());
                notificationRepository.save(notificationOuter);
                return new ApiResponse(true,"notification was created successful");
            }
            else {
                return new ApiResponse(false,"education year was not found by id: " + dto.getEducationId());
            }
        }
        else {
            return new ApiResponse(false,"dekanat was not found by id: " + dto.getDekanatId());
        }
    }
}
