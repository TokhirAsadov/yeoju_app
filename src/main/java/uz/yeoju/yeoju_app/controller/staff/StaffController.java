package uz.yeoju.yeoju_app.controller.staff;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Dekan;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.staff.StaffSaveFromSection;
import uz.yeoju.yeoju_app.payload.superAdmin.StaffSaveDto;
import uz.yeoju.yeoju_app.repository.DekanRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.staff.StaffService;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService service;
    private final DekanRepository dekanRepository;

    @DeleteMapping("/deleteStaff/{staffId}")
    public HttpEntity<?> deleteStaff(@PathVariable("staffId") String staffId,@CurrentUser User user){
        ApiResponse apiResponse = service.deleteStaff(staffId, user.getId());
        return ResponseEntity.status(apiResponse.isSuccess() ? 204 : 409).body(apiResponse);
    }

    @PostMapping("/saveStaff")
    public HttpEntity<?> saveStaff(@RequestBody StaffSaveFromSection dto,@CurrentUser User user){
        ApiResponse apiResponse = service.saveStaff(dto,user.getId());
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @GetMapping("/getStaffIdWithUserId/{userId}")
    public HttpEntity<?> getStaffIdWithUserId(@PathVariable("userId") String userId,@CurrentUser User user){
        ApiResponse apiResponse = service.getStaffIdWithUserId(userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getStatisticsForRektor")
    public HttpEntity<?> getStatisticssForRektor(
            @CurrentUser User user,
            @RequestParam(name = "sectionId",required = false) String sectionId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy.MM.dd") Date date
    )
    {
        if (sectionId!=null) {
            System.out.println(sectionId+" <- 666666");
            return ResponseEntity.ok(service.getStatisticsForRektor(sectionId, date)/*new ApiResponse(false,"texnik iwlar olib borilyapdi")*/);
        }
        else {



            System.out.println(sectionId+" <- 777777");

            Dekan dekanByUserId = dekanRepository.getDekanByUserId(user.getId());


            ApiResponse dekanStatisticsForRektor = service.getDekanStatisticsForRektor(dekanByUserId.getDekanat().getId(), date);


            return ResponseEntity.ok(dekanStatisticsForRektor);
        }
    }



    @PostMapping("/uploadStaff")
    public HttpEntity<?> uploadStaff(@RequestBody StaffSaveDto dto){
        ApiResponse apiResponse = service.saveStaff(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
