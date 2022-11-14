package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraDto;
import uz.yeoju.yeoju_app.repository.KafedraRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.KafedraService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/kafedra")
@RequiredArgsConstructor
public class KafedraController {

    public final KafedraService kafedraService;
    public final KafedraRepository kafedraRepository;


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

    @GetMapping("/getComeCountTodayStatistics/{id}")
    public HttpEntity<?> getComeCountTodayStatistics(@PathVariable("id") String id){
        return ResponseEntity.ok(kafedraService.getComeCountTodayStatistics(id));
    }

    @GetMapping("/getStatisticsForKafedraDashboard")
    public HttpEntity<?> getStatisticsForKafedraDashboard(@RequestParam("index") Integer index,@RequestParam("kafedraId") String kafedraId){
        return ResponseEntity.ok(kafedraService.getStatisticsForKafedraDashboard(index,kafedraId));
    }

}
