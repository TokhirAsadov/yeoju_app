package uz.yeoju.yeoju_app.controller.studentBall;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.studentBall.SubjectCreditDto;
import uz.yeoju.yeoju_app.service.useServices.SubjectCreditService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/subjectCredit")
@RequiredArgsConstructor
public class SubjectCreditController {
    private final SubjectCreditService creditService;


    @GetMapping("/all")
    public HttpEntity<?> getAllRetake(){
        return ResponseEntity.ok(creditService.findAll());
    }

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody SubjectCreditDto dto){
        return ResponseEntity.status(201).body(creditService.saveOrUpdate(dto));
    }
    @PutMapping("/update")
    public HttpEntity<?> update(@RequestBody SubjectCreditDto dto){
        return ResponseEntity.status(202).body(creditService.saveOrUpdate(dto));
    }

}
