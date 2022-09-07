package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.admin.DevicePort;
import uz.yeoju.yeoju_app.payload.resDto.admin.BuildingRestDto;
import uz.yeoju.yeoju_app.payload.resDto.admin.DevicePortRestDto;

import java.util.List;

public interface DevicePortRepository extends JpaRepository<DevicePort,String> {
    boolean existsDevicePortByPort(Integer port);

    @Query(value = "select id, port from DevicePort order by port",nativeQuery = true)
    List<DevicePortRestDto> getPorts();
}
