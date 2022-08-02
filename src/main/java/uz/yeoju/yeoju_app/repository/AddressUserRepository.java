package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.AddressUser;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentAddress;

public interface AddressUserRepository extends JpaRepository<AddressUser,String> {

    @Query(value = "select au.id,au.country,au.region,au.area,au.address from AddressUser au where user_id=:userId",nativeQuery = true)
    StudentAddress getAddressByUserId(@Param("userId") String userId);
}
