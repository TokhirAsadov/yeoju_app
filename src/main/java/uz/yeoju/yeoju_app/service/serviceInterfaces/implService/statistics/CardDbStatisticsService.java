package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface CardDbStatisticsService {
    ApiResponse getAllClassroomStatistics(Integer year, Integer week,Integer weekday);

}
