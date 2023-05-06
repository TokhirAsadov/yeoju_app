package uz.yeoju.yeoju_app.controller.school;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.school.SchoolDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.school.SchoolService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService service;

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(@CurrentUser User user){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById/{id}")
    public HttpEntity<?> findAll(@CurrentUser User user,@PathVariable(name = "id") String id){
        ApiResponse response = service.findById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@CurrentUser User user, @RequestBody SchoolDto dto){
        ApiResponse response = service.saveOrUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }

    @DeleteMapping("/deleteById/{id}")
    public HttpEntity<?> deleteById(@PathVariable(name = "id") String id){
        ApiResponse response = service.deleteById(id);
        return ResponseEntity.status(response.isSuccess() ? 204 : 403).body(response);
    }

}
