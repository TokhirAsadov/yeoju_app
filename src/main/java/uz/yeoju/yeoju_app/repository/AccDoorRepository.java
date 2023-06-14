package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.admin.AccDoor;
import uz.yeoju.yeoju_app.entity.admin.DeviceMenu;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.ForUserSaveDto;
import uz.yeoju.yeoju_app.payload.resDto.admin.DeviceList;
import uz.yeoju.yeoju_app.payload.resDto.admin.DevicePageRestDto;
import uz.yeoju.yeoju_app.payload.resDto.admin.ForAddUser;

import java.util.List;

public interface AccDoorRepository extends JpaRepository<AccDoor,Long> {

    @Query(value = "select :id as id",nativeQuery = true)
    ForAddUser getForAddUser(@Param("id") String id);

    List<AccDoor> findAllByDeviceId(Long deviceId);

    @Query(value = "select door_no as port from acc_door where device_id=:id and door_name like '172%'",nativeQuery = true)
    List<Long> getPortsByDeviceId(@Param("id") Long id);

    @Query(value = "select * from acc_door where device_id=:deviceId and door_no=:port",nativeQuery = true)
    AccDoor getDoorForSave(@Param("deviceId") Long deviceId, @Param("port") Integer port);

    @Query(value = "select TOP 100 u.id,u.fullName, al.device_name as device,al.event_point_name as room,al.time,u.passportNum as passport,al.card_no as card,u.login from acc_monitor_log al\n" +
            "join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "order by al.time desc",nativeQuery = true)
    List<DeviceMenu> getAccMonitoring();

    @Query(value = "select id from users where id =:id",nativeQuery = true)
    DevicePageRestDto deviceDates(@Param("id") String id);

    @Query(value = "select ac.id, ac.device_id as deviceId, m.IP as ip,ac.door_no as port,ac.door_name as room from acc_door ac join Machines m on ac.device_id=m.ID",nativeQuery = true)
    List<DeviceList> getDevicesList();


}
