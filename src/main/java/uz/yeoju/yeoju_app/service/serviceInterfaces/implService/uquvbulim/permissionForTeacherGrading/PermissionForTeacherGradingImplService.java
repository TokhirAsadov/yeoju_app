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


}
