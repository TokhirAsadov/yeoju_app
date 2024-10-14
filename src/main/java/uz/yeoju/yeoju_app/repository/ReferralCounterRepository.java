package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.dekanat.ReferralCounter;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetStudentReferrals;

import java.util.Set;

public interface ReferralCounterRepository extends JpaRepository<ReferralCounter,String> {

    @Query(value = "select max(queue) from ReferralCounter",nativeQuery = true)
    Long maxQueue();

    Boolean existsByUserIdAndReferralId(String user_id, String referral_id);
    @Query(value = "select u.id as studentId, u.fullName,g.name as groupName,g.level,f.name as direction, r_c.id, r.fromDate,r.toDate,r.createdAt,r.queue,r.decisionDate,r.numberOfDecision,r_c.queue as studentQueue from Referral r\n" +
            "    join Referral_groups r_g on r.id = r_g.Referral_id\n" +
            "    join groups g on r_g.groups_id = g.id\n" +
            "    join Faculty f on g.faculty_id = f.id\n" +
            "    join Student S on g.id = S.group_id\n" +
            "    join users u on u.id=S.user_id\n" +
            "    join ReferralCounter r_c on r.id = r_c.referral_id\n" +
            "    and r_c.user_id=u.id\n" +
            "order by r.createdAt desc",nativeQuery = true)
    Set<GetStudentReferrals> getAllCounters();

}
