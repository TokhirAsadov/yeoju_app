package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.PhoneNumber;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.PhoneNumberDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.PhoneNumberService;
import uz.yeoju.yeoju_app.service.useServices.UserService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/phoneNumber")
@RequiredArgsConstructor
public class PhoneNumberController {

    public final PhoneNumberService phoneNumberService;
    public final UserService userService;

    @GetMapping("/allPhoneNumbers")
    public HttpEntity<?> allPhoneNumbers(){
        return ResponseEntity.ok(phoneNumberService.findAll());
    }

    @GetMapping("/getPhoneNumberById/{id}")
    public HttpEntity<?> getPhoneNumberById(@PathVariable String id){
        return ResponseEntity.ok(phoneNumberService.findById(id));
    }

    @PostMapping("/createPhoneNumber")
    public HttpEntity<?> createNewPhoneNumber(@CurrentUser User user, @RequestBody PhoneNumberDto dto){
        ApiResponse apiResponse = userService.findById(user.getId());
        PhoneNumberDto phoneNumberDto = new PhoneNumberDto();
        phoneNumberDto.setId(dto.getId());
        phoneNumberDto.setUserDto(userService.generateUserDto((User) apiResponse.getObj()));
        phoneNumberDto.setPhoneNumber(dto.getPhoneNumber());
        phoneNumberDto.setPhoneType(dto.getPhoneType());
        phoneNumberDto.setActive(true);
        phoneNumberDto.setHasFacebook(dto.isHasFacebook());
        phoneNumberDto.setHasTg(dto.isHasTg());
        phoneNumberDto.setHasInstagram(dto.isHasInstagram());
        return ResponseEntity.status(201).body(phoneNumberService.saveOrUpdate(phoneNumberDto));
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
