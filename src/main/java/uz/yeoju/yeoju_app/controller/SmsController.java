package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.enums.SmsType;
import uz.yeoju.yeoju_app.payload.sms.SmsLogDto;
import uz.yeoju.yeoju_app.repository.SmsLogRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.SmsLogService;

import java.util.Base64;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsLogRepository repository;
    private final SmsLogService service;

    @GetMapping("/all")
    public HttpEntity<?> getAllSms(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public HttpEntity<?> createSms(
            @CurrentUser User user,
            @RequestBody SmsLogDto dto
    ){
        System.out.println(dto+" ***************************** ");
        if (dto.getSmsType() == SmsType.ALL || dto.getSmsType() == SmsType.ALL_TEACHER) {
            dto.setCourse(null);
            dto.setGroupName(null);
            dto.setUserId(null);
        }
        if (dto.getSmsType() == SmsType.COURSE) {
            dto.setGroupName(null);
            dto.setUserId(null);
        }
        if (dto.getSmsType() == SmsType.GROUP) {
            dto.setCourse(null);
            dto.setUserId(null);
        }
        if (dto.getSmsType() == SmsType.STUDENT || dto.getSmsType() == SmsType.TEACHER) {
            dto.setCourse(null);
            dto.setGroupName(null);
        }

        return ResponseEntity.ok(service.saveOrUpdate(dto));
    }

    @GetMapping("/getMessages")
    public HttpEntity<?> getMessages(@CurrentUser User user){
        return ResponseEntity.ok(repository.getHistoryOfSMSForDekan(user.getId()));
    }

    @GetMapping("/getMessagesForKafedra")
    public HttpEntity<?> getMessagesForKafedra(@CurrentUser User user){
        return ResponseEntity.ok(repository.getHistoryOfSMSForDekan(user.getId()));
    }

    @GetMapping("/decode")
    public HttpEntity<?> getDecode(){
        return ResponseEntity.ok(decode("$2a$10$QXgLJeQknkx6ISHCAXCanu4CkV0GGBvM8JsTANVNeOPI3kQLcs4D6"));
    }

    public String encode(String input) {
        if (!org.springframework.util.StringUtils.hasText(input)) {
            return null;
        }
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String decode(String input) {
        if (!org.springframework.util.StringUtils.hasText(input)) {
            return null;
        }
        return new String(Base64.getDecoder().decode(input));
    }
}
