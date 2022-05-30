package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.AccMonitorLog;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.AccMonitoringLogRepo;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.AccMonitoringLogImplService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccMonitoringLogService implements AccMonitoringLogImplService<AccMonitorLog> {
    private final AccMonitoringLogRepo accMonitoringLogRepo;

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

    @Override
    public ApiResponse findBeforeDate(String date) throws ParseException {
        List<AccMonitorLog> beforeDate = accMonitoringLogRepo.findBeforeDate(
                new SimpleDateFormat("yyyy-MM-dd").parse(date));
        beforeDate.forEach(System.out::println);

        return new ApiResponse(true,"success",beforeDate);
    }
}
