package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.statistics.CardDbStatisticsRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CardDbStatisticsImplService implements CardDbStatisticsService{
    private final CardDbStatisticsRepository repository;

    @Override
    public ApiResponse getPassedTeachers(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true,"all passed teachers", repository.getPassedTeachers(year, week, weekday));
    }

    @Override
    public ApiResponse getAllClassroomStatistics(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true,"all classroom statistics", repository.getClassroomAttendance(year, week, weekday));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatistics(Integer year, Integer week, Integer weekday,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics", repository.getTotalClassroomAttendance(year, week, weekday,eduForm));
    }


    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeek(Integer year, Integer week) {
        return new ApiResponse(true,"total all classroom statistics with week", repository.getTotalAllClassroomStatisticsWithWeek(year, week));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week, Integer weekday, String eduType,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by edu type: "+eduType, repository.getTotalClassroomAttendanceByEduType(year, week, weekday,eduType,eduForm));
    }


}
