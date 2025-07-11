package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekanat.NotificationOuter2;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetStudentNotificationOuters;

import java.util.List;
import java.util.Set;

public interface NotificationOuter2Repository extends JpaRepository<NotificationOuter2,String> {

    List<NotificationOuter2> findAllByCreatedBy(String createdBy);

    @Query(value = "select n.id,ey.name as educationYear, n.fromDate,n.toDate,n.createdAt,n.queue from NotificationOuter2 n \n" +
            "join EducationYear ey on ey.id = n.educationYear_id\n" +
            "join NotificationOuter2_groups NOg on n.id = NOg.NotificationOuter2_id\n" +
            "join groups g on NOg.groups_id = g.id\n" +
            "join Student S on g.id = S.group_id\n" +
            "where s.user_id=:studentId order by n.createdAt desc ",nativeQuery = true)
    Set<GetStudentNotificationOuters> getStudentNotificationOuters(@Param("studentId") String studentId);


    @Query(value = "select n.id,ey.name as educationYear, u2.fullName as dekan, n.fromDate,n.toDate,n.createdAt,n.queue,n.dynamicSection,NOC.queue as studentQueue from NotificationOuter2 n\n" +
            "      join EducationYear ey on ey.id = n.educationYear_id\n" +
            "      join NotificationOuter2_groups NOg on n.id = NOg.NotificationOuter2_id\n" +
            "      join groups g on NOg.groups_id = g.id\n" +
            "      join Faculty f on g.faculty_id = f.id\n" +
            "      join EducationType et on et.id=g.educationType_id\n" +
            "      join Dekanat_Faculty df on f.id = df.faculties_id\n" +
            "      join Dekanat d on df.Dekanat_id = d.id\n" +
            "      join Dekanat_EducationType de on de.eduType_id=et.id and de.Dekanat_id=d.id\n" +
            "      join users u2 on d.owner_id=u2.id\n" +
            "      join Student S on g.id = S.group_id\n" +
            "      join NotificationOuter2Counter NOC on n.id = NOC.notificationOuter2_id\n" +
            "where s.user_id=:studentId and NOC.user_id=:studentId order by n.createdAt desc",nativeQuery = true)
    Set<GetStudentNotificationOuters> getStudentNotificationOuters2(@Param("studentId") String studentId);

    @Query(value = "select COALESCE(MAX(queue), 0) from NotificationOuter2",nativeQuery = true)
    Long maxQueue();
}
