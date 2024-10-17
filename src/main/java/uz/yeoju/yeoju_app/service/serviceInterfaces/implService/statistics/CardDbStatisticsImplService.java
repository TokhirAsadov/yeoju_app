package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.statistics.CardDbStatisticsRepository;

@Service
@RequiredArgsConstructor
public class CardDbStatisticsImplService implements CardDbStatisticsService{
    private final CardDbStatisticsRepository repository;

}
