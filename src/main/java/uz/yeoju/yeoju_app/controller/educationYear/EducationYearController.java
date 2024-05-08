package uz.yeoju.yeoju_app.controller.educationYear;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.educationYear.WeekEduType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.educationYear.EducationYearDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.educationYear.EducationYearService;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statisticsOfEducationYear.StatisticsOfEducationYearService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/education")
@RequiredArgsConstructor
public class EducationYearController {
    private final EducationYearService educationYearService;
    private final StatisticsOfEducationYearService statisticsService;

    @GetMapping("/getStudentsStatisticsForDean")
    public HttpEntity<?> getStudentsStatisticsForDean(
            @RequestParam("educationYearId") String educationYearId,
            @RequestParam("group") String groupName
    ){
        return ResponseEntity.ok(statisticsService.getStudentsStatisticsForDean(educationYearId,groupName));
    }

    @GetMapping("/getGroupsByFacultyId/{facultyId}")
    public HttpEntity<?> getGroupsByFacultyId(
            @PathVariable("facultyId") String facultyId
    ){
        return ResponseEntity.ok(educationYearService.getGroupsByFacultyId(facultyId));
    }

    @GetMapping("/getStudentsStatisticsForTeacher")
    public HttpEntity<?> getStudentsStatisticsForTeacher(
            @RequestParam("educationYearId") String educationYearId,
            @RequestParam("passport") String teacherPassport,
            @RequestParam("lesson") String lessonName,
            @RequestParam("group") String groupName
    ){
        return ResponseEntity.ok(statisticsService.getStudentsStatisticsForTeacher(educationYearId,teacherPassport,lessonName,groupName));
    }


    @GetMapping("/getGroupsForTeacher")
    public HttpEntity<?> getGroupsForTeacher(
            @RequestParam("educationYearId") String educationYearId,
            @RequestParam("passport") String teacherPassport,
            @RequestParam("lesson") String lessonName,
            @RequestParam("eduType") WeekEduType eduType
    ){
        return ResponseEntity.ok(statisticsService.getGroupsForTeacher(educationYearId,teacherPassport,lessonName,eduType));
    }

    @GetMapping("/getSubjectOfTeacher")
    public HttpEntity<?> getSubjectOfTeacher(
            @RequestParam("educationYearId") String educationYearId,
            @RequestParam("passport") String passport
    ){
        return ResponseEntity.ok(statisticsService.getSubjectOfTeacher(educationYearId,passport));
    }

    @GetMapping("/statisticsOneGroup")
    public HttpEntity<?> statisticsOneGroup(
            @RequestParam("educationYearId") String educationYearId,
            @RequestParam("studentId") String studentId,
            @RequestParam("groupName") String groupName
    ){
        return ResponseEntity.ok(statisticsService.getStatisticsOneGroup(educationYearId,groupName,studentId));
    }

    @GetMapping("/statisticsOneLesson")
    public HttpEntity<?> statisticsOneLesson(
            @RequestParam("educationYearId") String educationYearId,
            @RequestParam("studentId") String studentId,
            @RequestParam("groupName") String groupName,
            @RequestParam("lessonName") String lessonName
    ){
        return ResponseEntity.ok(statisticsService.getStatisticsOneLesson(educationYearId,groupName,studentId,lessonName));
    }

    @GetMapping("/educationYearsForSelected")
    public HttpEntity<?> educationYearsForSelected(){
        return ResponseEntity.ok(educationYearService.educationYearsForSelected());
    }

    @GetMapping("/getSortNumberOfWeek/{educationYearId}")
    public HttpEntity<?> getSortNumberOfWeek(@PathVariable String educationYearId){
        return ResponseEntity.ok(educationYearService.getSortNumberOfWeek(educationYearId));
    }

    @GetMapping("/educationYearsForCRUD")
    public HttpEntity<?> educationYearsForCRUD(){
        return ResponseEntity.ok(educationYearService.educationYearsForCRUD());
    }

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(){
        return ResponseEntity.ok(educationYearService.findAll());
    }

    @GetMapping("/findById/{id}")
    public HttpEntity<?> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(educationYearService.findById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    public HttpEntity<?> deleteById(@PathVariable("id") String id){
        ApiResponse response = educationYearService.deletedById(id);
        return ResponseEntity.status(204).body(response);
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@org.springframework.web.bind.annotation.RequestBody EducationYearDto dto){
        System.out.println(dto);
        ApiResponse response = educationYearService.saveOrUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }

    @PutMapping("/update")
    public HttpEntity<?> update(@RequestBody EducationYearDto dto){
        ApiResponse response = educationYearService.saveOrUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }
}
