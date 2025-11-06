package uz.yeoju.yeoju_app.controller.moduleV2.testV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestV2Creator;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestV2Updater;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testV2.TestV2Service;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/testV2")
public class TestV2Controller {
    private final TestV2Service service;

    public TestV2Controller(TestV2Service service) {
        this.service = service;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> create(@RequestBody @Valid TestV2Creator creator){
        ApiResponse response = service.create(creator);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> update(@RequestBody @Valid TestV2Updater updater){
        ApiResponse response = service.update(updater);
        return ResponseEntity.status(response.isSuccess() ? 200 : 417)
                .body(response);
    }

    @DeleteMapping("/delete/{id}")
    HttpEntity<?> delete(@PathVariable String id){
        boolean deleted = service.delete(id);
        return ResponseEntity.status(deleted ? 200 : 417)
                .body(deleted ? "Course Test is deleted.":"Course Test not found by id="+id);
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

    @GetMapping("/findByStudentIdAndEducationYearIdV2/{studentId}/{educationYearId}")
    HttpEntity<?> findByStudentIdAndEducationYearId(@PathVariable String studentId,
                                                    @PathVariable String educationYearId){
        ApiResponse res = service.findByStudentIdAndEducationYearIdV2(studentId,educationYearId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/findByGroupIdAndEducationYearIdV2/{groupId}/{educationYearId}")
    HttpEntity<?> findByGroupIdAndEducationYearId(@PathVariable String groupId,
                                                    @PathVariable String educationYearId){
        ApiResponse res = service.findByGroupIdAndEducationYearIdV2(groupId,educationYearId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }
}
