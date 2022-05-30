package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

import java.text.ParseException;

public interface AccMonitoringLogImplService<T> extends MainService<T> {
    ApiResponse findBeforeDate(String date) throws ParseException;
}
