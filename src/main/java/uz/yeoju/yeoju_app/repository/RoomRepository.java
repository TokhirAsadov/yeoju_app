package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.admin.Room;
import uz.yeoju.yeoju_app.payload.resDto.dekan.FacultiesResDto;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,String> {

    boolean existsRoomByName(String name);

    @Query(value = "select id as value,door_name as label from acc_door where door_name not like '172%' order by door_name",nativeQuery = true)
    List<FacultiesResDto> getRoomsForSelect();

}
