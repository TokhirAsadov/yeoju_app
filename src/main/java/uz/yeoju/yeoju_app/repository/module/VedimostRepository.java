package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.Vedimost;
import uz.yeoju.yeoju_app.entity.module.VedimostCondition;
import uz.yeoju.yeoju_app.payload.resDto.module.vedimost.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VedimostRepository extends JpaRepository<Vedimost, String> {


    Boolean existsVedimostByIdAndCondition(String id, VedimostCondition condition);
    Boolean existsVedimostByTeacherIdAndLessonIdAndGroupId(String teacher_id, String lesson_id, String group_id);
    Boolean existsVedimostByEducationYearIdAndLessonIdAndGroupId(String educationYear_id, String lesson_id, String group_id);
    Boolean existsVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(String teacher_id, String lesson_id, String group_id, String educationYear_id);
    Optional<Vedimost> findVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(String teacher_id, String lesson_id, String group_id, String educationYear_id);
    Boolean existsVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearIdAndCondition(String teacher_id, String lesson_id, String group_id, String educationYear_id, VedimostCondition condition);

    @Query(value = "select dbo.CheckVedimostDeadlineIsEnable(?1);",nativeQuery = true)
    Boolean checkVedimostDeadlineIsEnable(String vedimostId);

    @Query(value = "select * from dbo.GetAllFinalGradesOfVedimost(?1) order by student",nativeQuery = true)
    Set<GetAllFinalGradesOfVedimost> getAllFinalGradesOfVedimost(String vedimostId);



    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?3 and v.group_id=?2 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndGroupId(String lessonId,String groupId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?3 and v.group_id=?2 and (d_f.Dekanat_id=?4 or t.kafedra_id=?4 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndGroupId(String lessonId,String groupId,String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?3 and g.faculty_id=?2 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndFacultyId(String lessonId,String facultyId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?3 and g.faculty_id=?2 and (d_f.Dekanat_id=?4 or t.kafedra_id=?4 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndFacultyId(String lessonId,String facultyId,String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select Top 1 \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    l.id as lessonId,\n" +
            "    u.id as teacherId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Teacher t on t.user_id=v.teacher_id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.id=?1",nativeQuery = true)
    GetVedimostOfKafedraWithFinalGrades getVedimostWithFinalGrades(String vedimostId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Teacher t on t.user_id=v.teacher_id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where t.kafedra_id=?1 and v.educationYear_id=?2 order by u.fullName",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostOfKafedra(String kafedraId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Teacher t on t.user_id=v.teacher_id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where t.kafedra_id=?1 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getAllVedimostOfKafedra(String kafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?2 and (d_f.Dekanat_id=?3 or t.kafedra_id=?3 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonId(String lessonId,String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?2 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonId(String lessonId,String educationYearId);


    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?2 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherId(String teacherId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?2 and (d_f.Dekanat_id=?3 or t.kafedra_id=?3 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherId(String teacherId,String educationYearId,String dekantIdOrKafedra);


    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?2 and v.lesson_id=?3 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndLessonIdAndEducationYearId(String teacherId,String educationYearId,String lessonId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?2 and v.lesson_id=?3 and (d_f.Dekanat_id=?4 or t.kafedra_id=?4 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndLessonIdAndEducationYearId(String teacherId,String educationYearId,String lessonId,String dekanatOrKafedraId);


    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?3 and g.faculty_id=?2 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndFacultyIdAndEducationId(String teacherId,String facultyId,String educationYearId);
    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?3 and g.faculty_id=?2 and (d_f.Dekanat_id=?4 or t.kafedra_id=?4 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndFacultyIdAndEducationId(String teacherId,String facultyId,String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?3 and v.group_id=?2 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndGroupId(String teacherId,String groupId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?3 and v.group_id=?2 and (d_f.Dekanat_id=?4 or t.kafedra_id=?4 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndGroupId(String teacherId,String groupId,String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getAllVedimostByTeacherId(String teacherId);

    @Query(value = "select Top 50\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            " group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getLast50Vedimost();

    @Query(value = "select Top 50\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where (d_f.Dekanat_id=?1 or t.kafedra_id=?1 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getLast50Vedimost(String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.group_id=?1 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getAllVedimostByGroupId(String groupId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.group_id=?1 and v.educationYear_id=?2  group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByGroupId(String groupId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.group_id=?1 and v.educationYear_id=?2 and (d_f.Dekanat_id=?3 or t.kafedra_id=?3 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByGroupId(String groupId,String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where g.level=?1 and v.educationYear_id=?2  group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLevel(Integer level,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where g.level=?1 and v.educationYear_id=?2 and (d_f.Dekanat_id=?3 or t.kafedra_id=?3 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLevel(Integer level,String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.educationYear_id=?1  group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByEducationYearId(String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.educationYear_id=?1 and (d_f.Dekanat_id=?2 or t.kafedra_id=?2 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByEducationYearId(String educationYearId,String dekanatIdOrKafedra);


    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where g.faculty_id=?1 and v.educationYear_id=?2  group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByFacultyId(String facultyId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where g.faculty_id=?1 and v.educationYear_id=?2 and (d_f.Dekanat_id=?3 or t.kafedra_id=?3 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByFacultyId(String facultyId,String educationYearId,String dekanatOrKafedraId);



    @Query(value = "select Top 1\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 and v.group_id=?3 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    GetVedimostOfKafedra getVedimostByTeacherIdAndLessonIdAndGroupId(String teacherId,String lessonId,String groupId);


    @Query(value = "select Top 1\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 and v.group_id=?3 and v.educationYear_id=?4  group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    GetVedimostOfKafedra getVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(String teacherId,String lessonId,String groupId,String educationYearId);

    @Query(value = "select Top 1\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 and g.faculty_id=?3 and v.educationYear_id=?4  group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    GetVedimostOfKafedra getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(String teacherId,String lessonId,String facultyId,String educationYearId);

    @Query(value = "select Top 1\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 and g.faculty_id=?3 and v.educationYear_id=?4 and (d_f.Dekanat_id=?5 or t.kafedra_id=?5 )  group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt desc",nativeQuery = true)
    GetVedimostOfKafedra getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(String teacherId,String lessonId,String facultyId,String educationYearId,String dekanatOrKafedraIdId);


    @Query(value = "select ldb.subject_id as lessonId, ldb_u.teachers_id as teacherId from CardDB c\n" +
            "join LessonDB ldb on c.lesson_id = ldb.id\n" +
            "join LessonDB_groups ldb_g on ldb.id = ldb_g.LessonDB_id\n" +
            "join LessonDB_users ldb_u on ldb.id = ldb_u.LessonDB_id\n" +
            "join users u on ldb_u.teachers_id = u.id\n" +
            "join WeekOfEducationYear w on ldb.weekOfEducationYear_id = w.id\n" +
            "join EducationYear_WeekOfEducationYear e_w on w.id = e_w.weeks_id\n" +
            "where e_w.EducationYear_id=?1 and ldb_g.groups_id=?2",nativeQuery = true)
    Set<GetLessonsIdsWithTeachersIds> getLessonsIdsWithTeachersIds(String educationYearId, String groupId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where g.level=?2 and v.lesson_id=?1 and v.educationYear_id=?3 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndLevel(String lessonId, Integer level, String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where g.level=?2 and v.lesson_id=?1 and v.educationYear_id=?3 and (d_f.Dekanat_id=?4 or t.kafedra_id=?4 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndLevel(String lessonId, Integer level, String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where g.level=?2 and g.faculty_id=?1 and v.educationYear_id=?3 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByFacultyIdAndLevel(String facultyId, Integer level, String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where g.level=?2 and g.faculty_id=?1 and v.educationYear_id=?3 and (d_f.Dekanat_id=?4 or t.kafedra_id=?4 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByFacultyIdAndLevel(String facultyId, Integer level, String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where g.level=?2 and v.teacher_id=?1 and v.educationYear_id=?3 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndLevel(String teacherId, Integer level, String educationYearId);
    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Faculty f on g.faculty_id = f.id\n" +
            "         join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where g.level=?2 and v.teacher_id=?1 and v.educationYear_id=?3 and (d_f.Dekanat_id=?4 or t.kafedra_id=?4 ) group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndLevel(String teacherId, Integer level, String educationYearId,String dekanatOrKafedraId);

    @Query(value = "select (select count(*) as counter from Vedimost v\n" +
            "                                             join groups g on v.group_id = g.id\n" +
            "                                             join Faculty f on g.faculty_id = f.id\n" +
            "                                             join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "         where d_f.Dekanat_id=?1 and v.educationYear_id=?2) as total,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "                                            join groups g on v.group_id = g.id\n" +
            "                                            join Faculty f on g.faculty_id = f.id\n" +
            "                                            join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "        where d_f.Dekanat_id=?1 and v.educationYear_id=?2 and condition='OPEN') as isOpen,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "                                            join groups g on v.group_id = g.id\n" +
            "                                            join Faculty f on g.faculty_id = f.id\n" +
            "                                            join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "        where d_f.Dekanat_id=?1 and v.educationYear_id=?2 and condition='DONE') as isDone,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "                                            join groups g on v.group_id = g.id\n" +
            "                                            join Faculty f on g.faculty_id = f.id\n" +
            "                                            join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "        where d_f.Dekanat_id=?1 and v.educationYear_id=?2 and condition='CLOSE') as isClose,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "                                            join groups g on v.group_id = g.id\n" +
            "                                            join Faculty f on g.faculty_id = f.id\n" +
            "                                            join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "        where d_f.Dekanat_id=?1 and v.educationYear_id=?2 and condition='NOT_DONE') as isNotDone",nativeQuery = true)
    GetDataAboutVedimostsInDekanat getDataAboutVedimostByDekanat(String dekanatId, String educationYearId);

    @Query(value = "select\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader,\n" +
            "    v.headOfAcademicAffair,\n" +
            "    v.headOfDepartment,\n" +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "join Faculty f on g.faculty_id = f.id\n" +
            "join Dekanat_Faculty d_f on f.id = d_f.faculties_id\n" +
            "where v.condition=?3 and d_f.Dekanat_id=?1 and v.educationYear_id=?2 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getDataAboutVedimostByDekanat(String dekanatId, String educationYearId,String condition);

    @Query(value = "select (select count(*) as counter from Vedimost v\n" +
            "                                   join Teacher t on v.teacher_id = t.user_id\n" +
            "         where t.kafedra_id=?1 and v.educationYear_id=?2) as total,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "                                           join Teacher t on v.teacher_id = t.user_id\n" +
            "         where t.kafedra_id=?1 and v.educationYear_id=?2 and condition='OPEN') as isOpen,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "                                           join Teacher t on v.teacher_id = t.user_id\n" +
            "         where t.kafedra_id=?1 and v.educationYear_id=?2 and condition='DONE') as isDone,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "                                           join Teacher t on v.teacher_id = t.user_id\n" +
            "         where t.kafedra_id=?1 and v.educationYear_id=?2 and condition='CLOSE') as isClose,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "                                            join Teacher t on v.teacher_id = t.user_id\n" +
            "        where t.kafedra_id=?1 and v.educationYear_id=?2 and condition='NOT_DONE') as isNotDone",nativeQuery = true)
    GetDataAboutVedimostsInDekanat getDataAboutVedimostByKafedra(String kafedraId, String educationYearId);

    @Query(value = "select\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader,\n" +
            "    v.headOfAcademicAffair,\n" +
            "    v.headOfDepartment,\n" +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Teacher t on v.teacher_id = t.user_id\n" +
            "where t.kafedra_id=?1 and v.educationYear_id=?2 and v.condition=?3 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getDataAboutVedimostByKafedra(String dekanatId, String educationYearId,String condition);

    @Query(value = "select (select count(*) as counter from Vedimost v\n" +
            "         where v.educationYear_id=?1) as total,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "         where v.educationYear_id=?1 and condition='OPEN') as isOpen,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "         where v.educationYear_id=?1 and condition='DONE') as isDone,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "         where v.educationYear_id=?1 and condition='CLOSE') as isClose,\n" +
            "       (select count(*) as counter from Vedimost v\n" +
            "        where v.educationYear_id=?1 and condition='NOT_DONE') as isNotDone",nativeQuery = true)
    GetDataAboutVedimostsInDekanat getDataAboutVedimostForMonitoring(String educationYearId);

    @Query(value = "select\n" +
            "    v.id,\n" +
            "    v.createdAt,\n" +
            "    v.courseLeader,\n" +
            "    v.headOfAcademicAffair,\n" +
            "    v.headOfDepartment,\n" +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.id as teacherId,\n" +
            "    l.id as lessonId,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "from Vedimost v\n" +
            "         join users u on v.teacher_id = u.id\n" +
            "         join Lesson l on l.id=v.lesson_id\n" +
            "         join groups g on v.group_id = g.id\n" +
            "         join Teacher t on v.teacher_id = t.user_id\n" +
            "where v.educationYear_id=?1 and v.condition=?2 group by v.id, v.createdAt, v.courseLeader, v.headOfAcademicAffair, v.headOfDepartment, v.direction, v.level, v.deadline, v.timeClose, v.condition, u.id, l.id, u.fullName, l.name, g.name order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getDataAboutVedimostForMonitoring(String educationYearId, String condition);
}
