package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import uz.yeoju.yeoju_app.entity.dekanat.Dekan;
import uz.yeoju.yeoju_app.payload.resDto.dekan.*;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dashboard.StudentDataByWeekDay;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dashboard.StudentStatisticsEachOneDay;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dashboard.StudentStatisticsWithWeekOfEduYear;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DekanRepository extends JpaRepository<Dekan,String> {

    Dekan getDekanByUserId(String user_id);

    Optional<Dekan> findDekanByDekanatId(String dekanat_id);

    @Query(value = "select u.id,u.fullName,u.email,u.passportNum,u.login,u.RFID,u.citizenship,u.nationality,g.name as groupName ,g.level from Student s join users u on s.user_id = u.id\n" +
            "join groups g on s.group_id = g.id where u.id=:id",nativeQuery = true)
    StudentDataForEditedDekan getStudentDataForEditedDekan(@Param("id") String id);


    @Query(value = "select u.id,u.RFID, u.fullName,u.login,u.email,u.passportNum,u.citizenship,u.nationality,u.bornYear from users u\n" +
            "    join Student s on s.user_id=u.id\n" +
            "    join groups g on s.group_id = g.id\n" +
            "    join Faculty F on g.faculty_id = F.id\n" +
            "    join Dekanat_Faculty DF on F.id = DF.faculties_id\n" +
            "    join Dekanat D on DF.Dekanat_id=D.id\n" +
            "join Dekan D2 on D.id = D2.dekanat_id\n" +
            "where D2.user_id=:id order by g.name",nativeQuery = true)
    List<StudentSettings> getStudentsForSettings(@Param("id") String id);

    @Query(value = "select id as value ,shortName as label from Faculty",nativeQuery = true)
    List<FacultiesResDto> getFacultiesForStudentTransfer();

//    @Query(value = "select Top 1 f.id,f.name from Faculty f\n" +
//            "join Dekan_Faculty DF on f.id = DF.faculties_id\n" +
//            "join Dekan D on DF.Dekan_id = D.id\n" +
//            "where D.user_id=:id",nativeQuery = true)
//    FacultyForDekan getFacultyForDekan(@Param("id") String id);

    @Query(value = "select Top 1 F.id,F.name from Dekanat_Faculty df\n" +
            "join Dekan D on df.Dekanat_id = D.dekanat_id\n" +
            "join users u on D.user_id = u.id\n" +
            "join Faculty F on df.faculties_id = F.id\n" +
            "where D.user_id=:id",nativeQuery = true)
    FacultyForDekan getFacultyForDekan(@Param("id") String id);


    @Query(value = "select g2.level,g2.name, g1.comeCount,g2.allCount from (\n" +
            "           select g.level,g.name,count(card.cardNo) as comeCount from\n" +
            "               (\n" +
            "                   select  al.card_no as cardNo\n" +
            "                   from acc_monitor_log al  join users u\n" +
            "                                                 on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                                            join users_Role ur on u.id = ur.users_id join Role R2 on ur.roles_id = R2.id\n" +
            "                   where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT'\n" +
            "                   group by al.card_no    ) as card\n" +
            "                   join users u on cast(card.cardNo as varchar) =\n" +
            "                                   cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                   join Student S on u.id = S.user_id\n" +
            "                   join groups g on g.id = S.group_id\n" +
            "                   join Faculty F on F.id = g.faculty_id\n" +
            "                   join Dekanat_Faculty DF on F.id = DF.faculties_id\n" +
            "                   join Dekanat D on DF.Dekanat_id = D.id\n" +
            "                   join Dekan D2 on D.id = D2.dekanat_id\n" +
            "           where D2.user_id=:id and g.educationType_id=D2.educationType_id and g.active=1 \n" +
            "           group by g.name, g.level\n" +
            "       ) as g1\n" +
            "         right join (select g.name,g.level,count(s.id) as allCount from Student s\n" +
            "                    join groups g on s.group_id = g.id join Faculty F on F.id = g.faculty_id\n" +
            "                    join Dekanat_Faculty DF on F.id = DF.faculties_id\n" +
            "                    join Dekanat D on DF.Dekanat_id = D.id\n" +
            "                    join Dekan D2 on D.id = D2.dekanat_id\n" +
            "                     where D2.user_id=:id and g.educationType_id=D2.educationType_id and g.active=1 \n" +
            "                     group by g.name, g.level) as g2 on g2.name = g1.name order by g1.level,g1.name",nativeQuery = true)
    List<CourseStatistics> getCourseStatisticsForDekan(
            @Param("id") String facultyId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @Query(value = "select * from dbo.GetCourseStatisticsForDekan(?1,?2,?3) ORDER BY level, name",nativeQuery = true)
    List<CourseStatistics> getCourseStatisticsForDekanNEW(String dekanId,Date dateFrom,Date dateTo);


//    @Query(value = "select countCome.level,countCome.comeCount,allUser.allCount from\n" +
//            "(select g.level,count(user_id) as comeCount from\n" +
//            "   (\n" +
//            "       select u.id from acc_monitor_log al\n" +
//            "       join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "       join users_Role ur on u.id = ur.users_id\n" +
//            "       join (select id from Role where roleName='ROLE_STUDENT') as role on role.id = ur.roles_id\n" +
//            "       where al.time\n" +
//            "                 between\n" +
//            "                 :dateFrom\n" +
//            "                 and\n" +
//            "                 :dateTo\n" +
//            "group by u.id" +
//            "    ) as users1\n" +
//            "join Student s on users1.id = s.user_id\n" +
//            "   join groups g on g.id = s.group_id\n" +
//            "       join Faculty F on F.id = g.faculty_id\n" +
//            "               where g.faculty_id=:facultyId\n" +
//            "group by g.level\n" +
//            ") as countCome\n" +
//            "join(\n" +
//            "select g1.level, count(s.id) as allCount from Student s\n" +
//            "join groups g1 on g1.id = s.group_id\n" +
//            "join Faculty F on F.id = g1.faculty_id\n" +
//            "where g1.faculty_id=:facultyId\n" +
//            "group by g1.level\n" +
//            ") as allUser on countCome.level=allUser.level order by countCome.level",nativeQuery = true)
//    List<CourseStatistics> getCourseStatisticsForDekan(
//            @Param("facultyId") String facultyId,
//            @Param("dateFrom") LocalDateTime dateFrom,
//            @Param("dateTo") LocalDateTime dateTo
//    );


    @Query(value = "select g2.level,g2.name, g1.comeCount,g2.allCount from (\n" +
            "       select g.level,g.name,count(card.cardNo) as comeCount from\n" +
            "           (\n" +
            "               select  al.card_no as cardNo\n" +
            "               from acc_monitor_log al  join users u\n" +
            "                                             on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                                        join users_Role ur on u.id = ur.users_id\n" +
            "                                        join Role R2 on ur.roles_id = R2.id\n" +
            "               where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT'\n" +
            "               group by al.card_no    ) as card\n" +
            "               join users u on cast(card.cardNo as varchar) =\n" +
            "                               cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "               join Student S on u.id = S.user_id\n" +
            "               join groups g on g.id = S.group_id\n" +
            "               join Faculty F on F.id = g.faculty_id\n" +
            "               join Dekanat_Faculty DF on F.id = DF.faculties_id\n" +
            "               join Dekanat D on DF.Dekanat_id = D.id\n" +
            "               join Dekan D2 on D.id = D2.dekanat_id\n" +
            "       where D2.user_id=:id and g.educationType_id=D2.educationType_id and g.active=1 \n" +
            "       group by g.name, g.level\n" +
            "   ) as g1\n" +
            "     right join (\n" +
            "    select g.name as name,count(s.id) as allCount,g.level as level  from Student s\n" +
            "   join groups g on s.group_id = g.id\n" +
            "   join Faculty F on F.id = g.faculty_id\n" +
            "   join Dekanat_Faculty DF on F.id = DF.faculties_id\n" +
            "   join Dekanat D on DF.Dekanat_id = D.id\n" +
            "   join Dekan D2 on D.id = D2.dekanat_id\n" +
            "    where D2.user_id=:id and g.educationType_id=D2.educationType_id and g.active=1 \n" +
            "    group by g.name, g.level) as g2 on g2.name = g1.name order by g1.level,g1.name",nativeQuery = true)
    List<DekanGroupsStatistic> getGroupsStatisticForDekan(
            @Param("id") String dekanId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );


    @Query(value = "select * from dbo.GetGroupsStatisticForDekan(?1,?2,?3) ORDER BY level, name",nativeQuery = true)
    List<DekanGroupsStatistic> getGroupsStatisticForDekanNEW(String dekanId, Date dateFrom,Date dateTo);


//    @Query(value = "select g1.level,g1.name, g1.comeCount,g2.allCount from (\n" +
//            "            select g.level,g.name,count(card.cardNo) as comeCount from\n" +
//            "              (\n" +
//            "                    select  al.card_no as cardNo\n" +
//            "              from acc_monitor_log al  join users u\n" +
//            "                  on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "                  join users_Role ur on u.id = ur.users_id\n" +
//            "                  join Role R2 on ur.roles_id = R2.id\n" +
//            "              where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT'\n" +
//            "              group by al.card_no    ) as card\n" +
//            "                   join users u on cast(card.cardNo as varchar) =\n" +
//            "                                    cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "                    join Student S on u.id = S.user_id\n" +
//            "                    join groups g on g.id = S.group_id\n" +
//            "                    join Faculty F on F.id = g.faculty_id\n" +
//            "            where F.id =:facultyId\n" +
//            "            group by g.name, g.level\n" +
//            "                ) as g1\n" +
//            "            join (select g.name,count(s.id) as allCount from Student s\n" +
//            "    join groups g on s.group_id = g.id\n" +
//            "    join Faculty F on F.id = g.faculty_id\n" +
//            "    and F.id = :facultyId\n" +
//            "    group by g.name) as g2 on g2.name = g1.name order by g1.level,g1.name",nativeQuery = true)
//    List<DekanGroupsStatistic> getGroupsStatisticForDekan(
//            @Param("facultyId") String facultyId,
//            @Param("dateFrom") LocalDateTime dateFrom,
//            @Param("dateTo") LocalDateTime dateTo
//    );

    @Query(value = "select g.id,g.level,g.name as name, el.name as language,et.name as type,ef.name as form,f.shortName as faculty from groups g\n" +
            "left join EducationLanguage el on g.educationLanguage_id = el.id\n" +
            "left join EducationType et on g.educationType_id = et.id\n" +
            "left join EducationForm ef on g.educationForm_id = ef.id\n" +
            "    join Faculty f on g.faculty_id = f.id\n" +
            "join Dekanat_Faculty d_f on d_f.faculties_id = g.faculty_id\n" +
            "join Dekanat D2 on d_f.Dekanat_id = D2.id\n" +
            "join Dekan D on D2.id = D.dekanat_id\n" +
            "where D.user_id=:id and g.educationType_id=D.educationType_id and g.active=1  order by g.name asc",nativeQuery = true)
    List<GroupsDatas> getGroupsNamesForDekanByFacultyId(@Param("id") String id);

    @Query(value = "select g.id,g.level,g.name as name, el.name as language,et.name as type,ef.name as form,f.shortName as faculty from groups g\n" +
            "left join EducationLanguage el on g.educationLanguage_id = el.id\n" +
            "left join EducationType et on g.educationType_id = et.id\n" +
            "left join EducationForm ef on g.educationForm_id = ef.id\n" +
            "    join Faculty f on g.faculty_id = f.id\n" +
            "join Dekanat_Faculty d_f on d_f.faculties_id = g.faculty_id\n" +
            "join Dekanat D2 on d_f.Dekanat_id = D2.id\n" +
            "join Dekan D on D2.id = D.dekanat_id\n" +
            "where D.user_id=:id and f.id=:facultyId and g.educationType_id=D.educationType_id and g.active=1 order by g.name asc",nativeQuery = true)
    List<GroupsDatas> getGroupsNamesForDekanByFacultyId(@Param("id") String id,@Param("facultyId") String facultyId);


    @Query(value = "select g.id,g.level,g.name as name, el.name as language,et.name as type,ef.name as form,f.shortName as faculty from groups g\n" +
            "    left join EducationLanguage el on g.educationLanguage_id = el.id\n" +
            "    left join EducationType et on g.educationType_id = et.id\n" +
            "    left join EducationForm ef on g.educationForm_id = ef.id\n" +
            "    join Faculty f on g.faculty_id = f.id\n" +
            "where g.faculty_id=?1 and g.level=?2 and et.name=?3 and g.active=1  order by g.name asc",nativeQuery = true)
    List<GroupsDatas> getGroupsNamesByFacultyIdAndLevelAndEduType(String facultyId, Integer course, String eduType);

    @Query(value = "select g.name from groups g\n" +
            "join Dekanat_Faculty DF on Df.faculties_id=g.faculty_id\n" +
            "join Dekanat D on DF.Dekanat_id = D.id\n" +
            "join Dekan d2 on D.id = d2.dekanat_id\n" +
            "where (g.level=:level and d2.user_id=:userId and g.educationType_id=d2.educationType_id and g.active=1) order by g.name asc ",nativeQuery = true)
    List<String> getGroupsNamesForDekanByDekanIdAndLevel(@Param("userId") String userId,@Param("level") Integer level);

//    @Query(value = "select g.name from groups g\n" +
//            "join Dekanat_Faculty DF on Df.faculties_id=g.faculty_id\n" +
//            "join Dekanat D on DF.Dekanat_id = D.id\n" +
//            "join Dekan d2 on D.id = d2.dekanat_id\n" +
//            "where (g.level=:level and d2.user_id=:userId)",nativeQuery = true)
//    List<String> getGroupsNamesForDekanByDekanIdAndLevel(@Param("userId") String userId,@Param("level") Integer level);
//    @Query(value = "select g.name from groups g\n" +
//            "join Dekan_Faculty d_f on d_f.faculties_id = g.faculty_id\n" +
//            "join Dekan D on d_f.Dekan_id = D.id\n" +
//            "where D.user_id=:userId and g.level=:level\n" +
//            "order by g.name,g.level desc",nativeQuery = true)
//    List<String> getGroupsNamesForDekanByDekanIdAndLevel(@Param("userId") String userId,@Param("level") Integer level);

//    @Query(value = "select g.name from groups g\n" +
//            "join Dekan_Faculty d_f on d_f.faculties_id = g.faculty_id\n" +
//            "join Dekan D on d_f.Dekan_id = D.id\n" +
//            "where D.user_id=:userId order by g.name,g.level desc ",nativeQuery = true)
//    List<String> getGroupsNamesForDekanByDekanId(@Param("userId") String userId);

    @Query(value = "select g.name from groups g\n" +
            "join Dekanat_Faculty d_f on d_f.faculties_id = g.faculty_id\n" +
            "    join Dekanat D2 on d_f.Dekanat_id = D2.id\n" +
            "join Dekan D on D2.id = D.dekanat_id\n" +
            "where D.user_id=:userId and g.educationType_id=D.educationType_id and g.active=1 order by g.name asc ",nativeQuery = true)
    List<String> getGroupsNamesForDekanByDekanId(@Param("userId") String userId);

    @Query(value = "select u.id as id,u.fullName as fullName from Student s\n" +
            "join users u on s.user_id = u.id\n" +
            "join groups g on g.id = s.group_id\n" +
            "join Dekan_Faculty df on df.faculties_id = g.faculty_id\n" +
            "join Dekan D on df.Dekan_id = D.id\n" +
            "where (u.passportNum=:searchParam or \n" +
            "u.login=:searchParam or u.RFID=:searchParam) and D.user_id=:dekanId",nativeQuery = true)
    SearchUserForDekanUseSendMessage getUserSearchingForDekan(@Param("searchParam") String searchParam, @Param("dekanId") String dekanId);

    @Query(value = "select u.id as id,u.fullName as fullName from Student s\n" +
            "join users u on s.user_id = u.id\n" +
            "join groups g on g.id = s.group_id\n" +
            "join Dekan_Faculty df on df.faculties_id = g.faculty_id\n" +
            "join Dekan D on df.Dekan_id = D.id\n" +
            "where (u.passportNum like :searchParam or \n" +
            "u.login like :searchParam or u.RFID like :searchParam) and D.user_id=:dekanId",nativeQuery = true)
    List<SearchUserForDekanUseSendMessage> getUserSearchingForDekan2(@Param("searchParam") String searchParam, @Param("dekanId") String dekanId);

    @Query(value = "select Top 16 u.id,count(sr.score) as fails from StudentResult sr\n" +
            "join users u on sr.user_id = u.id\n" +
            "join Student S on u.id = S.user_id\n" +
            "join groups g on S.group_id = g.id\n" +
            "where (g.faculty_id=:facultyId and CONVERT(int, LEFT(sr.score, CHARINDEX('.', sr.score) - 1))<65)\n" +
            "group by u.id order by fails asc",nativeQuery = true)
    List<DekanBestStudent> getBestStudents(@Param("facultyId") String facultyId);

    @Query(value = "select u.id from Student s\n" +
            "join users u on s.user_id = u.id\n" +
            "join groups g on s.group_id = g.id\n" +
            "where g.faculty_id=:id and s.teachStatus='TEACHING' ",nativeQuery = true)
    List<AllStudentsOfFaculty> getAllStudentsOfFaculty(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    GetForBadStudent getForBadStudent(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    BadAndBestStudent getBadAndStudent(@Param("id") String id);


    @Query(value = "select section2.name,section1.count from\n" +
            "    (select g.name,count(g.name) as count from groups g\n" +
            "left join Student s on g.id = s.group_id\n" +
            "    join users u on s.user_id = u.id\n" +
            "    join StudentResult sr on u.id = sr.user_id\n" +
            "where ( g.faculty_id=:facultyId and CONVERT(int, LEFT(sr.score, CHARINDEX('.', sr.score) - 1))<65)\n" +
            "group by g.name) as section1\n" +
            "right join (\n" +
            "    select name  from groups where faculty_id=:facultyId\n" +
            "    ) as section2 on section1.name=section2.name",nativeQuery = true)
    List<FailsCountOfGroup> getFailsCountOfGroup(@Param("facultyId") String facultyId);

    @Query(value = "select u.id,count(sr.score) as fails from StudentResult sr\n" +
            "join users u on sr.user_id = u.id\n" +
            "join Student S on u.id = S.user_id\n" +
            "join groups g on S.group_id = g.id\n" +
            "where (g.name=:group and CONVERT(int, LEFT(sr.score, CHARINDEX('.', sr.score) - 1))<65)\n" +
            "group by u.id order by fails asc",nativeQuery = true)
    List<ForGroupFails> getGroupFails(@Param("group") String group);


    @Query(value = "select :id as id",nativeQuery = true)
    ForStudentTransferData getForStudentTransferData(@Param("id") String id);

    @Query(value = "select id as value ,name as label from groups where faculty_id=:id and level=:course",nativeQuery = true)
    List<GroupByFacultyIdAndCourse> getGroupByFacultyIdAndCourse(@Param("id") String id, @Param("course") Integer course);

    @Query(value = "select ET.name from Dekan d join EducationType ET on d.educationType_id = ET.id where d.user_id=:id",nativeQuery = true)
    Set<String> getDekanEducationTypes(@Param("id") String id);


    @Query(value = "select  DATEADD(d, ?2, ?1) as time,?3 as facultyId,?4 as eduTypeId",nativeQuery = true)
    StudentStatisticsEachOneDay getStudentStatisticsWithWeekOfEduYear(Date start, Integer step, String facultyId,String eduTypeId);

    @Query(value = "select ?5 as groupsArr,  DATEADD(d, ?2, ?1) as time,?3 as facultyId,?4 as eduTypeId",nativeQuery = true)
    StudentStatisticsEachOneDay getStudentStatisticsWithWeekOfEduYear(Date start, Integer step, String facultyId,String eduTypeId,String groupsArr);

    @Query(value = "select  ?2 as facultyId,?4 as eduTypeId, w.id,w.sortNumber,w.weekNumber,w.start,w.[end] from WeekOfEducationYear w join EducationYear_WeekOfEducationYear ed_w on w.id = ed_w.weeks_id where ed_w.EducationYear_id=?1 and w.eduType=?3 ",nativeQuery = true)
    List<StudentStatisticsWithWeekOfEduYear> getStudentStatisticsWithWeekOfEdu(String eduYearId,String facultyId,String weekType,String eduTypeId);


    @Query(value = "select ?5 as groupsArr, ?2 as facultyId,?4 as eduTypeId, w.id,w.sortNumber,w.weekNumber,w.start,w.[end] from WeekOfEducationYear w join EducationYear_WeekOfEducationYear ed_w on w.id = ed_w.weeks_id where ed_w.EducationYear_id=?1 and w.eduType=?3 ",nativeQuery = true)
    List<StudentStatisticsWithWeekOfEduYear> getStudentStatisticsWithWeekOfEdu(String eduYearId,String facultyId,String weekType,String eduTypeId,String groups);

    @Query(value = "select t2.id,t2.fullName,t2.groupName,t2.level from (\n" +
            "            select al.card_no as cardNo, al.time as timeAsc\n" +
            "            from acc_monitor_log al\n" +
            "                     join users u  on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                     join Student s on u.id = s.user_id join groups g on g.id = s.group_id\n" +
            "            where al.time between :start and DATEADD(d, 1, :start) and g.faculty_id=:facultyId and g.educationType_id=:eduTypeId and s.teachStatus='TEACHING' \n" +
            "       ) as t1\n" +
            "     right join (\n" +
            "        select  u.id,u.fullName,g.name as groupName,u.RFID as cardNo,g.level from users u join Student s on u.id = s.user_id\n" +
            "            join groups g on g.id = s.group_id\n" +
            "        where g.faculty_id=:facultyId and g.educationType_id=:eduTypeId and s.teachStatus='TEACHING' \n" +
            "    ) as t2 on cast(t1.cardNo as varchar) = cast(t2.cardNo as varchar) COLLATE Chinese_PRC_CI_AS where t1.cardNo is null",nativeQuery = true)
    Set<StudentDataByWeekDay> getStudentDataByWeekDay(@Param("start") Date start,@Param("facultyId") String facultyId,@Param("eduTypeId") String eduTypeId);

    @Query(value = "select t2.id,t2.fullName,t2.groupName,t2.level from (\n" +
            "    select al.card_no as cardNo, al.time as timeAsc\n" +
            "    from acc_monitor_log al\n" +
            "             join users u  on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS \n" +
            "             join Student s on u.id = s.user_id join groups g on g.id = s.group_id \n" +
            "    where al.time between :start and DATEADD(d, 1, :start) and g.name IN (SELECT value FROM STRING_SPLIT(:groupsArr,',')) and s.teachStatus='TEACHING'  \n" +
            ") as t1\n" +
            "right join (\n" +
            "    select  u.id,u.fullName,g.name as groupName,u.RFID as cardNo,g.level from users u join Student s on u.id = s.user_id \n" +
            "                                                                                      join groups g on g.id = s.group_id \n" +
            "    where g.name IN (SELECT value FROM STRING_SPLIT(:groupsArr,',')) and s.teachStatus='TEACHING' \n" +
            ") as t2 on cast(t1.cardNo as varchar) = cast(t2.cardNo as varchar) COLLATE Chinese_PRC_CI_AS where t1.cardNo is null",nativeQuery = true)
    Set<StudentDataByWeekDay> getStudentDataByWeekDay(@Param("start") Date start,  @Param("groupsArr") String groupsArr);


    @Query(value = "select * from dbo.GetStudentDataByWeekDay(?1,?2)",nativeQuery = true)
    Set<StudentDataByWeekDay> getStudentDataByWeekDayNEW(Date start, String groupsArr);


}
