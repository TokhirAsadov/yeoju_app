package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentGroupField;
import uz.yeoju.yeoju_app.payload.resDto.group.GroupForStudent;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetGroupsDataForKafedraMudiri;
import uz.yeoju.yeoju_app.payload.resDto.rektor.journal.StudentsOfGroupWithTodayStatisticsAndScore;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group,String> {
   Group findGroupByName(String name);
   boolean existsGroupByName(String name);
   List<Group> findGroupsByLevel(Integer level);
   List<Group> findGroupsByFacultyId(String faculty_id);


   @Query(value = "select g.id,g.level,g.name,F.name as facultyName,et.name as educationTypeName from Student s\n" +
           "join groups g on g.id = s.group_id\n" +
           "join Faculty F on F.id = g.faculty_id\n" +
           "join EducationType et on g.educationType_id = et.id\n" +
           "where s.user_id =:userId\n" +
           "group by  g.id, g.level, g.name, F.name, et.name",nativeQuery = true)
   StudentGroupField getGroupFieldByUserId(@Param("userId") String userId);

   @Query(value = "select g.id,g.name from groups g join Student S on g.id = S.group_id where S.user_id=:id",nativeQuery = true)
   GroupForStudent getGroupNameByUserId(@Param("id") String id);

   @Query(value = "select u.id as deanId from groups g\n" +
           "    join Student s on g.id = s.group_id\n" +
           "    join Faculty f on f.id=g.faculty_id\n" +
           "    join Dekanat_Faculty df on f.id=df.faculties_id\n" +
           "join Dekanat D on g.educationType_id = D.eduType_id and D.id = df.Dekanat_id\n" +
           "join users u on D.owner_id = u.id where s.user_id=?1",nativeQuery = true)
   String getDeanId(String studentId);


   @Query(value = "select ?1 as educationYearId, u.id,u.firstName,u.lastName,u.middleName,u.fullName,u.passportNum as passport,u.RFID,u.login from Student s \n" +
           "    join groups g on s.group_id = g.id join users u on s.user_id = u.id where g.name=?2 order by u.fullName",nativeQuery = true)
   Set<StudentsOfGroupWithTodayStatisticsAndScore> getStudentsOfGroupWithTodayStatisticsAndScoreForJournal(String educationYearId,String groupName);
   @Query(value = "select  TOP 1 al.time from acc_monitor_log al\n" +
           "where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and al.card_no=?1 order by al.time",nativeQuery = true)
   Date getFirstOfEntering(String rfid);


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
