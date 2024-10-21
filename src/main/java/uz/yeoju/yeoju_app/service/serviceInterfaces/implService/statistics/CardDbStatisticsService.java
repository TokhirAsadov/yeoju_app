package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface CardDbStatisticsService {
    ApiResponse getAllClassroomStatistics(Integer year, Integer week,Integer weekday);
    ApiResponse getTotalAllClassroomStatistics(Integer year, Integer week,Integer weekday);
    ApiResponse getTotalAllClassroomStatisticsWithWeek(Integer year, Integer week);
    ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week,Integer weekday,String eduType);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduType(Integer year, Integer week,String eduType);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndFaculty(Integer year, Integer week, Integer weekday, String eduType, String faculty);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(Integer year, Integer week, String eduType, String faculty);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(Integer year, Integer week, Integer weekday, String eduType, String faculty, Integer course);

    ApiResponse getTotalAllClassroomStatisticsByCourse(Integer year, Integer week, Integer weekday, Integer course);

    ApiResponse getTotalAllClassroomStatisticsByFaculty(Integer year, Integer week, Integer weekday, String faculty);

    ApiResponse getTotalAllClassroomStatisticsByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, Integer course);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course);

}
