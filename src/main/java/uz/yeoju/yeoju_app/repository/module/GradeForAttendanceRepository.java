package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.GradeForAttendance;

public interface GradeForAttendanceRepository extends JpaRepository<GradeForAttendance,String> {

    @Query(value = "select dbo.existTeachersLesson(?1,?2,?3,?4)",nativeQuery = true)
    Boolean existTeachersLesson(String teacherId, String groupId,String educationYearId,String lessonId);

    @Query(value = "select dbo.existGradeForAttendance(?1,?2,?3)",nativeQuery = true)
    Boolean existGradeForAttendance(String groupId,String educationYearId,String lessonId);

    @Query(value = "select dbo.GetGradeForAttendance(?1,?2,?3)",nativeQuery = true)
    Float getGradeForAttendance(String groupId,String educationYearId,String lessonId);

    @Query(value = "select dbo.GetMaxGradeForAttendance(?1,?2,?3)",nativeQuery = true)
    Float getMaxGradeForAttendance(String groupId,String educationYearId,String lessonId);

    @Query(value = "select dbo.GetValueOfGradeForAttendance(?1,?2,?3)",nativeQuery = true)
    Float getValueOfGradeForAttendance(String groupId,String educationYearId,String lessonId);

    @Query(value = "select dbo.GetAllGradesForAttendance(?1,?2,?3,?4)",nativeQuery = true)
    Float getAllGradesForAttendance(String studentId,String groupId,String educationYearId,String lessonId);
}
