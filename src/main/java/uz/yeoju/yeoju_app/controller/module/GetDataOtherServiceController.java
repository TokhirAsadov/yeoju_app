package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.getDataOtherService.GetDataOtherService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/otherService")
@RequiredArgsConstructor
public class GetDataOtherServiceController {
    private final GetDataOtherService otherService;

    @GetMapping("/studentsFinals/{studentId}")
    public HttpEntity<?> getStudentFinals(@PathVariable("studentId") String studentId){
        ApiResponse response = otherService.getStudentsFinals(studentId);
        return ResponseEntity.status(response.isSuccess() ? 200:409).body(response);
    }

    @GetMapping("/studentsFinals2/{login}")
    public HttpEntity<?> getStudentFinals2(@PathVariable("login") String login){
        ApiResponse response = otherService.getStudentsFinals2(login);
        return ResponseEntity.status(response.isSuccess() ? 200:409).body(response);
    }

    @GetMapping("/studentsResults/{studentId}")
    public HttpEntity<?> getStudentResults(@PathVariable("studentId") String studentId){
        ApiResponse response = otherService.getStudentsResults(studentId);
        return ResponseEntity.status(response.isSuccess() ? 200:409).body(response);
    }

    @GetMapping("/studentsResults2/{login}")
    public HttpEntity<?> getStudentResults2(@PathVariable("login") String login){
        ApiResponse response = otherService.getStudentsResults2(login);
        return ResponseEntity.status(response.isSuccess() ? 200:409).body(response);
    }


    @GetMapping("/studentsFails/{studentId}")
    public HttpEntity<?> getStudentFails(@PathVariable("studentId") String studentId){
        ApiResponse response = otherService.getStudentFails(studentId);
        return ResponseEntity.status(response.isSuccess() ? 200:409).body(response);
    }
    @GetMapping("/studentsFails2/{login}")
    public HttpEntity<?> getStudentFails2(@PathVariable("login") String login){
        ApiResponse response = otherService.getStudentFails2(login);
        return ResponseEntity.status(response.isSuccess() ? 200:409).body(response);
    }
    @GetMapping("/studentsGPA/{studentId}")
    public HttpEntity<?> getStudentGPA(@PathVariable("studentId") String studentId){
        ApiResponse response = otherService.getStudentGPA(studentId);
        return ResponseEntity.status(response.isSuccess() ? 200:409).body(response);
    }
    @GetMapping("/studentsGPA2/{login}")
    public HttpEntity<?> getStudentGPA2(@PathVariable("login") String login){
        ApiResponse response = otherService.getStudentGPA2(login);
        return ResponseEntity.status(response.isSuccess() ? 200:409).body(response);
    }

    @GetMapping("/getDataByStudentId/{studentId}")
    public HttpEntity<?> getDataByStudentId(@PathVariable("studentId") String studentId){
        return ResponseEntity.ok(otherService.getDataByStudentId(studentId));
    }
    @GetMapping("/getDataByStudentId2/{login}")
    public HttpEntity<?> getDataByStudentId2(@PathVariable("login") String login){
        return ResponseEntity.ok(otherService.getDataByStudentId2(login));
    }
}
