package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.service.useServices.SectionService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/section")
@RequiredArgsConstructor
public class SectionController {
    public final SectionService sectionService;

    @GetMapping("/allSections")
    public HttpEntity<?> getAllSections(){
        return ResponseEntity.ok(sectionService.findAll());
    }

    @GetMapping("/getSectionById/{id}")
    public HttpEntity<?> getSectionById(@PathVariable String id){
        return ResponseEntity.ok(sectionService.findById(id));
    }

    @PostMapping("/createSection")
    public HttpEntity<?> createNewSection(@RequestBody SectionDto dto){
        return ResponseEntity.status(201).body(sectionService.saveOrUpdate(dto));
    }

    @PostMapping("/updateSection")
    public HttpEntity<?> updateSection(@RequestBody SectionDto dto){
        return ResponseEntity.status(202).body(sectionService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteSection/{id}")
    public HttpEntity<?> deleteSection(@PathVariable String id){
        return ResponseEntity.status(204).body(sectionService.deleteById(id));
    }
}
