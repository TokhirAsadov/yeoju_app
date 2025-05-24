package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.TestQuestionCreator;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testQuestion.TestQuestionService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/test-question")
public class TestQuestionController {
    private final TestQuestionService service;

    public TestQuestionController(TestQuestionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> create(@RequestBody TestQuestionCreator creator){
        ApiResponse response = service.create(creator);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }


}
