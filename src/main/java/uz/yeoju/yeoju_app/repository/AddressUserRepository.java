package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.AddressUser;

public interface AddressUserRepository extends JpaRepository<AddressUser,String> {
}
