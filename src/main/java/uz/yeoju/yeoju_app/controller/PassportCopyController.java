package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.PassportCopyDto;
import uz.yeoju.yeoju_app.service.useServices.PassportCopyService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/passportCopy")
@RequiredArgsConstructor
public class PassportCopyController {
    public final PassportCopyService passportCopyService;

    @GetMapping("/allPassportCopies")
    public HttpEntity<?> getAllPassportCopies(){
        return ResponseEntity.ok(passportCopyService.findAll());
    }

    @GetMapping("/getPassportCopyById/{id}")
    public HttpEntity<?> getPersonalSheetByIdOrUserIdOrActive(
            @PathVariable String id,
            @RequestParam String user_id,
            @RequestParam boolean active)
    {
        return id != null ? ResponseEntity.ok(passportCopyService.findById(id))
                :
                user_id != null ? ResponseEntity.ok(passportCopyService.findPassportCopiesByUserId(user_id))
                :
                ResponseEntity.ok(passportCopyService.findPassportCopiesByActive(active));
    }

    @PostMapping("/createPassportCopy")
    public HttpEntity<?> createNewPassportCopy(@RequestBody PassportCopyDto dto){
        return ResponseEntity.status(201).body(passportCopyService.saveOrUpdate(dto));
    }

    @PostMapping("/updatePassportCopy")
    public HttpEntity<?> updatePassportCopy(@RequestBody PassportCopyDto dto){
        return ResponseEntity.status(202).body(passportCopyService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deletePassportCopy/{id}")
    public HttpEntity<?> deletePassportCopy(@PathVariable String id){
        return ResponseEntity.status(204).body(passportCopyService.deleteById(id));
    }
}
