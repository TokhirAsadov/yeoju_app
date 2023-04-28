package uz.yeoju.yeoju_app.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.address.Districts;

import java.util.List;

public interface DistrictsRepository extends JpaRepository<Districts,Long> {
    List<Districts> findDistrictsByRegionId(Long region_id);
}
