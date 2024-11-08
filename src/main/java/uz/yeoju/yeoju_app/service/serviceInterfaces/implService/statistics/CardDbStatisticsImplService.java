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


}
