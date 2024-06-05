package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.Vedimost;
import uz.yeoju.yeoju_app.entity.module.VedimostCondition;
import uz.yeoju.yeoju_app.payload.resDto.module.vedimost.GetAllFinalGradesOfVedimost;
import uz.yeoju.yeoju_app.payload.resDto.module.vedimost.GetVedimostOfKafedra;
import uz.yeoju.yeoju_app.payload.resDto.module.vedimost.GetVedimostOfKafedraWithFinalGrades;

import java.util.Optional;
import java.util.Set;

public interface VedimostRepository extends JpaRepository<Vedimost, String> {


    Boolean existsVedimostByIdAndCondition(String id, VedimostCondition condition);
    Boolean existsVedimostByTeacherIdAndLessonIdAndGroupId(String teacher_id, String lesson_id, String group_id);
    Boolean existsVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(String teacher_id, String lesson_id, String group_id, String educationYear_id);
    Optional<Vedimost> findVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(String teacher_id, String lesson_id, String group_id, String educationYear_id);
    Boolean existsVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearIdAndCondition(String teacher_id, String lesson_id, String group_id, String educationYear_id, VedimostCondition condition);

    @Query(value = "select dbo.CheckVedimostDeadlineIsEnable(?1);",nativeQuery = true)
    Boolean checkVedimostDeadlineIsEnable(String vedimostId);

    @Query(value = "select * from dbo.GetAllFinalGradesOfVedimost(?1) order by student",nativeQuery = true)
    Set<GetAllFinalGradesOfVedimost> getAllFinalGradesOfVedimost(String vedimostId);



    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?3 and v.group_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndGroupId(String lessonId,String groupId,String educationYearId);
    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 and v.group_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndGroupId(String lessonId,String groupId);


    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?3 and g.faculty_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndFacultyId(String lessonId,String facultyId,String educationYearId);
    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 and g.faculty_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonIdAndFacultyId(String lessonId,String facultyId);


    @Query(value = "select Top 1 \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
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
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
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
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Teacher t on t.user_id=v.teacher_id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where t.kafedra_id=?1 order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getAllVedimostOfKafedra(String kafedraId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 and v.educationYear_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonId(String lessonId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.lesson_id=?1 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByLessonId(String lessonId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherId(String teacherId,String educationYearId);
    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherId(String teacherId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?2 and v.lesson_id=?3 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndLessonIdAndEducationYearId(String teacherId,String educationYearId,String lessonId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndLessonId(String teacherId,String lessonId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?3 and g.faculty_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndFacultyIdAndEducationId(String teacherId,String facultyId,String educationYearId);
    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and g.faculty_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndFacultyId(String teacherId,String facultyId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?3 and v.group_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndGroupId(String teacherId,String groupId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.group_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherIdAndGroupId(String teacherId,String groupId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getAllVedimostByTeacherId(String teacherId);

    @Query(value = "select Top 50\n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            " order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getLast50Vedimost();

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.group_id=?1 order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getAllVedimostByGroupId(String groupId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.group_id=?1 and v.educationYear_id=?2  order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByGroupId(String groupId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.educationYear_id=?1  order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByEducationYearId(String educationYearId);


    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.group_id=?1 order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByGroupId(String groupId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where g.faculty_id=?1 and v.educationYear_id=?2  order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByFacultyId(String facultyId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where g.faculty_id=?1 order by v.createdAt desc",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByFacultyId(String facultyId);


    @Query(value = "select Top 1\n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 and v.group_id=?3 order by v.createdAt desc",nativeQuery = true)
    GetVedimostOfKafedra getVedimostByTeacherIdAndLessonIdAndGroupId(String teacherId,String lessonId,String groupId);


    @Query(value = "select Top 1\n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 and v.group_id=?3 and v.educationYear_id=?4  order by v.createdAt desc",nativeQuery = true)
    GetVedimostOfKafedra getVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(String teacherId,String lessonId,String groupId,String educationYearId);

    @Query(value = "select Top 1\n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 and g.faculty_id=?3 and v.educationYear_id=?4  order by v.createdAt desc",nativeQuery = true)
    GetVedimostOfKafedra getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(String teacherId,String lessonId,String facultyId,String educationYearId);

    @Query(value = "select Top 1\n" +
            "    v.id,\n" +
            "    v.courseLeader," +
            "    v.headOfAcademicAffair," +
            "    v.headOfDepartment," +
            "    v.direction,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.lesson_id=?2 and g.faculty_id=?3 order by v.createdAt desc",nativeQuery = true)
    GetVedimostOfKafedra getVedimostByTeacherIdAndLessonIdAndFacultyId(String teacherId,String lessonId,String facultyId);
}
