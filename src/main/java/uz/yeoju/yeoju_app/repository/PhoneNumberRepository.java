package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.PhoneNumber;
import uz.yeoju.yeoju_app.entity.User;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, String> {
    PhoneNumber getPhoneNumberByAndPhoneNumber(String phoneNumber);
    boolean existsPhoneNumberByPhoneNumber(String phoneNumber);

    List<PhoneNumber> findPhoneNumbersByUser(User user);

}
