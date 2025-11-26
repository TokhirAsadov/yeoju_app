package uz.yeoju.yeoju_app.controller.moduleV2;

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
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userTestAnswer.UserTestAnswerService;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/user-test-answer")
public class UserTestAnswerController {
    private final UserTestAnswerService service;

    public UserTestAnswerController(UserTestAnswerService service) {
        this.service = service;
    }

    @PostMapping("/finishCourseTest")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> finishCourseTest(@CurrentUser User user, @RequestBody UserTestAnswerFinisher finisher){
        System.out.println("Finishing course test with finisher: " + finisher);
        ApiResponse response = service.finishCourseTest(finisher);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> create(@RequestBody UserTestAnswerCreator creator){
        ApiResponse response = service.create(creator);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }

    @DeleteMapping("/delete/{id}")
    HttpEntity<?> delete(@PathVariable String id){
        boolean deleted = service.deleteById(id);
        return ResponseEntity.status(deleted ? 200 : 417)
                .body("User test answer is deleted.");
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

    @GetMapping("/findStudentResult/{studentUserId}/{courseTestId}")
    HttpEntity<?> findStudentResult(@PathVariable String studentUserId,
                                    @PathVariable String courseTestId){
        TestResultDto res = service.calculateUserScore(studentUserId, courseTestId);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/ai")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_KAFEDRA')")
    HttpEntity<?> ai(@RequestBody AiRequestDto dto) throws IOException {
        return service.ai(dto);
    }

    @GetMapping("/generate")
    public String generateText(@RequestParam String prompt) {
        System.out.println("Generating text with prompt: " + prompt);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{\"inputs\": \"" + prompt + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api-inference.huggingface.co/models/mrm8488/t5-base-finetuned-question-generation-ap",
                    entity,
                    String.class
            );
            System.out.println("Response: " + response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.err.println("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return "Client error: " + e.getMessage();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return "Unexpected error: " + e.getMessage();
        }

    }
}
