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

    @Query(value = "select sr.user_id as id,count(cast(sr.score as double precision)) as fail,\n" +
            "       u.fullName, u.email,u.passportNum as passport,u.login,u.RFID as cardNo\n" +
            "from StudentResult sr\n" +
            "join SubjectCredit SC on SC.id = sr.subjectCredit_id\n" +
            "join users u on sr.user_id = u.id\n" +
            "join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id\n" +
            "where g.name=:groupName and cast(score as double precision) < 65\n" +
            "group by u.fullName, u.passportNum, u.email, u.login, u.RFID, sr.user_id",nativeQuery = true)
    List<StudentResultsForDekan> getStudentResultsForDekan(@Param("groupName") String groupName);

    @Query(value = "select sr.id,SC.year,cast(SC.semester as double precision) as semester ,\n" +
            "       l.name as subject,cast(SC.credit as double precision) as credit, \n" +
            "       cast(sr.score as double precision) as score from StudentResult sr\n" +
            "join SubjectCredit SC on SC.id = sr.subjectCredit_id\n" +
            "join Lesson l on SC.lesson_id = l.id\n" +
            "where sr.user_id=:userId",nativeQuery = true)
    List<StudentResults> getStudentResultsByUserId(@Param("userId") String userId);
}
