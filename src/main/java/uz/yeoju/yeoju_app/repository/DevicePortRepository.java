package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.admin.DeviceMenu;
import uz.yeoju.yeoju_app.entity.admin.DevicePort;
import uz.yeoju.yeoju_app.payload.resDto.admin.BuildingRestDto;
import uz.yeoju.yeoju_app.payload.resDto.admin.DevicePageRestDto;
import uz.yeoju.yeoju_app.payload.resDto.admin.DevicePortRestDto;

import java.util.List;

public interface DevicePortRepository extends JpaRepository<DevicePort,String> {
    boolean existsDevicePortByPort(Integer port);

    @Query(value = "select id, port from DevicePort order by port",nativeQuery = true)
    List<DevicePortRestDto> getPorts();

    @Query(value = "select id from users where id =:id",nativeQuery = true)
    DevicePageRestDto deviceDates(@Param("id") String id);

    @Query(value = "select TOP 100 u.id,u.fullName, al.device_name as device,al.event_point_name as room,al.time,u.passportNum as passport,al.card_no as card,u.login from acc_monitor_log al\n" +
            "join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "order by al.time desc",nativeQuery = true)
    List<DeviceMenu> getAccMonitoring();
}
