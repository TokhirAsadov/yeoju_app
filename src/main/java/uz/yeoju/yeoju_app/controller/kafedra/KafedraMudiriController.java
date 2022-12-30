package uz.yeoju.yeoju_app.controller.kafedra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.dekanat.DekanSave;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraMudiriSaving;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.KafedraMudiriService;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/kafera-mudiri")
public class KafedraMudiriController {

    @Autowired
    private KafedraMudiriService service;


    @GetMapping("/positionEdit")
    public HttpEntity<?> positionEdit(@RequestParam("id") String id){
        return ResponseEntity.ok(service.positionEdit(id));
    }


    @PostMapping("/saveKafedra")
    public HttpEntity<?> saveKafedra(@RequestBody DekanSave dto){
        return ResponseEntity.status(201).body(service.saveKafedra(dto));
    }

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody KafedraMudiriSaving saving){
        return ResponseEntity.ok(service.save(saving));
    }

    @GetMapping("/getStatistics")
    public HttpEntity<?> getStatistics(@CurrentUser User user) {
        return ResponseEntity.ok(service.getStatistics(user));
    }

    @GetMapping("/getStatisticss")
    public HttpEntity<?> getStatistics(@CurrentUser User user,@RequestParam("userId") String userId,@RequestParam("date")
    @DateTimeFormat(pattern = "yyyy.MM.dd") Date date)
    {
        return ResponseEntity.ok(service.getStatistics(user,userId,date));
    }

    @GetMapping("/getStatisticssForRektor")
    public HttpEntity<?> getStatisticssForRektor(@CurrentUser User user,@RequestParam("kafedraId") String kafedraId,@RequestParam("date")
    @DateTimeFormat(pattern = "yyyy.MM.dd") Date date)
    {
        return ResponseEntity.ok(service.getStatisticsForRektor(kafedraId,date));
    }

//    @PostMapping("/date")
//    public HttpEntity<?> date(@RequestParam("date")
//                     @DateTimeFormat(pattern = "yyyy.MM.dd") Date date) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//
//    }
}
