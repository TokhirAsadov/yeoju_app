package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.USERINFO;

public interface UserInfoRepo extends JpaRepository<USERINFO, Integer> {



    @Query(value = "select * from USERINFO where CardNo=:cardNo",nativeQuery = true)
    USERINFO getUSERINFOByCardNoForSaving(@Param("cardNo") String cardNo);

    @Query(value = "select Top 1 Badgenumber from USERINFO order by Badgenumber desc",nativeQuery = true)
    Long getBadgenumber();
}
