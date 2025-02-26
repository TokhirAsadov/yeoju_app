package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import uz.yeoju.yeoju_app.payload.ApiResponse;

import java.util.Date;

public interface CardDbStatisticsService {
    ApiResponse getPassedTeachers(Integer year, Integer week,Integer weekday);
    ApiResponse getAllClassroomStatistics(Integer year, Integer week,Integer weekday);
    ApiResponse getTotalAllClassroomStatistics(Integer year, Integer week,Integer weekday,String eduForm);
    ApiResponse getTotalAllClassroomStatisticsWithWeek(Integer year, Integer week);
    ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week,Integer weekday,String eduType,String eduForm);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduType(Integer year, Integer week,String eduType);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndFaculty(Integer year, Integer week, Integer weekday, String eduType, String faculty,String eduForm);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(Integer year, Integer week, String eduType, String faculty);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(Integer year, Integer week, Integer weekday, String eduType, String faculty, Integer course,String eduForm);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFacultyAndCourse(Integer year, Integer week, String eduType, String faculty, Integer course);

    ApiResponse getTotalAllClassroomStatisticsByCourse(Integer year, Integer week, Integer weekday, Integer course,String eduForm);
    ApiResponse getTotalAllClassroomStatisticsWithWeekByCourse(Integer year, Integer week, Integer course);

    ApiResponse getTotalAllClassroomStatisticsByFaculty(Integer year, Integer week, Integer weekday, String faculty,String eduForm);
    ApiResponse getTotalAllClassroomStatisticsWithWeekByFaculty(Integer year, Integer week, String faculty);

    ApiResponse getTotalAllClassroomStatisticsByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, Integer course,String eduForm);
    ApiResponse getTotalAllClassroomStatisticsWithWeekByFacultyAndCourse(Integer year, Integer week, String faculty, Integer course);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course,String eduForm);
    ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndCourse(Integer year, Integer week, String eduType, Integer course);

    ApiResponse getTotalClassroomAttendanceWithFaculties(Integer year, Integer week, Integer weekday);
    ApiResponse getTotalClassroomAttendanceWithFacultiesAndCourse(Integer year, Integer week, Integer weekday,Integer course);
    ApiResponse getTotalClassroomAttendanceWithFacultiesAndEduType(Integer year, Integer week, Integer weekday,String eduType);
    ApiResponse getTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(Integer year, Integer week, Integer weekday,String eduType,Integer course);

    ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndEduTypeAndCourse(Integer year, Integer week, Integer weekday,String faculty,String eduType,Integer course);
    ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndCourse(Integer year, Integer week, Integer weekday,String faculty,Integer course);
    ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndEduType(Integer year, Integer week, Integer weekday,String faculty,String eduType);
    ApiResponse getTotalClassroomAttendanceEveryGroupByEduTypeAndCourse(Integer year, Integer week, Integer weekday,String eduType,Integer course);
    ApiResponse getTotalClassroomAttendanceEveryGroupByFaculty(Integer year, Integer week, Integer weekday,String faculty);
    ApiResponse getTotalClassroomAttendanceEveryGroupByEduType(Integer year, Integer week, Integer weekday,String eduType);
    ApiResponse getTotalClassroomAttendanceEveryGroupByCourse(Integer year, Integer week, Integer weekday,Integer course);
    ApiResponse getTotalClassroomAttendanceEveryGroup(Integer year, Integer week, Integer weekday);

    ApiResponse getDateRangeAttendance(Date start,Date end,String eduForm);
    ApiResponse getDateRangeAttendanceByEduType(Date start,Date end,String eduType,String eduForm);
    ApiResponse getDateRangeAttendanceByCourse(Date start,Date end,Integer course,String eduForm);
    ApiResponse getDateRangeAttendanceByFaculty(Date start,Date end,String faculty,String eduForm);
    ApiResponse getDateRangeAttendanceByEduTypeAndCourse(Date start,Date end,String eduType,Integer course,String eduForm);
    ApiResponse getDateRangeAttendanceByEduTypeAndFaculty(Date start,Date end,String eduType,String faculty,String eduForm);
    ApiResponse getDateRangeAttendanceByFacultyAndCourse(Date start,Date end,String faculty,Integer course,String eduForm);
    ApiResponse getDateRangeAttendanceByEduTypeAndFacultyAndCourse(Date start,Date end,String eduType,String faculty,Integer course,String eduForm);
}
