package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.*;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetLessonStatistics31;

import java.util.List;

public interface TimeTableByWeekOfYearService {

    void getTimeTableByWeek(Integer week);
    void getTimeTableByWeek(Integer year,Integer week);
    void getTimeTableByWeekMed(Integer year,Integer week);

    ApiResponseTwoObj getStatisticsForDeanDashboard(String educationYearId,String eduType,String eduTypeId,String facultyId,String facultyShortName);

    ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user, String groupName, Integer week, Integer day, Boolean s);
    ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user, String groupName,Integer year, Integer week, Integer day, Boolean s);
    ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(String userId, String groupName,Integer year, Integer week, Integer day, Boolean s);
    ApiResponseTwoObj getTimesForRoomStatisticsByUserIdAndWeek(User user, String groupName, Integer year,Integer week, Integer day, Boolean s);
    ApiResponse getTeacherTimeTable(User user,Integer week,Integer year);
    ApiResponse getTeacherTimeTableToday(User user,Integer week,Integer year);
    ApiResponseTwoObj getTeacherTimeTableAndStatisticsForKafedra(User user,String kafedraId,Integer year,Integer month, Integer day,Integer week, Integer weekday);

    ApiResponseTwoObj getTimeTableByRoomAndWeek(User user, String room, Integer weekday, Integer week, Integer year);

    ApiResponseTwoObj getTimeTableByAllRoomAndWeek(User user,String building, Integer weekday, Integer week, Integer year,Boolean t);
    ApiResponseTwoObj getTimeTableByAllRoomAndWeek2(User user, Integer weekday, Integer week, Integer year,Boolean t);
    ApiResponseTwoObj getKafedraKunlikVaHaftalikStatistikasi2(User user,String kafedraId,Integer year,Integer month, Integer day,Integer week, Integer weekday);
    ApiResponseTwoObj getKafedraKunlikVaHaftalikStatistikasi5(User user, String kafedraId, Integer year, Integer month, Integer day, Integer week, Integer weekday);
    ApiResponseStats getKafedraKunlikVaHaftalikStatistikasi6(User user, String kafedraId, Integer year, Integer month, Integer day, Integer week, Integer weekday);
    List<ApiResponseStats2> getKafedraKunlikVaHaftalikStatistikasi7(User user, Integer year, Integer month, Integer day, Integer week, Integer weekday);
    List<ApiResponseStats2> getKafedraKunlikVaHaftalikStatistikasi9(User user, Integer year, Integer month, Integer day, Integer week, Integer weekday);
    List<ApiResponseStats2> getKafedraKunlikVaHaftalikStatistikasi8(User user, Integer year, Integer month, Integer day, Integer week, Integer weekday);
    GetLessonStatistics31 getTeacherMonthlyStatistics(String teacherId, Integer year, Integer month);
    ApiResponseStats3 getTeacherDailyOrMonthlyStatistics(String teacherId, Integer year, Integer month, Integer day);
}
