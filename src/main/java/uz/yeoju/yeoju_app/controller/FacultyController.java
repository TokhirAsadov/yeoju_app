package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.service.useServices.FacultyService;

@RestController
@RequestMapping("/api/v1/faculty")
@RequiredArgsConstructor
public class FacultyController {

    public final FacultyService facultyService;

    @GetMapping("/allFaculties")
    public HttpEntity<?> allFaculties(){
        return ResponseEntity.ok(facultyService.findAll());
    }

    @GetMapping("/getFacultyById/{id}")
    public HttpEntity<?> getFacultyById(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.findById(id));
    }

    @PostMapping("/createFaculty")
    public HttpEntity<?> createNewFaculty(@RequestBody FacultyDto dto){
        return ResponseEntity.status(201).body(facultyService.saveOrUpdate(dto));
    }

    @PostMapping("/updateFaculty")
    public HttpEntity<?> updateFaculty(@RequestBody FacultyDto dto){
        return ResponseEntity.status(202).body(facultyService.saveOrUpdate(dto));
    }

}
