package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.USERINFO;
import uz.yeoju.yeoju_app.payload.resDto.admin.GetInformationAboutCountsOfUsers;

import java.util.Set;

public interface UserInfoRepo extends JpaRepository<USERINFO, Integer> {



    @Query(value = "select * from USERINFO where CardNo=:cardNo",nativeQuery = true)
    USERINFO getUSERINFOByCardNoForSaving(@Param("cardNo") String cardNo);

    @Query(value = "select Top 1 Badgenumber from USERINFO order by Badgenumber desc",nativeQuery = true)
    Long getBadgenumber();

    @Query(value = "select count(u1.id) as counts,'STUDENTS' as sections from users u1\n" +
            "join users_Role u1r on u1.id = u1r.users_id\n" +
            "join Role r1 on u1r.roles_id = r1.id\n" +
            "where r1.roleName='ROLE_STUDENT'\n" +
            "union\n" +
            "select count(u2.id) as counts,'TEACHERS' as sections from users u2\n" +
            "join users_Role u2r on u2.id = u2r.users_id\n" +
            "join Role r2 on u2r.roles_id = r2.id\n" +
            "where r2.roleName='ROLE_TEACHER'\n" +
            "union\n" +
            "select count(u3.id) as counts,'STUFFS' as sections from users u3\n" +
            "join users_Role u3r on u3.id = u3r.users_id\n" +
            "join Role r3 on u3r.roles_id = r3.id\n" +
            "where r3.roleName!='ROLE_TEACHER' and r3.roleName!='ROLE_STUDENT'",nativeQuery = true)
    Set<GetInformationAboutCountsOfUsers> getInformationAboutCountsOfUsers();
}
