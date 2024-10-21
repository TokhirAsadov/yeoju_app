package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface CardDbStatisticsService {
    ApiResponse getAllClassroomStatistics(Integer year, Integer week,Integer weekday);
    ApiResponse getTotalAllClassroomStatistics(Integer year, Integer week,Integer weekday);
    ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week,Integer weekday,String eduType);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndFaculty(Integer year, Integer week, Integer weekday, String eduType, String faculty);

    ApiResponse getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(Integer year, Integer week, Integer weekday, String eduType, String faculty, Integer course);



}
