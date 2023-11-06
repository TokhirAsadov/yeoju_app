package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTableDB;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;

public interface TimeTableDBService {

    void getTimeTableByWeek(Integer year,Integer week);
    void getTimeTableByWeekMed(Integer year,Integer week);

    ApiResponseTwoObj generateNewTimeTableToDB(String educationYearId,Integer year,Integer week);
    ApiResponseTwoObj generateNewTimeTableToDBMed(String educationYearId,Integer year,Integer week);



}
