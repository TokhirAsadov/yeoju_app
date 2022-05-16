package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.StudentDto;
import uz.yeoju.yeoju_app.service.useServices.StudentService;

@RestController
@RequestMapping("/v1/student")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService studentService;

    @GetMapping("/allStudent")
    public HttpEntity<?> allStudent(){
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/getStudentByIdOrUserIdOrPassportSerial/{id}")
    public HttpEntity<?> getStudentByIdOrUserIdOrPassportSerial(
            @PathVariable String id,
            @RequestParam String user_id,
            @RequestParam String passportSerial
    ){
        return id!=null ? ResponseEntity.ok(studentService.findById(id))
                :
                user_id!=null ? ResponseEntity.ok(studentService.findStudentByUserId(user_id))
                :
                ResponseEntity.ok(studentService.findStudentByPassportSerial(passportSerial));
    }

    @PostMapping("/createStudent")
    public HttpEntity<?> createNewStudent(@RequestBody StudentDto dto){
        return ResponseEntity.status(201).body(studentService.saveOrUpdate(dto));
    }

    @PostMapping("/updateStudent")
    public HttpEntity<?> updateStudent(@RequestBody StudentDto dto){
        return ResponseEntity.status(202).body(studentService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteStudent/{id}")
    public HttpEntity<?> deleteStudent(@PathVariable String id){
        return ResponseEntity.status(204).body(studentService.deleteById(id));
    }

    @GetMapping("/findStudentsByGroupId/{group_id}")
    public HttpEntity<?> findStudentsByGroupId(@PathVariable String group_id){
        return ResponseEntity.ok(studentService.findStudentsByGroupId(group_id));
    }
}
