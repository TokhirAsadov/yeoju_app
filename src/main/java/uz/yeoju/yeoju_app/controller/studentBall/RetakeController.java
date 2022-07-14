package uz.yeoju.yeoju_app.controller.studentBall;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.studentBall.RetakeDto;
import uz.yeoju.yeoju_app.service.useServices.RetakeService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/retake")
@RequiredArgsConstructor
public class RetakeController {
    private final RetakeService retakeService;


    @GetMapping("/all")
    public HttpEntity<?> getAllRetake(){
        return ResponseEntity.ok(retakeService.findAll());
    }

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody RetakeDto dto){
        return ResponseEntity.status(201).body(retakeService.saveOrUpdate(dto));
    }
    @PutMapping("/update")
    public HttpEntity<?> update(@RequestBody RetakeDto dto){
        return ResponseEntity.status(202).body(retakeService.saveOrUpdate(dto));
    }

}
