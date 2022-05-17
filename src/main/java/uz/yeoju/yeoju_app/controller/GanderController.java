package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.GanderDto;
import uz.yeoju.yeoju_app.service.useServices.GanderService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/gander")
@RequiredArgsConstructor
public class GanderController {

    public final GanderService ganderService;

    @GetMapping("/allGanders")
    public HttpEntity<?> allGanders(){
        return ResponseEntity.ok(ganderService.findAll());
    }

    @GetMapping("/getGanderById/{id}")
    public HttpEntity<?> getGanderById(@PathVariable Long id){
        return ResponseEntity.ok(ganderService.findById(id));
    }

    @PostMapping("/createGander")
    public HttpEntity<?> createNewGander(@RequestBody GanderDto dto){
        return ResponseEntity.status(201).body(ganderService.saveOrUpdate(dto));
    }

    @PostMapping("/updateGander")
    public HttpEntity<?> updateGander(@RequestBody GanderDto dto){
        return ResponseEntity.status(202).body(ganderService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteGander/{id}")
    public HttpEntity<?> deleteGander(@PathVariable Long id){
        return ResponseEntity.status(204).body(ganderService.deleteById(id));
    }

}
