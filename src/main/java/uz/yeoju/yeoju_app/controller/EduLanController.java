package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.EducationLanguageDto;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.service.useServices.EduLanService;

import java.util.UUID;

@RestController
@RequestMapping("/v1/eduLan")
@RequiredArgsConstructor
public class EduLanController {

    public final EduLanService eduLanService;

    @GetMapping("/allLanguages")
    public HttpEntity<?> allLanguages(){
        return ResponseEntity.ok(eduLanService.findAll());
    }

    @GetMapping("/getLanguageById/{id}")
    public HttpEntity<?> getLanguageById(@PathVariable UUID id){
        return ResponseEntity.ok(eduLanService.findById(id));
    }


}
