package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.statistics.CardDbStatisticsRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CardDbStatisticsImplService implements CardDbStatisticsService{
    private final CardDbStatisticsRepository repository;

    @Override
    public ApiResponse getPassedTeachers(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true,"all passed teachers", repository.getPassedTeachers(year, week, weekday));
    }

    @Override
    public ApiResponse getAllClassroomStatistics(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true,"all classroom statistics", repository.getClassroomAttendance(year, week, weekday));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatistics(Integer year, Integer week, Integer weekday,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics", repository.getTotalClassroomAttendance(year, week, weekday,eduForm));
    }


    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeek(Integer year, Integer week) {
        return new ApiResponse(true,"total all classroom statistics with week", repository.getTotalAllClassroomStatisticsWithWeek(year, week));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week, Integer weekday, String eduType,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by edu type: "+eduType, repository.getTotalClassroomAttendanceByEduType(year, week, weekday,eduType,eduForm));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithWeekByEduType(Integer year, Integer week, String eduType) {
        return new ApiResponse(true,"total all classroom statistics with week by edu type: "+eduType, repository.getTotalClassroomAttendanceWithWeekByEduType(year, week, eduType));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceByEduTypeAndFaculty(Integer year, Integer week, Integer weekday, String eduType, String faculty,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by edu type: "+eduType+" and faculty: "+faculty, repository.getTotalClassroomAttendanceByEduTypeAndFaculty(year, week, weekday,eduType,faculty,eduForm));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(Integer year, Integer week, String eduType, String faculty) {
        return new ApiResponse(true,"total all classroom statistics with week by edu type: "+eduType+" and faculty: "+faculty, repository.getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(year, week,eduType,faculty));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(Integer year, Integer week, Integer weekday, String eduType, String faculty, Integer course,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by edu type: "+eduType+" and faculty: "+faculty+" and course: "+course, repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday,eduType,faculty,course,eduForm));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFacultyAndCourse(Integer year, Integer week, String eduType, String faculty, Integer course) {
        return new ApiResponse(true,"total all classroom statistics by edu type: "+eduType+" and faculty: "+faculty+" and course: "+course, repository.getTotalClassroomAttendanceWithWeekByEduTypeAndFacultyAndCourse(year, week,eduType,faculty,course));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsByCourse(Integer year, Integer week, Integer weekday, Integer course,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by course: "+course, repository.getTotalClassroomAttendanceByCourse(year, week, weekday,course,eduForm));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeekByCourse(Integer year, Integer week, Integer course) {
        return new ApiResponse(true,"total all classroom statistics with week by course: "+course, repository.getTotalAllClassroomStatisticsWithWeekByCourse(year, week,course));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsByFaculty(Integer year, Integer week, Integer weekday, String faculty,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by faculty: "+faculty, repository.getTotalClassroomAttendanceByFaculty(year, week, weekday,faculty,eduForm));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeekByFaculty(Integer year, Integer week, String faculty) {
        return new ApiResponse(true,"total all classroom statistics with week by faculty: "+faculty, repository.getTotalAllClassroomStatisticsWithWeekByFaculty(year, week,faculty));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, Integer course,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by faculty: "+faculty+" and course: "+course, repository.getTotalClassroomAttendanceByFacultyAndCourse(year, week, weekday,faculty,course,eduForm));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeekByFacultyAndCourse(Integer year, Integer week, String faculty, Integer course) {
        return new ApiResponse(true,"total all classroom statistics by faculty: "+faculty+" and course: "+course, repository.getTotalAllClassroomStatisticsWithWeekByFacultyAndCourse(year, week, faculty,course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course,String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by edu type: "+eduType+" and course: "+course, repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday,eduType,course,eduForm));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndCourse(Integer year, Integer week, String eduType, Integer course) {
        return new ApiResponse(true,"total all classroom statistics with week by edu type: "+eduType+" and course: "+course, repository.getTotalClassroomAttendanceWithWeekByEduTypeAndCourse(year, week, eduType,course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithFaculties(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true,"all classroom statistics", repository.getTotalClassroomAttendanceWithFaculties(year, week, weekday));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithFacultiesAndCourse(Integer year, Integer week, Integer weekday, Integer course) {
        return new ApiResponse(true,"all classroom statistics with course: "+course, repository.getTotalClassroomAttendanceWithFacultiesAndCourse(year, week, weekday,course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithFacultiesAndEduType(Integer year, Integer week, Integer weekday, String eduType) {
        return new ApiResponse(true,"all classroom statistics with edu type: "+eduType, repository.getTotalClassroomAttendanceWithFacultiesAndEduType(year, week, weekday,eduType));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course) {
        return new ApiResponse(true,"all classroom statistics with edu type: "+eduType+" and course: "+course, repository.getTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(year, week, weekday,eduType,course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndEduTypeAndCourse(Integer year, Integer week, Integer weekday, String faculty, String eduType, Integer course) {
        return new ApiResponse(true,"all classroom statistics with edu type: "+eduType+" and course: "+course, repository.getTotalClassroomAttendanceEveryGroupByFacultyAndEduTypeAndCourse(year, week, weekday,faculty,eduType,course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, Integer course) {
        return new ApiResponse(true,"all classroom statistics with faculty: "+faculty+" and course: "+course, repository.getTotalClassroomAttendanceEveryGroupByFacultyAndCourse(year, week, weekday,faculty,course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndEduType(Integer year, Integer week, Integer weekday, String faculty, String eduType) {
        return new ApiResponse(true,"all classroom statistics with edu type: "+eduType+" and faculty: "+faculty, repository.getTotalClassroomAttendanceEveryGroupByFacultyAndEduType(year, week, weekday,faculty,eduType));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course) {
        return new ApiResponse(true,"all classroom statistics with edu type: "+eduType+" and course: "+course, repository.getTotalClassroomAttendanceEveryGroupByEduTypeAndCourse(year, week, weekday,eduType,course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByFaculty(Integer year, Integer week, Integer weekday, String faculty) {
        return new ApiResponse(true,"all classroom statistics with faculty: "+faculty, repository.getTotalClassroomAttendanceEveryGroupByFaculty(year, week, weekday,faculty));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByEduType(Integer year, Integer week, Integer weekday, String eduType) {
        return new ApiResponse(true,"all classroom statistics with edu type: "+eduType, repository.getTotalClassroomAttendanceEveryGroupByEduType(year, week, weekday,eduType));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByCourse(Integer year, Integer week, Integer weekday, Integer course) {
        return new ApiResponse(true,"all classroom statistics with course: "+course, repository.getTotalClassroomAttendanceEveryGroupByCourse(year, week, weekday,course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroup(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true,"all classroom statistics", repository.getTotalClassroomAttendanceEveryGroup(year, week, weekday));
    }

    @Override
    public ApiResponse getDateRangeAttendance(Date start, Date end,String eduForm) {
        return new ApiResponse(true,"data", repository.getDateRangeAttendance(start, end,eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByEduType(Date start, Date end, String eduType,String eduForm) {
        return new ApiResponse(true,"data", repository.getDateRangeAttendanceByEduType(start, end,eduType,eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByCourse(Date start, Date end, Integer course,String eduForm) {
        return new ApiResponse(true,"data", repository.getDateRangeAttendanceByCourse(start, end,course,eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByFaculty(Date start, Date end, String faculty,String eduForm) {
        return new ApiResponse(true,"data", repository.getDateRangeAttendanceByFaculty(start, end,faculty,eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByEduTypeAndCourse(Date start, Date end, String eduType, Integer course,String eduForm) {
        return new ApiResponse(true,"data", repository.getDateRangeAttendanceByEduTypeAndCourse(start, end,eduType,course,eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByEduTypeAndFaculty(Date start, Date end, String eduType, String faculty,String eduForm) {
        return new ApiResponse(true,"data", repository.getDateRangeAttendanceByEduTypeAndFaculty(start, end,eduType,faculty,eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByFacultyAndCourse(Date start, Date end, String faculty, Integer course,String eduForm) {
        return new ApiResponse(true,"data", repository.getDateRangeAttendanceByFacultyAndCourse(start, end,faculty,course,eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByEduTypeAndFacultyAndCourse(Date start, Date end, String eduType, String faculty, Integer course,String eduForm) {
        return new ApiResponse(true,"data", repository.getDateRangeAttendanceByEduTypeAndFacultyAndCourse(start, end,eduType,faculty,course,eduForm));
    }
}
