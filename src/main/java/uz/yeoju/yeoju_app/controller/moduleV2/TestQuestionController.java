package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.GiveScoreToWrittenUserAnswerDto;
import uz.yeoju.yeoju_app.payload.moduleV2.TestQuestionCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.TestQuestionCreatorV2;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testQuestion.TestQuestionService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/test-question")
public class TestQuestionController {
    private final TestQuestionService service;

    public TestQuestionController(TestQuestionService service) {
        this.service = service;
    }

    @Deprecated
    @PostMapping("/createOld")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> create(@RequestBody TestQuestionCreator creator){
        ApiResponse response = service.create(creator);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> createV2(@RequestBody TestQuestionCreatorV2 creator){
        ApiResponse response = service.createV2(creator);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }

    @DeleteMapping("/delete/{id}")
    HttpEntity<?> delete(@PathVariable String id){
        boolean deleted = service.deleteById(id);
        return ResponseEntity.status(deleted ? 200 : 417)
                .body("Test question is deleted.");
    }

    @GetMapping("/findAll")
    HttpEntity<?> findAll(Pageable pageable){
        ApiResponse res = service.findAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/findById/{id}")
    HttpEntity<?> findById(@PathVariable String id){
        ApiResponse res = service.findById(id);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/findByCourseTestId/{courseTestId}")
    HttpEntity<?> findByCourseTestId(@PathVariable String courseTestId){
        ApiResponse res = service.findByCourseTestId(courseTestId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/getTestQuestionsByCourseTestIdForStudent/{courseTestId}")
    HttpEntity<?> getTestQuestionByCourseTestIdForStudent(@PathVariable String courseTestId){
        ApiResponse res = service.findTestQuestionsByCourseIdWithShuffledOptions(courseTestId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/student-answers")
    HttpEntity<?> getStudentCourseTestAnswers(
            @RequestParam String testId,
            @RequestParam String userId
    ){
        ApiResponse response = service.getStudentCourseTestAnswers(testId, userId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 417)
                .body(response);
    }

    @PutMapping("/giveScoreToWrittenUserAnswer")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    HttpEntity<?> giveScoreToWrittenUserAnswer(
            @CurrentUser User user,
            @RequestBody GiveScoreToWrittenUserAnswerDto dto
    ){
        ApiResponse response = service.giveScoreToWrittenUserAnswer(dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 417)
                .body(response);
    }
}
