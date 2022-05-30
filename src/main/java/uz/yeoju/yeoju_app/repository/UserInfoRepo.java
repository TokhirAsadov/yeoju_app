package uz.yeoju.yeoju_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.USERINFO;

public interface UserInfoRepo extends JpaRepository<USERINFO, Integer> {
}
