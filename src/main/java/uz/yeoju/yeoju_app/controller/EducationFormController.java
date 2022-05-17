package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.EducationFormDto;
import uz.yeoju.yeoju_app.service.useServices.EducationFormService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/eduForm")
@RequiredArgsConstructor
public class EducationFormController {

    public final EducationFormService educationFormService;

    @GetMapping("/allEduForms")
    public HttpEntity<?> allEduForms(){
        return ResponseEntity.ok(educationFormService.findAll());
    }

    @GetMapping("/getEduFormById/{id}")
    public HttpEntity<?> getEduFormById(@PathVariable String id){
        return ResponseEntity.ok(educationFormService.findById(id));
    }

    @PostMapping("/createEduForm")
    public HttpEntity<?> createNewEduForm(@RequestBody EducationFormDto dto){
        return ResponseEntity.status(201).body(educationFormService.saveOrUpdate(dto));
    }

    @PostMapping("/updateEduForm")
    public HttpEntity<?> updateEduForm(@RequestBody EducationFormDto dto){
        return ResponseEntity.status(202).body(educationFormService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteEduForm/{id}")
    public HttpEntity<?> deleteEduForm(@PathVariable String id){
        return ResponseEntity.status(204).body(educationFormService.deleteById(id));
    }
}
