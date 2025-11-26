package uz.yeoju.yeoju_app.controller.moduleV2.testV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.AiRequestDto;
import uz.yeoju.yeoju_app.payload.moduleV2.TestResultDto;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerFinisher;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.UserTestAnswerV2Finisher;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testOptionV2.TestOptionV2Service;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.userTestAnswerV2.UserTestAnswerV2Service;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userTestAnswer.UserTestAnswerService;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/user-test-answer-v2")
public class UserTestAnswerV2Controller {
    private final UserTestAnswerV2Service service;
    private final TestOptionV2Service testOptionV2Service;

    public UserTestAnswerV2Controller(UserTestAnswerV2Service service, TestOptionV2Service testOptionV2Service) {
        this.service = service;
        this.testOptionV2Service = testOptionV2Service;
    }

    @PostMapping("/finishCourseTest")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> finishCourseTest(@CurrentUser User user, @RequestBody UserTestAnswerV2Finisher finisher){
        System.out.println("Finishing course test with finisher: " + finisher);
        ApiResponse response = testOptionV2Service.finishCourseTest(finisher);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }


    @GetMapping("/findStudentResult/{studentUserId}/{testId}")
    HttpEntity<?> findStudentResult(@PathVariable String studentUserId,
                                    @PathVariable String testId){
        TestResultDto res = service.calculateUserScore(studentUserId, testId);
        return ResponseEntity.ok(res);
    }

}
