package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;

public interface TimeTableByWeekOfYearService {

    void getTimeTableByWeek(Integer week);

    ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user, String groupName, Integer week, Integer day, Boolean s);
    ApiResponseTwoObj getTimesForRoomStatisticsByUserIdAndWeek(User user, String groupName, Integer year,Integer week, Integer day, Boolean s);
    ApiResponse getTeacherTimeTable(User user,Integer week);
    ApiResponseTwoObj getTeacherTimeTableAndStatisticsForKafedra(User user,String kafedraId,Integer year,Integer month, Integer day,Integer week, Integer weekday);

    ApiResponseTwoObj getTimeTableByRoomAndWeek(User user, String room, Integer weekday, Integer week, Integer year);

    ApiResponseTwoObj getTimeTableByAllRoomAndWeek(User user,String building, Integer weekday, Integer week, Integer year,Boolean t);
    ApiResponseTwoObj getTimeTableByAllRoomAndWeek2(User user, Integer weekday, Integer week, Integer year,Boolean t);
}
