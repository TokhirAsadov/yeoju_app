package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.TestResultDto;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerFinisher;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userTestAnswer.UserTestAnswerService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/user-test-answer")
public class UserTestAnswerController {
    private final UserTestAnswerService service;

    public UserTestAnswerController(UserTestAnswerService service) {
        this.service = service;
    }


}
