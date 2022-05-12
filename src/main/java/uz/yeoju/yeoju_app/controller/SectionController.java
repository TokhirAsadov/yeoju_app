package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.service.useServices.SectionService;

import java.util.UUID;

@RestController
@RequestMapping("/v1/section")
@RequiredArgsConstructor
public class SectionController {
    public final SectionService sectionService;

    @GetMapping("/allSections")
    public HttpEntity<?> getAllSections(){
        return ResponseEntity.ok(sectionService.findAll());
    }

    @GetMapping("/getSectionById/{id}")
    public HttpEntity<?> getSectionById(@PathVariable UUID id){
        return ResponseEntity.ok(sectionService.findById(id));
    }

    @PostMapping("/createSection")
    public HttpEntity<?> createNewSection(@RequestBody SectionDto dto){
        return ResponseEntity.status(201).body(sectionService.saveOrUpdate(dto));
    }



}
