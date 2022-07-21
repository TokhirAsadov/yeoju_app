package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekan.Dekan;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekan.DekanDto;
import uz.yeoju.yeoju_app.repository.DekanRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.DekanService;
import uz.yeoju.yeoju_app.service.useServices.FacultyService;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/dekan")
@RequiredArgsConstructor
public class DekanController {
    private final DekanRepository dekanRepository;
    private final DekanService dekanService;
    private final UserService userService;
    private final FacultyService facultyService;


    @GetMapping("/getGroupsNamesForDekanByFacultyIdAndLevel/{level}")
    public HttpEntity<?> getGroupsNamesForDekanByFacultyIdAndLevel(@CurrentUser User user,@PathVariable("level") Integer level){
        return ResponseEntity.ok(dekanRepository.getGroupsNamesForDekanByDekanIdAndLevel(user.getId(),level));
    }

    @GetMapping("/getGroupsNamesForDekanByFacultyId")
    public HttpEntity<?> getGroupsNamesForDekanByFacultyId(@RequestParam(value = "facultyId") String facultyId){
        return ResponseEntity.ok(dekanRepository.getGroupsNamesForDekanByFacultyId(facultyId));
    }

    @GetMapping("/getFacultiesFromDekanByUserId")
    public HttpEntity<?> getFacultiesFromDekanByUserId(@RequestParam(value = "userId") String userId){
        Dekan byUserId = dekanRepository.getDekanByUserId(userId);
        System.out.println(byUserId);
        System.out.println(byUserId.getFaculties());

        DekanDto dekanDto = dekanService.generateDekanDto(byUserId);
        System.out.println(dekanDto);

        return ResponseEntity.ok(new ApiResponse(true,"look",dekanDto.getFacultyDtos()));
    }

    @GetMapping("/get")
    public HttpEntity<?> get(@RequestParam("facultyId") String facultyId,
                             @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                             @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {

        return ResponseEntity.ok(dekanRepository.getCourseStatisticsForDekan(facultyId,startTime,endTime));
    }
    @GetMapping("/getGroupStatistics")
    public HttpEntity<?> getGroupStatistics(@RequestParam("facultyId") String facultyId,
                             @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                             @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {

        return ResponseEntity.ok(dekanRepository.getGroupsStatisticForDekan(facultyId,startTime,endTime));
    }
}
