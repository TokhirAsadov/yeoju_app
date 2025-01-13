package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekanat.Referral;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetStudentReferrals;

import java.util.Set;

public interface ReferralRepository extends JpaRepository<Referral,String> {
    @Query(value = "select r.id, r.fromDate,r.toDate,r.createdAt,r.queue,r.decisionDate,r.numberOfDecision from Referral r\n" +
            "       join Referral_groups r_g on r.id = r_g.Referral_id\n" +
            "       join groups g on r_g.groups_id = g.id\n" +
            "       join Student S on g.id = S.group_id\n" +
            "where s.user_id=:studentId order by r.createdAt desc",nativeQuery = true)
    Set<GetStudentReferrals> getStudentReferrals(@Param("studentId") String studentId);


    @Query(value = "select r.id, r.fromDate,r.toDate,r.createdAt,r.queue,r.decisionDate,r.numberOfDecision,r_c.queue as studentQueue from Referral r\n" +
            "    join Referral_groups r_g on r.id = r_g.Referral_id\n" +
            "    join groups g on r_g.groups_id = g.id\n" +
            "    join Student S on g.id = S.group_id\n" +
            "  join ReferralCounter r_c on r.id = r_c.referral_id\n" +
            "where s.user_id=:studentId and r_c.user_id=:studentId order by r.createdAt desc",nativeQuery = true)
    Set<GetStudentReferrals> getStudentNotificationOuters2(@Param("studentId") String studentId);

    @Query(value = "select max(queue) from Referral",nativeQuery = true)
    Long maxQueue();
}
