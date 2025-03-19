package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface ActiveUserStatisticsService {
    ApiResponse createStatistics(User user);
}
