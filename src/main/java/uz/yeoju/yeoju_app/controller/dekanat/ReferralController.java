package uz.yeoju.yeoju_app.controller.dekanat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ReferralCreateDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.referral.ReferralService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/referral")
@RequiredArgsConstructor
public class ReferralController {
    private final ReferralService service;

    @GetMapping("/getAllReferrals")
    public HttpEntity<?> getAllReferrals() {
        return ResponseEntity.ok(service.findAllReferrals());
    }

    @GetMapping("/getAllCounters")
    public HttpEntity<?> getAllCounters() {
        return ResponseEntity.ok(service.getAllCounters());
    }


    @GetMapping("/getStudentReferrals/{studentId}")
    public HttpEntity<?> getStudentReferrals(@PathVariable("studentId") String studentId) {
        return ResponseEntity.ok(service.getStudentReferrals(studentId));
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody ReferralCreateDto dto){
        ApiResponse response = service.createAndUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/update")
    public HttpEntity<?> update(@RequestBody ReferralCreateDto dto){
        ApiResponse response = service.createAndUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize("hasRole('DEKAN')")
    @DeleteMapping("/deleteReferral/{id}")
    public HttpEntity<?> delete(@CurrentUser User user, @PathVariable("id") String id){
        ApiResponse delete = service.delete(user, id);
        return ResponseEntity.status(delete.isSuccess() ? 204 : 409).body(delete);
    }

}
