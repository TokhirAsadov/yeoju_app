package uz.yeoju.yeoju_app.payload.resDto.admin;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.admin.Room;

import java.util.List;

public interface DevicePageRestDto {

    String getId();

    @Value("#{@machinesRepository.findEmptyDevices()}")
    List<MachinesRestDto> getDevices();

    @Value("#{@roomRepository.getRoomsForDeviceAdmin()}")
    List<RoomsForDeviceAdmin> getRooms();

    @Value("#{@buildingRepository.getBuildings()}")
    List<BuildingRestDto> getBuildings();

    @Value("#{@accDoorRepository.getDevicesList()}")
    List<DeviceList> getDeviceList();
}
