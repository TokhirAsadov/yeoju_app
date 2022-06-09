package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

import java.text.ParseException;
import java.time.LocalDateTime;

public interface AccMonitoringLogImplService<T> extends MainService<T> {
    ApiResponse countComeUsersBetweenDate(LocalDateTime time, String roleId);
    ApiResponse countComeUsersOneDay(LocalDateTime startTime,LocalDateTime endTime, String roleId);
}
