package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.timetableDB.CardDB;

import java.util.Set;

public interface CardDBRepository extends JpaRepository<CardDB,String> {
    @Query(value = "select Top 1 c.id from CardDB c\n" +
            "join LessonDB l on c.lesson_id = l.id\n" +
            "join LessonDB_users lu on l.id = lu.LessonDB_id\n" +
            "join WeekOfEducationYear w on c.weekOfEducationYear_id = w.id\n" +
            "where c.day=?1 and c.period=?2 and lu.teachers_id=?3 and w.sortNumber=?4 and w.year=?5",nativeQuery = true)
    String getCardId(Integer day, Integer period, Set<User> lesson_teachers, Integer weekOfEducationYear_sortNumber, Integer weekOfEducationYear_year);
}
