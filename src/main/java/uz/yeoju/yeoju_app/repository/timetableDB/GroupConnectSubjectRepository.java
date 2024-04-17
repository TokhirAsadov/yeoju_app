package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.timetableDB.GroupConnectSubject;
import uz.yeoju.yeoju_app.payload.resDto.module.GetLessonForGroup;
import uz.yeoju.yeoju_app.payload.resDto.module.GetLinesForStudent;
import uz.yeoju.yeoju_app.payload.resDto.module.student.GetStudentGradesByLessons;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.*;

import java.util.Optional;
import java.util.Set;

public interface GroupConnectSubjectRepository extends JpaRepository<GroupConnectSubject,String> {
    Boolean existsGroupConnectSubjectByGroupIdAndEducationYearId(String group_id, String educationYear_id);
    Optional<GroupConnectSubject> findGroupConnectSubjectByGroupIdAndEducationYearId(String group_id, String educationYear_id);


    @Query(value = "select p.id as planId from PlanOfSubject p join PlanOfSubject_groups pg on p.id = pg.PlanOfSubject_id \n" +
            "where p.educationYear_id=?1 and pg.groups_id=?2 and p.subject_id =?3 and p.user_id=?4",nativeQuery = true)
    GetLinesForStudent getLinesForStudent(String educationYearId, String groupId,String subjectId, String userId);

    @Query(value = "select u.id as teacherId,u.firstName,u.lastName,u.middleName, u.fullName,l.id as lessonId, l.name as lessonName from TeacherConnectSubject tcs\n" +
            "        join TeacherConnectSubject_groups TCSg on tcs.id = TCSg.TeacherConnectSubject_id join WeekOfEducationYear WOEY on tcs.weeks_id = WOEY.id join EducationYear_WeekOfEducationYear EYWOEY on WOEY.id = EYWOEY.weeks_id join groups g on TCSg.groups_id = g.id join users u on tcs.user_id = u.id join Lesson l on tcs.lesson_id = l.id\n" +
            "where EYWOEY.EducationYear_id=?1 and g.name=?2 GROUP BY u.id, u.firstName,u.lastName,u.middleName,u.fullName, l.id, l.name",nativeQuery = true)
    Set<GetLessonForGroup> getLessonForGroupByEducationYearIdAndGroupName(String eduYearId,String groupName);

    @Query(value = "select ?1 as educationYearId,g.id as groupId,?3 as studentId,u.id as teacherId,u.firstName,u.lastName,u.middleName, u.fullName,l.id as lessonId, l.name as lessonName from TeacherConnectSubject tcs\n" +
            "        join TeacherConnectSubject_groups TCSg on tcs.id = TCSg.TeacherConnectSubject_id join WeekOfEducationYear WOEY on tcs.weeks_id = WOEY.id join EducationYear_WeekOfEducationYear EYWOEY on WOEY.id = EYWOEY.weeks_id join groups g on TCSg.groups_id = g.id join users u on tcs.user_id = u.id join Lesson l on tcs.lesson_id = l.id\n" +
            "where EYWOEY.EducationYear_id=?1 and g.name=?2 GROUP BY u.id, u.firstName,u.lastName,u.middleName,u.fullName, l.id, l.name,g.id ",nativeQuery = true)
    Set<GetStudentGradesByLessons> getLessonForGroupByEducationYearIdAndGroupNameForGetGrades(String eduYearId, String groupName, String studentId);

    @Query(value = "select ?3 as studentId, c.classroom as room,c.day,c.period as section,g.name as groupName ,w.year,w.sortNumber as week,l.name as subject from CardDB c\n" +
            "      join LessonDB ld on c.lesson_id = ld.id join Lesson l on ld.subject_id = l.id join LessonDB_groups LDg on ld.id = LDg.LessonDB_id join groups g on LDg.groups_id = g.id join WeekOfEducationYear w on c.weekOfEducationYear_id = w.id join EducationYear_WeekOfEducationYear EYWOEY on w.id = EYWOEY.weeks_id\n" +
            "where g.name=?1 and EYWOEY.EducationYear_id=?2 order by l.name",nativeQuery = true)
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjectsByEduYearIdAndGroupAndStudentId(String groupName,String educationId,String studentId);
    @Query(value = "select c.classroom as room,c.period as section from CardDB c\n" +
            "                      join LessonDB ld on c.lesson_id = ld.id\n" +
            "    join LessonDB_users lu on ld.id = lu.LessonDB_id\n" +
            "                      join Lesson l on ld.subject_id = l.id\n" +
            "                      join LessonDB_groups LDg on ld.id = LDg.LessonDB_id\n" +
            "                      join groups g on LDg.groups_id = g.id\n" +
            "                      join WeekOfEducationYear w on c.weekOfEducationYear_id = w.id\n" +
            "                      join EducationYear_WeekOfEducationYear EYWOEY on w.id = EYWOEY.weeks_id\n" +
            "where g.id=?1 and EYWOEY.EducationYear_id=?2 and l.id=?3 and w.year=?4 and w.sortNumber=?5 and c.day=?6 and lu.teachers_id=?7 order by c.period\n",nativeQuery = true)
    Set<GetSectionsAndRooms> getSectionsAndRooms(String groupId,String educationId,String subjectId,String year,Integer week, Integer day,String teacherId);


    @Query(value = "select ?4 as studentId, c.classroom as room,c.day,c.period as section,g.name as groupName ,w.year,w.sortNumber as week,l.name as subject from CardDB c\n" +
            "    join LessonDB ld on c.lesson_id = ld.id \n" +
            "    join Lesson l on ld.subject_id = l.id \n" +
            "    join LessonDB_groups LDg on ld.id = LDg.LessonDB_id \n" +
            "    join groups g on LDg.groups_id = g.id \n" +
            "    join WeekOfEducationYear w on c.weekOfEducationYear_id = w.id \n" +
            "    join EducationYear_WeekOfEducationYear EYWOEY on w.id = EYWOEY.weeks_id\n" +
            "where g.id=?1 and EYWOEY.EducationYear_id=?2 and l.id=?3 and w.year=?5 and w.sortNumber=?6 and c.day=?7 order by l.name",nativeQuery = true)
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjectsByEduYearIdAndGroupAndStudentId(String groupId,String educationId,String subjectId,String studentId,String year,Integer week, Integer day);


    @Query(value = "select ?4 as studentId, c.classroom as room,c.day,c.period as section,g.name as groupName ,w.year,w.sortNumber as week,l.name as subject from CardDB c\n" +
            "    join LessonDB ld on c.lesson_id = ld.id \n" +
            "    join Lesson l on ld.subject_id = l.id \n" +
            "    join LessonDB_groups LDg on ld.id = LDg.LessonDB_id \n" +
            "    join groups g on LDg.groups_id = g.id \n" +
            "    join WeekOfEducationYear w on c.weekOfEducationYear_id = w.id \n" +
            "    join EducationYear_WeekOfEducationYear EYWOEY on w.id = EYWOEY.weeks_id\n" +
            "where g.id=?1 and EYWOEY.EducationYear_id=?2 and l.id=?3 order by l.name",nativeQuery = true)
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjectsByEduYearIdAndGroupAndStudentId(String groupId,String educationId,String subjectId,String studentId);

    @Query(value = "select * from dbo.GetSubjectsByEduYearIdAndGroupAndStudentId(?1,?2,?3,?4)",nativeQuery = true)
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjectsByEduYearIdAndGroupAndStudentIdNEW(String studentId,String groupId,String educationId,String subjectId);


    @Query(value = "select ?3 as studentId, c.classroom as room,c.day,c.period as section,g.name as groupName ,w.year,w.sortNumber as week,l.name as subject from CardDB c\n" +
            "      join LessonDB ld on c.lesson_id = ld.id\n" +
            "      join Lesson l on ld.subject_id = l.id\n" +
            "      join LessonDB_groups LDg on ld.id = LDg.LessonDB_id\n" +
            "      join groups g on LDg.groups_id = g.id\n" +
            "      join WeekOfEducationYear w on c.weekOfEducationYear_id = w.id\n" +
            "      join EducationYear_WeekOfEducationYear EYWOEY on w.id = EYWOEY.weeks_id\n" +
            "where g.id=?1 and EYWOEY.EducationYear_id=?2 order by l.name",nativeQuery = true)
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjectsByEduYearIdAndGroupAndStudentIdForDean(String groupId,String educationId,String studentId);

    @Query(value = "select l.id ,l.name  from TeacherConnectSubject ts join Lesson l on ts.lesson_id = l.id join WeekOfEducationYear w on ts.weeks_id = w.id join EducationYear_WeekOfEducationYear ew on w.id = ew.weeks_id\n" +
            "    join EducationYear e on e.id = ew.EducationYear_id where ts.user_id=?1 and e.id=?2 group by l.name,l.id",nativeQuery = true)
    Set<LessonOfTeachersByEducationYear> getSubjectsOfTeacherByEducationId(String teacherId, String educationId);

    @Query(value = "select g.id ,g.name from TeacherConnectSubject ts join TeacherConnectSubject_groups tg on ts.id = tg.TeacherConnectSubject_id join groups g on tg.groups_id = g.id join WeekOfEducationYear w on ts.weeks_id = w.id join EducationYear_WeekOfEducationYear ew on w.id = ew.weeks_id join EducationYear e on e.id = ew.EducationYear_id where ts.user_id=?1 and e.id=?2 and ts.lesson_id=?3 group by g.id, g.name order by g.name",nativeQuery = true)
    Set<LessonOfTeachersByEducationYear> getGroupsOfTeacherByEducationYearId(String teacherId, String educationId,String subjectId);

    @Query(value = "select g.id ,g.name from TeacherConnectSubject ts join TeacherConnectSubject_groups tg on ts.id = tg.TeacherConnectSubject_id join groups g on tg.groups_id = g.id join WeekOfEducationYear w on ts.weeks_id = w.id join EducationYear_WeekOfEducationYear ew on w.id = ew.weeks_id join EducationYear e on e.id = ew.EducationYear_id join EducationType ET on g.educationType_id = ET.id where ts.user_id=?1 and e.id=?2 and ts.lesson_id=?3 and (ET.name=?4 or ET.name=?5) group by g.id, g.name order by g.name",nativeQuery = true)
    Set<LessonOfTeachersByEducationYear> getGroupsOfTeacherByEducationYearId(String teacherId, String educationId,String subjectId,String edu1,String edu2);

    @Query(value = "select\n" +
            "    ?1 as educationId,\n" +
            "    ?2 as groupId,\n" +
            "    ?3 as subjectId,\n" +
            "    ?4 as teacherId,\n" +
            "    ?5 as year,\n" +
            "    ?6 as week,\n" +
            "    ?7 as day,\n" +
            "    u.id as studentId,\n" +
            "    u.firstName,\n" +
            "    u.lastName,\n" +
            "    u.middleName,\n" +
            "    u.fullName,\n" +
            "    u.login as userId\n" +
            "    from users u\n" +
            "    join Student s on u.id = s.user_id\n" +
            "    where s.group_id=?2",nativeQuery = true)
    Set<StatisticsOfGroupForTeacherForToday> getStatisticsOfGroupForTeacher(String educationId, String groupId, String subjectId, String teacherId, String year, Integer week, Integer day);



    @Query(value = "select\n" +
            "    ?1 as educationId,\n" +
            "    ?2 as groupId,\n" +
            "    ?3 as subjectId,\n" +
            "    ?4 as teacherId,\n" +
            "    u.id as studentId,\n" +
            "    u.firstName,\n" +
            "    u.lastName,\n" +
            "    u.middleName,\n" +
            "    u.fullName,\n" +
            "    u.login as userId\n" +
            "    from users u\n" +
            "    join Student s on u.id = s.user_id\n" +
            "    where s.group_id=?2",nativeQuery = true)
    Set<StatisticsOfGroupForTeacher> getStatisticsOfGroupForTeacher(String educationId, String groupId, String subjectId, String teacherId);

    @Query(value = "select\n" +
            "    ?1 as educationId,\n" +
            "    ?2 as groupId,\n" +
            "    ?3 as subjectId,\n" +
            "    ?4 as teacherId ",nativeQuery = true)
    Set<StatisticsOfGroupForTeacherForTodayWithMax2> getStatisticsOfGroupForTeacherWithMax(String educationId, String groupId, String subjectId, String teacherId);


    @Query(value = "select\n" +
            "    ?1 as educationId,\n" +
            "    ?2 as groupId,\n" +
            "    ?3 as subjectId,\n" +
            "    ?4 as teacherId,\n" +
            "    ?5 as year,\n" +
            "    ?6 as week,\n" +
            "    ?7 as day ",nativeQuery = true)
    Set<StatisticsOfGroupForTeacherForTodayWithMax> getStatisticsOfGroupForTeacherWithMax(String educationId, String groupId, String subjectId, String teacherId, String year, Integer week, Integer day);



    @Query(value = "select\n" +
            "    ?1 as educationId,\n" +
            "    ?2 as groupId,\n" +
            "    u.id as studentId,\n" +
            "    u.firstName,\n" +
            "    u.lastName,\n" +
            "    u.middleName,\n" +
            "    u.fullName,\n" +
            "    u.login as userId\n" +
            "    from users u\n" +
            "    join Student s on u.id = s.user_id\n" +
            "    where s.group_id=?2",nativeQuery = true)
    Set<StatisticsOfGroupForDean> getStatisticsOfGroupForDean(String educationId, String groupId);

    @Query(value = "select ?1 as educationYearId,?2 as groupId, ?3 as studentId, l.id as subjectId ,l.name as subjectName from LessonDB ld\n" +
            "    join Lesson l on ld.subject_id = l.id\n" +
            "    join WeekOfEducationYear wy on ld.weekOfEducationYear_id = wy.id\n" +
            "    join EducationYear_WeekOfEducationYear e_w on wy.id = e_w.weeks_id\n" +
            "    join LessonDB_groups LDg on ld.id = LDg.LessonDB_id\n" +
            "    where e_w.EducationYear_id=?1 and LDg.groups_id=?2\n" +
            "    group by l.id, l.name ",nativeQuery = true)
    Set<GetLessonsOfGroupForBall> getLessonsOfGroupForBall(String educationYearId, String groupId, String studentId);

    @Query(value = "select s.user_id as studentId, c.classroom as room,c.day,c.period as section,w.year,w.sortNumber as week from CardDB c\n" +
            "    join LessonDB ldb on c.lesson_id = ldb.id\n" +
            "    join LessonDB_groups lg on ldb.id = lg.LessonDB_id\n" +
            "    join groups g on lg.groups_id = g.id\n" +
            "    join Student s on g.id = s.group_id\n" +
            "    join LessonDB_users lu on ldb.id = lu.LessonDB_id\n" +
            "    join WeekOfEducationYear w on ldb.weekOfEducationYear_id = w.id\n" +
            "where lu.teachers_id=?1 and ldb.subject_id=?2 and lg.groups_id=?3 and w.year=?4 and w.sortNumber=?5 and c.day=?6",nativeQuery = true)
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId2> getStatisticsOfGroupForTeacherByDay(String teacherId,String lessonId,String groupId,Integer year, Integer week,Integer day);

}
