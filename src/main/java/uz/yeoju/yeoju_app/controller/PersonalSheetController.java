package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.ObyektivkaDto;
import uz.yeoju.yeoju_app.service.useServices.ObyektivkaService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/personalSheet")
@RequiredArgsConstructor
public class PersonalSheetController {
    public final ObyektivkaService obyektivkaService;

    @GetMapping("/allPersonalSheets")
    public HttpEntity<?> getAllPersonalSheets(){
        return ResponseEntity.ok(obyektivkaService.findAll());
    }

    @GetMapping("/getPersonalSheetById/{id}")
    public HttpEntity<?> getPersonalSheetByIdOrUserIdOrActive(
            @PathVariable String id,
            @RequestParam String user_id,
            @RequestParam boolean active)
    {
        return id!=null ? ResponseEntity.ok(obyektivkaService.findById(id))
                :
                user_id!=null ? ResponseEntity.ok(obyektivkaService.findObyektivkasByUserId(id))
                :
                ResponseEntity.ok(obyektivkaService.findObyektivkasByActive(active));
    }

    @PostMapping("/createPersonalSheet")
    public HttpEntity<?> createNewPersonalSheet(@RequestBody ObyektivkaDto dto){
        return ResponseEntity.status(201).body(obyektivkaService.saveOrUpdate(dto));
    }

    @PostMapping("/updatePersonalSheet")
    public HttpEntity<?> updatePersonalSheet(@RequestBody ObyektivkaDto dto){
        return ResponseEntity.status(202).body(obyektivkaService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deletePersonalSheet/{id}")
    public HttpEntity<?> deletePersonalSheet(@PathVariable String id){
        return ResponseEntity.status(204).body(obyektivkaService.deleteById(id));
    }
}
