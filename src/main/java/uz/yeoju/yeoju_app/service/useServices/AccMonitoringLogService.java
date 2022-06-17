package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.AccMonitorLog;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ComeUserCount;
import uz.yeoju.yeoju_app.repository.AccMonitoringLogRepo;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.AccMonitoringLogImplService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccMonitoringLogService implements AccMonitoringLogImplService<AccMonitorLog> {
    private final AccMonitoringLogRepo accMonitoringLogRepo;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public ApiResponse countUsersByRoleIdAndWeekOrMonth(String roleId,Integer weekOrMonth){
        Long comeUserCount = accMonitoringLogRepo.countUsersByRoleIdAndWeekOrMonth(roleId, weekOrMonth);
        Long allUsers = userRepository.countUsersByRoleId(roleId);
        return new ApiResponse(
                true,
                "all "+roleRepository.findById(roleId).get().getRoleName()+" -->  ",
                new ComeUserCount(comeUserCount, allUsers)
        );
    }


    @Override
    public ApiResponse countComeUsersBetweenDate(LocalDateTime time, String roleId){
        LocalDateTime startTime = LocalDateTime.of(time.getYear(),time.getMonth(),time.getDayOfMonth(), 0, 0);
        Long comeUserCount = accMonitoringLogRepo.countUsersByRoleIdAndBetweenDate(roleId, startTime, time);
        Long allUsers = userRepository.countUsersByRoleId(roleId);
        return new ApiResponse(
                true,
                "all "+roleRepository.findById(roleId).get().getRoleName()+" -->  ",
                new ComeUserCount(comeUserCount, allUsers)
        );
    }

    @Override
    public ApiResponse countComeUsersOneDay(LocalDateTime startTime,LocalDateTime endTime, String roleId){
        Long comeUserCount = accMonitoringLogRepo.countUsersByRoleIdAndBetweenDate(roleId, startTime, endTime);
        Long allUsers = userRepository.countUsersByRoleId(roleId);
        return new ApiResponse(
                true,
                "all "+roleRepository.findById(roleId).get().getRoleName()+" -->  ",
                new ComeUserCount(comeUserCount, allUsers)
        );
    }


    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(String id) {
        return null;
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(AccMonitorLog accMonitorLog) {
        return null;
    }

    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }

}
