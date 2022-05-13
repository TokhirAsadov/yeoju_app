package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.Section;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findRoleByRoleName(String roleName);
    List<Role> findAllByRoleName(String roleName);
    List<Role> findAllBySection(Section section);

    Optional<Role> findRoleByRoleNameAndSection(String roleName, Section section);
    boolean existsRoleByRoleNameAndSection(String roleName, Section section);
}
