package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekanat.NotificationOuter;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetStudentNotificationOuters;

import java.util.Set;

public interface NotificationOuterRepository extends JpaRepository<NotificationOuter,String> {
    @Query(value = "select n.id,ey.name as educationYear, n.fromDate,n.toDate from NotificationOuter n \n" +
            "join EducationYear ey on ey.id = n.educationYear_id\n" +
            "join NotificationOuter_groups NOg on n.id = NOg.NotificationOuter_id\n" +
            "join groups g on NOg.groups_id = g.id\n" +
            "join Student S on g.id = S.group_id\n" +
            "where s.user_id=:studentId order by n.createdAt desc ",nativeQuery = true)
    Set<GetStudentNotificationOuters> getStudentNotificationOuters(@Param("studentId") String studentId);
}
