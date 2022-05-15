package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.PhoneNumberDto;
import uz.yeoju.yeoju_app.service.useServices.PhoneNumberService;

@RestController
@RequestMapping("/v1/phoneNumber")
@RequiredArgsConstructor
public class PhoneNumberController {

    public final PhoneNumberService phoneNumberService;

    @GetMapping("/allPhoneNumbers")
    public HttpEntity<?> allPhoneNumbers(){
        return ResponseEntity.ok(phoneNumberService.findAll());
    }

    @GetMapping("/getPhoneNumberById/{id}")
    public HttpEntity<?> getPhoneNumberById(@PathVariable String id){
        return ResponseEntity.ok(phoneNumberService.findById(id));
    }

    @PostMapping("/createPhoneNumber")
    public HttpEntity<?> createNewPhoneNumber(@RequestBody PhoneNumberDto dto){
        return ResponseEntity.status(201).body(phoneNumberService.saveOrUpdate(dto));
    }

    @PostMapping("/updatePhoneNumber")
    public HttpEntity<?> updatePhoneNumber(@RequestBody PhoneNumberDto dto){
        return ResponseEntity.status(202).body(phoneNumberService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deletePhoneNumber/{id}")
    public HttpEntity<?> deletePhoneNumber(@PathVariable String id){
        return ResponseEntity.status(204).body(phoneNumberService.deleteById(id));
    }
}
