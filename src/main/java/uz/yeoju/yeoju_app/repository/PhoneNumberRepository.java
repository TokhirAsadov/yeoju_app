package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.PhoneNumber;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentPhones;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, String> {
    PhoneNumber getPhoneNumberByAndPhoneNumber(String phoneNumber);
    boolean existsPhoneNumberByPhoneNumber(String phoneNumber);

    List<PhoneNumber> findPhoneNumbersByUserId(String user_id);


    @Query(value = "select id,phoneNumber,phoneType from PhoneNumber p_n where p_n.user_id=:userId",nativeQuery = true)
    List<StudentPhones> getPhoneNumberUserId(@Param("userId") String userId);

}
