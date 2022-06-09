package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.StudentDto;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.service.useServices.StudentService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/student")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService studentService;
    public final StudentRepository studentRepository;

    @PostMapping("/getFacultyAndComingCount")
    public HttpEntity<?> findMonitoring(
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok(studentService.getFacultyAndComingCountWithAll(startTime,endTime));
    }
    @PostMapping("/getFacultyAndComingCountWithAllByGroupLevel")
    public HttpEntity<?> getFacultyAndComingCountWithAllByGroupLevel(
            @RequestParam("level") Integer level,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok(studentService.getFacultyAndComingCountWithAllByGroupLevel(level,startTime,endTime));
    }


    @GetMapping("/createStudentsByRfidAndGroupNames")
    public HttpEntity<?> createStudentsByRfidAndGroupNames(
            @RequestParam("rfid") List<String> rfid,
            @RequestParam("groupNames") List<String> groupNames
    ){
        return ResponseEntity.ok(studentService.createStudentsByRfidAndGroupNames(rfid,groupNames));
    }

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


//    @GetMapping("/findStudentsByEducationFormId/{educationForm_id}")
//    public HttpEntity<?> findStudentsByEducationFormId(@PathVariable String educationForm_id){
//        return ResponseEntity.ok(studentService.findStudentsByEducationFormId(educationForm_id));
//    }
//
//    @GetMapping("/findStudentsByEducationTypeId/{educationType_id}")
//    public HttpEntity<?> findStudentsByEducationTypeId(@PathVariable String educationType_id){
//        return ResponseEntity.ok(studentService.findStudentsByEducationTypeId(educationType_id));
//    }
//
//    @GetMapping("/findStudentsByEducationLanguageId/{educationLanguage_id}")
//    public HttpEntity<?> findStudentsByEducationLanguageId(@PathVariable String educationLanguage_id){
//        return ResponseEntity.ok(studentService.findStudentsByEducationLanguageId(educationLanguage_id));
//    }

    @GetMapping("/findStudentsByBornYear/{bornYear}")
    public HttpEntity<?> findStudentsByBornYear(@PathVariable Timestamp bornYear){
        return ResponseEntity.ok(studentService.findStudentsByBornYear(bornYear));
    }
    @GetMapping("/findStudentsByEnrollmentYear/{enrollmentYear}")
    public HttpEntity<?> findStudentsByEnrollmentYear(@PathVariable Timestamp enrollmentYear){
        return ResponseEntity.ok(studentService.findStudentsByEnrollmentYear(enrollmentYear));
    }

    @GetMapping("/findStudentsByCitizenship/{citizenship}")
    public HttpEntity<?> findStudentsByCitizenship(@PathVariable String citizenship){
        return ResponseEntity.ok(studentService.findStudentsByCitizenship(citizenship));
    }
}
