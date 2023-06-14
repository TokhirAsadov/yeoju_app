package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.admin.Machines;
import uz.yeoju.yeoju_app.payload.resDto.admin.MachinesRestDto;

import java.util.List;

public interface MachinesRepository extends JpaRepository<Machines,Long> {

    @Query(value = "select m.id as id ,m.IP as ip from Machines m join acc_door a_d on m.id=a_d.device_id where a_d.door_name like '172%' group by m.id, m.IP order by m.IP",nativeQuery = true)
    List<MachinesRestDto> findEmptyDevices();
}
