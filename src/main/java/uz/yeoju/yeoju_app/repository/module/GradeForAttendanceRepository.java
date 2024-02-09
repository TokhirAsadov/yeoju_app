package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.GradeForAttendance;

public interface GradeForAttendanceRepository extends JpaRepository<GradeForAttendance,String> {

    @Query(value = "select dbo.existTeachersLesson(?1,?2,?3,?4)",nativeQuery = true)
    Boolean existTeachersLesson(String teacherId, String groupId,String educationYearId,String lessonId);
}
