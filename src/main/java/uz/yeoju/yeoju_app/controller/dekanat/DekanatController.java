package uz.yeoju.yeoju_app.controller.dekanat;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.AddNewStudentDto;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatDto;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatSaveDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.DekanatService;

import java.util.Date;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/dekanat")
@RequiredArgsConstructor
public class DekanatController {

    private final DekanatService service;


    @PostMapping("/addNewStudent")
    public HttpEntity<?> addNewStudent(@RequestBody AddNewStudentDto dto){
        return ResponseEntity.status(201).body(service.addNewStudent(dto));
    }
    @GetMapping("/getStatisticsOfGroupForDean")
    public HttpEntity<?> getStatisticssForRektor(
            @CurrentUser User user,
            @RequestParam("groupId") String groupId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy.MM.dd") Date date)
    {
        return ResponseEntity.ok(service.getStatisticsOfGroupForDean(groupId,date));
    }

    @DeleteMapping("/deleteById/{id}")
    public HttpEntity<?> deletedById(@CurrentUser User user,@PathVariable("id") String id){
        return ResponseEntity.status(204).body(service.deleteById(id));
    }

    @GetMapping("/getDekanatById")
    public HttpEntity<?> getDekanatById(@CurrentUser User user,@RequestParam("id") String id){
        ApiResponse response = service.getDekanatById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @GetMapping("/dekanatDataForDekan")
    public HttpEntity<?> getDekanatDataForDekan(@CurrentUser User user){
        return ResponseEntity.ok(service.getDekanatDataForDekan(user.getId()));
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody DekanatSaveDto dto){
        return ResponseEntity.status(201).body(service.saveDekanat(dto));
    }

    @PostMapping("/saveV2")
    public HttpEntity<?> saveV2(@RequestBody DekanatDto dto){
        return ResponseEntity.status(201).body(service.saveDekanatV2(dto));
    }

    @GetMapping("/all")
    public HttpEntity<?> allDekanats(){
        return ResponseEntity.ok(service.findAllDekanats());
    }


    @GetMapping("/getDatasForSavedDekan")
    public HttpEntity<?> getDatasForSavedDekan(){
        return ResponseEntity.ok(service.getDekansSavedDatas());
    }

    @GetMapping("/getDatasForSavedKafedraMudiri")
    public HttpEntity<?> getDatasForSavedKafedraMudiri(){
        return ResponseEntity.ok(service.getKafedrasSavedDatas());
    }

    @GetMapping("/getUserForDekanSave")
    public HttpEntity<?> getUserForDekanSaving(@CurrentUser User user,@RequestParam("bool") Boolean bool){
        return ResponseEntity.ok(service.getUserForDekanSave(user.getId(),bool));
    }
}
