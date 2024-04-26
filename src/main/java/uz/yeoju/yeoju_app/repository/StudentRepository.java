package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.enums.TeachStatus;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentDataForTeachStatus;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StudentEduLangFormType;
import uz.yeoju.yeoju_app.payload.resDto.student.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findStudentByUserId(String user_id);
    Student findStudentByUserLogin(String user_login);


    @Query(value = "select ?1 as year,?2 as week,?3 as studentId,?4 as groupId;",nativeQuery = true)
    MonitoringByWeek getMonitoringByWeek(Integer year,Integer week,String studentId,String groupId);


    @Query(value = "SELECT\n" +
            "    ?3 as studentId,?4 as groupId,\n" +
            "    DATEADD(DAY, number, DATEADD(WEEK, ?2 - 1, DATEADD(YEAR, ?1 - 1900, 0))) AS date,\n" +
            "    DATEPART(YEAR , DATEADD(DAY, number, DATEADD(WEEK, ?2 - 1, DATEADD(YEAR, ?1 - 1900, 0)))) AS year,\n" +
            "    DAY(DATEADD(DAY, number, DATEADD(WEEK, ?2 - 1, DATEADD(YEAR, ?1 - 1900, 0)))) AS day,\n" +
            "    DATEPART(WEEK, DATEADD(DAY, number, DATEADD(WEEK, ?2 - 1, DATEADD(YEAR, ?1 - 1900, 0)))) AS week,\n" +
            "    DATEPART(WEEKDAY, DATEADD(DAY, number, DATEADD(WEEK, ?2 - 1, DATEADD(YEAR, ?1 - 1900, 0))))-1 AS weekDay\n" +
            "FROM\n" +
            "    master..spt_values\n" +
            "WHERE\n" +
            "    type = 'P'\n" +
            "  AND number BETWEEN 0 AND 6\n" +
            "  AND DATEPART(WEEK, DATEADD(DAY, number, DATEADD(WEEK, ?2 - 1, DATEADD(YEAR, ?1 - 1900, 0)))) = ?2",nativeQuery = true)
    Set<MonitoringByMonth> getDataOfMonitoringByWeek(Integer year,Integer week,String studentId,String groupId);


    @Query(value = "WITH DateList AS (\n" +
            "    SELECT CAST(?1 AS DATE) AS [Date]\n" +
            "    UNION ALL\n" +
            "    SELECT DATEADD(DAY, 1, [Date])\n" +
            "    FROM DateList\n" +
            "    WHERE MONTH(DATEADD(DAY, 1, [Date])) = MONTH(?1)\n" +
            ")\n" +
            "\n" +
            "SELECT ?2 as studentId,?3 as groupId,DATEPART(YEAR , [Date]) as year,DATEPART(WEEK, [Date]) as week,DAY([Date]) as day,DATEPART(DW,[Date])-1 as weekDay,[Date]\n" +
            "FROM DateList\n" +
            "OPTION (MAXRECURSION 0);",nativeQuery = true)
    Set<MonitoringByMonth> getMonitoringByMonth(String date,String studentId,String groupId);


    @Query(value = "select * from dbo.GetAllSubjectsByDayAndGroupAndStudentId(?1,?2,?3,?4,?5) order by section;",nativeQuery = true)
    Set<GetAllSubjectsByDayAndGroupAndStudentId> getAllSubjectsByDayAndGroupAndStudentId(String studentId,String groupId,Integer year,Integer week,Integer day);

    @Query(value = "select dbo.GetStudentMonitoringByDay(?1,?2,?3,?4,?5);",nativeQuery = true)
    String getStudentMonitoringByDay(String studentId,String groupId,Integer year,Integer week,Integer day);

    @Query(value = "select dbo.GetStudentMonitoringByWeek(?1,?2,?3,?4);",nativeQuery = true)
    String getStudentMonitoringByWeek(String studentId,String groupId,Integer year,Integer week);

    @Query(value = "select dbo.GetStudentMonitoringByEducationYear(?1,?2,?3)",nativeQuery = true)
    String getStudentMonitoringByEducationYear(String studentId,String groupId,String educationYearId);

    @Query(value = "select ?1 as studentId, ?2 as groupId, ?3 as educationYearId,?4 as year,?5 as week,?6 as day",nativeQuery = true)
    StudentMonitoringRestDto getStudentMonitoring(String studentId,String groupId, String educationYearId, Integer year, Integer week, Integer day);

    @Query(value = "select \n" +
            "    u.id,\n" +
            "    u.fullName,\n" +
            "    u.firstName,\n" +
            "    u.lastName,\n" +
            "    u.middleName,\n" +
            "    u.login,\n" +
            "    u.passportNum as passport,\n" +
            "    u.RFID,\n" +
            "    s.teachStatus,\n" +
            "    s.rektororder,\n" +
            "    g.name as groupName\n" +
            "\n" +
            "    from Student s\n" +
            "    join users u on s.user_id = u.id\n" +
            "    join groups g on s.group_id = g.id\n" +
            "    where s.teachStatus=?1 and g.faculty_id=?2",nativeQuery = true)
    Set<StudentDataForTeachStatus> getStudentDataForTeachStatus2(String status,String facultyId);


    @Query(value = "select\n" +
            "\n" +
            "    u.id,\n" +
            "    u.fullName,\n" +
            "    u.firstName,\n" +
            "    u.lastName,\n" +
            "    u.middleName,\n" +
            "    u.login,\n" +
            "    u.passportNum,\n" +
            "    u.RFID,\n" +
            "    s.teachStatus,\n" +
            "    s.rektororder,\n" +
            "    g.name as groupName,\n" +
            "    f.name as facultyName\n" +
            "\n" +
            "    from Student s\n" +
            "                  join users u on s.user_id = u.id\n" +
            "                  join groups g on s.group_id = g.id\n" +
            "         join Faculty F on g.faculty_id = F.id\n" +
            "where s.teachStatus=?1",nativeQuery = true)
    Set<StudentDataForTeachStatus> getStudentDataForTeachStatus(String status);


    @Query(value = "select\n" +
            "\n" +
            "    u.id,\n" +
            "    u.fullName,\n" +
            "    u.firstName,\n" +
            "    u.lastName,\n" +
            "    u.middleName,\n" +
            "    u.login,\n" +
            "    u.passportNum,\n" +
            "    u.RFID,\n" +
            "    s.teachStatus,\n" +
            "    s.rektororder,\n" +
            "    g.name as groupName,\n" +
            "    f.name as facultyName\n" +
            "\n" +
            "    from Student s\n" +
            "                  join users u on s.user_id = u.id\n" +
            "                  join groups g on s.group_id = g.id\n" +
            "         join Faculty F on g.faculty_id = F.id\n" +
            "where s.teachStatus!='TEACHING' and s.teachStatus is not null",nativeQuery = true)
    Set<StudentDataForTeachStatus> getStudentDataForTeachStatusAll();

    @Query(value = "select\n" +
            "\n" +
            "    u.id,\n" +
            "    u.fullName,\n" +
            "    u.firstName,\n" +
            "    u.lastName,\n" +
            "    u.middleName,\n" +
            "    u.login,\n" +
            "    u.passportNum,\n" +
            "    u.RFID,\n" +
            "    s.teachStatus,\n" +
            "    s.rektororder,\n" +
            "    g.name as groupName,\n" +
            "    f.name as facultyName\n" +
            "\n" +
            "    from Student s\n" +
            "                  join users u on s.user_id = u.id\n" +
            "                  join groups g on s.group_id = g.id\n" +
            "         join Faculty F on g.faculty_id = F.id\n" +
            "where s.teachStatus!='TEACHING' and s.teachStatus is not null and g.faculty_id=?1",nativeQuery = true)
    Set<StudentDataForTeachStatus> getStudentDataForTeachStatusAllByFacultyId(String facultyId);


    @Query(value = "select\n" +
            "\n" +
            "    u.id,\n" +
            "    u.fullName,\n" +
            "    u.firstName,\n" +
            "    u.lastName,\n" +
            "    u.middleName,\n" +
            "    u.login,\n" +
            "    u.passportNum,\n" +
            "    u.RFID,\n" +
            "    s.teachStatus,\n" +
            "    s.rektororder,\n" +
            "    g.name as groupName,\n" +
            "    f.name as facultyName\n" +
            "\n" +
            "    from Student s\n" +
            "                  join users u on s.user_id = u.id\n" +
            "                  join groups g on s.group_id = g.id\n" +
            "         join Faculty F on g.faculty_id = F.id\n" +
            "    join Dekanat_Faculty DF on F.id = DF.faculties_id\n" +
            "    join Dekanat D on DF.Dekanat_id = D.id\n" +
            "where s.teachStatus!='TEACHING' and s.teachStatus is not null and D.owner_id=?1",nativeQuery = true)
    Set<StudentDataForTeachStatus> getStudentDataForTeachStatusAllByDekanatOwnerId(String ownerId);


    @Query(value = "select  COUNT(al.card_no) as count\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) =\n" +
            "                 cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id,roleName from Role where (roleName <> 'ROLE_STUDENT' and roleName <> 'ROLE_TEACHER' )) as role\n" +
            "              on ur.roles_id = role.id\n" +
            "where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "group by al.card_no, u.fullName, role.roleName\n" +
            "\n" +
            "union\n" +
            "\n" +
            "select count(u.id) from users u  join users_Role ur\n" +
            "    on u.id = ur.users_id join (select id,roleName from Role where (roleName <> 'ROLE_STUDENT' and roleName <> 'ROLE_TEACHER')) as role\n" +
            "    on ur.roles_id = role.id",nativeQuery = true)
    List<Integer> getStaffComeCount();



    @Query(value = "SELECT count(f.cardNo) as comeCount from (\n" +
            "select  COUNT(al.card_no) as cardNo\n" +
            "from acc_monitor_log al\n" +
            "      join users u\n" +
            "           on cast(u.RFID as varchar) =\n" +
            "              cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "      join users_Role ur\n" +
            "           on u.id = ur.users_id\n" +
            "      join (select id from Role where roleName = 'ROLE_STUDENT') as role\n" +
            "           on ur.roles_id = role.id\n" +
            "where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "group by al.card_no\n" +
            "\n" +
            ") as f\n" +
            "union\n" +
            "select count(s.id) from Student s join AddressUser a on s.user_id = a.user_id and s.teachStatus='TEACHING'",nativeQuery = true)
    List<Integer> getStudentComeCount();

    @Query(value = "select * from groups g\n" +
            "join Student s on g.id = s.group_id\n" +
            "join users u on s.user_id = u.id\n" +
            "where u.login =:login",nativeQuery = true)
    Group getGroupByUserLogin(@Param("login") String login);

    List<Student> findStudentsByGroupId(String group_id);
//    List<Student> findStudentsByEducationFormId(String educationForm_id);
//    List<Student> findStudentsByEducationTypeId(String educationType_id);
//    List<Student> findStudentsByEducationLanguageId(String educationLanguage_id);
    Student findStudentByPassportSerial(String passportSerial);
    List<Student> findStudentsByBornYear(Timestamp bornYear);
    List<Student> findStudentsByEnrollmentYear(Timestamp enrollmentYear);
    List<Student> findStudentsByCitizenship(String citizenship);
    boolean existsStudentByUserId(String user_id);

    @Query(value = "select  f1.name,f1.count as comeCount,f2.count as allCount from (\n" +
            "select F.name,count(card.cardNo) as count from\n" +
            "(select  al.card_no as cardNo\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) =\n" +
            "                 cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id from Role where roleName = 'ROLE_STUDENT') as role\n" +
            "              on ur.roles_id = role.id\n" +
            "where al.time between :dateFrom and :dateTo\n" +
            "group by al.card_no\n" +
            ") as card\n" +
            "join users u on cast(card.cardNo as varchar) =\n" +
            "      cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "group by F.name\n" +
            "              ) as f1\n" +
            "join (\n" +
            "    select F.name, count(S.id) as count from Student S\n" +
            "     join groups g on S.group_id = g.id\n" +
            "     join Faculty F on F.id = g.faculty_id\n" +
            "    group by F.name\n" +
            ") as f2 on f2.name = f1.name",nativeQuery = true)
    List<FacultyStatistic> getFacultyAndComingCountWithAll(@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "select F.name, count(S.id) as count from Student S\n" +
            "join groups g on S.group_id = g.id\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "group by F.name",nativeQuery = true)
    List<FacultyStatisticComing> getAllStudentByFaculty();

    @Query(value =  "select  f1.name,f1.count as comeCount,f2.count as allCount from\n" +
            "    (\n" +
            "        select F.name,count(card.cardNo) as count from\n" +
            "            (\n" +
            "                select  al.card_no as cardNo\n" +
            "                from acc_monitor_log al\n" +
            "                 join users u\n" +
            "                      on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                 join users_Role ur\n" +
            "                      on u.id = ur.users_id\n" +
            "                 join Role R2 on ur.roles_id = R2.id\n" +
            "                where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT'\n" +
            "                group by al.card_no\n" +
            "            ) as card\n" +
            "                join users u on cast(card.cardNo as varchar) =\n" +
            "                                cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                join Student S on u.id = S.user_id\n" +
            "                join groups g on g.id = S.group_id\n" +
            "                join Faculty F on F.id = g.faculty_id\n" +
            "        where g.level=:level\n" +
            "        group by F.name\n" +
            "    ) as f1\n" +
            "        join (\n" +
            "        select F.name, count(S.id) as count from Student S\n" +
            "                         join groups g on S.group_id = g.id\n" +
            "                         join Faculty F on F.id = g.faculty_id\n" +
            "        where g.level=:level\n" +
            "        group by F.name\n" +
            "    ) as f2 on f2.name = f1.name",nativeQuery = true)
    List<FacultyStatistic> getFacultyAndComingCountWithAllByGroupLevel(@Param("level") Integer level,@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);


    @Query(value = "select  f2.name,f1.count as comeCount,f2.count as allCount from\n" +
            "    (\n" +
            "        select F.name,count(card.cardNo) as count from\n" +
            "            (\n" +
            "                select  al.card_no as cardNo\n" +
            "                from acc_monitor_log al\n" +
            "                         join users u  on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                         join users_Role ur  on u.id = ur.users_id\n" +
            "                         join Role R2 on ur.roles_id = R2.id\n" +
            "                where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT'\n" +
            "                group by al.card_no\n" +
            "            ) as card\n" +
            "                join users u on cast(card.cardNo as varchar) =  cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                join Student S on u.id = S.user_id\n" +
            "                join groups g on g.id = S.group_id\n" +
            "                join EducationType et on g.educationType_id = et.id\n" +
            "                join Faculty F on F.id = g.faculty_id\n" +
            "        where g.level=:level and F.schoolCode=:schoolCode and et.name=:eduType\n" +
            "        group by F.name\n" +
            "    ) as f1\n" +
            "        right join (\n" +
            "        select F.name, count(S.id) as count from Student S\n" +
            "            join groups g on S.group_id = g.id\n" +
            "            join EducationType et on g.educationType_id = et.id\n" +
            "            join Faculty F on F.id = g.faculty_id\n" +
            "        where g.level=:level and F.schoolCode=:schoolCode and et.name=:eduType\n" +
            "        group by F.name\n" +
            "    ) as f2 on f2.name = f1.name",nativeQuery = true)
    List<FacultyStatistic> getFacultyAndComingCountWithAllByGroupLevelWithSchoolCode(@Param("schoolCode") Integer schoolCode,@Param("level") Integer level,@Param("eduType") String eduType,@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "select s.id,s.nameEn as schoolName, s.code as schoolCode,:eduType as eduType ,:level as level, :dateFrom as dateFrom, :dateTo as dateTo from School s order by s.code",nativeQuery = true)
    List<FacultyStatisticsWithSchoolCode> getSchoolStatistics(@Param("level") Integer level,@Param("eduType") String eduType,@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);


    @Query(value = "select :eduType as eduType, s.id,s.nameEn as schoolName, s.code as schoolCode,:level as level, :weekOrMonth as weekOrMonth from School s order by s.code",nativeQuery = true)
    List<FacultyStatisticsWithWeekOrMonth> getSchoolStatisticsByWeekOrMonth(@Param("level") Integer level,@Param("weekOrMonth") Integer weekOrMonth,@Param("eduType") String eduType);


//
//    @Query(value =  "select  f1.name,f1.count as comeCount,f2.count as allCount from\n" +
//            "    (\n" +
//            "        select F.name,count(card.cardNo) as count from\n" +
//            "            (\n" +
//            "                select  al.card_no as cardNo\n" +
//            "                from acc_monitor_log al\n" +
//            "                 join users u\n" +
//            "                      on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "                 join users_Role ur\n" +
//            "                      on u.id = ur.users_id\n" +
//            "                 join Role R2 on ur.roles_id = R2.id\n" +
//            "                where al.time between dateadd(d,:weekOrMonth,convert(DATE,GETDATE())) and dateadd(d,1,convert(DATE,GETDATE())) and R2.roleName='ROLE_STUDENT'\n" +
//            "                group by al.card_no\n" +
//            "            ) as card\n" +
//            "                join users u on cast(card.cardNo as varchar) =\n" +
//            "                                cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "                join Student S on u.id = S.user_id\n" +
//            "                join groups g on g.id = S.group_id\n" +
//            "                join Faculty F on F.id = g.faculty_id\n" +
//            "        where g.level=:level\n" +
//            "        group by F.name\n" +
//            "    ) as f1\n" +
//            "        join (\n" +
//            "        select F.name, count(S.id) as count from Student S\n" +
//            "                         join groups g on S.group_id = g.id\n" +
//            "                         join Faculty F on F.id = g.faculty_id\n" +
//            "        where g.level=:level\n" +
//            "        group by F.name\n" +
//            "    ) as f2 on f2.name = f1.name",nativeQuery = true)
//    List<FacultyStatistic> getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth(@Param("level") Integer level,@Param("weekOrMonth") Integer weekOrMonth);

    @Query(value = "select g1.name,g1.come as comeCount,g2.allCount from\n" +
            "(\n" +
            "select g.name ,count (card.cardNo) as come from (\n" +
            "    select  al.card_no as cardNo\n" +
            "     from acc_monitor_log al\n" +
            "  join users u\n" +
            "       on cast(u.RFID as varchar) =\n" +
            "          cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "  join users_Role ur\n" +
            "       on u.id = ur.users_id\n" +
            "  join (select id from Role where roleName = 'ROLE_STUDENT') as role\n" +
            "       on ur.roles_id = role.id\n" +
            "where al.time between :dateFrom and :dateTo\n" +
            "group by al.card_no\n" +
            "\n" +
            ") as card\n" +
            "join users u on cast(card.cardNo as varchar) =\n" +
            "                cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "where g.level=:level and F.name=:facultyName\n" +
            "group by F.name,g.name\n" +
            "    ) as g1\n" +
            "join\n" +
            "(\n" +
            "select g.name,count(s.id) as allCount from Student s\n" +
            "join groups g on s.group_id = g.id\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "where g.level=:level  and F.name=:facultyName\n" +
            "group by g.name\n" +
            "    ) as g2 on g1.name = g2.name",nativeQuery = true)
    List<FacultyStatistic> getStudentCountByGroupAndFaculty(@Param("facultyName") String facultyName,@Param("level") Integer level,@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "select F.name  from groups g\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "                      where level=:level\n" +
            "group by g.level,F.name order by F.name asc",nativeQuery = true)
    List<String> getFacultyByCourseLevel(@Param("level") Integer level);

    @Query(value = "select level from groups\n" +
            "group by level",nativeQuery = true)
    List<Integer> getAllCourses();


    @Query(value = "select F.name,count(card.cardNo) as comeCount from\n" +
            "    (\n" +
            "        select  al.card_no as cardNo\n" +
            "        from acc_monitor_log al\n" +
            "                 join users u\n" +
            "                      on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                 join users_Role ur\n" +
            "                      on u.id = ur.users_id\n" +
            "                 join Role R2 on ur.roles_id = R2.id\n" +
            "        where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT'\n" +
            "        group by al.card_no\n" +
            "    ) as card\n" +
            "        join users u on cast(card.cardNo as varchar) =\n" +
            "                        cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "        join Student S on u.id = S.user_id\n" +
            "        join groups g on g.id = S.group_id\n" +
            "        join Faculty F on F.id = g.faculty_id\n" +
            "where g.level=:level\n" +
            "group by F.name",nativeQuery = true)
    List<FacultyStatisticComing> getComeCountStudentAndFacultyName(@Param("level") Integer level,@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "select F.name,count(s.id) as allCount from Student s\n" +
            "join groups g on g.id = s.group_id\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "where level=:level\n" +
            "group by F.name",nativeQuery = true)
    List<FacultyStatisticAll> getAllCountStudentAndFaculty(@Param("level") Integer level);

    @Query(value = "select g.name from groups g\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "where g.level=:level and F.name=:facultyName\n" +
            "order by g.name",nativeQuery = true)
    List<String> getGroupsByLevelAndFacultyName(@Param("level") Integer level,@Param("facultyName") String facultyName);

    @Query(value = "select g1.name, g1.comeCount,g2.allCount from (\n" +
            "select g.name,count(card.cardNo) as comeCount from\n" +
            "    (\n" +
            "        select  al.card_no as cardNo\n" +
            "        from acc_monitor_log al\n" +
            "                 join users u\n" +
            "                      on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                 join users_Role ur\n" +
            "                      on u.id = ur.users_id\n" +
            "                 join Role R2 on ur.roles_id = R2.id\n" +
            "        where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT'\n" +
            "        group by al.card_no\n" +
            "    ) as card\n" +
            "        join users u on cast(card.cardNo as varchar) =\n" +
            "                        cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "        join Student S on u.id = S.user_id\n" +
            "        join groups g on g.id = S.group_id\n" +
            "        join Faculty F on F.id = g.faculty_id\n" +
            "where g.level=:level and F.name =:facultyName\n" +
            "group by g.name\n" +
            "    ) as g1\n" +
            "join (\n" +
            "    select g.name,count(s.id) as allCount from Student s\n" +
            "    join groups g on s.group_id = g.id\n" +
            "    join Faculty F on F.id = g.faculty_id\n" +
            "    where g.level = :level\n" +
            "      and F.name = :facultyName\n" +
            "    group by g.name\n" +
            ") as g2 on g2.name = g1.name\n" +
            "order by g1.name",nativeQuery = true)
    List<FacultyStatistic> getGroupsStatisticByFacultyNameAndGroupLevel(
            @Param("level") Integer level,
            @Param("facultyName") String facultyName,
            @Param("dateFrom")LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @Query(value = "select g1.name, g1.comeCount,g2.allCount from (\n" +
            "select g.name,count(card.cardNo) as comeCount from\n" +
            "    (\n" +
            "        select  al.card_no as cardNo\n" +
            "        from acc_monitor_log al\n" +
            "                 join users u\n" +
            "                      on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                 join users_Role ur\n" +
            "                      on u.id = ur.users_id\n" +
            "                 join Role R2 on ur.roles_id = R2.id\n" +
            "        where al.time between dateadd(d,:timeInterval,convert(DATE,GETDATE())) and dateadd(d,1,convert(DATE,GETDATE())) and R2.roleName='ROLE_STUDENT'\n" +
            "        group by al.card_no\n" +
            "    ) as card\n" +
            "        join users u on cast(card.cardNo as varchar) =\n" +
            "                        cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "        join Student S on u.id = S.user_id\n" +
            "        join groups g on g.id = S.group_id\n" +
            "        join Faculty F on F.id = g.faculty_id\n" +
            "where g.level=:level and F.name =:facultyName\n" +
            "group by g.name\n" +
            "    ) as g1\n" +
            "join (\n" +
            "    select g.name,count(s.id) as allCount from Student s\n" +
            "    join groups g on s.group_id = g.id\n" +
            "    join Faculty F on F.id = g.faculty_id\n" +
            "    where g.level = :level\n" +
            "      and F.name = :facultyName\n" +
            "    group by g.name\n" +
            ") as g2 on g2.name = g1.name\n" +
            "order by g1.name",nativeQuery = true)
    List<FacultyStatistic> getStudentsByTimeIntervalAndLevelAndFacultyName(
            @Param("timeInterval") Integer timeInterval,
            @Param("level") Integer level,
            @Param("facultyName") String facultyName
    );

    @Query(value = "select u.accountNonLocked,s.teachStatus as status, u.id,u.firstName,u.lastName, u.middleName,u.fullName,u.email,u.RFID, u.login, u.passportNum as passport from users u\n" +
            "join Student s on u.id = s.user_id\n" +
            "join groups g on g.id = s.group_id\n" +
            "where g.name =:groupName and (s.teachStatus ='TEACHING' or s.teachStatus is null )",nativeQuery = true)
    List<StudentWithRFID> getStudentWithRFID(@Param("groupName") String groupName);

    @Query(value = "select t1.cardNo,t1.timeAsc,t2.timeDesc from (\n" +
            "select  Top 1 al.card_no as cardNo, al.time as timeAsc\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join Role R2 on ur.roles_id = R2.id\n" +
            "where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT' and cast(:cardNo as varchar) = cast(al.card_no as varchar)\n" +
            "order by al.time asc\n" +
            "              ) as t1\n" +
            "join (\n" +
            "select  Top 1 al.card_no, al.time as timeDesc\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join Role R2 on ur.roles_id = R2.id\n" +
            "where al.time between :dateFrom and :dateTo and R2.roleName='ROLE_STUDENT' and  cast(:cardNo as varchar) = cast(al.card_no as varchar)\n" +
            "order by al.time desc\n" +
            ") as t2 on t1.cardNo = t2.card_no",nativeQuery = true)
    StudentWithAscAndDescDate getStudentWithAscAndDescDate(
            @Param("cardNo") String cardNo,
            @Param("dateFrom")LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @Query(value = "select t1.cardNo,t1.timeAsc,t2.timeDesc from (\n" +
            "select  Top 1 al.card_no as cardNo, al.time as timeAsc\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join Role R2 on ur.roles_id = R2.id\n" +
            "where al.time between dateadd(d,0,convert(DATE,GETDATE())) and dateadd(d,1,convert(DATE,GETDATE())) and al.card_no =:cardNo\n" +
            "order by al.time asc\n" +
            "              ) as t1\n" +
            "join (\n" +
            "select  Top 1 al.card_no, al.time as timeDesc\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join Role R2 on ur.roles_id = R2.id\n" +
            "where al.time between dateadd(d,0,convert(DATE,GETDATE())) and dateadd(d,1,convert(DATE,GETDATE())) and R2.roleName='ROLE_STUDENT' and al.card_no =:cardNo\n" +
            "order by al.time desc\n" +
            ") as t2 on t1.cardNo = t2.card_no",nativeQuery = true)
    StudentWithAscAndDescDate getStudentWithAscAndDescDateForToday(
            @Param("cardNo") String cardNo
    );

    @Query(value = "select g.* from Student s\n" +
            "join users u on s.user_id = u.id\n" +
            "join groups g on g.id = s.group_id\n" +
            "where u.login=:login",nativeQuery = true)
    Group getGroupIdForStudentResult(@Param("login") String login);

    @Query(value = "select TOP 50 u.id,u.fullName,g.name as groupName,g.level,EF.name as form,ET.name as type,EL.name as lang from Student s\n" +
            "join users u on s.user_id = u.id\n" +
            "join groups g on s.group_id = g.id\n" +
            "    join EducationType ET on g.educationType_id = ET.id\n" +
            "    join EducationLanguage EL on g.educationLanguage_id = EL.id\n" +
            "    left join EducationForm EF on g.educationForm_id = EF.id\n" +
            "where g.faculty_id=:id",nativeQuery = true)
    List<ForStudentTransfer> getStudentsForTransfer(@Param("id") String id);


    @Query(value = "select count(s.user_id) as count, EL.name from Student s\n" +
            "join groups g on s.group_id = g.id\n" +
            "join EducationLanguage EL on g.educationLanguage_id = EL.id\n" +
            "group by EL.name",nativeQuery = true)
    List<StudentEduLangFormType> getCountStudentLang();

    @Query(value = "select count(s.user_id) as count, EF.name from Student s\n" +
            "join groups g on s.group_id = g.id\n" +
            "join EducationForm EF on g.educationForm_id = EF.id\n" +
            "group by EF.name",nativeQuery = true)
    List<StudentEduLangFormType> getCountStudentForm();

    @Query(value = "select count(s.user_id) as count, ET.name from Student s\n" +
            "join groups g on s.group_id = g.id\n" +
            "join EducationType ET on g.educationType_id = ET.id\n" +
            "group by ET.name",nativeQuery = true)
    List<StudentEduLangFormType> getCountStudentType();


    @Query(value = "select  f2.name,f1.count as comeCount,f2.count as allCount from\n" +
            "    (\n" +
            "        select F.name,count(card.cardNo) as count from\n" +
            "            (\n" +
            "                select  al.card_no as cardNo\n" +
            "                from acc_monitor_log al\n" +
            "                         join users u\n" +
            "                              on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                         join users_Role ur\n" +
            "                              on u.id = ur.users_id\n" +
            "                         join Role R2 on ur.roles_id = R2.id\n" +
            "                where al.time between dateadd(d,:weekOrMonth,convert(DATE,GETDATE())) and dateadd(d,1,convert(DATE,GETDATE())) and R2.roleName='ROLE_STUDENT'\n" +
            "                group by al.card_no\n" +
            "            ) as card\n" +
            "                join users u on cast(card.cardNo as varchar) =\n" +
            "                                cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                join Student S on u.id = S.user_id\n" +
            "                join groups g on g.id = S.group_id\n" +
            "                join Faculty F on F.id = g.faculty_id\n" +
            "                join EducationType E on g.educationType_id = E.id\n" +
            "        where g.level=:level and F.schoolCode=:schoolCode and E.name=:eduType\n" +
            "        group by F.name\n" +
            "    ) as f1\n" +
            "        join (\n" +
            "        select F.name, count(S.id) as count from Student S\n" +
            "                                                     join groups g on S.group_id = g.id\n" +
            "                                                     join Faculty F on F.id = g.faculty_id\n" +
            "                                            join EducationType ET on g.educationType_id = ET.id\n" +
            "        where g.level=:level and F.schoolCode=:schoolCode and ET.name=:eduType\n" +
            "        group by F.name\n" +
            "    ) as f2 on f2.name = f1.name",nativeQuery = true)
    List<FacultyStatistic> studentFaculty123231213ByWeekOrMonthBySchoolCode123(@Param("schoolCode") Integer schoolCode, @Param("level") Integer level, @Param("weekOrMonth") Integer weekOrMonth,@Param("eduType") String eduType);

    @Query(value = "select p.id,p.queue,p.numeration, p.updatedAt as time,u.fullName,u.passportNum as passport, F.name as direction , g.level as grade , el.name as eduLang, et.name as eduType,s.lengthOfStudying,s.rektororder,u2.fullName as dean from users u join Student s on u.id = s.user_id\n" +
            "      join groups g on s.group_id = g.id\n" +
            "      join EducationType et on g.educationType_id = et.id\n" +
            "      join EducationLanguage el on g.educationLanguage_id = el.id\n" +
            "      join PReference p on u.id = p.student_id\n" +
            "      join Faculty F on g.faculty_id = F.id\n" +
            "      join Dekanat_Faculty DF on F.id = DF.faculties_id\n" +
            "      join Dekanat D on DF.Dekanat_id = D.id\n" +
            "      join users u2 on D.owner_id=u2.id\n" +
            "where p.id=?1 and p.status='CONFIRM' and u2.id=p.dean_id group by p.id, p.queue, p.numeration, p.updatedAt, u.fullName, u.passportNum, F.name, g.level, el.name, et.name, s.lengthOfStudying, s.rektororder, u2.fullName",nativeQuery = true)
    GetDataForStudentReference getDataForStudentReference(String studentId);

//    @Query(value = "select u.id, u.fullName,u.passportNum as passport, F.name as direction , g.level as grade , el.name as eduLang, et.name as eduType,s.lengthOfStudying,s.rektororder,u2.fullName as dean from users u join Student s on u.id = s.user_id\n" +
//            "    join groups g on s.group_id = g.id\n" +
//            "    join EducationType et on g.educationType_id = et.id\n" +
//            "    join EducationLanguage el on g.educationLanguage_id = el.id\n" +
//            "    join Faculty F on g.faculty_id = F.id\n" +
//            "join Dekanat_Faculty DF on F.id = DF.faculties_id\n" +
//            "join Dekanat D on DF.Dekanat_id = D.id\n" +
//            "join users u2 on D.owner_id=u2.id\n" +
//            "\n" +
//            "where u.id=?1",nativeQuery = true)
//    GetDataForStudentReference getDataForStudentReference(String studentId);
}
