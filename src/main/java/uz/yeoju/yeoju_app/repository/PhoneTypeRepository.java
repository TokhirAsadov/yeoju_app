package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.PhoneType;

public interface PhoneTypeRepository extends JpaRepository<PhoneType, Long> {
    PhoneType getPhoneTypeByName(String name);
    boolean existsPhoneTypeByName(String name);
}
