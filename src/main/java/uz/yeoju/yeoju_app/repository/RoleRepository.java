package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Role;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
