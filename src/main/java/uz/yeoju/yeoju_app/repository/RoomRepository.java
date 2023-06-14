package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.admin.Room;
import uz.yeoju.yeoju_app.payload.resDto.admin.RoomsForDeviceAdmin;
import uz.yeoju.yeoju_app.payload.resDto.dekan.FacultiesResDto;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,String> {

    boolean existsRoomByName(String name);

    @Query(value = "select id as value,door_name as label from acc_door where door_name not like '172%' order by door_name",nativeQuery = true)
    List<FacultiesResDto> getRoomsForSelect();

    @Query(value = "select r.id, r.name from Room r left join acc_door ad on cast(ad.door_name as varchar) = cast(r.name as varchar) COLLATE Chinese_PRC_CI_AS where ad.door_name is null order by r.name",nativeQuery = true)
    List<RoomsForDeviceAdmin> getRoomsForDeviceAdmin();

}
