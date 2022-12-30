package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.payload.resDto.dekan.SearchUserForDekanUseSendMessage;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StudentEduLangFormType;
import uz.yeoju.yeoju_app.payload.resDto.student.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findStudentByUserId(String user_id);
    Student findStudentByUserLogin(String user_login);

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
            "\n" +
            "select  COUNT(al.card_no) as cardNo\n" +
            "from acc_monitor_log al\n" +
            " join users u\n" +
            "      on cast(u.RFID as varchar) =\n" +
            "         cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            " join users_Role ur\n" +
            "      on u.id = ur.users_id\n" +
            " join (select id from Role where roleName = 'ROLE_STUDENT') as role\n" +
            "      on ur.roles_id = role.id\n" +
            "where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "group by al.card_no\n" +
            "\n" +
            ") as f\n" +
            "union\n" +
            "select count(u.id) from users u  join users_Role ur\n" +
            "on u.id = ur.users_id join (select id from Role where roleName = 'ROLE_STUDENT') as role\n" +
            "on ur.roles_id = role.id",nativeQuery = true)
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
            "                where al.time between dateadd(d,:weekOrMonth,convert(DATE,GETDATE())) and dateadd(d,1,convert(DATE,GETDATE())) and R2.roleName='ROLE_STUDENT'\n" +
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
    List<FacultyStatistic> getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth(@Param("level") Integer level,@Param("weekOrMonth") Integer weekOrMonth);

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

    @Query(value = "select u.fullName,u.RFID from users u\n" +
            "join Student s on u.id = s.user_id\n" +
            "join groups g on g.id = s.group_id\n" +
            "where g.name =:groupName",nativeQuery = true)
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
}
