package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.permissionForTeacherGrading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.permissionPost.PNotification;
import uz.yeoju.yeoju_app.entity.uquvbulim.PermissionForTeacherGrading;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.resDto.uquvbulim.PermissionForTeacherGradingResDto;
import uz.yeoju.yeoju_app.payload.uquvbulimi.CreatePermissionForTeacherGradingDto;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.uquvbulimi.PermissionForTeacherGradingRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notification.PNotificationService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionForTeacherGradingImplService implements PermissionForTeacherGradingService{
    private final PermissionForTeacherGradingRepository permissionRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PNotificationService notificationService;
    private final LessonRepository subjectRepository;
    private final EducationYearRepository educationYearRepository;


    @Override
    public ApiResponse findAllPermissionsForTeacherGrading() {
        return new ApiResponse(true,"find all permissions",permissionRepository.findAll());
    }
    @Override
    public ApiResponse findAllPermissionsForTeacherGradingByEducationYearIdAndStatus(String educationYearId, PPostStatus status) {

        System.out.println(educationYearId);
        System.out.println(status);
        List<PermissionForTeacherGradingResDto> all = permissionRepository.findAllByStatusAndEducationYearIdOrderByCreatedAt(educationYearId,status.name());
        return new ApiResponse(true,"all permissions by status and education year",all);
    }

    @Override
    public ApiResponse findById(String id) {
        Optional<PermissionForTeacherGrading> optional = permissionRepository.findById(id);
        return optional.map(permissionForTeacherGrading -> new ApiResponse(true, "permission was found by id", permissionForTeacherGrading)).orElseGet(() -> new ApiResponse(false, "not found permission by id: " + id));
    }

    @Override
    public ApiResponse createOrUpdatePermissionsForTeacherGrading(User user,CreatePermissionForTeacherGradingDto dto) {
        if (dto.getId()==null) {
            return save(user,dto);
        }
        else {
            return update(user,dto);
        }
    }

    @Override
    public Set<ApiResponse> changePermissionStatus(User user, Set<CreatePermissionForTeacherGradingDto> dtoSet) {
        Set<ApiResponse> responses = new HashSet<>();
        dtoSet.forEach(dto -> {
            ApiResponse update = update(user, dto);
            responses.add(update);
        });
        return responses;
    }

    @Override
    public ApiResponse getHistory() {
        List<PermissionForTeacherGradingResDto> history = permissionRepository.getHistory();
        return new ApiResponse(true,"history of permission for teacher grading",history);
    }

    @Override
    public ApiResponse getConfirmPermission(User user, String educationYearId, String teacherId, String subjectId, String groupId) {
        return new ApiResponse(true,"your permissions",permissionRepository.getConfirmPermission(educationYearId,teacherId,subjectId,groupId));
    }

    @Override
    public ApiResponse checkExistsPermission(User user, String educationYearId, String teacherId, String subjectId, String groupId, PPostStatus status) {
        Boolean bool = permissionRepository.existsPermissionForTeacherGradingByTeacherIdAndEducationYearIdAndSubjectIdAndGroupIdAndStatus(teacherId, educationYearId, subjectId, groupId, status);
        return new ApiResponse(bool,bool ? "already exists":"not found");
    }


    private ApiResponse update(User user,CreatePermissionForTeacherGradingDto dto) {
        Optional<PermissionForTeacherGrading> optional = permissionRepository.findById(dto.getId());
        if (optional.isPresent()) {
            PermissionForTeacherGrading permission = optional.get();
            Optional<User> optionalUser = userRepository.findById(dto.getTeacherId());
            if (optionalUser.isPresent()) {
                User teacher = optionalUser.get();
                permission.setStatus(dto.getStatus());
                permission.setDeadline(dto.getDeadline());



                if (user.getFullName() == null) {
                    notificationService.save(PNotification.builder()
                            .delivered(false)
                            .content("Your request was "+dto.getStatus().name() + " by " + user.getFirstName().charAt(0)+"."+user.getLastName())
                            .type(dto.getStatus())
                            .userFrom(user)
                            .userTo(teacher).build());
                }
                else {
                    notificationService.save(PNotification.builder()
                            .delivered(false)
                            .content("Your request was "+dto.getStatus().name() + " by " + user.getFullName())
                            .type(dto.getStatus())
                            .userFrom(user)
                            .userTo(teacher).build());
                }


                permissionRepository.save(permission);
                return new ApiResponse(true, "permission was updated successfully");
            }
            else {
                return new ApiResponse(false,"not found teacher by id: ",dto.getTeacherId());
            }
        }
        else {
            return new ApiResponse(false,"permission not found by id: "+dto.getId());
        }
    }

    private ApiResponse save(User user,CreatePermissionForTeacherGradingDto dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getTeacherId());
        if (optionalUser.isPresent()) {
            User teacher = optionalUser.get();

            Optional<Group> groupOptional = groupRepository.findById(dto.getGroupId());
            if(groupOptional.isPresent()) {
                Optional<Lesson> lessonOptional = subjectRepository.findById(dto.getSubjectId());
                if (lessonOptional.isPresent()) {

                    Optional<EducationYear> educationYearOptional = educationYearRepository.findById(dto.getEducationYearId());
                    if (educationYearOptional.isPresent()) {

                        Group group = groupOptional.get();
                        Lesson lesson = lessonOptional.get();
                        EducationYear educationYear = educationYearOptional.get();

                        PermissionForTeacherGrading permission = new PermissionForTeacherGrading();
                        permission.setTeacher(teacher);
                        permission.setGroup(group);
                        permission.setSubject(lesson);
                        permission.setEducationYear(educationYear);
                        permission.setStatus(PPostStatus.AT_PROCESS);

                        String uquvBulimBoshligiId = permissionRepository.getUquvBulimBoshligi();
                        Optional<User> userOptional = userRepository.findById(uquvBulimBoshligiId);
                        if (userOptional.isPresent()) {
                            User uquvBulimBoshligi = userOptional.get();
                            if (user.getFullName() == null) {
                                notificationService.save(PNotification.builder()
                                        .delivered(false)
                                        .content("new permission from " + user.getFirstName().charAt(0)+"."+user.getLastName()+". (S)He is asking for permission to change rated of "+group.getName())
                                        .type(PPostStatus.COMMENT)
                                        .userFrom(user)
                                        .userTo(uquvBulimBoshligi).build());
                            }
                            else {
                                notificationService.save(PNotification.builder()
                                        .delivered(false)
                                        .content("new permission from " +user.getFullName()+". (S)He is asking for permission to change rated of "+group.getName())
                                        .type(PPostStatus.COMMENT)
                                        .userFrom(user)
                                        .userTo(uquvBulimBoshligi).build());
                            }



                            permissionRepository.save(permission);
                            return new ApiResponse(true,"permission was created successfully");
                        }
                        else {
                            return new ApiResponse(false,"not found uquv bulimi boshlig`i");
                        }


                    }
                    else {
                        return new ApiResponse(false,"not found education year by id: ",dto.getEducationYearId());
                    }
                }
                else {
                    return new ApiResponse(false,"not found subject by id: ",dto.getSubjectId());
                }
            }
            else {
                return new ApiResponse(false,"not found group by id: ",dto.getGroupId());
            }

        }
        else {
            return new ApiResponse(false,"not found teacher by id: ",dto.getTeacherId());
        }
    }

}
