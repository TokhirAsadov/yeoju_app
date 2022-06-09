package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.service.useServices.FacultyService;

import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/faculty")
@RequiredArgsConstructor
public class FacultyController {

    public final FacultyService facultyService;


    @GetMapping("/createFacultiesByNames")
    public HttpEntity<?> createFacultiesByNames(@RequestParam("namesOfFaculties") List<String> namesOfFaculties){
        return ResponseEntity.ok(facultyService.createFacultiesByNames(namesOfFaculties));
    }

    @GetMapping("/allFaculties")
    public HttpEntity<?> allFaculties(){
        return ResponseEntity.ok(facultyService.findAll());
    }

    @GetMapping("/getFacultyById/{id}")
    public HttpEntity<?> getFacultyById(@PathVariable String id){
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

    @DeleteMapping("/deleteFaculty/{id}")
    public HttpEntity<?> deleteFaculty(@PathVariable String id){
        return ResponseEntity.status(204).body(facultyService.deleteById(id));
    }
}
