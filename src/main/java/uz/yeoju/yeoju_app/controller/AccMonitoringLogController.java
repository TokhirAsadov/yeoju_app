package uz.yeoju.yeoju_app.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.USERINFO;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.AccMonitoringLogRepo;
import uz.yeoju.yeoju_app.repository.UserInfoRepo;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.useServices.AccMonitoringLogService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/acc")
@RequiredArgsConstructor
public class AccMonitoringLogController {

    public final AccMonitoringLogRepo accMonitoringLogRepo;
    public final AccMonitoringLogService service;
    public final UserInfoRepo userInfoRepo;
    public final UserRepository userRepository;




    @GetMapping("/get")
    public HttpEntity<?> getAcc(){

        return ResponseEntity.ok(new ApiResponse(true,"All acc",accMonitoringLogRepo.findAll()));
    }

    @GetMapping("/buildUsersFromUserInfo")
    public HttpEntity<?> buildUsersFromUserInfo(){

        List<USERINFO> userinfoList = userInfoRepo.findAll();
        for (USERINFO userinfo : userinfoList) {
            User user = new User();
            user.setUserId(userinfo.getUSERID());
            user.setFullName(userinfo.getName()+" "+userinfo.getLastname());
            user.setRFID(userinfo.getCardNo());

            userRepository.save(user);
            System.out.println("OK");
        }

        return ResponseEntity.ok(new ApiResponse(true,"All build users"));
    }

    @SneakyThrows
    @GetMapping("/dates/{date}")
    public HttpEntity<?> dates(@PathVariable String date){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");
//        LocalDateTime newDate = LocalDateTime.parse(date,formatter);
//        System.out.println(newDate);
//        Timestamp newTime = Timestamp.valueOf(newDate);

        return ResponseEntity.ok(service.findBeforeDate(date));
    }

    @GetMapping("/dates2/{date}")
    public HttpEntity<?> dates2(@PathVariable  @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return ResponseEntity.ok(accMonitoringLogRepo.findBeforeDate(date));
    }
    @GetMapping("/card_no/{card}")
    public HttpEntity<?> dates2(@PathVariable String card){
        return ResponseEntity.ok(accMonitoringLogRepo.findAccMonitorLogsByCard_no(card));
    }
    @PostMapping("/time")
    public HttpEntity<?> findAccMonitorLogsByTimeBefore(
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok(accMonitoringLogRepo.findAccMonitorLogsByTimeBetween(startTime,endTime));
    }
}
