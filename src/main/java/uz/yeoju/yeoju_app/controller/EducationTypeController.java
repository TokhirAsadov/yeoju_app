package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.EducationTypeDto;
import uz.yeoju.yeoju_app.service.useServices.EducationTypeService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/eduType")
@RequiredArgsConstructor
public class EducationTypeController {

    public final EducationTypeService educationTypeService;

    @GetMapping("/allEduTypes")
    public HttpEntity<?> allEduTypes(){
        return ResponseEntity.ok(educationTypeService.findAll());
    }

    @GetMapping("/getEduTypeById/{id}")
    public HttpEntity<?> getEduTypeById(@PathVariable String id){
        return ResponseEntity.ok(educationTypeService.findById(id));
    }

    @PostMapping("/createEduType")
    public HttpEntity<?> createNewEduType(@RequestBody EducationTypeDto dto){
        return ResponseEntity.status(201).body(educationTypeService.saveOrUpdate(dto));
    }

    @PostMapping("/updateEduType")
    public HttpEntity<?> updateEduType(@RequestBody EducationTypeDto dto){
        return ResponseEntity.status(202).body(educationTypeService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteEduType/{id}")
    public HttpEntity<?> deleteEduType(@PathVariable String id){
        return ResponseEntity.status(204).body(educationTypeService.deleteById(id));
    }
}
