package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.PlanOfSubject;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetSubjectsForTeacherWithSubjectForPlan;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetTeacherWIthSubjectForPlan;
import uz.yeoju.yeoju_app.payload.resDto.module.GetExistsPlans;
import uz.yeoju.yeoju_app.payload.resDto.module.GetGroupsOfPlan;
import uz.yeoju.yeoju_app.payload.resDto.module.GetPlansForTeacherSciences;

import java.util.Set;

public interface PlanOfSubjectRepository extends JpaRepository<PlanOfSubject,String> {
    Set<PlanOfSubject> getPlanOfSubjectsByUserIdAndEducationYearIdAndSubjectIdAndLevel(String user_id, String educationYear_id, String subject_id, Integer level);


    @Query(value = "select ?2 as educationYearId, u.id,u.fullName,u.firstName,u.lastName,u.middleName from users u\n" +
            "    join Teacher T on u.id = T.user_id\n" +
            "    join Kafedra K on T.kafedra_id = K.id\n" +
            "    where K.owner_id=?1",nativeQuery = true)
    Set<GetTeacherWIthSubjectForPlan> getTeacherWIthSubjectForPlan(String id,String educationYearId);

    @Query(value = "select L.id,L.name from PlanOfSubject p join Lesson L on p.subject_id = L.id where p.user_id=?1 group by L.id, L.name",nativeQuery = true)
    Set<GetSubjectsForTeacherWithSubjectForPlan> getSubjectsForTeacherWithSubjectForPlan(String id);


    @Query(value = "select L.id,L.name from PlanOfSubject p join Lesson L on p.subject_id = L.id where p.user_id=?1 and p.educationYear_id=?2 group by L.id, L.name",nativeQuery = true)
    Set<GetSubjectsForTeacherWithSubjectForPlan> getSubjectsForTeacherWithSubjectForPlan(String id,String educationYearId);


    @Query(value = "select ps.id,L.id as lessonId,L.name as lessonName,ET.name as eduType,EL.name as eduLang,ps.level from PlanOfSubject ps join Lesson L on ps.subject_id = L.id join EducationType ET on ps.educationType_id = ET.id join EducationLanguage EL on ps.educationLanguage_id = EL.id\n" +
            "where ps.user_id=?1 and ps.educationYear_id=?2 and ps.subject_id=?3 and ps.level=?4",nativeQuery = true)
    Set<GetExistsPlans> getExistsPlans(String userId,String educationYearId,String subjectId,Integer level);

    @Query(value = "select ps.id,L.id as lessonId,L.name as lessonName,ET.name as eduType,EL.name as eduLang,ps.level from PlanOfSubject ps\n" +
            "    join EducationType ET on ps.educationType_id = ET.id\n" +
            "    join EducationLanguage EL on ps.educationLanguage_id = EL.id\n" +
            "    join Lesson L on ps.subject_id = L.id\n" +
            "where ps.user_id=?1 and ps.educationYear_id=?2",nativeQuery = true)
    Set<GetExistsPlans> getAllExistsPlans(String userId,String educationYearId);

    @Query(value = "select g.id as groupId,g.name as groupName from PlanOfSubject_groups pg join groups g on pg.groups_id = g.id\n" +
            "where pg.PlanOfSubject_id=?1",nativeQuery = true)
    Set<GetGroupsOfPlan> getGroupsOfPlan(String id);

    @Query(value = "select u.id as userId,  u.fullName ,u.firstName,u.lastName,u.middleName, g.id as groupId,g.name as groupName,g.level,et.name as eduType,EL.name as lang from TeacherConnectSubject ts\n" +
            "    join users u on ts.user_id = u.id\n" +
            "    join EducationYear_WeekOfEducationYear ey_wey on ts.weeks_id = ey_wey.weeks_id\n" +
            "    join TeacherConnectSubject_groups ug on ug.TeacherConnectSubject_id=ts.id\n" +
            "    join groups g on ug.groups_id = g.id\n" +
            "    join EducationType et on g.educationType_id = et.id\n" +
            "    join EducationLanguage EL on g.educationLanguage_id = EL.id\n" +
            "\n" +
            "    where u.id=?1 and ey_wey.EducationYear_id=?2 and ts.lesson_id=?3 and g.level=?4\n" +
            "\n" +
            " group by u.id, u.fullName, u.firstName, u.lastName, u.middleName, g.id, g.name, g.level, et.name, EL.name  order by u.fullName",nativeQuery = true)
    Set<GetPlansForTeacherSciences> getPlansForTeacherSciences(String teacherId, String educationYearId, String subjectId, Integer level);
    @Query(value = "select u.id as userId,  u.fullName ,u.firstName,u.lastName,u.middleName, g.id as groupId,g.name as groupName,g.level,et.name as eduType,EL.name as lang from TeacherConnectSubject ts\n" +
            "    join users u on ts.user_id = u.id\n" +
            "    join EducationYear_WeekOfEducationYear ey_wey on ts.weeks_id = ey_wey.weeks_id\n" +
            "    join TeacherConnectSubject_groups ug on ug.TeacherConnectSubject_id=ts.id\n" +
            "    join groups g on ug.groups_id = g.id\n" +
            "    join EducationType et on g.educationType_id = et.id\n" +
            "    join EducationLanguage EL on g.educationLanguage_id = EL.id\n" +
            "\n" +
            "    where u.id=?1 and ey_wey.EducationYear_id=?2 \n" +
            "\n" +
            " group by u.id, u.fullName, u.firstName, u.lastName, u.middleName, g.id, g.name, g.level, et.name, EL.name  order by u.fullName",nativeQuery = true)
    Set<GetPlansForTeacherSciences> getAllPlansForTeacherSciences(String teacherId, String educationYearId);
}
