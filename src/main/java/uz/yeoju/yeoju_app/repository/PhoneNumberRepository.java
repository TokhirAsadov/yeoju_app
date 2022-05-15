package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.PhoneNumber;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, String> {
    PhoneNumber getPhoneNumberByAndPhoneNumber(String phoneNumber);
    boolean existsPhoneNumberByPhoneNumber(String phoneNumber);

    List<PhoneNumber> findPhoneNumbersByUserId(String user_id);

}
