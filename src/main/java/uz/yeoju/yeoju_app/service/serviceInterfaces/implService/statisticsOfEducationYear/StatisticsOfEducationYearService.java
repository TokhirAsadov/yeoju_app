package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statisticsOfEducationYear;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.WeekEduType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;

public interface StatisticsOfEducationYearService {

    void getTimeTableByWeek(Integer week);
    void getTimeTableByWeek(Integer year,Integer week);

    ApiResponse getStudentsStatisticsForDean(String educationYearId,String groupName);

    ApiResponse getStudentsStatisticsForTeacher(String educationYearId,String teacherPassport,String lessonName,String groupName);
    ApiResponse getGroupsForTeacher(String educationYearId, String teacherPassport, String lessonName, WeekEduType eduType);

    ApiResponse getSubjectOfTeacher(String educationYearId, String passport);

    ApiResponseTwoObj getStatisticsOneGroup(String educationYearId, String groupName, String studentId);
    ApiResponseTwoObj getStatisticsOneLesson(String educationYearId, String groupName, String studentId, String lessonName);

    ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user, String groupName, Integer week, Integer day, Boolean s);
    ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user, String groupName,Integer year, Integer week, Integer day, Boolean s);
    ApiResponseTwoObj getTimesForRoomStatisticsByUserIdAndWeek(User user, String groupName, Integer year,Integer week, Integer day, Boolean s);
    ApiResponse getTeacherTimeTable(User user,Integer week);
    ApiResponseTwoObj getTeacherTimeTableAndStatisticsForKafedra(User user,String kafedraId,Integer year,Integer month, Integer day,Integer week, Integer weekday);

    ApiResponseTwoObj getTimeTableByRoomAndWeek(User user, String room, Integer weekday, Integer week, Integer year);

    ApiResponseTwoObj getTimeTableByAllRoomAndWeek(User user,String building, Integer weekday, Integer week, Integer year,Boolean t);
    ApiResponseTwoObj getTimeTableByAllRoomAndWeek2(User user, Integer weekday, Integer week, Integer year,Boolean t);
}
