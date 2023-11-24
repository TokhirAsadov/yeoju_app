package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.workOtherService.WorkOtherService;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/workOtherService")
@RequiredArgsConstructor
public class WorkDataOtherServiceController {
    private final WorkOtherService service;

    @PostMapping("/saveResult")
    public HttpEntity<?> saveResult(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer(request,"/result/importStudentsResults"));
    }
    @PostMapping("/saveFails")
    public HttpEntity<?> saveFails(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(service.sendMultipartDataOtherServer(request,"/failtable/importStudentsFails"));
    }
}
