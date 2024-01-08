package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentGroupField;
import uz.yeoju.yeoju_app.payload.resDto.group.GroupForStudent;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetGroupsDataForKafedraMudiri;
import uz.yeoju.yeoju_app.payload.resDto.rektor.journal.StudentsOfGroupWithTodayStatisticsAndScore;
import uz.yeoju.yeoju_app.payload.resDto.student.GetStudentStatisticsForDeanOneWeek;
import uz.yeoju.yeoju_app.payload.resDto.student.GetStudentStatisticsForDeanOneWeekSection;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group,String> {
   Group findGroupByName(String name);
   boolean existsGroupByName(String name);
   List<Group> findGroupsByLevel(Integer level);
   List<Group> findGroupsByFacultyId(String faculty_id);


   @Query(value = "select u.rfid, g.id as groupId,u.id as studentId,u.fullName,?2 as educationYearId,?3 as weekday,?4 as week, ?5 as year from groups g \n" +
           "join Student S on g.id = S.group_id\n" +
           "join users u on S.user_id = u.id\n" +
           "where g.id=?1",nativeQuery = true)
   Set<GetStudentStatisticsForDeanOneWeek> getStudentStatisticsForDeanOneWeek(String groupId,String educationYearId,Integer weekday,Integer week,Integer year);

   @Query(value = "select * from dbo.GetStudentStatisticsForDeanOneWeek(?1,?2,?3,?4,?5)",nativeQuery = true)
   Set<GetStudentStatisticsForDeanOneWeek> getStudentStatisticsForDeanOneWeekNEW(String educationYearId,String groupId,Integer year,Integer week,Integer weekday);

   @Query(value = "select ?6 as studentId, c.betweenDuringDate, c.classroom as room,c.day as weekday,c.period as section,l.name as lesson,w.weekNumber,w.sortNumber as week,w.year from CardDB c\n" +
           "    join WeekOfEducationYear w on c.weekOfEducationYear_id = w.id\n" +
           "    join EducationYear_WeekOfEducationYear ew on w.id = ew.weeks_id\n" +
           "    join LessonDB LD on c.lesson_id = LD.id\n" +
           "    join LessonDB_groups LDg on LD.id = LDg.LessonDB_id\n" +
           "    join Lesson l on LD.subject_id = l.id\n" +
           "where ew.EducationYear_id=?1 and LDg.groups_id=?2 and c.day=?3 and w.sortNumber=?4 and w.year=?5 group by c.period, c.betweenDuringDate, c.classroom, c.day, l.name, w.weekNumber, w.sortNumber, w.year",nativeQuery = true)
   Set<GetStudentStatisticsForDeanOneWeekSection> getStudentStatisticsForDeanOneWeekSection(String educationYearId,String groupId,Integer weekday,Integer week,Integer year,String studentId);


   @Query(value = "select * from dbo.GetStudentStatisticsForDeanOneWeekSection(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
   Set<GetStudentStatisticsForDeanOneWeekSection> getStudentStatisticsForDeanOneWeekSectionNEW(String educationYearId,String groupId,Integer year,Integer week,Integer weekday,String studentId);

   @Query(value = "select g.id,g.level,g.name,F.name as facultyName,et.name as educationTypeName from Student s\n" +
           "join groups g on g.id = s.group_id\n" +
           "join Faculty F on F.id = g.faculty_id\n" +
           "join EducationType et on g.educationType_id = et.id\n" +
           "where s.user_id =:userId\n" +
           "group by  g.id, g.level, g.name, F.name, et.name",nativeQuery = true)
   StudentGroupField getGroupFieldByUserId(@Param("userId") String userId);

   @Query(value = "select g.id,g.name from groups g join Student S on g.id = S.group_id where S.user_id=:id",nativeQuery = true)
   GroupForStudent getGroupNameByUserId(@Param("id") String id);
   @Query(value = "select g.id,g.name from groups g\n" +
           "join EducationType ET on g.educationType_id = ET.id\n" +
           "where ET.name=?1 and g.level=?2 and g.faculty_id=?3",nativeQuery = true)
   Set<GroupForStudent> getGroupsByFacultiesIds(String educationType,Integer level,String facilityId);
   @Query(value = "select g.id,g.name from groups g where g.id=:id",nativeQuery = true)
   GroupForStudent getGroupNameByGroupId(@Param("id") String id);

   @Query(value = "select u.id as deanId from groups g\n" +
           "    join Student s on g.id = s.group_id\n" +
           "    join Faculty f on f.id=g.faculty_id\n" +
           "    join Dekanat_Faculty df on f.id=df.faculties_id\n" +
           "join Dekanat D on g.educationType_id = D.eduType_id and D.id = df.Dekanat_id\n" +
           "join users u on D.owner_id = u.id where s.user_id=?1",nativeQuery = true)
   String getDeanId(String studentId);


   @Query(value = "select ?1 as educationYearId, u.id,u.firstName,u.lastName,u.middleName,u.fullName,u.passportNum as passport,u.RFID,u.login from Student s \n" +
           "    join groups g on s.group_id = g.id join users u on s.user_id = u.id where g.name=?2 and s.teachStatus='TEACHING' order by u.fullName",nativeQuery = true)
   Set<StudentsOfGroupWithTodayStatisticsAndScore> getStudentsOfGroupWithTodayStatisticsAndScoreForJournal(String educationYearId,String groupName);
   @Query(value = "select  TOP 1 al.time from acc_monitor_log al\n" +
           "where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and al.card_no=?1 order by al.time",nativeQuery = true)
   Date getFirstOfEntering(String rfid);


   @Query(value = "select dbo.GetFirstOfEntering(?1)",nativeQuery = true)
   Date getFirstOfEnteringNEW(String rfid);

   @Query(value = "select g.id as id,g.name as groupName from groups g \n" +
           "    join EducationLanguage el on g.educationLanguage_id = el.id\n" +
           "    join EducationType ET on g.educationType_id = ET.id\n" +
           "where el.name=?1 and ET.name=?2 and g.level=?3 group by g.id, g.name order by g.name",nativeQuery = true)
   Set<GetGroupsDataForKafedraMudiri> getGroupsForKafedraMudiriWithLangAndEduTypeAndLevel(String lang, String eduType, Integer level);

   @Query(value = "select g.id as id,g.name as groupName from groups g \n" +
           "    join EducationType ET on g.educationType_id = ET.id\n" +
           "where ET.name=?1 and g.level=?2 group by g.id, g.name order by g.name",nativeQuery = true)
   Set<GetGroupsDataForKafedraMudiri> getGroupsForKafedraMudiriWithEduTypeAndLevel( String eduType, Integer level);
   @Query(value = "select g.id as id,g.name as groupName from groups g \n" +
           "    join EducationLanguage el on g.educationLanguage_id = el.id\n" +
           "where el.name=?1 and g.level=?2 group by g.id, g.name order by g.name",nativeQuery = true)
   Set<GetGroupsDataForKafedraMudiri> getGroupsForKafedraMudiriWithLangAndLevel(String lang, Integer level);
   @Query(value = "select g.id as id,g.name as groupName from groups g \n" +
           "where g.level=?1 group by g.id, g.name order by g.name",nativeQuery = true)
   Set<GetGroupsDataForKafedraMudiri> getGroupsForKafedraMudiriWithLevel(Integer level);
   @Query(value = "select g.id as id,g.name as groupName from groups g \n" +
           "    join EducationLanguage el on g.educationLanguage_id = el.id\n" +
           "    join EducationType ET on g.educationType_id = ET.id\n" +
           "where el.name=?1 and ET.name=?2 group by g.id, g.name order by g.name",nativeQuery = true)
   Set<GetGroupsDataForKafedraMudiri> getGroupsForKafedraMudiriWithLangAndEduType(String lang, String eduType);
   @Query(value = "select g.id as id,g.name as groupName from groups g \n" +
           "    join EducationType ET on g.educationType_id = ET.id\n" +
           "where ET.name=?1 group by g.id, g.name order by g.name",nativeQuery = true)
   Set<GetGroupsDataForKafedraMudiri> getGroupsForKafedraMudiriWithEduType(String eduType);
   @Query(value = "select g.id as id,g.name as groupName from groups g \n" +
           "    join EducationLanguage el on g.educationLanguage_id = el.id\n" +
           "where el.name=?1 group by g.id, g.name order by g.name",nativeQuery = true)
   Set<GetGroupsDataForKafedraMudiri> getGroupsForKafedraMudiriWithLang(String lang);
}
