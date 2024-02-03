package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.kafedra.KafedraMudiri;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraDto;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraV2Dto;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraMudirRepository;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraRepository;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.KafedraService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/kafedra")
@RequiredArgsConstructor
public class KafedraController {

    public final KafedraService kafedraService;
    public final KafedraRepository kafedraRepository;
    public final KafedraMudirRepository kafedraMudirRepository;
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    public final RoleRepository roleRepository;


    @GetMapping("/getKafedraV3ById")
    public HttpEntity<?> getKafedraV3ById(@CurrentUser User user,@RequestParam("id") String id){
        return ResponseEntity.ok(kafedraService.getKafedraV3ById(id));
    }

    @GetMapping("/getKafedraByIdV2")
    public HttpEntity<?> getKafedraByIdV2(@CurrentUser User user,@RequestParam("id") String id){
        return ResponseEntity.ok(kafedraService.getKafedraByIdV2(id));
    }

    @PostMapping("/createKafedraV2")
    public HttpEntity<?> createNewKafedraV2(@RequestBody KafedraV2Dto dto){
        System.out.println(dto);
        return ResponseEntity.status(201).body(kafedraService.saveOrUpdateV2(dto));
    }

    @PutMapping("/updateKafedraV2")
    public HttpEntity<?> updateKafedraV2(@RequestBody KafedraV2Dto dto){
        return ResponseEntity.status(202).body(kafedraService.saveOrUpdateV2(dto));
    }


    @GetMapping("/getTeachersForSelectByKafedraId")
    public HttpEntity<?> getTeachersForSelectByKafedraId(@CurrentUser User user,@RequestParam(name = "kafedraId") String kafedraId){
        return ResponseEntity.ok(kafedraService.getTeachersForSelectByKafedraId(kafedraId));
    }
    @GetMapping("/getTeachersForTableByKafedraId")
    public HttpEntity<?> getTeachersForTableByKafedraId(@CurrentUser User user,@RequestParam(name = "kafedraId") String kafedraId){
        return ResponseEntity.ok(kafedraService.getTeachersForTableByKafedraId(kafedraId));
    }

    @GetMapping("/getKafedrasForSelect")
    public HttpEntity<?> getKafedrasForSelect(@CurrentUser User user){
        return ResponseEntity.ok(kafedraService.getKafedrasForSelect());
    }

    @GetMapping("/getTeachersForSendSms")
    public HttpEntity<?> getTeachersForSendSms(@RequestParam("kafedraId") String kafedraId, @RequestParam("search") String search){

        return ResponseEntity.ok(kafedraRepository.getTeachersForSendSms(kafedraId,"%"+search+"%"));
    }

    @GetMapping("/getKafedra")
    public HttpEntity<?> getKafedraNameByUserId(@CurrentUser User user){
        return ResponseEntity.ok(kafedraRepository.getKafedraNameByUserId(user.getId()));
    }

    @GetMapping("/allKafedra")
    public HttpEntity<?> allKafedra(){
        return ResponseEntity.ok(kafedraService.findAll());
    }

    @GetMapping("/getKafedraById/{id}")
    public HttpEntity<?> getKafedraById(@PathVariable String id){
        return ResponseEntity.ok(kafedraService.findById(id));
    }

    @PostMapping("/createKafedra")
    public HttpEntity<?> createNewKafedra(@RequestBody KafedraDto dto){
        System.out.println(dto);
        return ResponseEntity.status(201).body(kafedraService.saveOrUpdate(dto));
    }

    @PutMapping("/updateKafedra")
    public HttpEntity<?> updateKafedra(@RequestBody KafedraDto dto){
        return ResponseEntity.status(202).body(kafedraService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteKafedra/{id}")
    public HttpEntity<?> deleteKafedra(@PathVariable String id){
        return ResponseEntity.status(204).body(kafedraService.deleteById(id));
    }

//    getStatisticsForKafedraDashboard
    @GetMapping("/getComeCountTodayStatistics")
    public HttpEntity<?> getComeCountTodayStatistics(@CurrentUser User user,@RequestParam(name = "id") String kafedraId){
        return ResponseEntity.ok(kafedraService.getComeCountTodayStatistics(kafedraId));
    }

    //getStatisticsForKafedraDashboard
    @GetMapping("/getStatisticsForKafedraDashboard")
    public HttpEntity<?> getStatisticsForKafedraDashboard(@RequestParam("index") Integer index,@RequestParam("kafedraId") String kafedraId){
        return ResponseEntity.ok(kafedraService.getStatisticsForKafedraDashboard(index,kafedraId));
    }


        @GetMapping("/change")
    public HttpEntity<?> changed(@RequestParam("userId") String userId,@RequestParam("dekanId") String dekanId){
        Optional<User> userOptional = userRepository.findById(userId);
//        Optional<Dekanat> dekanatOptional = dekanatRepository.findById(dekanatId);
        Optional<KafedraMudiri> dekanOptional = kafedraMudirRepository.findById(dekanId);

        User user = userOptional.get();
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setEnabled(true);
        user.setLogin(user.getFullName());
        user.setPassword(passwordEncoder.encode("root123"));
        Set<Role> userRoles = new HashSet<>();
        Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_KAFEDRA");
        roleOptional.ifPresent(userRoles::add);
        user.setRoles(userRoles);

        userRepository.save(user);

        KafedraMudiri dekan = dekanOptional.get();
        dekan.setUser(userOptional.get());

        kafedraMudirRepository.save(dekan);
        return ResponseEntity.ok(new ApiResponse(true,"ok"));
    }

}
