package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.DirectionDto;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.payload.school.SchoolDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.FacultyService;

import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/faculty")
@RequiredArgsConstructor
public class FacultyController {

    public final FacultyService facultyService;

    @GetMapping("/getDirectionsOfFaculty")
    public HttpEntity<?> getDirectionsOfFaculty(@CurrentUser User user){
        return ResponseEntity.ok(facultyService.getDirectionsOfFaculty());
    }

    @PostMapping("/saveDirection")
    public HttpEntity<?> save(@CurrentUser User user, @RequestBody DirectionDto dto){
        ApiResponse response = facultyService.saveOrUpdateDirection(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }

    @GetMapping("/getGroupsForSelect")
    public HttpEntity<?> getGroupsForSelect(@CurrentUser User user,@RequestParam("facultyId") String facultyId,@RequestParam("eduTypeName") String eduTypeName){
        return ResponseEntity.ok(facultyService.getGroupsForSelect(facultyId,eduTypeName));
    }

    @GetMapping("/getFacultiesForSelect")
    public HttpEntity<?> getFacultiesForSelect(@CurrentUser User user,@RequestParam("educationName") String educationName){
        return ResponseEntity.ok(facultyService.getFacultiesForSelect(educationName));
    }

    @GetMapping("/getFacultyForDekanatSaved")
    public HttpEntity<?> getFacultyForDekanatSaved(){
        return ResponseEntity.ok(facultyService.getFacultyForDekanatSaved());
    }

    //getFacultyForDekanatSaved


    @GetMapping("/createFacultiesByNames")
    public HttpEntity<?> createFacultiesByNames(@RequestParam("namesOfFaculties") List<String> namesOfFaculties){
        return ResponseEntity.ok(facultyService.createFacultiesByNames(namesOfFaculties));
    }

    @GetMapping("/allFaculties")
    public HttpEntity<?> allFaculties(){
        return ResponseEntity.ok(facultyService.findAll());
    }

    @GetMapping("/allFacultiesWithShortName")
    public HttpEntity<?> allFacultiesWithShortName(){
        return ResponseEntity.ok(facultyService.findAllShortName());
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
