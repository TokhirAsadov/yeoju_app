package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.timetableDB.DailyTeacherMissedLesson;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetDailyTeacherMissedLesson;

import java.util.List;


public interface DailyTeacherMissedLessonRepository extends JpaRepository<DailyTeacherMissedLesson, String> {

    @Query(value = "select\n" +
            "    d.teacher_id as teacherId,\n" +
            "    d.id,\n" +
            "    d.year,\n" +
            "    d.month,\n" +
            "    d.day,\n" +
            "    d.week,\n" +
            "    d.weekday,\n" +
            "    d.hour,\n" +
            "    d.room\n" +
            "from DailyTeacherMissedLesson d\n" +
            "where d.teacher_id=?1 and d.year=?2 and d.month=?3 and d.day=?4 order by d.weekday\n" +
            ";",nativeQuery = true)
    List<GetDailyTeacherMissedLesson> getTeacherMissedLessonByDay(String teacherId, Integer year, Integer month, Integer day);
}
