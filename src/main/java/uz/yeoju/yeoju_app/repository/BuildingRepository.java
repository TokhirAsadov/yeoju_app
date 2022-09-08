package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.admin.Building;
import uz.yeoju.yeoju_app.payload.resDto.admin.BuildingRestDto;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building,String> {
    boolean existsBuildingByName(String name);

    @Query(value = "select id, name from building order by name",nativeQuery = true)
    List<BuildingRestDto> getBuildings();
}
