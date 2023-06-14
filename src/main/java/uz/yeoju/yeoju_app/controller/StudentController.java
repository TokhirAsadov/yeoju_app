package uz.yeoju.yeoju_app.controller;

import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.StudentDto;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.StudentService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/student")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService studentService;
    public final StudentRepository studentRepository;



    @PostMapping("/uploadStudent")
    public HttpEntity<?> uploadPhotoForUser(MultipartHttpServletRequest request, @CurrentUser User user) throws IOException {
        System.out.println(" ----------------------------- 1 1 1 ------------------------ --");
        ApiResponse apiResponse = studentService.saving(request);
//        ApiResponse apiResponse = new ApiResponse(true,"keldi");
        System.out.println(" ----------------------------- ------------------------ --");
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getStudentWithRFIDForToday/{groupName}")
    public HttpEntity<?> getStudentWithRFIDForToday(
            @PathVariable("groupName") String groupName
    ){
        return ResponseEntity.ok(studentService.getStudentWithRFIDForToday(groupName));
    }

    @PostMapping("/getStudentWithRFID")
    public HttpEntity<?> getStudentWithRFID(
            @RequestParam("groupName") String groupName,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok(studentService.getStudentWithRFID(groupName,startTime,endTime));
    }

    @GetMapping("/getStudentsByTimeIntervalAndLevelAndFacultyName")
    public HttpEntity<?> getStudentsByTimeIntervalAndLevelAndFacultyName(
            @RequestParam("timeInterval") Integer timeInterval,
            @RequestParam("level") Integer level,
            @RequestParam("facultyName") String facultyName
    ){
        return ResponseEntity.ok(studentService.getStudentsByTimeIntervalAndLevelAndFacultyName(timeInterval,level,facultyName));
    }

    @PostMapping("/getGroupsStatisticByFacultyNameAndGroupLevel")
    public HttpEntity<?> getGroupsStatisticByFacultyNameAndGroupLevel(
            @RequestParam("level") Integer level,
            @RequestParam("facultyName") String facultyName,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok(studentService.getGroupsStatisticByFacultyNameAndGroupLevel(level,facultyName,startTime,endTime));
    }

    @GetMapping("/getGroupsByLevelAndFacultyName/{level}")
    public HttpEntity<?> getGroupsByLevelAndFacultyName(
            @PathVariable("level") Integer level,
            @RequestParam("facultyName") String facultyName
    ){
        return ResponseEntity.ok(studentService.getGroupsByLevelAndFacultyName(level,facultyName));
    }

    @GetMapping("/getAllCourses")
    public HttpEntity<?> getAllCourses(){
        return ResponseEntity.ok(studentService.getAllCourses());
    }

    @GetMapping("/getFacultyByCourseLevel/{level}")
    public HttpEntity<?>getFacultyByCourseLevel(@PathVariable Integer level){
        return ResponseEntity.ok(studentService.getFacultyByCourseLevel(level));
    }

    @PostMapping("/getStudentCountByGroupAndFaculty")
    public HttpEntity<?> getStudentCountByGroupAndFaculty(
            @RequestParam("facultyName") String facultyName,
            @RequestParam("level") Integer level,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok(studentService.getStudentCountByGroupAndFaculty(facultyName,level,startTime,endTime));
    }
    @PostMapping("/getFacultyAndComingCount")
    public HttpEntity<?> findMonitoring(
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok(studentService.getFacultyAndComingCountWithAll(startTime,endTime));
    }
    @GetMapping("/getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth") //todo----------------------------------getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth
    public HttpEntity<?> getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth(
            @RequestParam("level") Integer level,
            @RequestParam("eduType") String eduType,
            @RequestParam("weekOrMonth") Integer weekOrMonth
    ){
        return ResponseEntity.ok(studentService.getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth(level,weekOrMonth,eduType));
    }
    @PostMapping("/getFacultyAndComingCountWithAllByGroupLevel") //todo-----------------------------------------------
    public HttpEntity<?> getFacultyAndComingCountWithAllByGroupLevel(
            @RequestParam("level") String level,
            @RequestParam("eduType") String eduType,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok(studentService.getFacultyAndComingCountWithAllByGroupLevel(Integer.parseInt(level),eduType,startTime,endTime));
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
