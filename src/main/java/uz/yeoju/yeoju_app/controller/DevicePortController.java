package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.admin.BuildingDto;
import uz.yeoju.yeoju_app.payload.admin.DevicePortDto;
import uz.yeoju.yeoju_app.service.useServices.DevicePortService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/device-port")
@RequiredArgsConstructor
public class DevicePortController {

    public final DevicePortService portService;

    @GetMapping("/device-ports")
    public HttpEntity<?> allDevicePorts(){
        return ResponseEntity.ok(portService.findAll());
    }

    @GetMapping("/getDevicePortById/{id}")
    public HttpEntity<?> getDevicePortById(@PathVariable String id){
        return ResponseEntity.ok(portService.findById(id));
    }

    @PostMapping("/createDevicePort")
    public HttpEntity<?> createDevicePort(@RequestBody DevicePortDto dto){
        return ResponseEntity.status(201).body(portService.saveOrUpdate(dto));
    }

    @PutMapping("/updateDevicePort")
    public HttpEntity<?> updateDevicePort(@RequestBody DevicePortDto dto){
        return ResponseEntity.status(202).body(portService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteDevicePort/{id}")
    public HttpEntity<?> deleteDevicePort(@PathVariable String id){
        return ResponseEntity.status(204).body(portService.deleteById(id));
    }

}
