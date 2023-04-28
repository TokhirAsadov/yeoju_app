package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.admin.Building;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.BuildingDto;
import uz.yeoju.yeoju_app.repository.BuildingRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.BuildingImplService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingService implements BuildingImplService<BuildingDto> {
    private final BuildingRepository buildingRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All buildings",
                buildingRepository.findAll().stream()
                        .map(this::generateBuildingDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> BuildingDto generateBuildingDto(Building building) {
        return new BuildingDto(
                building.getId(),
                building.getName()
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return new ApiResponse(true,"Building Item",generateBuildingDto(buildingRepository.getById(id)));
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(BuildingDto dto) {

        if (dto.getId() == null){ //saveOrUpdate
            if (!buildingRepository.existsBuildingByName(dto.getName())){
                buildingRepository.save(generateBuilding(dto));
                return new ApiResponse(true,"saveOrUpdate building successfully!..");
            }
            return new ApiResponse(false,"error, sorry already exist name of building");
        }
        else { //update
            if (!buildingRepository.existsBuildingByName(dto.getName())){
                Optional<Building> buildingOptional = buildingRepository.findById(dto.getId());
                if (buildingOptional.isPresent()) {
                    Building building = buildingOptional.get();
                    building.setName(dto.getName());
                    buildingRepository.save(building);
                    return new ApiResponse(true,"update building successfully!..");
                }
                return new ApiResponse(false,"error, not fount building");
            }
            return new ApiResponse(false,"error, sorry already exist name of building");
        }
    }

    private Building generateBuilding(BuildingDto dto) {
        return new Building(
                dto.getId(),
                dto.getName()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        buildingRepository.deleteById(id);
        return new ApiResponse(true,"Deleted building successfully!...");
    }

    public ApiResponse getBuildingsForSelect() {
        return new ApiResponse(true,"buildings",buildingRepository.getBuildingsForSelect());
    }
}
