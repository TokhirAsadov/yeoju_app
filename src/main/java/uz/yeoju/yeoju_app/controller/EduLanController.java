package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.EducationLanguageDto;
import uz.yeoju.yeoju_app.service.useServices.EduLanService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/eduLan")
@RequiredArgsConstructor
public class EduLanController {

    public final EduLanService eduLanService;

    @GetMapping("/allLanguages")
    public HttpEntity<?> allLanguages(){
        return ResponseEntity.ok(eduLanService.findAll());
    }

    @GetMapping("/getLanguageById/{id}")
    public HttpEntity<?> getLanguageById(@PathVariable String id){
        return ResponseEntity.ok(eduLanService.findById(id));
    }

    @PostMapping("/createLanguage")
    public HttpEntity<?> createNewLanguage(@RequestBody EducationLanguageDto dto){
        return ResponseEntity.status(201).body(eduLanService.saveOrUpdate(dto));
    }

    @PostMapping("/updateLanguage")
    public HttpEntity<?> updateLanguage(@RequestBody EducationLanguageDto dto){
        return ResponseEntity.status(202).body(eduLanService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteLanguage/{id}")
    public HttpEntity<?> deleteFaculty(@PathVariable String id){
        return ResponseEntity.status(204).body(eduLanService.deleteById(id));
    }

}
