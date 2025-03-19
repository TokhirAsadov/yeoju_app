package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.statistics.ActiveUserStatistics;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.statistics.ActiveUserStatisticsRepository;

@Service
@RequiredArgsConstructor
public class ActiveUserStatisticsImplService implements ActiveUserStatisticsService{
    private final ActiveUserStatisticsRepository repository;

    @Override
    public ApiResponse createStatistics(User user) {
        Integer bool = repository.checkingDataForToday(user.getId());
        if (bool==1){
            ActiveUserStatistics today = repository.getActiveUserByUserIdAndToday(user.getId());
            today.setCount(today.getCount() + 1);
            repository.save(today);
            return new ApiResponse(true,"user is active.");
        }
        else {
            repository.save(new ActiveUserStatistics(user));
            return new ApiResponse(true,"user is active.");
        }
    }
}
