package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.EducationLanguage;
import uz.yeoju.yeoju_app.entity.EducationType;
import uz.yeoju.yeoju_app.entity.moduleV2.PlanOfSubjectV2;
import uz.yeoju.yeoju_app.payload.moduleV2.GetTeacherLessonInModule;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetSubjectsForTeacherWithSubjectForPlan;
import uz.yeoju.yeoju_app.payload.resDto.module.GetGroupsOfPlan;
import uz.yeoju.yeoju_app.payload.resDto.module.GetPlansForTeacherSciences;
import uz.yeoju.yeoju_app.payload.resDto.moduleV2.GetExistsPlansV2;
import uz.yeoju.yeoju_app.payload.resDto.moduleV2.GetTeacherWIthSubjectForPlanV2;
import uz.yeoju.yeoju_app.payload.resDto.moduleV2.GetTeacherWIthSubjectForPlanV22;

import java.util.List;
import java.util.Set;

public interface PlanOfSubjectV2Repository extends JpaRepository<PlanOfSubjectV2,String> {
    Set<PlanOfSubjectV2> getPlanOfSubjectsByEducationYearIdAndSubjectIdAndLevel(String educationYear_id, String subject_id, Integer level);

    @Query(value = "select dbo.get_course_group_details(?1);",nativeQuery = true)
    String getCourseGroupDetails(String planId);

    @Query(value = "select dbo.get_course_details_by_id(?1);",nativeQuery = true)
    String getCourseDetailsByCourseId(String planId);

    Boolean existsByUserIdAndEducationYearIdAndSubjectIdAndLevelAndEducationLanguageIdAndEducationTypeId(String user_id, String educationYear_id, String subject_id, Integer level, String educationLanguage_id, String educationType_id);


    @Query(value = "select g.id as groupId,g.name as groupName from PlanOfSubjectV2_groups pg join groups g on pg.groups_id = g.id\n" +
            "where pg.PlanOfSubjectV2_id=?1",nativeQuery = true)
    Set<GetGroupsOfPlan> getGroupsOfPlan(String id);

    @Query(value = "select ?2 as educationYearId, u.id,u.fullName,u.firstName,u.lastName,u.middleName from users u\n" +
            "    join Teacher T on u.id = T.user_id\n" +
            "    join Kafedra K on T.kafedra_id = K.id\n" +
            "    where K.owner_id=?1",nativeQuery = true)
    Set<GetTeacherWIthSubjectForPlanV2> getTeacherWIthSubjectForPlan(String id, String educationYearId);

    @Query(value = "select TOP 1 u.id,ey.id as educationYearId,u.fullName,u.firstName,u.lastName,u.middleName from users u\n" +
            " join PlanOfSubjectV2 ps on u.id = ps.user_id\n" +
            " join EducationYear ey on ps.educationYear_id = ey.id\n" +
            "where u.id=?1",nativeQuery = true)
    GetTeacherWIthSubjectForPlanV22 getTeacherWIthSubjectForPlanGetData(String id);

    @Query(value = "select L.id,L.name from PlanOfSubjectV2 p join Lesson L on p.subject_id = L.id where p.user_id=?1 group by L.id, L.name",nativeQuery = true)
    Set<GetSubjectsForTeacherWithSubjectForPlan> getSubjectsForTeacherWithSubjectForPlan(String id);


    @Query(value = "select L.id,L.name from PlanOfSubjectV2 p join Lesson L on p.subject_id = L.id where p.user_id=?1 and p.educationYear_id=?2 group by L.id, L.name",nativeQuery = true)
    Set<GetSubjectsForTeacherWithSubjectForPlan> getSubjectsForTeacherWithSubjectForPlan(String id,String educationYearId);


    @Query(value = "select ps.user_id as teacherId, ps.id,L.id as lessonId,L.name as lessonName,ET.name as eduType,EL.name as eduLang,ps.level from PlanOfSubject ps join Lesson L on ps.subject_id = L.id join EducationType ET on ps.educationType_id = ET.id join EducationLanguage EL on ps.educationLanguage_id = EL.id\n" +
            "where ps.user_id=?1 and ps.educationYear_id=?2 and ps.subject_id=?3 and ps.level=?4",nativeQuery = true)
    Set<GetExistsPlansV2> getExistsPlans(String userId,String educationYearId,String subjectId,Integer level);

    @Query(value = "select ps.user_id as teacherId, ps.id,L.id as lessonId,L.name as lessonName,ET.name as eduType,EL.name as eduLang,ps.level from PlanOfSubjectV2 ps\n" +
            "    join EducationType ET on ps.educationType_id = ET.id\n" +
            "    join EducationLanguage EL on ps.educationLanguage_id = EL.id\n" +
            "    join Lesson L on ps.subject_id = L.id\n" +
            "where ps.user_id=?1 and ps.educationYear_id=?2",nativeQuery = true)
    Set<GetExistsPlansV2> getAllExistsPlans(String userId, String educationYearId);

    @Query(value = "select ps.user_id as teacherId, ps.id,L.id as lessonId,L.name as lessonName,ET.name as eduType,EL.name as eduLang,ps.level from PlanOfSubjectV2 ps\n" +
            "    join EducationType ET on ps.educationType_id = ET.id\n" +
            "    join EducationLanguage EL on ps.educationLanguage_id = EL.id\n" +
            "    join Lesson L on ps.subject_id = L.id\n" +
            "where ps.createdBy=?1 ",nativeQuery = true)
    Set<GetExistsPlansV2> getPlansByKafedraId(String userId);

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

    @Query(value = "select\n" +
            "    p.id as planId,\n" +
            "    u.id as teacherId,\n" +
            "    u.fullName as teacherFullName,\n" +
            "    l.id as lessonId,\n" +
            "    l.name as lessonName,\n" +
            "    p.level ,\n" +
            "    ey.name as educationYear,\n" +
            "    el.name as educationLanguage,\n" +
            "    et.name as educationType\n" +
            "from PlanOfSubjectV2 p\n" +
            "    join users u on p.user_id = u.id\n" +
            "         join EducationYear ey on ey.id=p.educationYear_id\n" +
            "         join EducationLanguage el on p.educationLanguage_id=el.id\n" +
            "         join EducationType et on p.educationType_id=et.id\n" +
            "         join Lesson l on p.subject_id=l.id\n" +
            "where p.user_id=?1 and p.educationYear_id=?2",nativeQuery = true)
    List<GetTeacherLessonInModule> getTeacherLessonByTeacherIdAndEducationYearId(String teacherId, String educationYearId);

    @Query(value = "select\n" +
            "    p.id as planId,\n" +
            "    u.id as teacherId,\n" +
            "    u.fullName as teacherFullName,\n" +
            "    l.id as lessonId,\n" +
            "    l.name as lessonName,\n" +
            "    p.level ,\n" +
            "    ey.name as educationYear,\n" +
            "    el.name as educationLanguage,\n" +
            "    et.name as educationType\n" +
            "from PlanOfSubjectV2 p\n" +
            "    join users u on p.user_id = u.id\n" +
            "         join EducationYear ey on ey.id=p.educationYear_id\n" +
            "         join EducationLanguage el on p.educationLanguage_id=el.id\n" +
            "         join EducationType et on p.educationType_id=et.id\n" +
            "         join Lesson l on p.subject_id=l.id\n" +
            "where l.id=?1 and p.educationYear_id=?2",nativeQuery = true)
    List<GetTeacherLessonInModule> getPlansBySubjectId(String lessonId, String educationYearId);
}
