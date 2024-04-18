package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notificationOuter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.entity.dekanat.NotificationOuter;
import uz.yeoju.yeoju_app.entity.dekanat.NotificationOuterCounter;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.GetNotificationOuterDto;
import uz.yeoju.yeoju_app.payload.dekanat.NotificationOuterCreateDto;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetStudentNotificationOuters;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationOuterImplService implements NotificationOuterService{
    private final NotificationOuterRepository notificationRepository;
    private final NotificationOuterCounterRepository notificationOuterCounterRepository;
    private final UserRepository userRepository;
    private final DekanatRepository dekanatRepository;
    private final EducationYearRepository educationYearRepository;
    private final FacultyRepository facultyRepository;
    private final GroupRepository groupRepository;

    @Override
    public ApiResponse findAllNotifications() {
        return new ApiResponse(true,"all notifications", notificationRepository.findAll().stream().map(this::generateDto).collect(Collectors.toSet()));
    }

    @Override
    public ApiResponse getAllCounters() {
        return new ApiResponse(true,"all counters",notificationOuterCounterRepository.getAllCounters());
    }

    @Override
    public ApiResponse getStudentNotifications(String studentId) {

        for (GetStudentNotificationOuters outer : notificationRepository.getStudentNotificationOuters(studentId)) {
            Boolean exists = notificationOuterCounterRepository.existsByUserIdAndNotificationOuterId(studentId, outer.getId());
            if (!exists) {
                Optional<User> userOptional = userRepository.findById(studentId);
                if (userOptional.isPresent()){
                    Optional<NotificationOuter> outerOptional = notificationRepository.findById(outer.getId());
                    if (outerOptional.isPresent()){
                        User user = userOptional.get();
                        NotificationOuter notificationOuter = outerOptional.get();
                        Long aLong = notificationOuterCounterRepository.maxQueue();
                        if (aLong==null || aLong<1) {
                            notificationOuterCounterRepository.save(new NotificationOuterCounter(
                                    1L,
                                    user,
                                    notificationOuter
                            ));
                        }
                        else {
                            notificationOuterCounterRepository.save(new NotificationOuterCounter(
                                    aLong+1L,
                                    user,
                                    notificationOuter
                            ));
                        }

                    }
                    else {
                        return new ApiResponse(false,"notification was not found by id: " + outer.getId());
                    }
                }
                else {
                    return new ApiResponse(false,"student not found by id: " + studentId);
                }
            }
        }


        return new ApiResponse(true,"student notifications",notificationRepository.getStudentNotificationOuters2(studentId));
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

    @Override
    public ApiResponse delete(User user, String id) {
        Optional<NotificationOuter> outerOptional = notificationRepository.findById(id);
        if (outerOptional.isPresent()){
            NotificationOuter notificationOuter = outerOptional.get();
            if (Objects.equals(notificationOuter.getCreatedBy(), user.getId())){
                Boolean b = notificationOuterCounterRepository.existsByUserIdAndNotificationOuterId(user.getId(), notificationOuter.getId());
                if (!b) {
                    notificationRepository.deleteById(id);
                    return new ApiResponse(true,"notification was deleted successfully");
                }
                else {
                    return new ApiResponse(false,"You cannot delete this notification. Because this notification was already used other place.");
                }
            }
            else {
                return new ApiResponse(false,"You cannot delete any notification.");
            }
        }
        return new ApiResponse(false,"notification was not found by id: " + id);
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
                    notificationOuter.setDynamicSection(dto.getDynamicSection());
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
                notificationOuter.setQueue(notificationRepository.maxQueue()+1L);
                notificationOuter.setDekanat(dekanat);
                notificationOuter.setEducationYear(educationYear);
                notificationOuter.setFaculties(new HashSet<>(faculties));
                notificationOuter.setGroups(new HashSet<>(groups));
                notificationOuter.setCourse(dto.getCourse());
                notificationOuter.setFromDate(dto.getFromDate());
                notificationOuter.setToDate(dto.getToDate());
                notificationOuter.setDynamicSection(dto.getDynamicSection());
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

    public GetNotificationOuterDto generateDto(NotificationOuter notification){
        return new GetNotificationOuterDto(
            notification.getId(),
            notification.getQueue(),
            notification.getDynamicSection(),
            notification.getDekanat().getName(),
            notification.getEducationYear().getName(),
            notification.getCourse(),
            notification.getFromDate(),
            notification.getToDate(),
            notification.getFaculties().stream().map(Faculty::getShortName).collect(Collectors.toSet()),
            notification.getGroups().stream().map(Group::getName).collect(Collectors.toSet())
        );
    }
}
