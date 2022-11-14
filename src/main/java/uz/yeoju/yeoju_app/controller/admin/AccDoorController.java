package uz.yeoju.yeoju_app.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.admin.AccDoorDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.admin.AccDoorService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/ac-door")
public class AccDoorController {

    @Autowired
    private AccDoorService accDoorService;

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(){
        return ResponseEntity.ok(accDoorService.findAll());
    }

    @GetMapping("/findById/{id}")
    public HttpEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(accDoorService.findById(id));
    }

    @PutMapping("/updated")
    public HttpEntity<?> updated(@RequestBody AccDoorDto dto){
        return ResponseEntity.ok(accDoorService.save(dto));
    }

    @GetMapping("/findByDeviceId/{deviceId}")
    public HttpEntity<?> findByDeviceId(@PathVariable Long deviceId){
        return ResponseEntity.ok(accDoorService.findByDeviceId(deviceId));
    }

    @GetMapping("/getPortsByDeviceId/{deviceId}")
    public HttpEntity<?> getPortsByDeviceId(@PathVariable Long deviceId){
        return ResponseEntity.ok(accDoorService.getPortsByDeviceId(deviceId));
    }

    @GetMapping("/getDevicesList")
    public HttpEntity<?> getDevicesList(){
        return ResponseEntity.ok(accDoorService.getDevicesList());
    }
}
