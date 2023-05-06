package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.payload.resDto.role.RoleResDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findRoleByRoleName(String roleName);
    List<Role> findAllByRoleName(String roleName);
    boolean existsRoleByRoleName(String roleName);

    @Query(value = "select r.id ,r.roleName from ROLE r where r.deleted=0 ",nativeQuery = true)
    List<RoleResDto> allRoles();

    @Query(value = "select r.id,r.roleName from ROLE r join users_Role ur on ur.roles_id=r.id join users u on ur.users_id=u.id where u.id =:userId",nativeQuery = true)
    List<RoleResDto> findByUserIdRoleName(@Param("userId") String userId);

    @Query(value = "select roleName from ROLE order by createdAt",nativeQuery = true)
    Set<String> getRolesNamesForSelect();

    @Query(value = "select r.roleName from ROLE r join users_Role ur on ur.roles_id=r.id where ur.users_id =:userId",nativeQuery = true)
    Set<String> getUserRolesName(@Param("userId") String userId);
}
