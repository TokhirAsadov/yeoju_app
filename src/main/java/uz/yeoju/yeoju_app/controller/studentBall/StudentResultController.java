package uz.yeoju.yeoju_app.controller.studentBall;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.studentBall.ForBuildStudentResult;
import uz.yeoju.yeoju_app.payload.studentBall.StudentResultDto;
import uz.yeoju.yeoju_app.repository.StudentResultRepository;
import uz.yeoju.yeoju_app.service.useServices.StudentResultService;

import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/studentResult")
@RequiredArgsConstructor
public class StudentResultController {
    private final StudentResultService resultService;
    private final StudentResultRepository repository;


    //getStudentResultsForDekan
    @GetMapping("/getStudentResultsForRektor")//#/studentResult/getStudentResultsForDekan/
    public HttpEntity<?> getStudentResultsForRektor(){
        return ResponseEntity.ok(repository.getStudentResultsForRektor());
    }

//getStudentResultsForDekan
    @GetMapping("/getStudentResultsForDekan/{groupName}")
    public HttpEntity<?> getStudentResultsForDekan(@PathVariable("groupName") String groupName){
        return ResponseEntity.ok(repository.getStudentResultsForDekan(groupName));
    }


    @GetMapping("/all")
    public HttpEntity<?> getAllRetake(){
        return ResponseEntity.ok(resultService.findAll());
    }

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody StudentResultDto dto){
        return ResponseEntity.status(201).body(resultService.saveOrUpdate(dto));
    }
    @PutMapping("/update")
    public HttpEntity<?> update(@RequestBody StudentResultDto dto){
        return ResponseEntity.status(202).body(resultService.saveOrUpdate(dto));
    }


    //TODO============================================================================
    @PostMapping("/multiCreate")
    public HttpEntity<?> multiCreate(@RequestBody List<ForBuildStudentResult> results){
        return ResponseEntity.ok("OK"/*resultService.multiStudentResults(results)*/);
    }
    //TODO============================================================================

}
