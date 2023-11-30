package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.FailTableDto;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.FinalDto;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.GpaDto;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.ResultDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.workOtherService.WorkOtherService;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/workOtherService")
@RequiredArgsConstructor
public class WorkDataOtherServiceController {
    private final WorkOtherService service;

    @GetMapping("/getFinals")
    public HttpEntity<?> getFinals(@RequestParam("page") Integer page,@RequestParam("size") Integer size) throws IOException {
        return ResponseEntity.ok(service.getDataFromOther("/final/getAllFinals",page,size));
    }

    @GetMapping("/getResults")
    public HttpEntity<?> getResults(@RequestParam("page") Integer page,@RequestParam("size") Integer size) throws IOException {
        return ResponseEntity.ok(service.getDataFromOther("/result/getAllResults",page,size));
    }

    @GetMapping("/getFails")
    public HttpEntity<?> getFails(@RequestParam("page") Integer page,@RequestParam("size") Integer size) throws IOException {
        return ResponseEntity.ok(service.getDataFromOther("/failtable/getAllFails",page,size));
    }

    @GetMapping("/getGPAs")
    public HttpEntity<?> getGPAs(@RequestParam("page") Integer page,@RequestParam("size") Integer size) throws IOException {
        return ResponseEntity.ok(service.getDataFromOther("/gpa/getAllGPAs",page,size));
    }
    @PostMapping("/saveSingleResult")
    public HttpEntity<?> saveSingleResult(@RequestBody ResultDto dto) throws IOException {
        return ResponseEntity.ok(service.createOrUpdateResult(dto));
    }

    @PutMapping("/updateSingleResult")
    public HttpEntity<?> updateSingleResult(@RequestBody ResultDto dto) throws IOException {
        return ResponseEntity.ok(service.createOrUpdateResult(dto));
    }

    @PostMapping("/saveSingleFail")
    public HttpEntity<?> saveSingleFail(@RequestBody FailTableDto dto) throws IOException {
        return ResponseEntity.ok(service.createOrUpdateFail(dto));
    }
    @PutMapping("/updateSingleFail")
    public HttpEntity<?> updateSingleFail(@RequestBody FailTableDto dto) throws IOException {
        return ResponseEntity.ok(service.createOrUpdateFail(dto));
    }

    @PostMapping("/saveSingleGPA")
    public HttpEntity<?> saveSingleGPA(@RequestBody GpaDto dto) throws IOException {
        return ResponseEntity.ok(service.createOrUpdateGPA(dto));
    }
    @PutMapping("/updateSingleGPA")
    public HttpEntity<?> updateSingleGPA(@RequestBody GpaDto dto) throws IOException {
        return ResponseEntity.ok(service.createOrUpdateGPA(dto));
    }

    @PostMapping("/saveSingleFinal")
    public HttpEntity<?> saveSingleFinal(@RequestBody FinalDto dto) throws IOException {
        return ResponseEntity.ok(service.createOrUpdateFinal(dto));
    }
    @PutMapping("/updateSingleFinal")
    public HttpEntity<?> updateSingleFinal(@RequestBody FinalDto dto) throws IOException {
        return ResponseEntity.ok(service.createOrUpdateFinal(dto));
    }
    @PostMapping("/saveFinal")
    public HttpEntity<?> saveFinal(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer(request,"/final/importStudentsFinals"));
    }

    @PostMapping("/saveResult")
    public HttpEntity<?> saveResult(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer(request,"/result/importStudentsResults"));
    }
    @PostMapping("/saveFails")
    public HttpEntity<?> saveFails(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer(request,"/failtable/importStudentsFails"));
    }
    @PostMapping("/saveGPA")
    public HttpEntity<?> saveGPA(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer(request,"/gpa/importStudentsGPAs"));
    }

    @PutMapping("/updateResult")
    public HttpEntity<?> updateResult(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer2(request,"/result/updateStudentsResults"));
    }
    @PutMapping("/updateFails")
    public HttpEntity<?> updateFails(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer2(request,"/failtable/updateStudentsFails"));
    }
    @PutMapping("/updateGPA")
    public HttpEntity<?> updateGPA(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer2(request,"/gpa/updateStudentsGPAs"));
    }
}
