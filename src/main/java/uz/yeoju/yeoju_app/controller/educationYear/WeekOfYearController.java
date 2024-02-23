package uz.yeoju.yeoju_app.controller.educationYear;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.educationYear.WeekOfYearDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.weekOfEducationYear.WeekOfEducationYearService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/week")
@RequiredArgsConstructor
public class WeekOfYearController {
    private final WeekOfEducationYearService weekService;

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(){
        return ResponseEntity.ok(weekService.findAll());
    }

    @GetMapping("/findById/{id}")
    public HttpEntity<?> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(weekService.findById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    public HttpEntity<?> deleteById(@PathVariable("id") String id){
        ApiResponse response = weekService.deletedById(id);
        return ResponseEntity.status(204).body(response);
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody WeekOfYearDto dto){
        System.out.println(dto);
        ApiResponse response = weekService.saveOrUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }

    @PutMapping("/update")
    public HttpEntity<?> update(@RequestBody WeekOfYearDto dto){
        ApiResponse response = weekService.saveOrUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }

    @PostMapping("/saveV2")
    public HttpEntity<?> saveV2(@RequestBody WeekOfYearDto dto){
        System.out.println(dto);
        ApiResponse response = weekService.saveV2(dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }
}
