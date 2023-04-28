package uz.yeoju.yeoju_app.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.address.Regions;
import uz.yeoju.yeoju_app.entity.address.Villages;

import java.util.List;

public interface VillagesRepository extends JpaRepository<Villages,Long> {
    List<Villages> findVillagesByDistrictId(Long district_id);
}
