package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.KafedraDto;
import uz.yeoju.yeoju_app.service.useServices.KafedraService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/kafedra")
@RequiredArgsConstructor
public class KafedraController {

    public final KafedraService kafedraService;

    @GetMapping("/allKafedra")
    public HttpEntity<?> allKafedra(){
        return ResponseEntity.ok(kafedraService.findAll());
    }

    @GetMapping("/getKafedraById/{id}")
    public HttpEntity<?> getKafedraById(@PathVariable String id){
        return ResponseEntity.ok(kafedraService.findById(id));
    }

    @PostMapping("/createKafedra")
    public HttpEntity<?> createNewKafedra(@RequestBody KafedraDto dto){
        return ResponseEntity.status(201).body(kafedraService.saveOrUpdate(dto));
    }

    @PostMapping("/updateKafedra")
    public HttpEntity<?> updateKafedra(@RequestBody KafedraDto dto){
        return ResponseEntity.status(202).body(kafedraService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteKafedra/{id}")
    public HttpEntity<?> deleteKafedra(@PathVariable String id){
        return ResponseEntity.status(204).body(kafedraService.deleteById(id));
    }
}
