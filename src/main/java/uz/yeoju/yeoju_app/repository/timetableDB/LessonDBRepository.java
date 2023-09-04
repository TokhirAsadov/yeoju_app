package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.timetableDB.LessonDB;

public interface LessonDBRepository extends JpaRepository<LessonDB,String> {
}
