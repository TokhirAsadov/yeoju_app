package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.PositionDto;
import uz.yeoju.yeoju_app.service.useServices.PositionService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/position")
@RequiredArgsConstructor
public class PositionController {

    public final PositionService positionService;


    @GetMapping("/build")
    public HttpEntity<?> createFacultiesByNames(){
        return ResponseEntity.ok(positionService.build());
    }

    @GetMapping("/allPositions")
    public HttpEntity<?> allPositions(){
        return ResponseEntity.ok(positionService.findAll());
    }

    @GetMapping("/getPositionById/{id}")
    public HttpEntity<?> getPositionById(@PathVariable Long id){
        return ResponseEntity.ok(positionService.findById(id));
    }

    @PostMapping("/createPosition")
    public HttpEntity<?> createNewPosition(@RequestBody PositionDto dto){
        return ResponseEntity.status(201).body(positionService.saveOrUpdate(dto));
    }

    @PutMapping("/updatePosition")
    public HttpEntity<?> updatePosition(@RequestBody PositionDto dto){
        return ResponseEntity.status(202).body(positionService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deletePosition/{id}")
    public HttpEntity<?> deletePosition(@PathVariable Long id){
        return ResponseEntity.status(204).body(positionService.deleteById(id));
    }
}
