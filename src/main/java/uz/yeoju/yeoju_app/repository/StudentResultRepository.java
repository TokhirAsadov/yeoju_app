package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.studentBall.StudentResult;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentResults;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentResultsForDekan;

import java.util.List;

public interface StudentResultRepository extends JpaRepository<StudentResult, Long> {
    List<StudentResult> getStudentResultsByUserLogin(String user_login);

    @Query(value = "select u2.id, f1.fail, u2.fullName,u2.email,u2.passportNum as passport,u2.login, u2.RFID as cardNo from (select sr.user_id as id,count(cast(sr.score as double precision)) as fail from StudentResult sr\n" +
            "join SubjectCredit SC on SC.id = sr.subjectCredit_id join users u on sr.user_id = u.id right join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id where g.name = :groupName and cast(score as double precision) < 60 group by u.fullName, u.passportNum, u.email, u.login, u.RFID, sr.user_id) as f1\n" +
            "right join Student s on f1.id=s.user_id join users u2 on s.user_id = u2.id join groups g2 on s.group_id = g2.id where g2.name= :groupName\n" +
            "group by u2.id, f1.fail, u2.fullName, u2.email, u2.passportNum, u2.login, u2.RFID\n" +
            "order by fail desc",nativeQuery = true)
    List<StudentResultsForDekan> getStudentResultsForDekan(@Param("groupName") String groupName);

//    @Query(value = "select u2.id, f1.fail, u2.fullName,u2.email,u2.passportNum as passport,u2.login, u2.RFID as cardNo from (select sr.user_id as id,count(cast(sr.score as double precision)) as fail from StudentResult sr\n" +
//            "join SubjectCredit SC on SC.id = sr.subjectCredit_id join users u on sr.user_id = u.id right join Student S on u.id = S.user_id\n" +
//            "join groups g on g.id = S.group_id where g.name = :groupName and cast(score as double precision) < 60 group by u.fullName, u.passportNum, u.email, u.login, u.RFID, sr.user_id) as f1\n" +
//            "right join Student s on f1.id=s.user_id join users u2 on s.user_id = u2.id join groups g2 on s.group_id = g2.id where g2.name= :groupName order by fail desc",nativeQuery = true)
//    List<StudentResultsForDekan> getStudentResultsForDekan(@Param("groupName") String groupName);

    @Query(value = "select u2.id, f1.fail, u2.fullName,u2.email,u2.passportNum as passport,u2.login, u2.RFID as cardNo from (select sr.user_id as id,count(cast(sr.score as double precision)) as fail from StudentResult sr\n" +
            "join SubjectCredit SC on SC.id = sr.subjectCredit_id join users u on sr.user_id = u.id right join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id where cast(score as double precision) < 60 group by u.fullName, u.passportNum, u.email, u.login, u.RFID, sr.user_id) as f1\n" +
            "right join Student s on f1.id=s.user_id join users u2 on s.user_id = u2.id join groups g2 on s.group_id = g2.id order by fail desc",nativeQuery = true)
    List<StudentResultsForDekan> getStudentResultsForRektor();

    @Query(value = "select sr.id,SC.year,cast(SC.semester as double precision) as semester ,\n" +
            "       l.name as subject,cast(SC.credit as double precision) as credit, \n" +
            "       cast(sr.score as double precision) as score from StudentResult sr\n" +
            "join SubjectCredit SC on SC.id = sr.subjectCredit_id\n" +
            "join Lesson l on SC.lesson_id = l.id\n" +
            "where sr.user_id=:userId order by semester desc",nativeQuery = true)
    List<StudentResults> getStudentResultsByUserId(@Param("userId") String userId);
}
