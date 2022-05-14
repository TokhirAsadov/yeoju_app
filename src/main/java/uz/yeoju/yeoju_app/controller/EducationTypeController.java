package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.service.useServices.EducationTypeService;

@RestController
@RequestMapping("/v1/eduType")
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


}
