package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position getPositionByUserPositionName(String name);

    Optional<Position> findRoleByUserPositionName(String userPositionName);
    boolean existsPositionByUserPositionName(String name);


    @Query(value = "select CAST(p.id as varchar) as value, p.userPositionName as label\n" +
            "from Position p\n" +
            "         join users_Position uP on p.id = uP.positions_id\n" +
            "where uP.users_id=:id",nativeQuery = true)
    UserForTeacherSaveItem getUserPositionForEditUserPosition(@Param("id") String id);

    @Query(value = "select CAST(id as varchar) as value, userPositionName as label from Position order by userPositionName asc",nativeQuery = true)
    List<UserForTeacherSaveItem> getPositionsForTeacherSaving();

    @Query(value = "select CAST(p.id as varchar) as value,p.userPositionName as label from Kafedra k join Kafedra_Position kp on k.id = kp.Kafedra_id join Position p on kp.positions_id = p.id \n" +
            "join KafedraMudiri km on k.id = km.kafedra_id where km.user_id=:userId ",nativeQuery = true)
    List<UserForTeacherSaveItem> getPositionsForTeacherSaving2(@Param("userId") String userId);

    @Query(value = "select CAST(p.id as varchar) as value,p.userPositionName as label from Dekanat d join Dekanat_Position dp on d.id = dp.Dekanat_id join Position p on dp.positions_id = p.id\n" +
            "    join Staff dn on d.id = dn.dekanat_id where dn.user_id=:userId",nativeQuery = true)
    List<UserForTeacherSaveItem> getPositionsForDekanSaving(@Param("userId") String userId);

    @Query(value = "select CAST(p.id as varchar) as value,p.userPositionName as label from Section s join Section_Position sp on s.id = sp.Section_id join Position p on sp.positions_id = p.id\n" +
            "  join Staff dn on s.id = dn.section_id where dn.user_id=:userId",nativeQuery = true)
    List<UserForTeacherSaveItem> getPositionsForSectionSaving(@Param("userId") String userId);


    @Query(value = "select p.userPositionName from Position p join users_Position up on up.positions_id=p.id where up.users_id=:userId",nativeQuery = true)
    List<String> getNamesOfPosition(@Param("userId") String userId);

    @Query(value = "select Top 1 p.userPositionName from Position p join users_Position up on up.positions_id=p.id where up.users_id=:userId",nativeQuery = true)
    String getNameOfPosition(@Param("userId") String userId);

    @Query(value = "select userPositionName from Position order by createdAt",nativeQuery = true)
    Set<String> getPositionsNameForSelect();

    @Query(value = "select p.userPositionName from Position p join users_Position up on up.positions_id=p.id where up.users_id=:userId",nativeQuery = true)
    Set<String> getUserPositionsName(@Param("userId") String userId);
}
