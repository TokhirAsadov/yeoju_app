package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.GradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.payload.resDto.module.GetGradesOfStudent;
import uz.yeoju.yeoju_app.payload.resDto.module.GetGradesOfStudentWithRetake;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetStudentDataForMiddleGrade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GradeOfStudentByTeacherRepository extends JpaRepository<GradeOfStudentByTeacher,String> {

    Boolean existsByEducationYearIdAndThemeIdAndStudentIdAndActive(String educationYear_id, String theme_id, String student_id, Boolean active);

    @Query(value = "select dbo.IsEnableGrade(?1,?2,?3,?4,?5)",nativeQuery = true)
    Boolean isEnableGrade(String studentId,String groupId,String educationYearId,String lessonId,Float grade);


    @Query(value = "select dbo.GetMaxEnableGrade(?1,?2,?3,?4)",nativeQuery = true)
    Float getMaxEnableGrade(String studentId,String groupId,String educationYearId,String lessonId);


    List<GradeOfStudentByTeacher> findAllByFailGradeIdAndActive(String failGrade_id, Boolean active);
    Boolean existsByFailGradeId(String failGrade_id);
    @Query(value = "select MAX(step) from (\n" +
            "                          select count(gs.id) as step,u.id,s.group_id from GradeOfStudentByTeacher gs\n" +
            "                           join users u on gs.student_id = u.id\n" +
            "                           join Student S on u.id = S.user_id\n" +
            "                          where gs.educationYear_id=?1 and gs.lesson_id=?2 and s.group_id=?3 group by s.group_id,u.id\n" +
            "                      ) as newvalue",nativeQuery = true)
    Long getMaxStep(String educationYearId,String subjectId,String groupId);

    @Query(value = "select sum(grade) as middle from GradeOfStudentByTeacher where active=1 and createdBy=?1 and student_id=?2 and educationYear_id=?3 and lesson_id=?4\n" +
            "group by createdBy,student_id,educationYear_id,lesson_id",nativeQuery = true)
    Double getSumGrade(String teacherId, String studentId, String educationYearId, String lessonId);

    @Query(value = "select sum(grade) from GradeOfStudentByTeacher where active=1 and student_id=?1 and educationYear_id=?2 and lesson_id=?3\n" +
            "group by student_id,educationYear_id,lesson_id",nativeQuery = true)
    Double getAllSumGrade(String studentId, String educationYearId,String lessonId);

    @Query(value = "select Top 1 dbo.GetSumOfAllGrades(?1,?2,?3);",nativeQuery = true)
    Float getAllSumGradeNEW(String studentId,String educationYearId,String lessonId);


    Optional<GradeOfStudentByTeacher> findByIdAndCreatedBy(String id, String createdBy);
    @Query(value = "select g.failGrade_id as failGradeId,g.id,g.grade,g.time,g.description,g.createdAt,th.id as themeId,th.name as theme, th.maxGrade  from GradeOfStudentByTeacher g \n" +
            " left   join ThemeOfSubjectForGradeByTeacher th on g.theme_id = th.id                                           \n" +
            "where g.active=1 and g.createdBy=?1 and g.student_id=?2 and g.educationYear_id=?3 and g.lesson_id=?4 order by g.createdAt",nativeQuery = true)
    Set<GetGradesOfStudent> getGradesOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonId(String teacherId, String studentId, String educationYearId, String lessonId);

    @Query(value = "select id,grade,time,description,createdAt from GradeOfStudentByTeacher where id=?1 order by createdAt",nativeQuery = true)
    GetGradesOfStudentWithRetake getGradeOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonIdRetakes(String failGradeId);

    @Query(value = "select id,grade,time,description,createdAt from GradeOfStudentByTeacher where failGrade_id=?1 order by createdAt",nativeQuery = true)
    Set<GetGradesOfStudent> getGradesOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonIdRetakes(String failGradeId);

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
