package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position getPositionByUserPositionName(String name);
    boolean existsPositionByUserPositionName(String name);


    @Query(value = "select CAST(p.id as varchar) as value, p.userPositionName as label\n" +
            "from Position p\n" +
            "         join users_Position uP on p.id = uP.positions_id\n" +
            "where uP.users_id=:id",nativeQuery = true)
    UserForTeacherSaveItem getUserPositionForEditUserPosition(@Param("id") String id);

    @Query(value = "select CAST(id as varchar) as value, userPositionName as label from Position order by userPositionName asc",nativeQuery = true)
    List<UserForTeacherSaveItem> getPositionsForTeacherSaving();

    @Query(value = "select p.userPositionName from Position p join users_Position up on up.positions_id=p.id where up.users_id=:userId",nativeQuery = true)
    List<String> getNamesOfPosition(@Param("userId") String userId);

    @Query(value = "select Top 1 p.userPositionName from Position p join users_Position up on up.positions_id=p.id where up.users_id=:userId",nativeQuery = true)
    String getNameOfPosition(@Param("userId") String userId);
}
