package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekanat.NotificationOuterCounter;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetStudentNotificationOuters;

import java.util.Set;

public interface NotificationOuterCounterRepository extends JpaRepository<NotificationOuterCounter,String> {

    @Query(value = "select max(queue) from NotificationOuterCounter",nativeQuery = true)
    Long maxQueue();

    Boolean existsByUserIdAndNotificationOuterId(String user_id, String notificationOuter_id);
    @Query(value = "select u.id as studentId, u2.fullName as dekan, et.name as eduType, u.fullName,g.name as groupName,g.level,f.name as direction, NOC.id,ey.name as educationYear, n.fromDate,n.toDate,n.createdAt,n.queue,n.dynamicSection,NOC.queue as studentQueue from NotificationOuter n\n" +
            "   join EducationYear ey on ey.id = n.educationYear_id\n" +
            "   join NotificationOuter_groups NOg on n.id = NOg.NotificationOuter_id\n" +
            "   join groups g on NOg.groups_id = g.id\n" +
            "   join Faculty f on g.faculty_id = f.id\n" +
            "   join EducationType et on et.id=g.educationType_id\n" +
            "    join Dekanat_Faculty df on f.id = df.faculties_id\n" +
            "    join Dekanat d on df.Dekanat_id = d.id\n" +
            "    join Dekanat_EducationType de on de.eduType_id=et.id and de.Dekanat_id=d.id\n" +
            "    join users u2 on d.owner_id=u2.id\n" +
            "   join Student S on g.id = S.group_id\n" +
            "   join users u on u.id=S.user_id\n" +
            "   join NotificationOuterCounter NOC on n.id = NOC.notificationOuter_id\n" +
            "    and NOC.user_id=u.id\n" +
            "order by n.createdAt desc",nativeQuery = true)
    Set<GetStudentNotificationOuters> getAllCounters();

}
