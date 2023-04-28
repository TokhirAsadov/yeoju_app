package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.admin.BuildingDto;
import uz.yeoju.yeoju_app.payload.admin.RoomDto;
import uz.yeoju.yeoju_app.service.useServices.BuildingService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/building")
@RequiredArgsConstructor
public class BuildingController {

    public final BuildingService buildingService;

    @GetMapping("/getBuildingsForSelect")
    public HttpEntity<?> getBuildingsForSelect(){
        return ResponseEntity.ok(buildingService.getBuildingsForSelect());
    }

    @GetMapping("/buildings")
    public HttpEntity<?> allBuildings(){
        return ResponseEntity.ok(buildingService.findAll());
    }

    @GetMapping("/getBuildingById/{id}")
    public HttpEntity<?> getBuildingById(@PathVariable String id){
        return ResponseEntity.ok(buildingService.findById(id));
    }

    @PostMapping("/createBuilding")
    public HttpEntity<?> createBuilding(@RequestBody BuildingDto dto){
        return ResponseEntity.status(201).body(buildingService.saveOrUpdate(dto));
    }

    @PutMapping("/updateBuilding")
    public HttpEntity<?> updateBuilding(@RequestBody BuildingDto dto){
        return ResponseEntity.status(202).body(buildingService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteBuilding/{id}")
    public HttpEntity<?> deleteBuilding(@PathVariable String id){
        return ResponseEntity.status(204).body(buildingService.deleteById(id));
    }

}
