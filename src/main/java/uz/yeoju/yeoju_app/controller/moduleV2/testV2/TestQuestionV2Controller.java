package uz.yeoju.yeoju_app.controller.moduleV2.testV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.GiveScoreToWrittenUserAnswerDto;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestQuestionV2Creator;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testQuestionV2.TestQuestionV2Service;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/test-question-v2")
public class TestQuestionV2Controller {
    private final TestQuestionV2Service service;

    public TestQuestionV2Controller(TestQuestionV2Service service) {
        this.service = service;
    }

    @GetMapping("/getRandomQuestionsForTest/{testId}")
    HttpEntity<?> getRandomQuestionsForTest(@PathVariable String testId,
                                            @RequestParam(required = false) String moduleId){
        ApiResponse res = service.getRandomQuestionsForTest(testId, moduleId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getCountOfTypeOfQuestionsByCourseId/{courseId}")
    HttpEntity<?> getCountOfTypeOfQuestionsByCourseId(@PathVariable String courseId){
        ApiResponse res = service.getCountOfTypeOfQuestionsByCourseId(courseId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getCountOfTypeOfQuestionsByModuleId/{moduleId}")
    HttpEntity<?> getCountOfTypeOfQuestionsByModuleId(@PathVariable String moduleId){
        ApiResponse res = service.getCountOfTypeOfQuestionsByModuleId(moduleId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/checkEnoughQuestions/{courseId}/{count}/{type}")
    HttpEntity<?> checkEnoughQuestions(
            @PathVariable String courseId,
            @PathVariable int count,
            @PathVariable TestType type){
        ApiResponse res = service.checkEnoughQuestions(courseId, count, type);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/checkEnoughQuestionsForModule/{moduleId}/{count}/{type}")
    HttpEntity<?> checkEnoughQuestionsForModule(
            @PathVariable String moduleId,
            @PathVariable int count,
            @PathVariable TestType type){
        ApiResponse res = service.checkEnoughQuestionsForModule(moduleId, count, type);
        return ResponseEntity.ok(res);
    }



    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> createV2(@RequestBody TestQuestionV2Creator creator){
        ApiResponse response = service.createTestQuestionV2(creator);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }

    @PutMapping("/update/{questionId}")
    HttpEntity<?> update(@PathVariable String questionId, @RequestBody TestQuestionV2Creator creator){
        ApiResponse response = service.updateTestQuestionV2(questionId,creator);
        return ResponseEntity.status(response.isSuccess() ? 200 : 417)
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

    @GetMapping("/findByCourseId/{courseId}")
    HttpEntity<?> findByCourseId(@PathVariable String courseId){
        ApiResponse res = service.findByCourseId(courseId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/findByModuleId/{moduleId}")
    HttpEntity<?> findByModuleId(@PathVariable String moduleId){
        ApiResponse res = service.findByModuleId(moduleId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }


    @GetMapping("/getTestQuestionsByCourseTestIdForStudent/{testId}")
    HttpEntity<?> getTestQuestionByCourseTestIdForStudent(@CurrentUser User user,@PathVariable String testId,
                                                          @RequestParam(required = false) String moduleId){
        ApiResponse res = service.findTestQuestionsByCourseIdWithShuffledOptions(user.getId(),testId, moduleId);
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
