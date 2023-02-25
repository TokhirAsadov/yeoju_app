package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.USERINFO;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.AccMonitoringLogRepo;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.UserInfoRepo;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.useServices.AccMonitoringLogService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/acc")
@RequiredArgsConstructor
public class AccMonitoringLogController {

    public final AccMonitoringLogRepo accMonitoringLogRepo;
    public final AccMonitoringLogService service;
    public final UserInfoRepo userInfoRepo;
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;




    @PostMapping("/getStudents")
    public HttpEntity<?> getStudents(
            @RequestParam(value = "time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time,
            @RequestParam(value = "roleId") String roleId
    ){
        return ResponseEntity.ok(service.countComeUsersBetweenDate(time,roleId));
    }


    @GetMapping("/countUsersByRoleIdAndWeekOrMonth")
    public HttpEntity<?> countUsersByRoleIdAndWeekOrMonth(
            @RequestParam("roleId") String roleId,
            @RequestParam("weekOrMonth") Integer weekOrMonth
    ){
        return ResponseEntity.ok(service.countUsersByRoleIdAndWeekOrMonth(roleId,weekOrMonth));
    }

    @PostMapping("/getUsersByRoleIdAndTimeInterval")
    public HttpEntity<?> getUsersByRoleIdAndTimeInterval(
            @RequestParam(value = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(value = "endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(value = "roleId") String roleId
    ){
        if (startTime==null)
            return ResponseEntity.ok( service.countComeUsersBetweenDate(endTime,roleId) );
        else
            return ResponseEntity.ok( service.countComeUsersOneDay(startTime,endTime,roleId));
    }

    @GetMapping("/buildUsersFromUserInfo")
    public HttpEntity<?> buildUsersFromUserInfo(){

        List<USERINFO> userinfoList = userInfoRepo.findAll();
        System.out.println(userinfoList.size());


        for (int i = 0; i < 17815; i++) {
            User user = new User();
            user.setUserId(Math.toIntExact(userinfoList.get(i).getUSERID())+"");
            user.setFullName(userinfoList.get(i).getName()+" "+userinfoList.get(i).getLastname());
            user.setRFID(userinfoList.get(i).getCardNo());

            userRepository.save(user);
            System.out.println("OK");
        }

        return ResponseEntity.ok(new ApiResponse(true,"All build users"));
    }


    @PostMapping("/dates2")
    public HttpEntity<?> dates2(@RequestParam("time")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time){
        return ResponseEntity.ok(accMonitoringLogRepo.findAccMonitorLogsByTimeBefore(time));
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


    @PostMapping("/monitoring")
    public HttpEntity<?> findMonitoring(
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){

        return ResponseEntity.ok(accMonitoringLogRepo.findMonitoring(startTime,endTime));
    }


    @GetMapping("/groupby")
    public HttpEntity<?> dates3(){
        return ResponseEntity.ok(accMonitoringLogRepo.groupby());
    }

    @GetMapping("/buildStudents")
    public HttpEntity<?> end(){
        Optional<Role> student = roleRepository.findRoleByRoleName("Student");
        System.out.println(student.get());

        List<User> userList = userRepository.findAll();

        Set<Role> roles = new HashSet<>();
        roles.add(student.get());
        System.out.println(roles);

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            user.setRoles(roles);
            userRepository.save(user);
            System.out.println("done");
        }

        return ResponseEntity.ok("change role user");
    }
}
