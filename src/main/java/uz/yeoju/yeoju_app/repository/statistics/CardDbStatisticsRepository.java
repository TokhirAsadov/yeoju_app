package uz.yeoju.yeoju_app.repository.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.timetableDB.CardDbStatistics;
import uz.yeoju.yeoju_app.payload.resDto.statistics.*;

import java.util.Date;
import java.util.Set;

public interface CardDbStatisticsRepository extends JpaRepository<CardDbStatistics,String> {


    @Query(value = "select * from dbo.GetPassedTeachers(?1,?2,?3) order by section,classroom",nativeQuery = true)
    Set<GetPassedTeacher> getPassedTeachers2(Integer year, Integer week, Integer weekday);

    @Query(value = "select f1.classroom, f1.fullName, f1.kafedra, f1.section, f1.login, f1.cardId, f1.rfid, dbo.GetEarliestTime(f1.cardId, f1.rfid) as time " +
            "from dbo.GetPassedTeachers(?1, ?2, ?3) f1 " +
            "order by f1.section, f1.classroom",
            nativeQuery = true)
    Set<GetPassedTeacher> getPassedTeachers(Integer year, Integer week, Integer weekday);


    @Query(value = "select dbo.GetEarliestTime(?1,?2)",nativeQuery = true)
    Date getEarliestTime(String cardId, String rfid);

    @Query(value = "select * from dbo.GetClassroomAttendance(?1,?2,?3)",nativeQuery = true)
    Set<GetClassroomAttendance> getClassroomAttendance(Integer year, Integer week, Integer weekday);

    //1
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendance(?1,?2,?3,?4)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendance(Integer year, Integer week, Integer weekday,String eduForm);

    //1.2 week
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceWithWeek(?1,?2)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalAllClassroomStatisticsWithWeek(Integer year, Integer week);

    //2
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceByEduType(?1,?2,?3,?4,?5)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceByEduType(Integer year, Integer week, Integer weekday,String eduType,String eduForm);

    //2.2 week
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceWithWeekByEduType(?1,?2,?3)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceWithWeekByEduType(Integer year, Integer week, String eduType);

    //3
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceByEduTypeAndFaculty(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceByEduTypeAndFaculty(Integer year, Integer week, Integer weekday, String eduType,String faculty,String eduForm);

    //3.2 week
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(?1,?2,?3,?4)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(Integer year, Integer week,String eduType,String faculty);

    //4
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(Integer year, Integer week, Integer weekday, String eduType, String faculty, Integer course,String eduForm);

    //4.2 week
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceWithByEduTypeAndFacultyAndCourse(?1,?2,?3,?4,?5)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceWithWeekByEduTypeAndFacultyAndCourse(Integer year, Integer week, String eduType, String faculty, Integer course);

    //5
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceByCourse(?1,?2,?3,?4,?5)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceByCourse(Integer year, Integer week, Integer weekday, Integer course,String eduForm);

    //5.2 week
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceWithWeekByCourse(?1,?2,?3)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalAllClassroomStatisticsWithWeekByCourse(Integer year, Integer week, Integer course);

    //6
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceByFaculty(?1,?2,?3,?4,?5)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceByFaculty(Integer year, Integer week, Integer weekday, String faculty,String eduForm);

    //6.2 week
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceWithWeekByFaculty(?1,?2,?3)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalAllClassroomStatisticsWithWeekByFaculty(Integer year, Integer week, String faculty);

    //7
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceByFacultyAndCourse(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, Integer course,String eduForm);

    //7.2 week
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceWithWeekByFacultyAndCourse(?1,?2,?3,?4)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalAllClassroomStatisticsWithWeekByFacultyAndCourse(Integer year, Integer week, String faculty, Integer course);

    //8
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceByEduTypeAndCourse(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course,String eduForm);

    //8.2 week
    @Query(value = "select Top 1 * from dbo.GetTotalClassroomAttendanceWithWeekByEduTypeAndCourse(?1,?2,?3,?4)",nativeQuery = true)
    GetTotalClassroomAttendance getTotalClassroomAttendanceWithWeekByEduTypeAndCourse(Integer year, Integer week, String eduType, Integer course);


    //9.1
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceWithFaculties(?1,?2,?3) order by shortName",nativeQuery = true)
    Set<GetTotalClassroomAttendanceWithFaculty> getTotalClassroomAttendanceWithFaculties(Integer year, Integer week, Integer weekday);

    //9.2
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceWithFacultiesAndCourse(?1,?2,?3,?4) order by shortName",nativeQuery = true)
    Set<GetTotalClassroomAttendanceWithFaculty> getTotalClassroomAttendanceWithFacultiesAndCourse(Integer year, Integer week, Integer weekday,Integer course);

    //9.3
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceWithFacultiesAndEduType(?1,?2,?3, ?4) order by shortName",nativeQuery = true)
    Set<GetTotalClassroomAttendanceWithFaculty> getTotalClassroomAttendanceWithFacultiesAndEduType(Integer year, Integer week, Integer weekday,String eduType);

    //9.4
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(?1,?2,?3,?4,?5) order by shortName",nativeQuery = true)
    Set<GetTotalClassroomAttendanceWithFaculty> getTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(Integer year, Integer week, Integer weekday,String eduType,Integer course);

    //10.1
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceEveryGroupByFacultyAndEduTypeAndCourse(?1,?2,?3,?4,?5,?6) order by facultyName,course",nativeQuery = true)
    Set<GetTotalClassroomAttendanceEveryGroup> getTotalClassroomAttendanceEveryGroupByFacultyAndEduTypeAndCourse(Integer year, Integer week, Integer weekday,String faculty, String eduType, Integer course);

    //10.2
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceEveryGroupByFacultyAndEduType(?1,?2,?3,?4,?5) order by facultyName,course",nativeQuery = true)
    Set<GetTotalClassroomAttendanceEveryGroup> getTotalClassroomAttendanceEveryGroupByFacultyAndEduType(Integer year, Integer week, Integer weekday,String faculty, String eduType);

    //10.3
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceEveryGroupByFaculty(?1,?2,?3,?4) order by facultyName,course",nativeQuery = true)
    Set<GetTotalClassroomAttendanceEveryGroup> getTotalClassroomAttendanceEveryGroupByFaculty(Integer year, Integer week, Integer weekday,String faculty);

    //10.4
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceEveryGroupByFacultyAndCourse(?1,?2,?3,?4,?5) order by facultyName,course",nativeQuery = true)
    Set<GetTotalClassroomAttendanceEveryGroup> getTotalClassroomAttendanceEveryGroupByFacultyAndCourse(Integer year, Integer week, Integer weekday,String faculty, Integer course);

    //10.5
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceEveryGroupByEduTypeAndCourse(?1,?2,?3,?4,?5) order by facultyName,course",nativeQuery = true)
    Set<GetTotalClassroomAttendanceEveryGroup> getTotalClassroomAttendanceEveryGroupByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course);

    //10.6
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceEveryGroupByEduType(?1,?2,?3,?4) order by facultyName,course",nativeQuery = true)
    Set<GetTotalClassroomAttendanceEveryGroup> getTotalClassroomAttendanceEveryGroupByEduType(Integer year, Integer week, Integer weekday,String eduType);

    //10.7
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceEveryGroupByCourse(?1,?2,?3,?4) order by facultyName,course",nativeQuery = true)
    Set<GetTotalClassroomAttendanceEveryGroup> getTotalClassroomAttendanceEveryGroupByCourse(Integer year, Integer week, Integer weekday,Integer course);

    //10.8
    @Query(value = "select * from dbo.GetTotalClassroomAttendanceEveryGroup(?1,?2,?3) order by facultyName,course",nativeQuery = true)
    Set<GetTotalClassroomAttendanceEveryGroup> getTotalClassroomAttendanceEveryGroup(Integer year, Integer week, Integer weekday);


    //1.11
    @Query(value = "select  * from dbo.GetDateRangeAttendance(?1,?2,?3)",nativeQuery = true)
    Set<GetDateRangeAttendance> getDateRangeAttendance(Date startDate, Date endDate,String eduForm);
    //2.11
    @Query(value = "select * from dbo.GetDateRangeAttendanceByEduType(?1,?2,?3,?4)",nativeQuery = true)
    Set<GetDateRangeAttendance> getDateRangeAttendanceByEduType(Date start,Date end,String eduType,String eduForm);
    //3.11
    @Query(value = "select * from dbo.GetDateRangeAttendanceByCourse(?1,?2,?3,?4)",nativeQuery = true)
    Set<GetDateRangeAttendance> getDateRangeAttendanceByCourse(Date start,Date end,Integer course,String eduForm);
    //4.11
    @Query(value = "select * from dbo.GetDateRangeAttendanceByFaculty(?1,?2,?3,?4)",nativeQuery = true)
    Set<GetDateRangeAttendance> getDateRangeAttendanceByFaculty(Date start,Date end,String faculty,String eduForm);
    //5.11
    @Query(value = "select * from dbo.GetDateRangeAttendanceByEduTypeAndCourse(?1,?2,?3,?4,?5)",nativeQuery = true)
    Set<GetDateRangeAttendance> getDateRangeAttendanceByEduTypeAndCourse(Date start,Date end,String eduType,Integer course,String eduForm);
    //6.11
    @Query(value = "select * from dbo.GetDateRangeAttendanceByEduTypeAndFaculty(?1,?2,?3,?4,?5)",nativeQuery = true)
    Set<GetDateRangeAttendance> getDateRangeAttendanceByEduTypeAndFaculty(Date start,Date end,String eduType,String faculty,String eduForm);
    //7.11
    @Query(value = "select * from dbo.GetDateRangeAttendanceByFacultyAndCourse(?1,?2,?3,?4,?5)",nativeQuery = true)
    Set<GetDateRangeAttendance> getDateRangeAttendanceByFacultyAndCourse(Date start,Date end,String faculty,Integer course,String eduForm);
    //8.11
    @Query(value = "select * from dbo.GetDateRangeAttendanceByEduTypeAndFacultyAndCourse(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    Set<GetDateRangeAttendance> getDateRangeAttendanceByEduTypeAndFacultyAndCourse(Date start,Date end,String eduType,String faculty,Integer course,String eduForm);
}
