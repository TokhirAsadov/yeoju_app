package uz.yeoju.yeoju_app.controller.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics.CardDbStatisticsService;

import java.util.Date;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/statistics")
@RequiredArgsConstructor
public class CardDbStatisticsController {
    private final CardDbStatisticsService service;

    @GetMapping("/total")
    HttpEntity<?> getTotalStatistics(
            @RequestParam("year") Integer year,
            @RequestParam("week") Integer week,
            @RequestParam("weekday") Integer weekday,
            @RequestParam("eduForm") String eduForm,
            @RequestParam(value = "eduType",required = false) String eduType,
            @RequestParam(value = "faculty",required = false) String faculty,
            @RequestParam(value = "course",required = false) Integer course

            ) {
        if (eduType == null && faculty == null && course == null) {
            return ResponseEntity.ok(service.getTotalAllClassroomStatistics(year, week, weekday,eduForm));
        }
        if (eduType == null && faculty == null) {
            return ResponseEntity.ok(service.getTotalAllClassroomStatisticsByCourse(year, week, weekday, course,eduForm));
        }
        if (eduType == null && course == null) {
            return ResponseEntity.ok(service.getTotalAllClassroomStatisticsByFaculty(year, week, weekday, faculty,eduForm));
        }
        if (eduType == null) {
            return ResponseEntity.ok(service.getTotalAllClassroomStatisticsByFacultyAndCourse(year, week, weekday, faculty, course,eduForm));
        }
        if (faculty == null && course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceByEduType(year, week, weekday, eduType,eduForm));
        }
        if (faculty == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, course,eduForm));
        }
        if (course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceByEduTypeAndFaculty(year, week, weekday, eduType, faculty,eduForm));
        }

        return ResponseEntity.ok(service.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, course,eduForm));

    }


    @GetMapping("/total2")
        HttpEntity<?> getTotalStatistics(
                @RequestParam("year") Integer year,
                @RequestParam("week") Integer week,
                @RequestParam("weekday") Integer weekday,
                @RequestParam("eduForm") String eduForm,
                @RequestParam(value = "eduType",required = false) String eduType,
                @RequestParam(value = "faculty",required = false) String faculty,
                @RequestParam(value = "courses",required = false) String courses

                ) {
            if (eduType == null && faculty == null && courses == null) {
                return ResponseEntity.ok(service.getTotalAllClassroomStatistics(year, week, weekday,eduForm));
            }
            if (eduType == null && faculty == null) {
                return ResponseEntity.ok(service.getTotalAllClassroomStatisticsByCourse(year, week, weekday, courses,eduForm));
            }
            if (eduType == null && courses == null) {
                return ResponseEntity.ok(service.getTotalAllClassroomStatisticsByFaculty(year, week, weekday, faculty,eduForm));
            }
            if (eduType == null) {
                return ResponseEntity.ok(service.getTotalAllClassroomStatisticsByFacultyAndCourse(year, week, weekday, faculty, courses,eduForm));
            }
            if (faculty == null && courses == null) {
                return ResponseEntity.ok(service.getTotalClassroomAttendanceByEduType(year, week, weekday, eduType,eduForm));
            }
            if (faculty == null) {
                return ResponseEntity.ok(service.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, courses,eduForm));
            }
            if (courses == null) {
                return ResponseEntity.ok(service.getTotalClassroomAttendanceByEduTypeAndFaculty(year, week, weekday, eduType, faculty,eduForm));
            }

            return ResponseEntity.ok(service.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, courses,eduForm));

        }

    @GetMapping("/totalBetween")
    HttpEntity<?> getTotalStatisticsBetween(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam("eduForm") String eduForm,
            @RequestParam(value = "eduType",required = false) String eduType,
            @RequestParam(value = "faculty",required = false) String faculty,
            @RequestParam(value = "course",required = false) Integer course

    ) {
        if (eduType == null && faculty == null && course == null) {
            return ResponseEntity.ok(service.getDateRangeAttendance(start, end,eduForm));
        }
        if (eduType == null && faculty == null) {
            return ResponseEntity.ok(service.getDateRangeAttendanceByCourse(start, end, course,eduForm));
        }
        if (eduType == null && course == null) {
            return ResponseEntity.ok(service.getDateRangeAttendanceByFaculty(start, end, faculty,eduForm));
        }
        if (eduType == null) {
            return ResponseEntity.ok(service.getDateRangeAttendanceByFacultyAndCourse(start, end, faculty, course,eduForm));
        }
        if (faculty == null && course == null) {
            return ResponseEntity.ok(service.getDateRangeAttendanceByEduType(start, end, eduType,eduForm));
        }
        if (faculty == null) {
            return ResponseEntity.ok(service.getDateRangeAttendanceByEduTypeAndCourse(start, end, eduType, course,eduForm));
        }
        if (course == null) {
            return ResponseEntity.ok(service.getDateRangeAttendanceByEduTypeAndFaculty(start, end, eduType, faculty,eduForm));
        }

        return ResponseEntity.ok(service.getDateRangeAttendanceByEduTypeAndFacultyAndCourse(start, end, eduType, faculty, course,eduForm));

    }

    @GetMapping("/totalTable")
    HttpEntity<?> getTotalTableStatistics(
            @RequestParam("year") Integer year,
            @RequestParam("week") Integer week,
            @RequestParam("weekday") Integer weekday,
            @RequestParam(value = "eduType",required = false) String eduType,
            @RequestParam(value = "faculty",required = false) String faculty,
            @RequestParam(value = "course",required = false) Integer course

    ) {
        if (eduType == null && faculty == null && course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceEveryGroup(year, week, weekday));
        }
        if (eduType == null && faculty == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceEveryGroupByCourse(year, week, weekday, course));
        }
        if (eduType == null && course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceEveryGroupByFaculty(year, week, weekday, faculty));
        }
        if (eduType == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceEveryGroupByFacultyAndCourse(year, week, weekday, faculty, course));
        }
        if (faculty == null && course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceEveryGroupByEduType(year, week, weekday, eduType));
        }
        if (faculty == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceEveryGroupByEduTypeAndCourse(year, week, weekday, eduType, course));
        }
        if (course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceEveryGroupByFacultyAndEduType(year, week, weekday, faculty, eduType));
        }

        return ResponseEntity.ok(service.getTotalClassroomAttendanceEveryGroupByFacultyAndEduTypeAndCourse(year, week, weekday, faculty, eduType, course));

    }

    @GetMapping("/totalWithWeek")
    HttpEntity<?> getTotalStatisticsWithWeek(
            @RequestParam("year") Integer year,
            @RequestParam("week") Integer week,
            @RequestParam(value = "eduType",required = false) String eduType,
            @RequestParam(value = "faculty",required = false) String faculty,
            @RequestParam(value = "course",required = false) Integer course

    ) {
        if (eduType == null && faculty == null && course == null) {
            return ResponseEntity.ok(service.getTotalAllClassroomStatisticsWithWeek(year, week));
        }
        if (eduType == null && faculty == null) {
            return ResponseEntity.ok(service.getTotalAllClassroomStatisticsWithWeekByCourse(year, week, course));
        }
        if (eduType == null && course == null) {
            return ResponseEntity.ok(service.getTotalAllClassroomStatisticsWithWeekByFaculty(year, week, faculty));
        }
        if (eduType == null) {
            return ResponseEntity.ok(service.getTotalAllClassroomStatisticsWithWeekByFacultyAndCourse(year, week, faculty, course));
        }
        if (faculty == null && course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceWithWeekByEduType(year, week, eduType));
        }
        if (faculty == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceWithWeekByEduTypeAndCourse(year, week, eduType, course));
        }
        if (course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(year, week, eduType, faculty));
        }

        return ResponseEntity.ok(service.getTotalClassroomAttendanceWithWeekByEduTypeAndFacultyAndCourse(year, week, eduType, faculty, course));

    }

    @GetMapping("/totalWithFaculty")
    HttpEntity<?> getTotalStatisticsWithFaculty(
            @RequestParam("year") Integer year,
            @RequestParam("week") Integer week,
            @RequestParam("weekday") Integer weekday,
            @RequestParam(value = "eduType",required = false) String eduType,
            @RequestParam(value = "course",required = false) Integer course

    ) {
        if (eduType == null && course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceWithFaculties(year, week,weekday));
        }
        if (eduType == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceWithFacultiesAndCourse(year, week,weekday, course));
        }
        if (course == null) {
            return ResponseEntity.ok(service.getTotalClassroomAttendanceWithFacultiesAndEduType(year, week, weekday,eduType));
        }

        return ResponseEntity.ok(service.getTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(year, week, weekday,eduType, course));

    }

    @GetMapping("/allRoom")
    HttpEntity<?> getAllRoomStatistics(
            @RequestParam("year") Integer year,
            @RequestParam("week") Integer week,
            @RequestParam("weekday") Integer weekday
    ) {
        return ResponseEntity.ok(service.getAllClassroomStatistics(year, week, weekday));
    }

    @GetMapping("/getPassedTeachers")
    HttpEntity<?> getPassedTeachers(
            @RequestParam("year") Integer year,
            @RequestParam("week") Integer week,
            @RequestParam("weekday") Integer weekday
    ) {
        return ResponseEntity.ok(service.getPassedTeachers(year, week, weekday));
    }
}
