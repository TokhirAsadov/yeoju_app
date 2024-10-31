package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface CardDbStatisticsService {
    ApiResponse getPassedTeachers(Integer year, Integer week,Integer weekday);
    ApiResponse getAllClassroomStatistics(Integer year, Integer week,Integer weekday);
    ApiResponse getTotalAllClassroomStatistics(Integer year, Integer week,Integer weekday);
    ApiResponse getTotalAllClassroomStatisticsWithWeek(Integer year, Integer week);
    ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week,Integer weekday,String eduType);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduType(Integer year, Integer week,String eduType);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndFaculty(Integer year, Integer week, Integer weekday, String eduType, String faculty);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(Integer year, Integer week, String eduType, String faculty);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(Integer year, Integer week, Integer weekday, String eduType, String faculty, Integer course);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFacultyAndCourse(Integer year, Integer week, String eduType, String faculty, Integer course);

    ApiResponse getTotalAllClassroomStatisticsByCourse(Integer year, Integer week, Integer weekday, Integer course);
    ApiResponse getTotalAllClassroomStatisticsWithWeekByCourse(Integer year, Integer week, Integer course);

    ApiResponse getTotalAllClassroomStatisticsByFaculty(Integer year, Integer week, Integer weekday, String faculty);
    ApiResponse getTotalAllClassroomStatisticsWithWeekByFaculty(Integer year, Integer week, String faculty);

    ApiResponse getTotalAllClassroomStatisticsByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, Integer course);
    ApiResponse getTotalAllClassroomStatisticsWithWeekByFacultyAndCourse(Integer year, Integer week, String faculty, Integer course);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndCourse(Integer year, Integer week, String eduType, Integer course);

    ApiResponse getTotalClassroomAttendanceWithFaculties(Integer year, Integer week, Integer weekday);
    ApiResponse getTotalClassroomAttendanceWithFacultiesAndCourse(Integer year, Integer week, Integer weekday,Integer course);
    ApiResponse getTotalClassroomAttendanceWithFacultiesAndEduType(Integer year, Integer week, Integer weekday,String eduType);
    ApiResponse getTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(Integer year, Integer week, Integer weekday,String eduType,Integer course);


}
