package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.payload.res.student.FacultyStatistic;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findStudentByUserId(String user_id);
    List<Student> findStudentsByGroupId(String group_id);
//    List<Student> findStudentsByEducationFormId(String educationForm_id);
//    List<Student> findStudentsByEducationTypeId(String educationType_id);
//    List<Student> findStudentsByEducationLanguageId(String educationLanguage_id);
    Student findStudentByPassportSerial(String passportSerial);
    List<Student> findStudentsByBornYear(Timestamp bornYear);
    List<Student> findStudentsByEnrollmentYear(Timestamp enrollmentYear);
    List<Student> findStudentsByCitizenship(String citizenship);
    boolean existsStudentByUserId(String user_id);

//    List<Student> findStudentsByTeachStatusId(String teachStatus_id);

/*    @Query(value = "select F.name,count(card.cardNo) as count from\n" +
            "(select  al.card_no as cardNo\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) =\n" +
            "                 cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id from Role where roleName = 'Student') as role\n" +
            "              on ur.roles_id = role.id\n" +
            "where al.time between :dateFrom and :dateTo\n" +
            "group by al.card_no\n" +
            ") as card\n" +
            "join users u on cast(card.cardNo as varchar) =\n" +
            "      cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "group by F.name",nativeQuery = true)*/
    @Query(value = "select  f1.name,f1.count as comeCount,f2.count as allCount from (\n" +
            "select F.name,count(card.cardNo) as count from\n" +
            "(select  al.card_no as cardNo\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) =\n" +
            "                 cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id from Role where roleName = 'Student') as role\n" +
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
    List<FacultyStatistic> getAllStudentByFaculty();

    @Query(value = "select  f1.name,f1.count as comeCount,f2.count as allCount from (\n" +
            "select F.name,count(card.cardNo) as count from\n" +
            "(select  al.card_no as cardNo\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) =\n" +
            "                 cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id from Role where roleName = 'Student') as role\n" +
            "              on ur.roles_id = role.id\n" +
            "where al.time between :dateFrom and :dateTo\n" +
            "group by al.card_no\n" +
            ") as card\n" +
            "join users u on cast(card.cardNo as varchar) =\n" +
            "      cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "                                          where g.level=:level\n" +
            "group by F.name\n" +
            "              ) as f1\n" +
            "join (\n" +
            "    select F.name, count(S.id) as count from Student S\n" +
            "     join groups g on S.group_id = g.id\n" +
            "     join Faculty F on F.id = g.faculty_id\n" +
            "                                        where g.level=:level\n" +
            "    group by F.name\n" +
            ") as f2 on f2.name = f1.name",nativeQuery = true)
    List<FacultyStatistic> getFacultyAndComingCountWithAllByGroupLevel(@Param("level") Integer level,@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
}
