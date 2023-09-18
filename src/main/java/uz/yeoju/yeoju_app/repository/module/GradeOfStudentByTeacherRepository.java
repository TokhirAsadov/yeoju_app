package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.GradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.payload.resDto.module.GetGradesOfStudent;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetStudentDataForMiddleGrade;

import java.util.Optional;
import java.util.Set;

public interface GradeOfStudentByTeacherRepository extends JpaRepository<GradeOfStudentByTeacher,String> {

    Optional<GradeOfStudentByTeacher> findByIdAndCreatedBy(String id, String createdBy);
    @Query(value = "select id,grade,time,description from GradeOfStudentByTeacher where createdBy=?1 and student_id=?2 and educationYear_id=?3 and lesson_id=?4 order by createdAt",nativeQuery = true)
    Set<GetGradesOfStudent> getGradesOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonId(String teacherId, String studentId, String educationYearId, String lessonId);

    @Query(value = "select avg(grade) as middle from GradeOfStudentByTeacher where createdBy=?1 and student_id=?2 and educationYear_id=?3 and lesson_id=?4\n" +
            "group by createdBy,student_id,educationYear_id,lesson_id",nativeQuery = true)
    Double getMiddleGrade(String teacherId, String studentId, String educationYearId,String lessonId);

    @Query(value = "select avg(grade) as middle from GradeOfStudentByTeacher where  educationYear_id=?1 and student_id=?2 and  lesson_id=?3\n" +
            "group by student_id,educationYear_id,lesson_id",nativeQuery = true)
    Double getMiddleGrade( String educationYearId, String studentId,String lessonId);

    @Query(value = "select Top 1 grade from GradeOfStudentByTeacher\n" +
            "    where createdBy=?1 and student_id=?2 and educationYear_id=?3 and lesson_id=?4 and createdAt between DATETIMEFROMPARTS(\n" +
            "            DATEPART(YEAR, getdate()),\n" +
            "            DATEPART(MONTH , getdate()),\n" +
            "            DATEPART(DAY, getdate()),\n" +
            "            00, 00, 00, 0\n" +
            "        ) and DATETIMEFROMPARTS(\n" +
            "            DATEPART(YEAR, getdate()),\n" +
            "            DATEPART(MONTH , getdate()),\n" +
            "            DATEPART(DAY, getdate()),\n" +
            "            23, 59, 59, 0\n" +
            "        ) order by createdAt desc",nativeQuery = true)
    Double getTodayGrade(String teacherId, String studentId, String educationYearId,String lessonId);

    @Query(value = "select u.id,u.firstName,u.lastName,u.middleName,u.fullName, ?1 as educationYearId,?2 as groupId from Student s\n" +
            "    join users u on s.user_id = u.id\n" +
            "    where s.group_id=?2",nativeQuery = true)
    Set<GetStudentDataForMiddleGrade> getStudentDataForMiddleGrade(String educationYearId, String groupId);
}
