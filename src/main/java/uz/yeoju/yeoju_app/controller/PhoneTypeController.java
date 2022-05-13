package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.PhoneTypeDto;
import uz.yeoju.yeoju_app.service.useServices.PhoneTypeService;

@RestController
@RequestMapping("/v1/phoneType")
@RequiredArgsConstructor
public class PhoneTypeController {

    public final PhoneTypeService phoneTypeService;

    @GetMapping("/allPhoneTypes")
    public HttpEntity<?> allPhoneTypes(){
        return ResponseEntity.ok(phoneTypeService.findAll());
    }

    @GetMapping("/getPhoneTypeById/{id}")
    public HttpEntity<?> getPhoneTypeById(@PathVariable Long id){
        return ResponseEntity.ok(phoneTypeService.findById(id));
    }

    @PostMapping("/createPhoneType")
    public HttpEntity<?> createNewPhoneType(@RequestBody PhoneTypeDto dto){
        return ResponseEntity.status(201).body(phoneTypeService.saveOrUpdate(dto));
    }

    @PostMapping("/updatePhoneType")
    public HttpEntity<?> updatePhoneType(@RequestBody PhoneTypeDto dto){
        return ResponseEntity.status(202).body(phoneTypeService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deletePhoneType/{id}")
    public HttpEntity<?> deletePhoneType(@PathVariable Long id){
        return ResponseEntity.status(204).body(phoneTypeService.deleteById(id));
    }
}
